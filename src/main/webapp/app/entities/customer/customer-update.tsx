import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './customer.reducer';

export const CustomerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customerEntity = useAppSelector(state => state.customer.entity);
  const loading = useAppSelector(state => state.customer.loading);
  const updating = useAppSelector(state => state.customer.updating);
  const updateSuccess = useAppSelector(state => state.customer.updateSuccess);

  const handleClose = () => {
    navigate(`/customer${location.search}`);
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
    if (values.budget !== undefined && typeof values.budget !== 'number') {
      values.budget = Number(values.budget);
    }
    if (values.rentalBudget !== undefined && typeof values.rentalBudget !== 'number') {
      values.rentalBudget = Number(values.rentalBudget);
    }

    const entity = {
      ...customerEntity,
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
      ? {}
      : {
          ...customerEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmRealStateApp.customer.home.createOrEditLabel" data-cy="CustomerCreateUpdateHeading">
            <Translate contentKey="crmRealStateApp.customer.home.createOrEditLabel">Create or edit a Customer</Translate>
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
                  id="customer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('crmRealStateApp.customer.firstName')}
                id="customer-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.middleName')}
                id="customer-middleName"
                name="middleName"
                data-cy="middleName"
                type="text"
                validate={{
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.lastName')}
                id="customer-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.email')}
                id="customer-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: {
                    value: /^[^@\s]+@[^@\s]+\.[^@\s]+$/,
                    message: translate('entity.validation.pattern', { pattern: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.phone')}
                id="customer-phone"
                name="phone"
                data-cy="phone"
                type="text"
                validate={{
                  pattern: {
                    value: /^\+?[1-9]\d{1,14}$/,
                    message: translate('entity.validation.pattern', { pattern: '^\\+?[1-9]\\d{1,14}$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.address')}
                id="customer-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.city')}
                id="customer-city"
                name="city"
                data-cy="city"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.state')}
                id="customer-state"
                name="state"
                data-cy="state"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.postalCode')}
                id="customer-postalCode"
                name="postalCode"
                data-cy="postalCode"
                type="text"
                validate={{
                  pattern: {
                    value: /^\d{5}(-\d{4})?$/,
                    message: translate('entity.validation.pattern', { pattern: '^\\d{5}(-\\d{4})?$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.country')}
                id="customer-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.socialMediaProfiles')}
                id="customer-socialMediaProfiles"
                name="socialMediaProfiles"
                data-cy="socialMediaProfiles"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.notes')}
                id="customer-notes"
                name="notes"
                data-cy="notes"
                type="textarea"
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.preferences')}
                id="customer-preferences"
                name="preferences"
                data-cy="preferences"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.budget')}
                id="customer-budget"
                name="budget"
                data-cy="budget"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.rentalBudget')}
                id="customer-rentalBudget"
                name="rentalBudget"
                data-cy="rentalBudget"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.customer.interactionHistory')}
                id="customer-interactionHistory"
                name="interactionHistory"
                data-cy="interactionHistory"
                type="textarea"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/customer" replace color="info">
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

export default CustomerUpdate;
