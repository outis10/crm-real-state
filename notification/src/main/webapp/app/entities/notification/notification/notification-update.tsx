import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { EntityNameEnum } from 'app/shared/model/enumerations/entity-name-enum.model';
import { NotificationTargetEnum } from 'app/shared/model/enumerations/notification-target-enum.model';
import { NotificationStatusEnum } from 'app/shared/model/enumerations/notification-status-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './notification.reducer';

export const NotificationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const notificationEntity = useAppSelector(state => state.notification.notification.entity);
  const loading = useAppSelector(state => state.notification.notification.loading);
  const updating = useAppSelector(state => state.notification.notification.updating);
  const updateSuccess = useAppSelector(state => state.notification.notification.updateSuccess);
  const entityNameEnumValues = Object.keys(EntityNameEnum);
  const notificationTargetEnumValues = Object.keys(NotificationTargetEnum);
  const notificationStatusEnumValues = Object.keys(NotificationStatusEnum);

  const handleClose = () => {
    navigate(`/notification/notification${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.entityId !== undefined && typeof values.entityId !== 'number') {
      values.entityId = Number(values.entityId);
    }
    values.timestamp = convertDateTimeToServer(values.timestamp);

    const entity = {
      ...notificationEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          timestamp: displayDefaultDateTime(),
        }
      : {
          entityName: 'PROPERTY',
          target: 'NEW',
          status: 'NEW',
          ...notificationEntity,
          timestamp: convertDateTimeFromServer(notificationEntity.timestamp),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="notificationApp.notificationNotification.home.createOrEditLabel" data-cy="NotificationCreateUpdateHeading">
            <Translate contentKey="notificationApp.notificationNotification.home.createOrEditLabel">
              Create or edit a Notification
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="notification-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('notificationApp.notificationNotification.entityId')}
                id="notification-entityId"
                name="entityId"
                data-cy="entityId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('notificationApp.notificationNotification.entityName')}
                id="notification-entityName"
                name="entityName"
                data-cy="entityName"
                type="select"
              >
                {entityNameEnumValues.map(entityNameEnum => (
                  <option value={entityNameEnum} key={entityNameEnum}>
                    {translate(`notificationApp.EntityNameEnum.${entityNameEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('notificationApp.notificationNotification.target')}
                id="notification-target"
                name="target"
                data-cy="target"
                type="select"
              >
                {notificationTargetEnumValues.map(notificationTargetEnum => (
                  <option value={notificationTargetEnum} key={notificationTargetEnum}>
                    {translate(`notificationApp.NotificationTargetEnum.${notificationTargetEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('notificationApp.notificationNotification.content')}
                id="notification-content"
                name="content"
                data-cy="content"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('notificationApp.notificationNotification.status')}
                id="notification-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {notificationStatusEnumValues.map(notificationStatusEnum => (
                  <option value={notificationStatusEnum} key={notificationStatusEnum}>
                    {translate(`notificationApp.NotificationStatusEnum.${notificationStatusEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('notificationApp.notificationNotification.timestamp')}
                id="notification-timestamp"
                name="timestamp"
                data-cy="timestamp"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notification/notification" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default NotificationUpdate;
