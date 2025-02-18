import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './charge.reducer';

export const ChargeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const chargeEntity = useAppSelector(state => state.charge.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="chargeDetailsHeading">
          <Translate contentKey="crmRealStateApp.charge.detail.title">Charge</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{chargeEntity.id}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="crmRealStateApp.charge.type">Type</Translate>
            </span>
          </dt>
          <dd>{chargeEntity.type}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="crmRealStateApp.charge.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{chargeEntity.amount}</dd>
          <dt>
            <span id="dueDate">
              <Translate contentKey="crmRealStateApp.charge.dueDate">Due Date</Translate>
            </span>
          </dt>
          <dd>{chargeEntity.dueDate ? <TextFormat value={chargeEntity.dueDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="crmRealStateApp.charge.status">Status</Translate>
            </span>
          </dt>
          <dd>{chargeEntity.status}</dd>
          <dt>
            <Translate contentKey="crmRealStateApp.charge.customer">Customer</Translate>
          </dt>
          <dd>{chargeEntity.customer ? chargeEntity.customer.id : ''}</dd>
          <dt>
            <Translate contentKey="crmRealStateApp.charge.rentalContract">Rental Contract</Translate>
          </dt>
          <dd>{chargeEntity.rentalContract ? chargeEntity.rentalContract.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/charge" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/charge/${chargeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChargeDetail;
