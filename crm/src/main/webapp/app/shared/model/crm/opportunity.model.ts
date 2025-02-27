import dayjs from 'dayjs';
import { ICustomer } from 'app/shared/model/crm/customer.model';
import { OpportunityStageEnum } from 'app/shared/model/enumerations/opportunity-stage-enum.model';

export interface IOpportunity {
  id?: number;
  name?: string;
  budget?: number | null;
  amount?: number;
  probability?: number;
  expectedCloseDate?: dayjs.Dayjs;
  stage?: keyof typeof OpportunityStageEnum;
  description?: string | null;
  createdAt?: dayjs.Dayjs;
  modifiedAt?: dayjs.Dayjs | null;
  closedAt?: dayjs.Dayjs | null;
  createdBy?: number | null;
  customer?: ICustomer;
}

export const defaultValue: Readonly<IOpportunity> = {};
