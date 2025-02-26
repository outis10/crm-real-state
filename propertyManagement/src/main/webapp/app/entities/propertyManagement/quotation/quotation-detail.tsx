import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './quotation.reducer';

export const QuotationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const quotationEntity = useAppSelector(state => state.propertymanagement.quotation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="quotationDetailsHeading">
          <Translate contentKey="propertyManagementApp.propertyManagementQuotation.detail.title">Quotation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{quotationEntity.id}</dd>
          <dt>
            <span id="finalPrice">
              <Translate contentKey="propertyManagementApp.propertyManagementQuotation.finalPrice">Final Price</Translate>
            </span>
          </dt>
          <dd>{quotationEntity.finalPrice}</dd>
          <dt>
            <span id="validityDate">
              <Translate contentKey="propertyManagementApp.propertyManagementQuotation.validityDate">Validity Date</Translate>
            </span>
          </dt>
          <dd>
            {quotationEntity.validityDate ? <TextFormat value={quotationEntity.validityDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="comments">
              <Translate contentKey="propertyManagementApp.propertyManagementQuotation.comments">Comments</Translate>
            </span>
          </dt>
          <dd>{quotationEntity.comments}</dd>
        </dl>
        <Button tag={Link} to="/propertymanagement/quotation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/propertymanagement/quotation/${quotationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuotationDetail;
