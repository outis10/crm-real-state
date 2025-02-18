import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './property.reducer';

export const PropertyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const propertyEntity = useAppSelector(state => state.property.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="propertyDetailsHeading">
          <Translate contentKey="crmRealStateApp.property.detail.title">Property</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="crmRealStateApp.property.name">Name</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.name}</dd>
          <dt>
            <span id="codeName">
              <Translate contentKey="crmRealStateApp.property.codeName">Code Name</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.codeName}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="crmRealStateApp.property.type">Type</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.type}</dd>
          <dt>
            <span id="operationType">
              <Translate contentKey="crmRealStateApp.property.operationType">Operation Type</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.operationType}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="crmRealStateApp.property.location">Location</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.location}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="crmRealStateApp.property.city">City</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="crmRealStateApp.property.state">State</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.state}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="crmRealStateApp.property.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.postalCode}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="crmRealStateApp.property.price">Price</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.price}</dd>
          <dt>
            <span id="rentalPrice">
              <Translate contentKey="crmRealStateApp.property.rentalPrice">Rental Price</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.rentalPrice}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="crmRealStateApp.property.area">Area</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.area}</dd>
          <dt>
            <span id="bedrooms">
              <Translate contentKey="crmRealStateApp.property.bedrooms">Bedrooms</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.bedrooms}</dd>
          <dt>
            <span id="bathrooms">
              <Translate contentKey="crmRealStateApp.property.bathrooms">Bathrooms</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.bathrooms}</dd>
          <dt>
            <span id="appreciationRate">
              <Translate contentKey="crmRealStateApp.property.appreciationRate">Appreciation Rate</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.appreciationRate}</dd>
          <dt>
            <span id="features">
              <Translate contentKey="crmRealStateApp.property.features">Features</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.features}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="crmRealStateApp.property.status">Status</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.status}</dd>
          <dt>
            <span id="images">
              <Translate contentKey="crmRealStateApp.property.images">Images</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.images}</dd>
        </dl>
        <Button tag={Link} to="/property" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/property/${propertyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PropertyDetail;
