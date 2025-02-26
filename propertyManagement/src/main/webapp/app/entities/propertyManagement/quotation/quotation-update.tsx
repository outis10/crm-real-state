import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { createEntity, getEntity, reset, updateEntity } from './quotation.reducer';

export const QuotationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const quotationEntity = useAppSelector(state => state.propertymanagement.quotation.entity);
  const loading = useAppSelector(state => state.propertymanagement.quotation.loading);
  const updating = useAppSelector(state => state.propertymanagement.quotation.updating);
  const updateSuccess = useAppSelector(state => state.propertymanagement.quotation.updateSuccess);

  const handleClose = () => {
    navigate(`/propertymanagement/quotation${location.search}`);
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
    if (values.finalPrice !== undefined && typeof values.finalPrice !== 'number') {
      values.finalPrice = Number(values.finalPrice);
    }
    values.validityDate = convertDateTimeToServer(values.validityDate);

    const entity = {
      ...quotationEntity,
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
          validityDate: displayDefaultDateTime(),
        }
      : {
          ...quotationEntity,
          validityDate: convertDateTimeFromServer(quotationEntity.validityDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="propertyManagementApp.propertyManagementQuotation.home.createOrEditLabel" data-cy="QuotationCreateUpdateHeading">
            <Translate contentKey="propertyManagementApp.propertyManagementQuotation.home.createOrEditLabel">
              Create or edit a Quotation
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
                  id="quotation-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementQuotation.finalPrice')}
                id="quotation-finalPrice"
                name="finalPrice"
                data-cy="finalPrice"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementQuotation.validityDate')}
                id="quotation-validityDate"
                name="validityDate"
                data-cy="validityDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('propertyManagementApp.propertyManagementQuotation.comments')}
                id="quotation-comments"
                name="comments"
                data-cy="comments"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/propertymanagement/quotation"
                replace
                color="info"
              >
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

export default QuotationUpdate;
