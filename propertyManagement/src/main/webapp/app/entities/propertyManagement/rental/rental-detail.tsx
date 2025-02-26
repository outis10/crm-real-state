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

  const rentalEntity = useAppSelector(state => state.propertymanagement.rental.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rentalDetailsHeading">
          <Translate contentKey="propertyManagementApp.propertyManagementRental.detail.title">Rental</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.id}</dd>
          <dt>
            <span id="propertyId">
              <Translate contentKey="propertyManagementApp.propertyManagementRental.propertyId">Property Id</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.propertyId}</dd>
          <dt>
            <span id="customerId">
              <Translate contentKey="propertyManagementApp.propertyManagementRental.customerId">Customer Id</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.customerId}</dd>
          <dt>
            <span id="oportunityId">
              <Translate contentKey="propertyManagementApp.propertyManagementRental.oportunityId">Oportunity Id</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.oportunityId}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="propertyManagementApp.propertyManagementRental.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.startDate ? <TextFormat value={rentalEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="propertyManagementApp.propertyManagementRental.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.endDate ? <TextFormat value={rentalEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="monthlyRent">
              <Translate contentKey="propertyManagementApp.propertyManagementRental.monthlyRent">Monthly Rent</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.monthlyRent}</dd>
          <dt>
            <span id="securityDeposit">
              <Translate contentKey="propertyManagementApp.propertyManagementRental.securityDeposit">Security Deposit</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.securityDeposit}</dd>
          <dt>
            <span id="contractStatus">
              <Translate contentKey="propertyManagementApp.propertyManagementRental.contractStatus">Contract Status</Translate>
            </span>
          </dt>
          <dd>{rentalEntity.contractStatus}</dd>
        </dl>
        <Button tag={Link} to="/propertymanagement/rental" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/propertymanagement/rental/${rentalEntity.id}/edit`} replace color="primary">
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
