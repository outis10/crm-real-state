import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './property.reducer';

export const Property = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const propertyList = useAppSelector(state => state.property.entities);
  const loading = useAppSelector(state => state.property.loading);
  const links = useAppSelector(state => state.property.links);
  const updateSuccess = useAppSelector(state => state.property.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="property-heading" data-cy="PropertyHeading">
        <Translate contentKey="crmRealStateApp.property.home.title">Properties</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="crmRealStateApp.property.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/property/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="crmRealStateApp.property.home.createLabel">Create new Property</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={propertyList ? propertyList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {propertyList && propertyList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="crmRealStateApp.property.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('name')}>
                    <Translate contentKey="crmRealStateApp.property.name">Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                  </th>
                  <th className="hand" onClick={sort('codeName')}>
                    <Translate contentKey="crmRealStateApp.property.codeName">Code Name</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('codeName')} />
                  </th>
                  <th className="hand" onClick={sort('type')}>
                    <Translate contentKey="crmRealStateApp.property.type">Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                  </th>
                  <th className="hand" onClick={sort('operationType')}>
                    <Translate contentKey="crmRealStateApp.property.operationType">Operation Type</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('operationType')} />
                  </th>
                  <th className="hand" onClick={sort('location')}>
                    <Translate contentKey="crmRealStateApp.property.location">Location</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('location')} />
                  </th>
                  <th className="hand" onClick={sort('city')}>
                    <Translate contentKey="crmRealStateApp.property.city">City</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('city')} />
                  </th>
                  <th className="hand" onClick={sort('state')}>
                    <Translate contentKey="crmRealStateApp.property.state">State</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('state')} />
                  </th>
                  <th className="hand" onClick={sort('postalCode')}>
                    <Translate contentKey="crmRealStateApp.property.postalCode">Postal Code</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('postalCode')} />
                  </th>
                  <th className="hand" onClick={sort('price')}>
                    <Translate contentKey="crmRealStateApp.property.price">Price</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('price')} />
                  </th>
                  <th className="hand" onClick={sort('rentalPrice')}>
                    <Translate contentKey="crmRealStateApp.property.rentalPrice">Rental Price</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('rentalPrice')} />
                  </th>
                  <th className="hand" onClick={sort('area')}>
                    <Translate contentKey="crmRealStateApp.property.area">Area</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('area')} />
                  </th>
                  <th className="hand" onClick={sort('bedrooms')}>
                    <Translate contentKey="crmRealStateApp.property.bedrooms">Bedrooms</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('bedrooms')} />
                  </th>
                  <th className="hand" onClick={sort('bathrooms')}>
                    <Translate contentKey="crmRealStateApp.property.bathrooms">Bathrooms</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('bathrooms')} />
                  </th>
                  <th className="hand" onClick={sort('appreciationRate')}>
                    <Translate contentKey="crmRealStateApp.property.appreciationRate">Appreciation Rate</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('appreciationRate')} />
                  </th>
                  <th className="hand" onClick={sort('features')}>
                    <Translate contentKey="crmRealStateApp.property.features">Features</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('features')} />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    <Translate contentKey="crmRealStateApp.property.status">Status</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                  </th>
                  <th className="hand" onClick={sort('images')}>
                    <Translate contentKey="crmRealStateApp.property.images">Images</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('images')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {propertyList.map((property, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/property/${property.id}`} color="link" size="sm">
                        {property.id}
                      </Button>
                    </td>
                    <td>{property.name}</td>
                    <td>{property.codeName}</td>
                    <td>{property.type}</td>
                    <td>
                      <Translate contentKey={`crmRealStateApp.OperationTypeEnum.${property.operationType}`} />
                    </td>
                    <td>{property.location}</td>
                    <td>{property.city}</td>
                    <td>{property.state}</td>
                    <td>{property.postalCode}</td>
                    <td>{property.price}</td>
                    <td>{property.rentalPrice}</td>
                    <td>{property.area}</td>
                    <td>{property.bedrooms}</td>
                    <td>{property.bathrooms}</td>
                    <td>{property.appreciationRate}</td>
                    <td>{property.features}</td>
                    <td>
                      <Translate contentKey={`crmRealStateApp.PropertyStatusEnum.${property.status}`} />
                    </td>
                    <td>{property.images}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/property/${property.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/property/${property.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/property/${property.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="crmRealStateApp.property.home.notFound">No Properties found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Property;
