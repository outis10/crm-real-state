import dayjs from 'dayjs';
import { ICustomer } from 'app/shared/model/customer.model';
import { IProperty } from 'app/shared/model/property.model';

export interface IQuotation {
  id?: number;
  finalPrice?: number;
  validityDate?: dayjs.Dayjs;
  comments?: string | null;
  customer?: ICustomer | null;
  property?: IProperty | null;
}

export const defaultValue: Readonly<IQuotation> = {};
