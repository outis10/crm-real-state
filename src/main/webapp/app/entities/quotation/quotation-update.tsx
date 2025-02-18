import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { getEntities as getProperties } from 'app/entities/property/property.reducer';
import { createEntity, getEntity, reset, updateEntity } from './quotation.reducer';

export const QuotationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customers = useAppSelector(state => state.customer.entities);
  const properties = useAppSelector(state => state.property.entities);
  const quotationEntity = useAppSelector(state => state.quotation.entity);
  const loading = useAppSelector(state => state.quotation.loading);
  const updating = useAppSelector(state => state.quotation.updating);
  const updateSuccess = useAppSelector(state => state.quotation.updateSuccess);

  const handleClose = () => {
    navigate(`/quotation${location.search}`);
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
    if (values.finalPrice !== undefined && typeof values.finalPrice !== 'number') {
      values.finalPrice = Number(values.finalPrice);
    }
    values.validityDate = convertDateTimeToServer(values.validityDate);

    const entity = {
      ...quotationEntity,
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
          validityDate: displayDefaultDateTime(),
        }
      : {
          ...quotationEntity,
          validityDate: convertDateTimeFromServer(quotationEntity.validityDate),
          customer: quotationEntity?.customer?.id,
          property: quotationEntity?.property?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmRealStateApp.quotation.home.createOrEditLabel" data-cy="QuotationCreateUpdateHeading">
            <Translate contentKey="crmRealStateApp.quotation.home.createOrEditLabel">Create or edit a Quotation</Translate>
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
                  id="quotation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('crmRealStateApp.quotation.finalPrice')}
                id="quotation-finalPrice"
                name="finalPrice"
                data-cy="finalPrice"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.quotation.validityDate')}
                id="quotation-validityDate"
                name="validityDate"
                data-cy="validityDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.quotation.comments')}
                id="quotation-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <ValidatedField
                id="quotation-customer"
                name="customer"
                data-cy="customer"
                label={translate('crmRealStateApp.quotation.customer')}
                type="select"
              >
                <option value="" key="0" />
                {customers
                  ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="quotation-property"
                name="property"
                data-cy="property"
                label={translate('crmRealStateApp.quotation.property')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/quotation" replace color="info">
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

export default QuotationUpdate;
