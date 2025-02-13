import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './opportunity.reducer';

export const OpportunityDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const opportunityEntity = useAppSelector(state => state.opportunity.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="opportunityDetailsHeading">
          <Translate contentKey="crmRealStateApp.opportunity.detail.title">Opportunity</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{opportunityEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="crmRealStateApp.opportunity.name">Name</Translate>
            </span>
          </dt>
          <dd>{opportunityEntity.name}</dd>
          <dt>
            <span id="budget">
              <Translate contentKey="crmRealStateApp.opportunity.budget">Budget</Translate>
            </span>
          </dt>
          <dd>{opportunityEntity.budget}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="crmRealStateApp.opportunity.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{opportunityEntity.amount}</dd>
          <dt>
            <span id="probability">
              <Translate contentKey="crmRealStateApp.opportunity.probability">Probability</Translate>
            </span>
          </dt>
          <dd>{opportunityEntity.probability}</dd>
          <dt>
            <span id="expectedCloseDate">
              <Translate contentKey="crmRealStateApp.opportunity.expectedCloseDate">Expected Close Date</Translate>
            </span>
          </dt>
          <dd>
            {opportunityEntity.expectedCloseDate ? (
              <TextFormat value={opportunityEntity.expectedCloseDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="stage">
              <Translate contentKey="crmRealStateApp.opportunity.stage">Stage</Translate>
            </span>
          </dt>
          <dd>{opportunityEntity.stage}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="crmRealStateApp.opportunity.description">Description</Translate>
            </span>
          </dt>
          <dd>{opportunityEntity.description}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="crmRealStateApp.opportunity.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>
            {opportunityEntity.createdAt ? <TextFormat value={opportunityEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="modifiedAt">
              <Translate contentKey="crmRealStateApp.opportunity.modifiedAt">Modified At</Translate>
            </span>
          </dt>
          <dd>
            {opportunityEntity.modifiedAt ? <TextFormat value={opportunityEntity.modifiedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="closedAt">
              <Translate contentKey="crmRealStateApp.opportunity.closedAt">Closed At</Translate>
            </span>
          </dt>
          <dd>
            {opportunityEntity.closedAt ? <TextFormat value={opportunityEntity.closedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="crmRealStateApp.opportunity.customer">Customer</Translate>
          </dt>
          <dd>{opportunityEntity.customer ? opportunityEntity.customer.email : ''}</dd>
          <dt>
            <Translate contentKey="crmRealStateApp.opportunity.property">Property</Translate>
          </dt>
          <dd>{opportunityEntity.property ? opportunityEntity.property.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/opportunity" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/opportunity/${opportunityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OpportunityDetail;
