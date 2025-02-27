import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getRentals } from 'app/entities/crm/rental/rental.reducer';
import { PaymentMethodEnum } from 'app/shared/model/enumerations/payment-method-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './payment.reducer';

export const PaymentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rentals = useAppSelector(state => state.crm.rental.entities);
  const paymentEntity = useAppSelector(state => state.crm.payment.entity);
  const loading = useAppSelector(state => state.crm.payment.loading);
  const updating = useAppSelector(state => state.crm.payment.updating);
  const updateSuccess = useAppSelector(state => state.crm.payment.updateSuccess);
  const paymentMethodEnumValues = Object.keys(PaymentMethodEnum);

  const handleClose = () => {
    navigate(`/crm/payment${location.search}`);
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
    values.paymentDate = convertDateTimeToServer(values.paymentDate);
    if (values.createdBy !== undefined && typeof values.createdBy !== 'number') {
      values.createdBy = Number(values.createdBy);
    }

    const entity = {
      ...paymentEntity,
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
          paymentDate: displayDefaultDateTime(),
        }
      : {
          paymentMethod: 'TRANSFER',
          ...paymentEntity,
          paymentDate: convertDateTimeFromServer(paymentEntity.paymentDate),
          rental: paymentEntity?.rental?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmApp.crmPayment.home.createOrEditLabel" data-cy="PaymentCreateUpdateHeading">
            <Translate contentKey="crmApp.crmPayment.home.createOrEditLabel">Create or edit a Payment</Translate>
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
                  id="payment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('crmApp.crmPayment.amount')}
                id="payment-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmApp.crmPayment.paymentDate')}
                id="payment-paymentDate"
                name="paymentDate"
                data-cy="paymentDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmApp.crmPayment.paymentMethod')}
                id="payment-paymentMethod"
                name="paymentMethod"
                data-cy="paymentMethod"
                type="select"
              >
                {paymentMethodEnumValues.map(paymentMethodEnum => (
                  <option value={paymentMethodEnum} key={paymentMethodEnum}>
                    {translate(`crmApp.PaymentMethodEnum.${paymentMethodEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('crmApp.crmPayment.reference')}
                id="payment-reference"
                name="reference"
                data-cy="reference"
                type="text"
              />
              <ValidatedField
                label={translate('crmApp.crmPayment.createdBy')}
                id="payment-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
              />
              <ValidatedField
                id="payment-rental"
                name="rental"
                data-cy="rental"
                label={translate('crmApp.crmPayment.rental')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/crm/payment" replace color="info">
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

export default PaymentUpdate;
