import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getCustomers } from 'app/entities/customer/customer.reducer';
import { NotificationTargetEnum } from 'app/shared/model/enumerations/notification-target-enum.model';
import { NotificationStatusEnum } from 'app/shared/model/enumerations/notification-status-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './notification.reducer';

export const NotificationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const customers = useAppSelector(state => state.customer.entities);
  const notificationEntity = useAppSelector(state => state.notification.entity);
  const loading = useAppSelector(state => state.notification.loading);
  const updating = useAppSelector(state => state.notification.updating);
  const updateSuccess = useAppSelector(state => state.notification.updateSuccess);
  const notificationTargetEnumValues = Object.keys(NotificationTargetEnum);
  const notificationStatusEnumValues = Object.keys(NotificationStatusEnum);

  const handleClose = () => {
    navigate(`/notification${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCustomers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.timestamp = convertDateTimeToServer(values.timestamp);

    const entity = {
      ...notificationEntity,
      ...values,
      customer: customers.find(it => it.id.toString() === values.customer?.toString()),
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
          target: 'NEW',
          status: 'NEW',
          ...notificationEntity,
          timestamp: convertDateTimeFromServer(notificationEntity.timestamp),
          customer: notificationEntity?.customer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmRealStateApp.notification.home.createOrEditLabel" data-cy="NotificationCreateUpdateHeading">
            <Translate contentKey="crmRealStateApp.notification.home.createOrEditLabel">Create or edit a Notification</Translate>
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
                label={translate('crmRealStateApp.notification.target')}
                id="notification-target"
                name="target"
                data-cy="target"
                type="select"
              >
                {notificationTargetEnumValues.map(notificationTargetEnum => (
                  <option value={notificationTargetEnum} key={notificationTargetEnum}>
                    {translate(`crmRealStateApp.NotificationTargetEnum.${notificationTargetEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('crmRealStateApp.notification.content')}
                id="notification-content"
                name="content"
                data-cy="content"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.notification.status')}
                id="notification-status"
                name="status"
                data-cy="status"
                type="select"
              >
                {notificationStatusEnumValues.map(notificationStatusEnum => (
                  <option value={notificationStatusEnum} key={notificationStatusEnum}>
                    {translate(`crmRealStateApp.NotificationStatusEnum.${notificationStatusEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('crmRealStateApp.notification.timestamp')}
                id="notification-timestamp"
                name="timestamp"
                data-cy="timestamp"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="notification-customer"
                name="customer"
                data-cy="customer"
                label={translate('crmRealStateApp.notification.customer')}
                type="select"
              >
                <option value="" key="0" />
                {customers
                  ? customers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notification" replace color="info">
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
