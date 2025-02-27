import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './notification.reducer';

export const NotificationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const notificationEntity = useAppSelector(state => state.notification.notification.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notificationDetailsHeading">
          <Translate contentKey="notificationApp.notificationNotification.detail.title">Notification</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.id}</dd>
          <dt>
            <span id="entityId">
              <Translate contentKey="notificationApp.notificationNotification.entityId">Entity Id</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.entityId}</dd>
          <dt>
            <span id="entityName">
              <Translate contentKey="notificationApp.notificationNotification.entityName">Entity Name</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.entityName}</dd>
          <dt>
            <span id="target">
              <Translate contentKey="notificationApp.notificationNotification.target">Target</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.target}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="notificationApp.notificationNotification.content">Content</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.content}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="notificationApp.notificationNotification.status">Status</Translate>
            </span>
          </dt>
          <dd>{notificationEntity.status}</dd>
          <dt>
            <span id="timestamp">
              <Translate contentKey="notificationApp.notificationNotification.timestamp">Timestamp</Translate>
            </span>
          </dt>
          <dd>
            {notificationEntity.timestamp ? <TextFormat value={notificationEntity.timestamp} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/notification/notification" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notification/notification/${notificationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotificationDetail;
