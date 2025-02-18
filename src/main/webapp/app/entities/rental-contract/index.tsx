import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RentalContract from './rental-contract';
import RentalContractDetail from './rental-contract-detail';
import RentalContractUpdate from './rental-contract-update';
import RentalContractDeleteDialog from './rental-contract-delete-dialog';

const RentalContractRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RentalContract />} />
    <Route path="new" element={<RentalContractUpdate />} />
    <Route path=":id">
      <Route index element={<RentalContractDetail />} />
      <Route path="edit" element={<RentalContractUpdate />} />
      <Route path="delete" element={<RentalContractDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RentalContractRoutes;
