import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getRentals } from 'app/entities/crm/rental/rental.reducer';
import { ChargeTypeEnum } from 'app/shared/model/enumerations/charge-type-enum.model';
import { ChargeStatusEnum } from 'app/shared/model/enumerations/charge-status-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './charge.reducer';

export const ChargeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rentals = useAppSelector(state => state.crm.rental.entities);
  const chargeEntity = useAppSelector(state => state.crm.charge.entity);
  const loading = useAppSelector(state => state.crm.charge.loading);
  const updating = useAppSelector(state => state.crm.charge.updating);
  const updateSuccess = useAppSelector(state => state.crm.charge.updateSuccess);
  const chargeTypeEnumValues = Object.keys(ChargeTypeEnum);
  const chargeStatusEnumValues = Object.keys(ChargeStatusEnum);

  const handleClose = () => {
    navigate(`/crm/charge${location.search}`);
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
    if (values.createdBy !== undefined && typeof values.createdBy !== 'number') {
      values.createdBy = Number(values.createdBy);
    }

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
          <h2 id="crmApp.crmCharge.home.createOrEditLabel" data-cy="ChargeCreateUpdateHeading">
            <Translate contentKey="crmApp.crmCharge.home.createOrEditLabel">Create or edit a Charge</Translate>
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
              <ValidatedField label={translate('crmApp.crmCharge.type')} id="charge-type" name="type" data-cy="type" type="select">
                {chargeTypeEnumValues.map(chargeTypeEnum => (
                  <option value={chargeTypeEnum} key={chargeTypeEnum}>
                    {translate(`crmApp.ChargeTypeEnum.${chargeTypeEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('crmApp.crmCharge.amount')}
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
                label={translate('crmApp.crmCharge.dueDate')}
                id="charge-dueDate"
                name="dueDate"
                data-cy="dueDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('crmApp.crmCharge.status')} id="charge-status" name="status" data-cy="status" type="select">
                {chargeStatusEnumValues.map(chargeStatusEnum => (
                  <option value={chargeStatusEnum} key={chargeStatusEnum}>
                    {translate(`crmApp.ChargeStatusEnum.${chargeStatusEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('crmApp.crmCharge.createdBy')}
                id="charge-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                id="charge-rental"
                name="rental"
                data-cy="rental"
                label={translate('crmApp.crmCharge.rental')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/crm/charge" replace color="info">
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
