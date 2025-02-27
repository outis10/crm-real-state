import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './rag-context.reducer';

export const RAGContextDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const rAGContextEntity = useAppSelector(state => state.agentai.rAGContext.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="rAGContextDetailsHeading">
          <Translate contentKey="agentAiApp.agentAiRAgContext.detail.title">RAGContext</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{rAGContextEntity.id}</dd>
          <dt>
            <span id="entityId">
              <Translate contentKey="agentAiApp.agentAiRAgContext.entityId">Entity Id</Translate>
            </span>
          </dt>
          <dd>{rAGContextEntity.entityId}</dd>
          <dt>
            <span id="entityName">
              <Translate contentKey="agentAiApp.agentAiRAgContext.entityName">Entity Name</Translate>
            </span>
          </dt>
          <dd>{rAGContextEntity.entityName}</dd>
          <dt>
            <span id="contextText">
              <Translate contentKey="agentAiApp.agentAiRAgContext.contextText">Context Text</Translate>
            </span>
          </dt>
          <dd>{rAGContextEntity.contextText}</dd>
        </dl>
        <Button tag={Link} to="/agentai/rag-context" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/agentai/rag-context/${rAGContextEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RAGContextDetail;
