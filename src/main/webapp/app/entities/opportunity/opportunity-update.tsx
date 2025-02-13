import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { getEntities as getProperties } from 'app/entities/property/property.reducer';
import { OpportunityStageEnum } from 'app/shared/model/enumerations/opportunity-stage-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './opportunity.reducer';

export const OpportunityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customers = useAppSelector(state => state.customer.entities);
  const properties = useAppSelector(state => state.property.entities);
  const opportunityEntity = useAppSelector(state => state.opportunity.entity);
  const loading = useAppSelector(state => state.opportunity.loading);
  const updating = useAppSelector(state => state.opportunity.updating);
  const updateSuccess = useAppSelector(state => state.opportunity.updateSuccess);
  const opportunityStageEnumValues = Object.keys(OpportunityStageEnum);

  const handleClose = () => {
    navigate(`/opportunity${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCustomers({}));
    dispatch(getProperties({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.budget !== undefined && typeof values.budget !== 'number') {
      values.budget = Number(values.budget);
    }
    if (values.amount !== undefined && typeof values.amount !== 'number') {
      values.amount = Number(values.amount);
    }
    if (values.probability !== undefined && typeof values.probability !== 'number') {
      values.probability = Number(values.probability);
    }
    values.createdAt = convertDateTimeToServer(values.createdAt);
    values.modifiedAt = convertDateTimeToServer(values.modifiedAt);
    values.closedAt = convertDateTimeToServer(values.closedAt);

    const entity = {
      ...opportunityEntity,
      ...values,
      customer: customers.find(it => it.id.toString() === values.customer?.toString()),
      property: properties.find(it => it.id.toString() === values.property?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createdAt: displayDefaultDateTime(),
          modifiedAt: displayDefaultDateTime(),
          closedAt: displayDefaultDateTime(),
        }
      : {
          stage: 'PROSPECTING',
          ...opportunityEntity,
          createdAt: convertDateTimeFromServer(opportunityEntity.createdAt),
          modifiedAt: convertDateTimeFromServer(opportunityEntity.modifiedAt),
          closedAt: convertDateTimeFromServer(opportunityEntity.closedAt),
          customer: opportunityEntity?.customer?.id,
          property: opportunityEntity?.property?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmRealStateApp.opportunity.home.createOrEditLabel" data-cy="OpportunityCreateUpdateHeading">
            <Translate contentKey="crmRealStateApp.opportunity.home.createOrEditLabel">Create or edit a Opportunity</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="opportunity-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('crmRealStateApp.opportunity.name')}
                id="opportunity-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.opportunity.budget')}
                id="opportunity-budget"
                name="budget"
                data-cy="budget"
                type="text"
                validate={{
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.opportunity.amount')}
                id="opportunity-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.opportunity.probability')}
                id="opportunity-probability"
                name="probability"
                data-cy="probability"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  max: { value: 100, message: translate('entity.validation.max', { max: 100 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.opportunity.expectedCloseDate')}
                id="opportunity-expectedCloseDate"
                name="expectedCloseDate"
                data-cy="expectedCloseDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.opportunity.stage')}
                id="opportunity-stage"
                name="stage"
                data-cy="stage"
                type="select"
              >
                {opportunityStageEnumValues.map(opportunityStageEnum => (
                  <option value={opportunityStageEnum} key={opportunityStageEnum}>
                    {translate(`crmRealStateApp.OpportunityStageEnum.${opportunityStageEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('crmRealStateApp.opportunity.description')}
                id="opportunity-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('crmRealStateApp.opportunity.createdAt')}
                id="opportunity-createdAt"
                name="createdAt"
                data-cy="createdAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.opportunity.modifiedAt')}
                id="opportunity-modifiedAt"
                name="modifiedAt"
                data-cy="modifiedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('crmRealStateApp.opportunity.closedAt')}
                id="opportunity-closedAt"
                name="closedAt"
                data-cy="closedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="opportunity-customer"
                name="customer"
                data-cy="customer"
                label={translate('crmRealStateApp.opportunity.customer')}
                type="select"
                required
              >
                <option value="" key="0" />
                {customers
                  ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.email}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="opportunity-property"
                name="property"
                data-cy="property"
                label={translate('crmRealStateApp.opportunity.property')}
                type="select"
              >
                <option value="" key="0" />
                {properties
                  ? properties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/opportunity" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OpportunityUpdate;
