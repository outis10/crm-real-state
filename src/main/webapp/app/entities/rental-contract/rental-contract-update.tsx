import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getProperties } from 'app/entities/property/property.reducer';
import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { ContractStatusEnum } from 'app/shared/model/enumerations/contract-status-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './rental-contract.reducer';

export const RentalContractUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const properties = useAppSelector(state => state.property.entities);
  const customers = useAppSelector(state => state.customer.entities);
  const rentalContractEntity = useAppSelector(state => state.rentalContract.entity);
  const loading = useAppSelector(state => state.rentalContract.loading);
  const updating = useAppSelector(state => state.rentalContract.updating);
  const updateSuccess = useAppSelector(state => state.rentalContract.updateSuccess);
  const contractStatusEnumValues = Object.keys(ContractStatusEnum);

  const handleClose = () => {
    navigate(`/rental-contract${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProperties({}));
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
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);
    if (values.monthlyRent !== undefined && typeof values.monthlyRent !== 'number') {
      values.monthlyRent = Number(values.monthlyRent);
    }
    if (values.securityDeposit !== undefined && typeof values.securityDeposit !== 'number') {
      values.securityDeposit = Number(values.securityDeposit);
    }

    const entity = {
      ...rentalContractEntity,
      ...values,
      property: properties.find(it => it.id.toString() === values.property?.toString()),
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
          startDate: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
        }
      : {
          status: 'ACTIVE',
          ...rentalContractEntity,
          startDate: convertDateTimeFromServer(rentalContractEntity.startDate),
          endDate: convertDateTimeFromServer(rentalContractEntity.endDate),
          property: rentalContractEntity?.property?.id,
          customer: rentalContractEntity?.customer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmRealStateApp.rentalContract.home.createOrEditLabel" data-cy="RentalContractCreateUpdateHeading">
            <Translate contentKey="crmRealStateApp.rentalContract.home.createOrEditLabel">Create or edit a RentalContract</Translate>
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
                  id="rental-contract-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('crmRealStateApp.rentalContract.startDate')}
                id="rental-contract-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.rentalContract.endDate')}
                id="rental-contract-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.rentalContract.monthlyRent')}
                id="rental-contract-monthlyRent"
                name="monthlyRent"
                data-cy="monthlyRent"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.rentalContract.securityDeposit')}
                id="rental-contract-securityDeposit"
                name="securityDeposit"
                data-cy="securityDeposit"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.rentalContract.status')}
                id="rental-contract-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {contractStatusEnumValues.map(contractStatusEnum => (
                  <option value={contractStatusEnum} key={contractStatusEnum}>
                    {translate(`crmRealStateApp.ContractStatusEnum.${contractStatusEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="rental-contract-property"
                name="property"
                data-cy="property"
                label={translate('crmRealStateApp.rentalContract.property')}
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
              <ValidatedField
                id="rental-contract-customer"
                name="customer"
                data-cy="customer"
                label={translate('crmRealStateApp.rentalContract.customer')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/rental-contract" replace color="info">
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

export default RentalContractUpdate;
