import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { EntityNameEnum } from 'app/shared/model/enumerations/entity-name-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './chat-interaction.reducer';

export const ChatInteractionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const chatInteractionEntity = useAppSelector(state => state.agentai.chatInteraction.entity);
  const loading = useAppSelector(state => state.agentai.chatInteraction.loading);
  const updating = useAppSelector(state => state.agentai.chatInteraction.updating);
  const updateSuccess = useAppSelector(state => state.agentai.chatInteraction.updateSuccess);
  const entityNameEnumValues = Object.keys(EntityNameEnum);

  const handleClose = () => {
    navigate(`/agentai/chat-interaction${location.search}`);
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
      ...chatInteractionEntity,
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
          ...chatInteractionEntity,
          timestamp: convertDateTimeFromServer(chatInteractionEntity.timestamp),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agentAiApp.agentAiChatInteraction.home.createOrEditLabel" data-cy="ChatInteractionCreateUpdateHeading">
            <Translate contentKey="agentAiApp.agentAiChatInteraction.home.createOrEditLabel">Create or edit a ChatInteraction</Translate>
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
                  id="chat-interaction-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agentAiApp.agentAiChatInteraction.entityId')}
                id="chat-interaction-entityId"
                name="entityId"
                data-cy="entityId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('agentAiApp.agentAiChatInteraction.entityName')}
                id="chat-interaction-entityName"
                name="entityName"
                data-cy="entityName"
                type="select"
              >
                {entityNameEnumValues.map(entityNameEnum => (
                  <option value={entityNameEnum} key={entityNameEnum}>
                    {translate(`agentAiApp.EntityNameEnum.${entityNameEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('agentAiApp.agentAiChatInteraction.customerQuestion')}
                id="chat-interaction-customerQuestion"
                name="customerQuestion"
                data-cy="customerQuestion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agentAiApp.agentAiChatInteraction.chatbotAnswer')}
                id="chat-interaction-chatbotAnswer"
                name="chatbotAnswer"
                data-cy="chatbotAnswer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('agentAiApp.agentAiChatInteraction.timestamp')}
                id="chat-interaction-timestamp"
                name="timestamp"
                data-cy="timestamp"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/agentai/chat-interaction" replace color="info">
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

export default ChatInteractionUpdate;
