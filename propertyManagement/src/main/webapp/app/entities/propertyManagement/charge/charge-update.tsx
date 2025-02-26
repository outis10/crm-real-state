import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getRentals } from 'app/entities/propertyManagement/rental/rental.reducer';
import { ChargeTypeEnum } from 'app/shared/model/enumerations/charge-type-enum.model';
import { ChargeStatusEnum } from 'app/shared/model/enumerations/charge-status-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './charge.reducer';

export const ChargeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rentals = useAppSelector(state => state.propertymanagement.rental.entities);
  const chargeEntity = useAppSelector(state => state.propertymanagement.charge.entity);
  const loading = useAppSelector(state => state.propertymanagement.charge.loading);
  const updating = useAppSelector(state => state.propertymanagement.charge.updating);
  const updateSuccess = useAppSelector(state => state.propertymanagement.charge.updateSuccess);
  const chargeTypeEnumValues = Object.keys(ChargeTypeEnum);
  const chargeStatusEnumValues = Object.keys(ChargeStatusEnum);

  const handleClose = () => {
    navigate(`/propertymanagement/charge${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRentals({}));
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
      rental: rentals.find(it => it.id.toString() === values.rental?.toString()),
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
          rental: chargeEntity?.rental?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="propertyManagementApp.propertyManagementCharge.home.createOrEditLabel" data-cy="ChargeCreateUpdateHeading">
            <Translate contentKey="propertyManagementApp.propertyManagementCharge.home.createOrEditLabel">
              Create or edit a Charge
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
                  id="charge-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementCharge.type')}
                id="charge-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {chargeTypeEnumValues.map(chargeTypeEnum => (
                  <option value={chargeTypeEnum} key={chargeTypeEnum}>
                    {translate(`propertyManagementApp.ChargeTypeEnum.${chargeTypeEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementCharge.amount')}
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
                label={translate('propertyManagementApp.propertyManagementCharge.dueDate')}
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
                label={translate('propertyManagementApp.propertyManagementCharge.status')}
                id="charge-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {chargeStatusEnumValues.map(chargeStatusEnum => (
                  <option value={chargeStatusEnum} key={chargeStatusEnum}>
                    {translate(`propertyManagementApp.ChargeStatusEnum.${chargeStatusEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="charge-rental"
                name="rental"
                data-cy="rental"
                label={translate('propertyManagementApp.propertyManagementCharge.rental')}
                type="select"
                required
              >
                <option value="" key="0" />
                {rentals
                  ? rentals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/propertymanagement/charge" replace color="info">
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
