import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedBlobField, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { EntityNameEnum } from 'app/shared/model/enumerations/entity-name-enum.model';
import { createEntity, getEntity, reset, updateEntity } from './attachment.reducer';

export const AttachmentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const attachmentEntity = useAppSelector(state => state.attachment.entity);
  const loading = useAppSelector(state => state.attachment.loading);
  const updating = useAppSelector(state => state.attachment.updating);
  const updateSuccess = useAppSelector(state => state.attachment.updateSuccess);
  const entityNameEnumValues = Object.keys(EntityNameEnum);

  const handleClose = () => {
    navigate(`/attachment${location.search}`);
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
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.entityId !== undefined && typeof values.entityId !== 'number') {
      values.entityId = Number(values.entityId);
    }

    const entity = {
      ...attachmentEntity,
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
          ...attachmentEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="crmRealStateApp.attachment.home.createOrEditLabel" data-cy="AttachmentCreateUpdateHeading">
            <Translate contentKey="crmRealStateApp.attachment.home.createOrEditLabel">Create or edit a Attachment</Translate>
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
                  id="attachment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedBlobField
                label={translate('crmRealStateApp.attachment.file')}
                id="attachment-file"
                name="file"
                data-cy="file"
                openActionLabel={translate('entity.action.open')}
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.attachment.entityId')}
                id="attachment-entityId"
                name="entityId"
                data-cy="entityId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('crmRealStateApp.attachment.entityName')}
                id="attachment-entityName"
                name="entityName"
                data-cy="entityName"
                type="select"
              >
                {entityNameEnumValues.map(entityNameEnum => (
                  <option value={entityNameEnum} key={entityNameEnum}>
                    {translate(`crmRealStateApp.EntityNameEnum.${entityNameEnum}`)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/attachment" replace color="info">
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

export default AttachmentUpdate;
