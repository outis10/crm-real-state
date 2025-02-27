import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rental.reducer';

export const RentalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rentalEntity = useAppSelector(state => state.crm.rental.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rentalDetailsHeading">
          <Translate contentKey="crmApp.crmRental.detail.title">Rental</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.id}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="crmApp.crmRental.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.startDate ? <TextFormat value={rentalEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="crmApp.crmRental.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.endDate ? <TextFormat value={rentalEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="monthlyRent">
              <Translate contentKey="crmApp.crmRental.monthlyRent">Monthly Rent</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.monthlyRent}</dd>
          <dt>
            <span id="securityDeposit">
              <Translate contentKey="crmApp.crmRental.securityDeposit">Security Deposit</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.securityDeposit}</dd>
          <dt>
            <span id="contractStatus">
              <Translate contentKey="crmApp.crmRental.contractStatus">Contract Status</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.contractStatus}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="crmApp.crmRental.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.createdBy}</dd>
          <dt>
            <Translate contentKey="crmApp.crmRental.property">Property</Translate>
          </dt>
          <dd>{rentalEntity.property ? rentalEntity.property.codeName : ''}</dd>
          <dt>
            <Translate contentKey="crmApp.crmRental.customer">Customer</Translate>
          </dt>
          <dd>{rentalEntity.customer ? rentalEntity.customer.id : ''}</dd>
          <dt>
            <Translate contentKey="crmApp.crmRental.opportunity">Opportunity</Translate>
          </dt>
          <dd>{rentalEntity.opportunity ? rentalEntity.opportunity.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/crm/rental" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/crm/rental/${rentalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RentalDetail;
