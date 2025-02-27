import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RAGContext from './rag-context';
import RAGContextDetail from './rag-context-detail';
import RAGContextUpdate from './rag-context-update';
import RAGContextDeleteDialog from './rag-context-delete-dialog';

const RAGContextRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RAGContext />} />
    <Route path="new" element={<RAGContextUpdate />} />
    <Route path=":id">
      <Route index element={<RAGContextDetail />} />
      <Route path="edit" element={<RAGContextUpdate />} />
      <Route path="delete" element={<RAGContextDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RAGContextRoutes;
