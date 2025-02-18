import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Charge from './charge';
import ChargeDetail from './charge-detail';
import ChargeUpdate from './charge-update';
import ChargeDeleteDialog from './charge-delete-dialog';

const ChargeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Charge />} />
    <Route path="new" element={<ChargeUpdate />} />
    <Route path=":id">
      <Route index element={<ChargeDetail />} />
      <Route path="edit" element={<ChargeUpdate />} />
      <Route path="delete" element={<ChargeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ChargeRoutes;
