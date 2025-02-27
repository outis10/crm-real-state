import dayjs from 'dayjs';
import { IRental } from 'app/shared/model/crm/rental.model';
import { PaymentMethodEnum } from 'app/shared/model/enumerations/payment-method-enum.model';

export interface IPayment {
  id?: number;
  amount?: number;
  paymentDate?: dayjs.Dayjs;
  paymentMethod?: keyof typeof PaymentMethodEnum;
  reference?: string | null;
  createdBy?: number | null;
  rental?: IRental;
}

export const defaultValue: Readonly<IPayment> = {};
