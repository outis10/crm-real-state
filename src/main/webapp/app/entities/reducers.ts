import attachment from 'app/entities/attachment/attachment.reducer';
import contact from 'app/entities/contact/contact.reducer';
import opportunity from 'app/entities/opportunity/opportunity.reducer';
import nearbyLocation from 'app/entities/nearby-location/nearby-location.reducer';
import property from 'app/entities/property/property.reducer';
import rentalContract from 'app/entities/rental-contract/rental-contract.reducer';
import charge from 'app/entities/charge/charge.reducer';
import payment from 'app/entities/payment/payment.reducer';
import customer from 'app/entities/customer/customer.reducer';
import chatInteraction from 'app/entities/chat-interaction/chat-interaction.reducer';
import notification from 'app/entities/notification/notification.reducer';
import quotation from 'app/entities/quotation/quotation.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  attachment,
  contact,
  opportunity,
  nearbyLocation,
  property,
  rentalContract,
  charge,
  payment,
  customer,
  chatInteraction,
  notification,
  quotation,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
