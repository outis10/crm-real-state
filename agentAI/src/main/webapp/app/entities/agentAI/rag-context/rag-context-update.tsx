import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { EntityNameEnum } from 'app/shared/model/enumerations/entity-name-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './rag-context.reducer';

export const RAGContextUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const rAGContextEntity = useAppSelector(state => state.agentai.rAGContext.entity);
  const loading = useAppSelector(state => state.agentai.rAGContext.loading);
  const updating = useAppSelector(state => state.agentai.rAGContext.updating);
  const updateSuccess = useAppSelector(state => state.agentai.rAGContext.updateSuccess);
  const entityNameEnumValues = Object.keys(EntityNameEnum);

  const handleClose = () => {
    navigate(`/agentai/rag-context${location.search}`);
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

    const entity = {
      ...rAGContextEntity,
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
      ? {}
      : {
          entityName: 'PROPERTY',
          ...rAGContextEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agentAiApp.agentAiRAgContext.home.createOrEditLabel" data-cy="RAGContextCreateUpdateHeading">
            <Translate contentKey="agentAiApp.agentAiRAgContext.home.createOrEditLabel">Create or edit a RAGContext</Translate>
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
                  id="rag-context-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('agentAiApp.agentAiRAgContext.entityId')}
                id="rag-context-entityId"
                name="entityId"
                data-cy="entityId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('agentAiApp.agentAiRAgContext.entityName')}
                id="rag-context-entityName"
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
                label={translate('agentAiApp.agentAiRAgContext.contextText')}
                id="rag-context-contextText"
                name="contextText"
                data-cy="contextText"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/agentai/rag-context" replace color="info">
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

export default RAGContextUpdate;
