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

  const contactEntity = useAppSelector(state => state.crm.contact.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactDetailsHeading">
          <Translate contentKey="crmApp.crmContact.detail.title">Contact</Translate>
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
              <Translate contentKey="crmApp.crmContact.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{contactEntity.firstName}</dd>
          <dt>
            <span id="middleName">
              <Translate contentKey="crmApp.crmContact.middleName">Middle Name</Translate>
            </span>
          </dt>
          <dd>{contactEntity.middleName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="crmApp.crmContact.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{contactEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="crmApp.crmContact.email">Email</Translate>
            </span>
          </dt>
          <dd>{contactEntity.email}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="crmApp.crmContact.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{contactEntity.phone}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="crmApp.crmContact.address">Address</Translate>
            </span>
          </dt>
          <dd>{contactEntity.address}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="crmApp.crmContact.city">City</Translate>
            </span>
          </dt>
          <dd>{contactEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="crmApp.crmContact.state">State</Translate>
            </span>
          </dt>
          <dd>{contactEntity.state}</dd>
          <dt>
            <span id="postalCode">
              <Translate contentKey="crmApp.crmContact.postalCode">Postal Code</Translate>
            </span>
          </dt>
          <dd>{contactEntity.postalCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="crmApp.crmContact.country">Country</Translate>
            </span>
          </dt>
          <dd>{contactEntity.country}</dd>
          <dt>
            <span id="socialMediaProfiles">
              <Translate contentKey="crmApp.crmContact.socialMediaProfiles">Social Media Profiles</Translate>
            </span>
          </dt>
          <dd>{contactEntity.socialMediaProfiles}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="crmApp.crmContact.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{contactEntity.notes}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="crmApp.crmContact.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{contactEntity.createdBy}</dd>
          <dt>
            <Translate contentKey="crmApp.crmContact.assignedTo">Assigned To</Translate>
          </dt>
          <dd>{contactEntity.assignedTo ? contactEntity.assignedTo.email : ''}</dd>
        </dl>
        <Button tag={Link} to="/crm/contact" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/crm/contact/${contactEntity.id}/edit`} replace color="primary">
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
