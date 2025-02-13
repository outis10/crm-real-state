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

  const chatInteractionEntity = useAppSelector(state => state.chatInteraction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="chatInteractionDetailsHeading">
          <Translate contentKey="crmRealStateApp.chatInteraction.detail.title">ChatInteraction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{chatInteractionEntity.id}</dd>
          <dt>
            <span id="customerMessage">
              <Translate contentKey="crmRealStateApp.chatInteraction.customerMessage">Customer Message</Translate>
            </span>
          </dt>
          <dd>{chatInteractionEntity.customerMessage}</dd>
          <dt>
            <span id="chatbotResponse">
              <Translate contentKey="crmRealStateApp.chatInteraction.chatbotResponse">Chatbot Response</Translate>
            </span>
          </dt>
          <dd>{chatInteractionEntity.chatbotResponse}</dd>
          <dt>
            <span id="timestamp">
              <Translate contentKey="crmRealStateApp.chatInteraction.timestamp">Timestamp</Translate>
            </span>
          </dt>
          <dd>
            {chatInteractionEntity.timestamp ? (
              <TextFormat value={chatInteractionEntity.timestamp} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="crmRealStateApp.chatInteraction.customer">Customer</Translate>
          </dt>
          <dd>{chatInteractionEntity.customer ? chatInteractionEntity.customer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/chat-interaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/chat-interaction/${chatInteractionEntity.id}/edit`} replace color="primary">
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
