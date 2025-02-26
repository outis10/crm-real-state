import dayjs from 'dayjs';
import { SaleStatusEnum } from 'app/shared/model/enumerations/sale-status-enum.model';

export interface ISale {
  id?: number;
  propertyId?: number;
  customerId?: number;
  oportunityId?: number | null;
  saleDate?: dayjs.Dayjs;
  totalAmount?: number;
  status?: keyof typeof SaleStatusEnum | null;
}

export const defaultValue: Readonly<ISale> = {};
