import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './paygove.reducer';
import { IPaygove } from 'app/shared/model/paygove.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PaygoveUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const paygoveEntity = useAppSelector(state => state.paygove.entity);
  const loading = useAppSelector(state => state.paygove.loading);
  const updating = useAppSelector(state => state.paygove.updating);
  const updateSuccess = useAppSelector(state => state.paygove.updateSuccess);
  const handleClose = () => {
    props.history.push('/paygove');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...paygoveEntity,
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
          ...paygoveEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="payGovRApp.paygove.home.createOrEditLabel" data-cy="PaygoveCreateUpdateHeading">
            Create or edit a Paygove
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="paygove-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Cik"
                id="paygove-cik"
                name="cik"
                data-cy="cik"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  minLength: { value: 3, message: 'This field is required to be at least 3 characters.' },
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                 // pattern: { value: /^[0-9]+[A-Za-z0-9]+[@#$%^&*()!]/, message: "This field should follow pattern for '^[a-zA-Z0-9]*.." },
                }}
              />
              <ValidatedField label="Ccc" id="paygove-ccc" name="ccc" data-cy="ccc" type="text" />
              <ValidatedField label="Payment Amount" id="paygove-paymentAmount" name="paymentAmount" data-cy="paymentAmount" type="text" />
              <ValidatedField label="Name" id="paygove-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Email" id="paygove-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Phone" id="paygove-phone" name="phone" data-cy="phone" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/paygove" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PaygoveUpdate;
