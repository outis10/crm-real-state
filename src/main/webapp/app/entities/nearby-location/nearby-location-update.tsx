import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getProperties } from 'app/entities/property/property.reducer';
import { createEntity, getEntity, reset, updateEntity } from './nearby-location.reducer';

export const NearbyLocationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const properties = useAppSelector(state => state.property.entities);
  const nearbyLocationEntity = useAppSelector(state => state.nearbyLocation.entity);
  const loading = useAppSelector(state => state.nearbyLocation.loading);
  const updating = useAppSelector(state => state.nearbyLocation.updating);
  const updateSuccess = useAppSelector(state => state.nearbyLocation.updateSuccess);

  const handleClose = () => {
    navigate(`/nearby-location${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProperties({}));
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
    if (values.distance !== undefined && typeof values.distance !== 'number') {
      values.distance = Number(values.distance);
    }

    const entity = {
      ...nearbyLocationEntity,
      ...values,
      property: properties.find(it => it.id.toString() === values.property?.toString()),
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
          ...nearbyLocationEntity,
          property: nearbyLocationEntity?.property?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmRealStateApp.nearbyLocation.home.createOrEditLabel" data-cy="NearbyLocationCreateUpdateHeading">
            <Translate contentKey="crmRealStateApp.nearbyLocation.home.createOrEditLabel">Create or edit a NearbyLocation</Translate>
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
                  id="nearby-location-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('crmRealStateApp.nearbyLocation.name')}
                id="nearby-location-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.nearbyLocation.type')}
                id="nearby-location-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.nearbyLocation.distance')}
                id="nearby-location-distance"
                name="distance"
                data-cy="distance"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.nearbyLocation.coordinates')}
                id="nearby-location-coordinates"
                name="coordinates"
                data-cy="coordinates"
                type="text"
              />
              <ValidatedField
                id="nearby-location-property"
                name="property"
                data-cy="property"
                label={translate('crmRealStateApp.nearbyLocation.property')}
                type="select"
                required
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nearby-location" replace color="info">
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

export default NearbyLocationUpdate;
