import { ICustomer } from 'app/shared/model/customer.model';

export interface IContact {
  id?: number;
  firstName?: string;
  middleName?: string | null;
  lastName?: string | null;
  email?: string;
  phone?: string | null;
  address?: string | null;
  city?: string | null;
  state?: string | null;
  postalCode?: string | null;
  country?: string | null;
  socialMediaProfiles?: string | null;
  notes?: string | null;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<IContact> = {};
