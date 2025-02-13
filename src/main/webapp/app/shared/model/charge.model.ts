import dayjs from 'dayjs';
import { ICustomer } from 'app/shared/model/customer.model';
import { IRentalContract } from 'app/shared/model/rental-contract.model';
import { ChargeTypeEnum } from 'app/shared/model/enumerations/charge-type-enum.model';
import { ChargeStatusEnum } from 'app/shared/model/enumerations/charge-status-enum.model';

export interface ICharge {
  id?: number;
  type?: keyof typeof ChargeTypeEnum;
  amount?: number;
  dueDate?: dayjs.Dayjs;
  status?: keyof typeof ChargeStatusEnum;
  customer?: ICustomer | null;
  rentalContract?: IRentalContract;
}

export const defaultValue: Readonly<ICharge> = {};
