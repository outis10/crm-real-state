import quotation from 'app/entities/propertyManagement/quotation/quotation.reducer';
import rental from 'app/entities/propertyManagement/rental/rental.reducer';
import sale from 'app/entities/propertyManagement/sale/sale.reducer';
import charge from 'app/entities/propertyManagement/charge/charge.reducer';
import payment from 'app/entities/propertyManagement/payment/payment.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  quotation,
  rental,
  sale,
  charge,
  payment,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
