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

  const propertyEntity = useAppSelector(state => state.crm.property.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="propertyDetailsHeading">
          <Translate contentKey="crmApp.crmProperty.detail.title">Property</Translate>
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
              <Translate contentKey="crmApp.crmProperty.name">Name</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.name}</dd>
          <dt>
            <span id="codeName">
              <Translate contentKey="crmApp.crmProperty.codeName">Code Name</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.codeName}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="crmApp.crmProperty.type">Type</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.type}</dd>
          <dt>
            <span id="operationType">
              <Translate contentKey="crmApp.crmProperty.operationType">Operation Type</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.operationType}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="crmApp.crmProperty.location">Location</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.location}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="crmApp.crmProperty.city">City</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="crmApp.crmProperty.state">State</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.state}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="crmApp.crmProperty.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.postalCode}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="crmApp.crmProperty.price">Price</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.price}</dd>
          <dt>
            <span id="rentalPrice">
              <Translate contentKey="crmApp.crmProperty.rentalPrice">Rental Price</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.rentalPrice}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="crmApp.crmProperty.area">Area</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.area}</dd>
          <dt>
            <span id="bedrooms">
              <Translate contentKey="crmApp.crmProperty.bedrooms">Bedrooms</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.bedrooms}</dd>
          <dt>
            <span id="bathrooms">
              <Translate contentKey="crmApp.crmProperty.bathrooms">Bathrooms</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.bathrooms}</dd>
          <dt>
            <span id="appreciationRate">
              <Translate contentKey="crmApp.crmProperty.appreciationRate">Appreciation Rate</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.appreciationRate}</dd>
          <dt>
            <span id="features">
              <Translate contentKey="crmApp.crmProperty.features">Features</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.features}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="crmApp.crmProperty.status">Status</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.status}</dd>
          <dt>
            <span id="images">
              <Translate contentKey="crmApp.crmProperty.images">Images</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.images}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="crmApp.crmProperty.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{propertyEntity.createdBy}</dd>
        </dl>
        <Button tag={Link} to="/crm/property" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/crm/property/${propertyEntity.id}/edit`} replace color="primary">
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
