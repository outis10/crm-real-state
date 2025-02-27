import customer from 'app/entities/crm/customer/customer.reducer';
import contact from 'app/entities/crm/contact/contact.reducer';
import opportunity from 'app/entities/crm/opportunity/opportunity.reducer';
import quotation from 'app/entities/crm/quotation/quotation.reducer';
import property from 'app/entities/crm/property/property.reducer';
import rental from 'app/entities/crm/rental/rental.reducer';
import sale from 'app/entities/crm/sale/sale.reducer';
import charge from 'app/entities/crm/charge/charge.reducer';
import payment from 'app/entities/crm/payment/payment.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  customer,
  contact,
  opportunity,
  quotation,
  property,
  rental,
  sale,
  charge,
  payment,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
