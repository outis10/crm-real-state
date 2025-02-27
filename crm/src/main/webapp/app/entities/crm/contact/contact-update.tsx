import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCustomers } from 'app/entities/crm/customer/customer.reducer';
import { createEntity, getEntity, reset, updateEntity } from './contact.reducer';

export const ContactUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customers = useAppSelector(state => state.crm.customer.entities);
  const contactEntity = useAppSelector(state => state.crm.contact.entity);
  const loading = useAppSelector(state => state.crm.contact.loading);
  const updating = useAppSelector(state => state.crm.contact.updating);
  const updateSuccess = useAppSelector(state => state.crm.contact.updateSuccess);

  const handleClose = () => {
    navigate(`/crm/contact${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

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
    if (values.createdBy !== undefined && typeof values.createdBy !== 'number') {
      values.createdBy = Number(values.createdBy);
    }

    const entity = {
      ...contactEntity,
      ...values,
      assignedTo: customers.find(it => it.id.toString() === values.assignedTo?.toString()),
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
          ...contactEntity,
          assignedTo: contactEntity?.assignedTo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmApp.crmContact.home.createOrEditLabel" data-cy="ContactCreateUpdateHeading">
            <Translate contentKey="crmApp.crmContact.home.createOrEditLabel">Create or edit a Contact</Translate>
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
                  id="contact-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('crmApp.crmContact.firstName')}
                id="contact-firstName"
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
                label={translate('crmApp.crmContact.middleName')}
                id="contact-middleName"
                name="middleName"
                data-cy="middleName"
                type="text"
                validate={{
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('crmApp.crmContact.lastName')}
                id="contact-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
                validate={{
                  minLength: { value: 2, message: translate('entity.validation.minlength', { min: 2 }) },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('crmApp.crmContact.email')}
                id="contact-email"
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
                label={translate('crmApp.crmContact.phone')}
                id="contact-phone"
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
                label={translate('crmApp.crmContact.address')}
                id="contact-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField label={translate('crmApp.crmContact.city')} id="contact-city" name="city" data-cy="city" type="text" />
              <ValidatedField label={translate('crmApp.crmContact.state')} id="contact-state" name="state" data-cy="state" type="text" />
              <ValidatedField
                label={translate('crmApp.crmContact.postalCode')}
                id="contact-postalCode"
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
                label={translate('crmApp.crmContact.country')}
                id="contact-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('crmApp.crmContact.socialMediaProfiles')}
                id="contact-socialMediaProfiles"
                name="socialMediaProfiles"
                data-cy="socialMediaProfiles"
                type="text"
              />
              <ValidatedField
                label={translate('crmApp.crmContact.notes')}
                id="contact-notes"
                name="notes"
                data-cy="notes"
                type="textarea"
              />
              <ValidatedField
                label={translate('crmApp.crmContact.createdBy')}
                id="contact-createdBy"
                name="createdBy"
                data-cy="createdBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="contact-assignedTo"
                name="assignedTo"
                data-cy="assignedTo"
                label={translate('crmApp.crmContact.assignedTo')}
                type="select"
              >
                <option value="" key="0" />
                {customers
                  ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.email}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/crm/contact" replace color="info">
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

export default ContactUpdate;
