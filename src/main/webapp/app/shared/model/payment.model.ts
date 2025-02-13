import dayjs from 'dayjs';
import { IRentalContract } from 'app/shared/model/rental-contract.model';
import { PaymentMethodEnum } from 'app/shared/model/enumerations/payment-method-enum.model';

export interface IPayment {
  id?: number;
  amount?: number;
  paymentDate?: dayjs.Dayjs;
  paymentMethod?: keyof typeof PaymentMethodEnum;
  reference?: string | null;
  rentralContract?: IRentalContract;
}

export const defaultValue: Readonly<IPayment> = {};
