import React, { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { JhiItemCount, JhiPagination, TextFormat, Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './opportunity.reducer';

export const Opportunity = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const opportunityList = useAppSelector(state => state.crm.opportunity.entities);
  const loading = useAppSelector(state => state.crm.opportunity.loading);
  const totalItems = useAppSelector(state => state.crm.opportunity.totalItems);

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
      <h2 id="opportunity-heading" data-cy="OpportunityHeading">
        <Translate contentKey="crmApp.crmOpportunity.home.title">Opportunities</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="crmApp.crmOpportunity.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/crm/opportunity/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="crmApp.crmOpportunity.home.createLabel">Create new Opportunity</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {opportunityList && opportunityList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="crmApp.crmOpportunity.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="crmApp.crmOpportunity.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('budget')}>
                  <Translate contentKey="crmApp.crmOpportunity.budget">Budget</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('budget')} />
                </th>
                <th className="hand" onClick={sort('amount')}>
                  <Translate contentKey="crmApp.crmOpportunity.amount">Amount</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('amount')} />
                </th>
                <th className="hand" onClick={sort('probability')}>
                  <Translate contentKey="crmApp.crmOpportunity.probability">Probability</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('probability')} />
                </th>
                <th className="hand" onClick={sort('expectedCloseDate')}>
                  <Translate contentKey="crmApp.crmOpportunity.expectedCloseDate">Expected Close Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('expectedCloseDate')} />
                </th>
                <th className="hand" onClick={sort('stage')}>
                  <Translate contentKey="crmApp.crmOpportunity.stage">Stage</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('stage')} />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="crmApp.crmOpportunity.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('description')} />
                </th>
                <th className="hand" onClick={sort('createdAt')}>
                  <Translate contentKey="crmApp.crmOpportunity.createdAt">Created At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                </th>
                <th className="hand" onClick={sort('modifiedAt')}>
                  <Translate contentKey="crmApp.crmOpportunity.modifiedAt">Modified At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('modifiedAt')} />
                </th>
                <th className="hand" onClick={sort('closedAt')}>
                  <Translate contentKey="crmApp.crmOpportunity.closedAt">Closed At</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('closedAt')} />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="crmApp.crmOpportunity.createdBy">Created By</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                </th>
                <th>
                  <Translate contentKey="crmApp.crmOpportunity.customer">Customer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {opportunityList.map((opportunity, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/crm/opportunity/${opportunity.id}`} color="link" size="sm">
                      {opportunity.id}
                    </Button>
                  </td>
                  <td>{opportunity.name}</td>
                  <td>{opportunity.budget}</td>
                  <td>{opportunity.amount}</td>
                  <td>{opportunity.probability}</td>
                  <td>
                    {opportunity.expectedCloseDate ? (
                      <TextFormat type="date" value={opportunity.expectedCloseDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`crmApp.OpportunityStageEnum.${opportunity.stage}`} />
                  </td>
                  <td>{opportunity.description}</td>
                  <td>
                    {opportunity.createdAt ? <TextFormat type="date" value={opportunity.createdAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {opportunity.modifiedAt ? <TextFormat type="date" value={opportunity.modifiedAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{opportunity.closedAt ? <TextFormat type="date" value={opportunity.closedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{opportunity.createdBy}</td>
                  <td>
                    {opportunity.customer ? <Link to={`/crm/customer/${opportunity.customer.id}`}>{opportunity.customer.email}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/crm/opportunity/${opportunity.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/crm/opportunity/${opportunity.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                          (window.location.href = `/crm/opportunity/${opportunity.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
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
              <Translate contentKey="crmApp.crmOpportunity.home.notFound">No Opportunities found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={opportunityList && opportunityList.length > 0 ? '' : 'd-none'}>
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

export default Opportunity;
