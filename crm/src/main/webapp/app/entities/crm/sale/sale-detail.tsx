import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sale.reducer';

export const SaleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const saleEntity = useAppSelector(state => state.crm.sale.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="saleDetailsHeading">
          <Translate contentKey="crmApp.crmSale.detail.title">Sale</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{saleEntity.id}</dd>
          <dt>
            <span id="propertyId">
              <Translate contentKey="crmApp.crmSale.propertyId">Property Id</Translate>
            </span>
          </dt>
          <dd>{saleEntity.propertyId}</dd>
          <dt>
            <span id="customerId">
              <Translate contentKey="crmApp.crmSale.customerId">Customer Id</Translate>
            </span>
          </dt>
          <dd>{saleEntity.customerId}</dd>
          <dt>
            <span id="oportunityId">
              <Translate contentKey="crmApp.crmSale.oportunityId">Oportunity Id</Translate>
            </span>
          </dt>
          <dd>{saleEntity.oportunityId}</dd>
          <dt>
            <span id="saleDate">
              <Translate contentKey="crmApp.crmSale.saleDate">Sale Date</Translate>
            </span>
          </dt>
          <dd>{saleEntity.saleDate ? <TextFormat value={saleEntity.saleDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="totalAmount">
              <Translate contentKey="crmApp.crmSale.totalAmount">Total Amount</Translate>
            </span>
          </dt>
          <dd>{saleEntity.totalAmount}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="crmApp.crmSale.status">Status</Translate>
            </span>
          </dt>
          <dd>{saleEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/crm/sale" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/crm/sale/${saleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SaleDetail;
