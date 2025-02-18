import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './contact.reducer';

export const ContactDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const contactEntity = useAppSelector(state => state.contact.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactDetailsHeading">
          <Translate contentKey="crmRealStateApp.contact.detail.title">Contact</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contactEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="crmRealStateApp.contact.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{contactEntity.firstName}</dd>
          <dt>
            <span id="middleName">
              <Translate contentKey="crmRealStateApp.contact.middleName">Middle Name</Translate>
            </span>
          </dt>
          <dd>{contactEntity.middleName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="crmRealStateApp.contact.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{contactEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="crmRealStateApp.contact.email">Email</Translate>
            </span>
          </dt>
          <dd>{contactEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="crmRealStateApp.contact.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{contactEntity.phone}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="crmRealStateApp.contact.address">Address</Translate>
            </span>
          </dt>
          <dd>{contactEntity.address}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="crmRealStateApp.contact.city">City</Translate>
            </span>
          </dt>
          <dd>{contactEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="crmRealStateApp.contact.state">State</Translate>
            </span>
          </dt>
          <dd>{contactEntity.state}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="crmRealStateApp.contact.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{contactEntity.postalCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="crmRealStateApp.contact.country">Country</Translate>
            </span>
          </dt>
          <dd>{contactEntity.country}</dd>
          <dt>
            <span id="socialMediaProfiles">
              <Translate contentKey="crmRealStateApp.contact.socialMediaProfiles">Social Media Profiles</Translate>
            </span>
          </dt>
          <dd>{contactEntity.socialMediaProfiles}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="crmRealStateApp.contact.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{contactEntity.notes}</dd>
          <dt>
            <Translate contentKey="crmRealStateApp.contact.customer">Customer</Translate>
          </dt>
          <dd>{contactEntity.customer ? contactEntity.customer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/contact" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact/${contactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactDetail;
