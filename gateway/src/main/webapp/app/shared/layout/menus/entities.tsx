import React, { Suspense } from 'react';
import { translate } from 'react-jhipster';
import { importRemote } from '@module-federation/utilities';
import { NavDropdown } from './menu-components';

const EntitiesMenuItems = React.lazy(() => import('app/entities/menu').catch(() => import('app/shared/error/error-loading')));

const CrmEntitiesMenuItems = React.lazy(async () =>
  importRemote<any>({
    url: `./services/crm`,
    scope: 'crm',
    module: './entities-menu',
  }).catch(() => import('app/shared/error/error-loading')),
);

const NotificationEntitiesMenuItems = React.lazy(async () =>
  importRemote<any>({
    url: `./services/notification`,
    scope: 'notification',
    module: './entities-menu',
  }).catch(() => import('app/shared/error/error-loading')),
);

const AttachmentEntitiesMenuItems = React.lazy(async () =>
  importRemote<any>({
    url: `./services/attachment`,
    scope: 'attachment',
    module: './entities-menu',
  }).catch(() => import('app/shared/error/error-loading')),
);

const AgentAIEntitiesMenuItems = React.lazy(async () =>
  importRemote<any>({
    url: `./services/agentai`,
    scope: 'agentai',
    module: './entities-menu',
  }).catch(() => import('app/shared/error/error-loading')),
);

export const EntitiesMenu = () => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <Suspense fallback={<div>loading...</div>}>
      <EntitiesMenuItems />
    </Suspense>
    <Suspense fallback={<div>loading...</div>}>
      <CrmEntitiesMenuItems />
    </Suspense>
    <Suspense fallback={<div>loading...</div>}>
      <NotificationEntitiesMenuItems />
    </Suspense>
    <Suspense fallback={<div>loading...</div>}>
      <AttachmentEntitiesMenuItems />
    </Suspense>
    <Suspense fallback={<div>loading...</div>}>
      <AgentAIEntitiesMenuItems />
    </Suspense>
  </NavDropdown>
);
