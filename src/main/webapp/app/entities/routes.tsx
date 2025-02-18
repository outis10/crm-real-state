import React from 'react';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import { Route } from 'react-router';
import Attachment from './attachment';
import Contact from './contact';
import Opportunity from './opportunity';
import NearbyLocation from './nearby-location';
import Property from './property';
import RentalContract from './rental-contract';
import Charge from './charge';
import Payment from './payment';
import Customer from './customer';
import ChatInteraction from './chat-interaction';
import Notification from './notification';
import Quotation from './quotation';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="attachment/*" element={<Attachment />} />
        <Route path="contact/*" element={<Contact />} />
        <Route path="opportunity/*" element={<Opportunity />} />
        <Route path="nearby-location/*" element={<NearbyLocation />} />
        <Route path="property/*" element={<Property />} />
        <Route path="rental-contract/*" element={<RentalContract />} />
        <Route path="charge/*" element={<Charge />} />
        <Route path="payment/*" element={<Payment />} />
        <Route path="customer/*" element={<Customer />} />
        <Route path="chat-interaction/*" element={<ChatInteraction />} />
        <Route path="notification/*" element={<Notification />} />
        <Route path="quotation/*" element={<Quotation />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
