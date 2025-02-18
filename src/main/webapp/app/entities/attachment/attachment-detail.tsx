import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, byteSize, openFile } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attachment.reducer';

export const AttachmentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attachmentEntity = useAppSelector(state => state.attachment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attachmentDetailsHeading">
          <Translate contentKey="crmRealStateApp.attachment.detail.title">Attachment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.id}</dd>
          <dt>
            <span id="file">
              <Translate contentKey="crmRealStateApp.attachment.file">File</Translate>
            </span>
          </dt>
          <dd>
            {attachmentEntity.file ? (
              <div>
                {attachmentEntity.fileContentType ? (
                  <a onClick={openFile(attachmentEntity.fileContentType, attachmentEntity.file)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {attachmentEntity.fileContentType}, {byteSize(attachmentEntity.file)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="entityId">
              <Translate contentKey="crmRealStateApp.attachment.entityId">Entity Id</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.entityId}</dd>
          <dt>
            <span id="entityName">
              <Translate contentKey="crmRealStateApp.attachment.entityName">Entity Name</Translate>
            </span>
          </dt>
          <dd>{attachmentEntity.entityName}</dd>
        </dl>
        <Button tag={Link} to="/attachment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attachment/${attachmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttachmentDetail;
