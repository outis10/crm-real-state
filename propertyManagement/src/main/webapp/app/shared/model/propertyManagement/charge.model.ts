import dayjs from 'dayjs';
import { IRental } from 'app/shared/model/propertyManagement/rental.model';
import { ChargeTypeEnum } from 'app/shared/model/enumerations/charge-type-enum.model';
import { ChargeStatusEnum } from 'app/shared/model/enumerations/charge-status-enum.model';

export interface ICharge {
  id?: number;
  type?: keyof typeof ChargeTypeEnum;
  amount?: number;
  dueDate?: dayjs.Dayjs;
  status?: keyof typeof ChargeStatusEnum;
  rental?: IRental;
}

export const defaultValue: Readonly<ICharge> = {};
