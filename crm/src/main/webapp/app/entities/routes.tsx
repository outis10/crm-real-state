import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import { ReducersMapObject, combineReducers } from '@reduxjs/toolkit';

import getStore from 'app/config/store';

import entitiesReducers from './reducers';

import Customer from './crm/customer';
import Contact from './crm/contact';
import Opportunity from './crm/opportunity';
import Quotation from './crm/quotation';
import Property from './crm/property';
import Rental from './crm/rental';
import Sale from './crm/sale';
import Charge from './crm/charge';
import Payment from './crm/payment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  const store = getStore();
  store.injectReducer('crm', combineReducers(entitiesReducers as ReducersMapObject));
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="/customer/*" element={<Customer />} />
        <Route path="/contact/*" element={<Contact />} />
        <Route path="/opportunity/*" element={<Opportunity />} />
        <Route path="/quotation/*" element={<Quotation />} />
        <Route path="/property/*" element={<Property />} />
        <Route path="/rental/*" element={<Rental />} />
        <Route path="/sale/*" element={<Sale />} />
        <Route path="/charge/*" element={<Charge />} />
        <Route path="/payment/*" element={<Payment />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
