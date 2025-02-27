import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getProperties } from 'app/entities/crm/property/property.reducer';
import { getEntities as getCustomers } from 'app/entities/crm/customer/customer.reducer';
import { getEntities as getOpportunities } from 'app/entities/crm/opportunity/opportunity.reducer';
import { ContractStatusEnum } from 'app/shared/model/enumerations/contract-status-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './rental.reducer';

export const RentalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const properties = useAppSelector(state => state.crm.property.entities);
  const customers = useAppSelector(state => state.crm.customer.entities);
  const opportunities = useAppSelector(state => state.crm.opportunity.entities);
  const rentalEntity = useAppSelector(state => state.crm.rental.entity);
  const loading = useAppSelector(state => state.crm.rental.loading);
  const updating = useAppSelector(state => state.crm.rental.updating);
  const updateSuccess = useAppSelector(state => state.crm.rental.updateSuccess);
  const contractStatusEnumValues = Object.keys(ContractStatusEnum);

  const handleClose = () => {
    navigate(`/crm/rental${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProperties({}));
    dispatch(getCustomers({}));
    dispatch(getOpportunities({}));
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
    if (values.createdBy !== undefined && typeof values.createdBy !== 'number') {
      values.createdBy = Number(values.createdBy);
    }

    const entity = {
      ...rentalEntity,
      ...values,
      property: properties.find(it => it.id.toString() === values.property?.toString()),
      customer: customers.find(it => it.id.toString() === values.customer?.toString()),
      opportunity: opportunities.find(it => it.id.toString() === values.opportunity?.toString()),
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
          property: rentalEntity?.property?.id,
          customer: rentalEntity?.customer?.id,
          opportunity: rentalEntity?.opportunity?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmApp.crmRental.home.createOrEditLabel" data-cy="RentalCreateUpdateHeading">
            <Translate contentKey="crmApp.crmRental.home.createOrEditLabel">Create or edit a Rental</Translate>
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
                label={translate('crmApp.crmRental.startDate')}
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
                label={translate('crmApp.crmRental.endDate')}
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
                label={translate('crmApp.crmRental.monthlyRent')}
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
                label={translate('crmApp.crmRental.securityDeposit')}
                id="rental-securityDeposit"
                name="securityDeposit"
                data-cy="securityDeposit"
                type="text"
              />
              <ValidatedField
                label={translate('crmApp.crmRental.contractStatus')}
                id="rental-contractStatus"
                name="contractStatus"
                data-cy="contractStatus"
                type="select"
              >
                {contractStatusEnumValues.map(contractStatusEnum => (
                  <option value={contractStatusEnum} key={contractStatusEnum}>
                    {translate(`crmApp.ContractStatusEnum.${contractStatusEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('crmApp.crmRental.createdBy')}
                id="rental-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                id="rental-property"
                name="property"
                data-cy="property"
                label={translate('crmApp.crmRental.property')}
                type="select"
              >
                <option value="" key="0" />
                {properties
                  ? properties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.codeName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="rental-customer"
                name="customer"
                data-cy="customer"
                label={translate('crmApp.crmRental.customer')}
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
                id="rental-opportunity"
                name="opportunity"
                data-cy="opportunity"
                label={translate('crmApp.crmRental.opportunity')}
                type="select"
              >
                <option value="" key="0" />
                {opportunities
                  ? opportunities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/crm/rental" replace color="info">
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
