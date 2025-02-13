import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { getEntities as getRentalContracts } from 'app/entities/rental-contract/rental-contract.reducer';
import { ChargeTypeEnum } from 'app/shared/model/enumerations/charge-type-enum.model';
import { ChargeStatusEnum } from 'app/shared/model/enumerations/charge-status-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './charge.reducer';

export const ChargeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customers = useAppSelector(state => state.customer.entities);
  const rentalContracts = useAppSelector(state => state.rentalContract.entities);
  const chargeEntity = useAppSelector(state => state.charge.entity);
  const loading = useAppSelector(state => state.charge.loading);
  const updating = useAppSelector(state => state.charge.updating);
  const updateSuccess = useAppSelector(state => state.charge.updateSuccess);
  const chargeTypeEnumValues = Object.keys(ChargeTypeEnum);
  const chargeStatusEnumValues = Object.keys(ChargeStatusEnum);

  const handleClose = () => {
    navigate(`/charge${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCustomers({}));
    dispatch(getRentalContracts({}));
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
    if (values.amount !== undefined && typeof values.amount !== 'number') {
      values.amount = Number(values.amount);
    }
    values.dueDate = convertDateTimeToServer(values.dueDate);

    const entity = {
      ...chargeEntity,
      ...values,
      customer: customers.find(it => it.id.toString() === values.customer?.toString()),
      rentalContract: rentalContracts.find(it => it.id.toString() === values.rentalContract?.toString()),
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
          dueDate: displayDefaultDateTime(),
        }
      : {
          type: 'LEASE',
          status: 'PENDING',
          ...chargeEntity,
          dueDate: convertDateTimeFromServer(chargeEntity.dueDate),
          customer: chargeEntity?.customer?.id,
          rentalContract: chargeEntity?.rentalContract?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmRealStateApp.charge.home.createOrEditLabel" data-cy="ChargeCreateUpdateHeading">
            <Translate contentKey="crmRealStateApp.charge.home.createOrEditLabel">Create or edit a Charge</Translate>
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
                  id="charge-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('crmRealStateApp.charge.type')} id="charge-type" name="type" data-cy="type" type="select">
                {chargeTypeEnumValues.map(chargeTypeEnum => (
                  <option value={chargeTypeEnum} key={chargeTypeEnum}>
                    {translate(`crmRealStateApp.ChargeTypeEnum.${chargeTypeEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('crmRealStateApp.charge.amount')}
                id="charge-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.charge.dueDate')}
                id="charge-dueDate"
                name="dueDate"
                data-cy="dueDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.charge.status')}
                id="charge-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {chargeStatusEnumValues.map(chargeStatusEnum => (
                  <option value={chargeStatusEnum} key={chargeStatusEnum}>
                    {translate(`crmRealStateApp.ChargeStatusEnum.${chargeStatusEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="charge-customer"
                name="customer"
                data-cy="customer"
                label={translate('crmRealStateApp.charge.customer')}
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
                id="charge-rentalContract"
                name="rentalContract"
                data-cy="rentalContract"
                label={translate('crmRealStateApp.charge.rentalContract')}
                type="select"
                required
              >
                <option value="" key="0" />
                {rentalContracts
                  ? rentalContracts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/charge" replace color="info">
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

export default ChargeUpdate;
