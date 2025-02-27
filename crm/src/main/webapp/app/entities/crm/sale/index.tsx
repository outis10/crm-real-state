import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sale from './sale';
import SaleDetail from './sale-detail';
import SaleUpdate from './sale-update';
import SaleDeleteDialog from './sale-delete-dialog';

const SaleRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sale />} />
    <Route path="new" element={<SaleUpdate />} />
    <Route path=":id">
      <Route index element={<SaleDetail />} />
      <Route path="edit" element={<SaleUpdate />} />
      <Route path="delete" element={<SaleDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SaleRoutes;
