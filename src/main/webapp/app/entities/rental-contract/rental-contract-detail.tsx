import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rental-contract.reducer';

export const RentalContractDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rentalContractEntity = useAppSelector(state => state.rentalContract.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rentalContractDetailsHeading">
          <Translate contentKey="crmRealStateApp.rentalContract.detail.title">RentalContract</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rentalContractEntity.id}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="crmRealStateApp.rentalContract.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {rentalContractEntity.startDate ? (
              <TextFormat value={rentalContractEntity.startDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="crmRealStateApp.rentalContract.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {rentalContractEntity.endDate ? <TextFormat value={rentalContractEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="monthlyRent">
              <Translate contentKey="crmRealStateApp.rentalContract.monthlyRent">Monthly Rent</Translate>
            </span>
          </dt>
          <dd>{rentalContractEntity.monthlyRent}</dd>
          <dt>
            <span id="securityDeposit">
              <Translate contentKey="crmRealStateApp.rentalContract.securityDeposit">Security Deposit</Translate>
            </span>
          </dt>
          <dd>{rentalContractEntity.securityDeposit}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="crmRealStateApp.rentalContract.status">Status</Translate>
            </span>
          </dt>
          <dd>{rentalContractEntity.status}</dd>
          <dt>
            <Translate contentKey="crmRealStateApp.rentalContract.property">Property</Translate>
          </dt>
          <dd>{rentalContractEntity.property ? rentalContractEntity.property.id : ''}</dd>
          <dt>
            <Translate contentKey="crmRealStateApp.rentalContract.customer">Customer</Translate>
          </dt>
          <dd>{rentalContractEntity.customer ? rentalContractEntity.customer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rental-contract" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rental-contract/${rentalContractEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RentalContractDetail;
