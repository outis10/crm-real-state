import dayjs from 'dayjs';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IChatInteraction {
  id?: number;
  customerMessage?: string;
  chatbotResponse?: string;
  timestamp?: dayjs.Dayjs;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<IChatInteraction> = {};
