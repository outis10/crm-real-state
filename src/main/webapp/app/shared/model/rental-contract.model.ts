import dayjs from 'dayjs';
import { IProperty } from 'app/shared/model/property.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { ContractStatusEnum } from 'app/shared/model/enumerations/contract-status-enum.model';

export interface IRentalContract {
  id?: number;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs;
  monthlyRent?: number;
  securityDeposit?: number | null;
  status?: keyof typeof ContractStatusEnum;
  property?: IProperty | null;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<IRentalContract> = {};
