import dayjs from 'dayjs';
import { IProperty } from 'app/shared/model/crm/property.model';
import { ICustomer } from 'app/shared/model/crm/customer.model';
import { IOpportunity } from 'app/shared/model/crm/opportunity.model';
import { ContractStatusEnum } from 'app/shared/model/enumerations/contract-status-enum.model';

export interface IRental {
  id?: number;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs;
  monthlyRent?: number;
  securityDeposit?: number | null;
  contractStatus?: keyof typeof ContractStatusEnum;
  createdBy?: number | null;
  property?: IProperty | null;
  customer?: ICustomer | null;
  opportunity?: IOpportunity | null;
}

export const defaultValue: Readonly<IRental> = {};
