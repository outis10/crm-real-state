import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ContractStatusEnum } from 'app/shared/model/enumerations/contract-status-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './rental.reducer';

export const RentalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rentalEntity = useAppSelector(state => state.propertymanagement.rental.entity);
  const loading = useAppSelector(state => state.propertymanagement.rental.loading);
  const updating = useAppSelector(state => state.propertymanagement.rental.updating);
  const updateSuccess = useAppSelector(state => state.propertymanagement.rental.updateSuccess);
  const contractStatusEnumValues = Object.keys(ContractStatusEnum);

  const handleClose = () => {
    navigate(`/propertymanagement/rental${location.search}`);
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
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);
    if (values.monthlyRent !== undefined && typeof values.monthlyRent !== 'number') {
      values.monthlyRent = Number(values.monthlyRent);
    }
    if (values.securityDeposit !== undefined && typeof values.securityDeposit !== 'number') {
      values.securityDeposit = Number(values.securityDeposit);
    }

    const entity = {
      ...rentalEntity,
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
          startDate: displayDefaultDateTime(),
          endDate: displayDefaultDateTime(),
        }
      : {
          contractStatus: 'ACTIVE',
          ...rentalEntity,
          startDate: convertDateTimeFromServer(rentalEntity.startDate),
          endDate: convertDateTimeFromServer(rentalEntity.endDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="propertyManagementApp.propertyManagementRental.home.createOrEditLabel" data-cy="RentalCreateUpdateHeading">
            <Translate contentKey="propertyManagementApp.propertyManagementRental.home.createOrEditLabel">
              Create or edit a Rental
            </Translate>
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
                  id="rental-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementRental.propertyId')}
                id="rental-propertyId"
                name="propertyId"
                data-cy="propertyId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementRental.customerId')}
                id="rental-customerId"
                name="customerId"
                data-cy="customerId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementRental.oportunityId')}
                id="rental-oportunityId"
                name="oportunityId"
                data-cy="oportunityId"
                type="text"
              />
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementRental.startDate')}
                id="rental-startDate"
                name="startDate"
                data-cy="startDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementRental.endDate')}
                id="rental-endDate"
                name="endDate"
                data-cy="endDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementRental.monthlyRent')}
                id="rental-monthlyRent"
                name="monthlyRent"
                data-cy="monthlyRent"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementRental.securityDeposit')}
                id="rental-securityDeposit"
                name="securityDeposit"
                data-cy="securityDeposit"
                type="text"
              />
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementRental.contractStatus')}
                id="rental-contractStatus"
                name="contractStatus"
                data-cy="contractStatus"
                type="select"
              >
                {contractStatusEnumValues.map(contractStatusEnum => (
                  <option value={contractStatusEnum} key={contractStatusEnum}>
                    {translate(`propertyManagementApp.ContractStatusEnum.${contractStatusEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/propertymanagement/rental" replace color="info">
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

export default RentalUpdate;
