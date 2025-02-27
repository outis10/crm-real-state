import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ChatInteraction from './chat-interaction';
import ChatInteractionDetail from './chat-interaction-detail';
import ChatInteractionUpdate from './chat-interaction-update';
import ChatInteractionDeleteDialog from './chat-interaction-delete-dialog';

const ChatInteractionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ChatInteraction />} />
    <Route path="new" element={<ChatInteractionUpdate />} />
    <Route path=":id">
      <Route index element={<ChatInteractionDetail />} />
      <Route path="edit" element={<ChatInteractionUpdate />} />
      <Route path="delete" element={<ChatInteractionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ChatInteractionRoutes;
