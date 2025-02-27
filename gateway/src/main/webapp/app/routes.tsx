import React, { Suspense } from 'react';
import { Route } from 'react-router';

import Loadable from 'react-loadable';
import { importRemote } from '@module-federation/utilities';

import LoginRedirect from 'app/modules/login/login-redirect';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import EntitiesRoutes from 'app/entities/routes';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';

const loading = <div>loading ...</div>;

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => loading,
});

const CrmRoutes = React.lazy(() =>
  importRemote<any>({
    url: `./services/crm`,
    scope: 'crm',
    module: './entities-routes',
  }).catch(() => import('app/shared/error/error-loading')),
);

const NotificationRoutes = React.lazy(() =>
  importRemote<any>({
    url: `./services/notification`,
    scope: 'notification',
    module: './entities-routes',
  }).catch(() => import('app/shared/error/error-loading')),
);

const AttachmentRoutes = React.lazy(() =>
  importRemote<any>({
    url: `./services/attachment`,
    scope: 'attachment',
    module: './entities-routes',
  }).catch(() => import('app/shared/error/error-loading')),
);

const AgentAIRoutes = React.lazy(() =>
  importRemote<any>({
    url: `./services/agentai`,
    scope: 'agentai',
    module: './entities-routes',
  }).catch(() => import('app/shared/error/error-loading')),
);

const AppRoutes = () => {
  return (
    <div className="view-routes">
      <ErrorBoundaryRoutes>
        <Route index element={<Home />} />
        <Route path="logout" element={<Logout />} />
        <Route
          path="admin/*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.ADMIN]}>
              <Admin />
            </PrivateRoute>
          }
        />
        <Route path="sign-in" element={<LoginRedirect />} />
        <Route
          path="crm/*"
          element={
            <Suspense fallback={loading}>
              <PrivateRoute hasAnyAuthorities={[AUTHORITIES.USER]}>
                <CrmRoutes />
              </PrivateRoute>
            </Suspense>
          }
        />
        <Route
          path="notification/*"
          element={
            <Suspense fallback={loading}>
              <PrivateRoute hasAnyAuthorities={[AUTHORITIES.USER]}>
                <NotificationRoutes />
              </PrivateRoute>
            </Suspense>
          }
        />
        <Route
          path="attachment/*"
          element={
            <Suspense fallback={loading}>
              <PrivateRoute hasAnyAuthorities={[AUTHORITIES.USER]}>
                <AttachmentRoutes />
              </PrivateRoute>
            </Suspense>
          }
        />
        <Route
          path="agentai/*"
          element={
            <Suspense fallback={loading}>
              <PrivateRoute hasAnyAuthorities={[AUTHORITIES.USER]}>
                <AgentAIRoutes />
              </PrivateRoute>
            </Suspense>
          }
        />
        <Route
          path="*"
          element={
            <PrivateRoute hasAnyAuthorities={[AUTHORITIES.USER]}>
              <EntitiesRoutes />
            </PrivateRoute>
          }
        />
        <Route path="*" element={<PageNotFound />} />
      </ErrorBoundaryRoutes>
    </div>
  );
};

export default AppRoutes;
