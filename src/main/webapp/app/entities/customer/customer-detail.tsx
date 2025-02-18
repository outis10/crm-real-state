import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customer.reducer';

export const CustomerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const customerEntity = useAppSelector(state => state.customer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerDetailsHeading">
          <Translate contentKey="crmRealStateApp.customer.detail.title">Customer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customerEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="crmRealStateApp.customer.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{customerEntity.firstName}</dd>
          <dt>
            <span id="middleName">
              <Translate contentKey="crmRealStateApp.customer.middleName">Middle Name</Translate>
            </span>
          </dt>
          <dd>{customerEntity.middleName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="crmRealStateApp.customer.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{customerEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="crmRealStateApp.customer.email">Email</Translate>
            </span>
          </dt>
          <dd>{customerEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="crmRealStateApp.customer.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{customerEntity.phone}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="crmRealStateApp.customer.address">Address</Translate>
            </span>
          </dt>
          <dd>{customerEntity.address}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="crmRealStateApp.customer.city">City</Translate>
            </span>
          </dt>
          <dd>{customerEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="crmRealStateApp.customer.state">State</Translate>
            </span>
          </dt>
          <dd>{customerEntity.state}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="crmRealStateApp.customer.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{customerEntity.postalCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="crmRealStateApp.customer.country">Country</Translate>
            </span>
          </dt>
          <dd>{customerEntity.country}</dd>
          <dt>
            <span id="socialMediaProfiles">
              <Translate contentKey="crmRealStateApp.customer.socialMediaProfiles">Social Media Profiles</Translate>
            </span>
          </dt>
          <dd>{customerEntity.socialMediaProfiles}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="crmRealStateApp.customer.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{customerEntity.notes}</dd>
          <dt>
            <span id="preferences">
              <Translate contentKey="crmRealStateApp.customer.preferences">Preferences</Translate>
            </span>
          </dt>
          <dd>{customerEntity.preferences}</dd>
          <dt>
            <span id="budget">
              <Translate contentKey="crmRealStateApp.customer.budget">Budget</Translate>
            </span>
          </dt>
          <dd>{customerEntity.budget}</dd>
          <dt>
            <span id="rentalBudget">
              <Translate contentKey="crmRealStateApp.customer.rentalBudget">Rental Budget</Translate>
            </span>
          </dt>
          <dd>{customerEntity.rentalBudget}</dd>
          <dt>
            <span id="interactionHistory">
              <Translate contentKey="crmRealStateApp.customer.interactionHistory">Interaction History</Translate>
            </span>
          </dt>
          <dd>{customerEntity.interactionHistory}</dd>
        </dl>
        <Button tag={Link} to="/customer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer/${customerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerDetail;
