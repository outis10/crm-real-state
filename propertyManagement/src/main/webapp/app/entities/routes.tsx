import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import { ReducersMapObject, combineReducers } from '@reduxjs/toolkit';

import getStore from 'app/config/store';

import entitiesReducers from './reducers';

import Quotation from './propertyManagement/quotation';
import Rental from './propertyManagement/rental';
import Sale from './propertyManagement/sale';
import Charge from './propertyManagement/charge';
import Payment from './propertyManagement/payment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  const store = getStore();
  store.injectReducer('propertymanagement', combineReducers(entitiesReducers as ReducersMapObject));
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="/quotation/*" element={<Quotation />} />
        <Route path="/rental/*" element={<Rental />} />
        <Route path="/sale/*" element={<Sale />} />
        <Route path="/charge/*" element={<Charge />} />
        <Route path="/payment/*" element={<Payment />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
