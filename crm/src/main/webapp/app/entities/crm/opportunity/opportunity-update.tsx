import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCustomers } from 'app/entities/crm/customer/customer.reducer';
import { OpportunityStageEnum } from 'app/shared/model/enumerations/opportunity-stage-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './opportunity.reducer';

export const OpportunityUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customers = useAppSelector(state => state.crm.customer.entities);
  const opportunityEntity = useAppSelector(state => state.crm.opportunity.entity);
  const loading = useAppSelector(state => state.crm.opportunity.loading);
  const updating = useAppSelector(state => state.crm.opportunity.updating);
  const updateSuccess = useAppSelector(state => state.crm.opportunity.updateSuccess);
  const opportunityStageEnumValues = Object.keys(OpportunityStageEnum);

  const handleClose = () => {
    navigate(`/crm/opportunity${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCustomers({}));
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
    if (values.createdBy !== undefined && typeof values.createdBy !== 'number') {
      values.createdBy = Number(values.createdBy);
    }

    const entity = {
      ...opportunityEntity,
      ...values,
      customer: customers.find(it => it.id.toString() === values.customer?.toString()),
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
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmApp.crmOpportunity.home.createOrEditLabel" data-cy="OpportunityCreateUpdateHeading">
            <Translate contentKey="crmApp.crmOpportunity.home.createOrEditLabel">Create or edit a Opportunity</Translate>
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
                label={translate('crmApp.crmOpportunity.name')}
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
                label={translate('crmApp.crmOpportunity.budget')}
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
                label={translate('crmApp.crmOpportunity.amount')}
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
                label={translate('crmApp.crmOpportunity.probability')}
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
                label={translate('crmApp.crmOpportunity.expectedCloseDate')}
                id="opportunity-expectedCloseDate"
                name="expectedCloseDate"
                data-cy="expectedCloseDate"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmApp.crmOpportunity.stage')}
                id="opportunity-stage"
                name="stage"
                data-cy="stage"
                type="select"
              >
                {opportunityStageEnumValues.map(opportunityStageEnum => (
                  <option value={opportunityStageEnum} key={opportunityStageEnum}>
                    {translate(`crmApp.OpportunityStageEnum.${opportunityStageEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('crmApp.crmOpportunity.description')}
                id="opportunity-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('crmApp.crmOpportunity.createdAt')}
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
                label={translate('crmApp.crmOpportunity.modifiedAt')}
                id="opportunity-modifiedAt"
                name="modifiedAt"
                data-cy="modifiedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('crmApp.crmOpportunity.closedAt')}
                id="opportunity-closedAt"
                name="closedAt"
                data-cy="closedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('crmApp.crmOpportunity.createdBy')}
                id="opportunity-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                id="opportunity-customer"
                name="customer"
                data-cy="customer"
                label={translate('crmApp.crmOpportunity.customer')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/crm/opportunity" replace color="info">
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
