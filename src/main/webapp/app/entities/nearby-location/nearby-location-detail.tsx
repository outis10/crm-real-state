import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nearby-location.reducer';

export const NearbyLocationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nearbyLocationEntity = useAppSelector(state => state.nearbyLocation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nearbyLocationDetailsHeading">
          <Translate contentKey="crmRealStateApp.nearbyLocation.detail.title">NearbyLocation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{nearbyLocationEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="crmRealStateApp.nearbyLocation.name">Name</Translate>
            </span>
          </dt>
          <dd>{nearbyLocationEntity.name}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="crmRealStateApp.nearbyLocation.type">Type</Translate>
            </span>
          </dt>
          <dd>{nearbyLocationEntity.type}</dd>
          <dt>
            <span id="distance">
              <Translate contentKey="crmRealStateApp.nearbyLocation.distance">Distance</Translate>
            </span>
          </dt>
          <dd>{nearbyLocationEntity.distance}</dd>
          <dt>
            <span id="coordinates">
              <Translate contentKey="crmRealStateApp.nearbyLocation.coordinates">Coordinates</Translate>
            </span>
          </dt>
          <dd>{nearbyLocationEntity.coordinates}</dd>
          <dt>
            <Translate contentKey="crmRealStateApp.nearbyLocation.property">Property</Translate>
          </dt>
          <dd>{nearbyLocationEntity.property ? nearbyLocationEntity.property.codeName : ''}</dd>
        </dl>
        <Button tag={Link} to="/nearby-location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nearby-location/${nearbyLocationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NearbyLocationDetail;
