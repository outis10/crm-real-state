import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NearbyLocation from './nearby-location';
import NearbyLocationDetail from './nearby-location-detail';
import NearbyLocationUpdate from './nearby-location-update';
import NearbyLocationDeleteDialog from './nearby-location-delete-dialog';

const NearbyLocationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NearbyLocation />} />
    <Route path="new" element={<NearbyLocationUpdate />} />
    <Route path=":id">
      <Route index element={<NearbyLocationDetail />} />
      <Route path="edit" element={<NearbyLocationUpdate />} />
      <Route path="delete" element={<NearbyLocationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NearbyLocationRoutes;
