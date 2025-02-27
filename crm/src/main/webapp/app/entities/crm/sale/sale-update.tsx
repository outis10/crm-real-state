import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { SaleStatusEnum } from 'app/shared/model/enumerations/sale-status-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './sale.reducer';

export const SaleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const saleEntity = useAppSelector(state => state.crm.sale.entity);
  const loading = useAppSelector(state => state.crm.sale.loading);
  const updating = useAppSelector(state => state.crm.sale.updating);
  const updateSuccess = useAppSelector(state => state.crm.sale.updateSuccess);
  const saleStatusEnumValues = Object.keys(SaleStatusEnum);

  const handleClose = () => {
    navigate(`/crm/sale${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.propertyId !== undefined && typeof values.propertyId !== 'number') {
      values.propertyId = Number(values.propertyId);
    }
    if (values.customerId !== undefined && typeof values.customerId !== 'number') {
      values.customerId = Number(values.customerId);
    }
    if (values.oportunityId !== undefined && typeof values.oportunityId !== 'number') {
      values.oportunityId = Number(values.oportunityId);
    }
    values.saleDate = convertDateTimeToServer(values.saleDate);
    if (values.totalAmount !== undefined && typeof values.totalAmount !== 'number') {
      values.totalAmount = Number(values.totalAmount);
    }

    const entity = {
      ...saleEntity,
      ...values,
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
          saleDate: displayDefaultDateTime(),
        }
      : {
          status: 'PENDING',
          ...saleEntity,
          saleDate: convertDateTimeFromServer(saleEntity.saleDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmApp.crmSale.home.createOrEditLabel" data-cy="SaleCreateUpdateHeading">
            <Translate contentKey="crmApp.crmSale.home.createOrEditLabel">Create or edit a Sale</Translate>
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
                  id="sale-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('crmApp.crmSale.propertyId')}
                id="sale-propertyId"
                name="propertyId"
                data-cy="propertyId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmApp.crmSale.customerId')}
                id="sale-customerId"
                name="customerId"
                data-cy="customerId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmApp.crmSale.oportunityId')}
                id="sale-oportunityId"
                name="oportunityId"
                data-cy="oportunityId"
                type="text"
              />
              <ValidatedField
                label={translate('crmApp.crmSale.saleDate')}
                id="sale-saleDate"
                name="saleDate"
                data-cy="saleDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmApp.crmSale.totalAmount')}
                id="sale-totalAmount"
                name="totalAmount"
                data-cy="totalAmount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField label={translate('crmApp.crmSale.status')} id="sale-status" name="status" data-cy="status" type="select">
                {saleStatusEnumValues.map(saleStatusEnum => (
                  <option value={saleStatusEnum} key={saleStatusEnum}>
                    {translate(`crmApp.SaleStatusEnum.${saleStatusEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/crm/sale" replace color="info">
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

export default SaleUpdate;
