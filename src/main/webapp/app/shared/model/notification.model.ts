import dayjs from 'dayjs';
import { ICustomer } from 'app/shared/model/customer.model';
import { NotificationTargetEnum } from 'app/shared/model/enumerations/notification-target-enum.model';
import { NotificationStatusEnum } from 'app/shared/model/enumerations/notification-status-enum.model';

export interface INotification {
  id?: number;
  target?: keyof typeof NotificationTargetEnum;
  content?: string;
  status?: keyof typeof NotificationStatusEnum;
  timestamp?: dayjs.Dayjs;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<INotification> = {};
