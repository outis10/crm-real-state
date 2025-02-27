import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { JhiItemCount, JhiPagination, TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './rental.reducer';

export const Rental = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const rentalList = useAppSelector(state => state.crm.rental.entities);
  const loading = useAppSelector(state => state.crm.rental.loading);
  const totalItems = useAppSelector(state => state.crm.rental.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
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
      <h2 id="rental-heading" data-cy="RentalHeading">
        <Translate contentKey="crmApp.crmRental.home.title">Rentals</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="crmApp.crmRental.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/crm/rental/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="crmApp.crmRental.home.createLabel">Create new Rental</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {rentalList && rentalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="crmApp.crmRental.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('startDate')}>
                  <Translate contentKey="crmApp.crmRental.startDate">Start Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('startDate')} />
                </th>
                <th className="hand" onClick={sort('endDate')}>
                  <Translate contentKey="crmApp.crmRental.endDate">End Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('endDate')} />
                </th>
                <th className="hand" onClick={sort('monthlyRent')}>
                  <Translate contentKey="crmApp.crmRental.monthlyRent">Monthly Rent</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('monthlyRent')} />
                </th>
                <th className="hand" onClick={sort('securityDeposit')}>
                  <Translate contentKey="crmApp.crmRental.securityDeposit">Security Deposit</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('securityDeposit')} />
                </th>
                <th className="hand" onClick={sort('contractStatus')}>
                  <Translate contentKey="crmApp.crmRental.contractStatus">Contract Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('contractStatus')} />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="crmApp.crmRental.createdBy">Created By</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                </th>
                <th>
                  <Translate contentKey="crmApp.crmRental.property">Property</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="crmApp.crmRental.customer">Customer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="crmApp.crmRental.opportunity">Opportunity</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {rentalList.map((rental, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/crm/rental/${rental.id}`} color="link" size="sm">
                      {rental.id}
                    </Button>
                  </td>
                  <td>{rental.startDate ? <TextFormat type="date" value={rental.startDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{rental.endDate ? <TextFormat type="date" value={rental.endDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{rental.monthlyRent}</td>
                  <td>{rental.securityDeposit}</td>
                  <td>
                    <Translate contentKey={`crmApp.ContractStatusEnum.${rental.contractStatus}`} />
                  </td>
                  <td>{rental.createdBy}</td>
                  <td>{rental.property ? <Link to={`/crm/property/${rental.property.id}`}>{rental.property.codeName}</Link> : ''}</td>
                  <td>{rental.customer ? <Link to={`/crm/customer/${rental.customer.id}`}>{rental.customer.id}</Link> : ''}</td>
                  <td>{rental.opportunity ? <Link to={`/crm/opportunity/${rental.opportunity.id}`}>{rental.opportunity.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/crm/rental/${rental.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/crm/rental/${rental.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/crm/rental/${rental.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
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
              <Translate contentKey="crmApp.crmRental.home.notFound">No Rentals found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={rentalList && rentalList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Rental;
