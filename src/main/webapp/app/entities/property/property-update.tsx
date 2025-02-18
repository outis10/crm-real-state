import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { OperationTypeEnum } from 'app/shared/model/enumerations/operation-type-enum.model';
import { PropertyStatusEnum } from 'app/shared/model/enumerations/property-status-enum.model';
import { createEntity, getEntity, updateEntity } from './property.reducer';

export const PropertyUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const propertyEntity = useAppSelector(state => state.property.entity);
  const loading = useAppSelector(state => state.property.loading);
  const updating = useAppSelector(state => state.property.updating);
  const updateSuccess = useAppSelector(state => state.property.updateSuccess);
  const operationTypeEnumValues = Object.keys(OperationTypeEnum);
  const propertyStatusEnumValues = Object.keys(PropertyStatusEnum);

  const handleClose = () => {
    navigate('/property');
  };

  useEffect(() => {
    if (!isNew) {
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
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }
    if (values.rentalPrice !== undefined && typeof values.rentalPrice !== 'number') {
      values.rentalPrice = Number(values.rentalPrice);
    }
    if (values.area !== undefined && typeof values.area !== 'number') {
      values.area = Number(values.area);
    }
    if (values.bedrooms !== undefined && typeof values.bedrooms !== 'number') {
      values.bedrooms = Number(values.bedrooms);
    }
    if (values.bathrooms !== undefined && typeof values.bathrooms !== 'number') {
      values.bathrooms = Number(values.bathrooms);
    }
    if (values.appreciationRate !== undefined && typeof values.appreciationRate !== 'number') {
      values.appreciationRate = Number(values.appreciationRate);
    }

    const entity = {
      ...propertyEntity,
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
          operationType: 'SALES',
          status: 'AVAILABLE',
          ...propertyEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmRealStateApp.property.home.createOrEditLabel" data-cy="PropertyCreateUpdateHeading">
            <Translate contentKey="crmRealStateApp.property.home.createOrEditLabel">Create or edit a Property</Translate>
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
                  id="property-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('crmRealStateApp.property.name')}
                id="property-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.codeName')}
                id="property-codeName"
                name="codeName"
                data-cy="codeName"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.type')}
                id="property-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.operationType')}
                id="property-operationType"
                name="operationType"
                data-cy="operationType"
                type="select"
              >
                {operationTypeEnumValues.map(operationTypeEnum => (
                  <option value={operationTypeEnum} key={operationTypeEnum}>
                    {translate(`crmRealStateApp.OperationTypeEnum.${operationTypeEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('crmRealStateApp.property.location')}
                id="property-location"
                name="location"
                data-cy="location"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.city')}
                id="property-city"
                name="city"
                data-cy="city"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.state')}
                id="property-state"
                name="state"
                data-cy="state"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.postalCode')}
                id="property-postalCode"
                name="postalCode"
                data-cy="postalCode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.price')}
                id="property-price"
                name="price"
                data-cy="price"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.rentalPrice')}
                id="property-rentalPrice"
                name="rentalPrice"
                data-cy="rentalPrice"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.area')}
                id="property-area"
                name="area"
                data-cy="area"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.bedrooms')}
                id="property-bedrooms"
                name="bedrooms"
                data-cy="bedrooms"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.bathrooms')}
                id="property-bathrooms"
                name="bathrooms"
                data-cy="bathrooms"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.appreciationRate')}
                id="property-appreciationRate"
                name="appreciationRate"
                data-cy="appreciationRate"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.features')}
                id="property-features"
                name="features"
                data-cy="features"
                type="text"
              />
              <ValidatedField
                label={translate('crmRealStateApp.property.status')}
                id="property-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {propertyStatusEnumValues.map(propertyStatusEnum => (
                  <option value={propertyStatusEnum} key={propertyStatusEnum}>
                    {translate(`crmRealStateApp.PropertyStatusEnum.${propertyStatusEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('crmRealStateApp.property.images')}
                id="property-images"
                name="images"
                data-cy="images"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/property" replace color="info">
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

export default PropertyUpdate;
