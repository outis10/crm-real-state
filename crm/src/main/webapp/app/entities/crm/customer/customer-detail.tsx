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

  const customerEntity = useAppSelector(state => state.crm.customer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerDetailsHeading">
          <Translate contentKey="crmApp.crmCustomer.detail.title">Customer</Translate>
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
              <Translate contentKey="crmApp.crmCustomer.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{customerEntity.firstName}</dd>
          <dt>
            <span id="middleName">
              <Translate contentKey="crmApp.crmCustomer.middleName">Middle Name</Translate>
            </span>
          </dt>
          <dd>{customerEntity.middleName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="crmApp.crmCustomer.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{customerEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="crmApp.crmCustomer.email">Email</Translate>
            </span>
          </dt>
          <dd>{customerEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="crmApp.crmCustomer.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{customerEntity.phone}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="crmApp.crmCustomer.address">Address</Translate>
            </span>
          </dt>
          <dd>{customerEntity.address}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="crmApp.crmCustomer.city">City</Translate>
            </span>
          </dt>
          <dd>{customerEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="crmApp.crmCustomer.state">State</Translate>
            </span>
          </dt>
          <dd>{customerEntity.state}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="crmApp.crmCustomer.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{customerEntity.postalCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="crmApp.crmCustomer.country">Country</Translate>
            </span>
          </dt>
          <dd>{customerEntity.country}</dd>
          <dt>
            <span id="socialMediaProfiles">
              <Translate contentKey="crmApp.crmCustomer.socialMediaProfiles">Social Media Profiles</Translate>
            </span>
          </dt>
          <dd>{customerEntity.socialMediaProfiles}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="crmApp.crmCustomer.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{customerEntity.notes}</dd>
          <dt>
            <span id="preferences">
              <Translate contentKey="crmApp.crmCustomer.preferences">Preferences</Translate>
            </span>
          </dt>
          <dd>{customerEntity.preferences}</dd>
          <dt>
            <span id="budget">
              <Translate contentKey="crmApp.crmCustomer.budget">Budget</Translate>
            </span>
          </dt>
          <dd>{customerEntity.budget}</dd>
          <dt>
            <span id="rentalBudget">
              <Translate contentKey="crmApp.crmCustomer.rentalBudget">Rental Budget</Translate>
            </span>
          </dt>
          <dd>{customerEntity.rentalBudget}</dd>
          <dt>
            <span id="interactionHistory">
              <Translate contentKey="crmApp.crmCustomer.interactionHistory">Interaction History</Translate>
            </span>
          </dt>
          <dd>{customerEntity.interactionHistory}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="crmApp.crmCustomer.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{customerEntity.createdBy}</dd>
        </dl>
        <Button tag={Link} to="/crm/customer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/crm/customer/${customerEntity.id}/edit`} replace color="primary">
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
