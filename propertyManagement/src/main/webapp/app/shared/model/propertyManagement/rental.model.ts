import dayjs from 'dayjs';
import { ContractStatusEnum } from 'app/shared/model/enumerations/contract-status-enum.model';

export interface IRental {
  id?: number;
  propertyId?: number;
  customerId?: number;
  oportunityId?: number | null;
  startDate?: dayjs.Dayjs;
  endDate?: dayjs.Dayjs;
  monthlyRent?: number;
  securityDeposit?: number | null;
  contractStatus?: keyof typeof ContractStatusEnum;
}

export const defaultValue: Readonly<IRental> = {};
