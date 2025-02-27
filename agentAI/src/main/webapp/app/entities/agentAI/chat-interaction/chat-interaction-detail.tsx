import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './chat-interaction.reducer';

export const ChatInteractionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const chatInteractionEntity = useAppSelector(state => state.agentai.chatInteraction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="chatInteractionDetailsHeading">
          <Translate contentKey="agentAiApp.agentAiChatInteraction.detail.title">ChatInteraction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{chatInteractionEntity.id}</dd>
          <dt>
            <span id="entityId">
              <Translate contentKey="agentAiApp.agentAiChatInteraction.entityId">Entity Id</Translate>
            </span>
          </dt>
          <dd>{chatInteractionEntity.entityId}</dd>
          <dt>
            <span id="entityName">
              <Translate contentKey="agentAiApp.agentAiChatInteraction.entityName">Entity Name</Translate>
            </span>
          </dt>
          <dd>{chatInteractionEntity.entityName}</dd>
          <dt>
            <span id="customerQuestion">
              <Translate contentKey="agentAiApp.agentAiChatInteraction.customerQuestion">Customer Question</Translate>
            </span>
          </dt>
          <dd>{chatInteractionEntity.customerQuestion}</dd>
          <dt>
            <span id="chatbotAnswer">
              <Translate contentKey="agentAiApp.agentAiChatInteraction.chatbotAnswer">Chatbot Answer</Translate>
            </span>
          </dt>
          <dd>{chatInteractionEntity.chatbotAnswer}</dd>
          <dt>
            <span id="timestamp">
              <Translate contentKey="agentAiApp.agentAiChatInteraction.timestamp">Timestamp</Translate>
            </span>
          </dt>
          <dd>
            {chatInteractionEntity.timestamp ? (
              <TextFormat value={chatInteractionEntity.timestamp} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/agentai/chat-interaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/agentai/chat-interaction/${chatInteractionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChatInteractionDetail;
