import dayjs from 'dayjs';
import { EntityNameEnum } from 'app/shared/model/enumerations/entity-name-enum.model';
import { NotificationTargetEnum } from 'app/shared/model/enumerations/notification-target-enum.model';
import { NotificationStatusEnum } from 'app/shared/model/enumerations/notification-status-enum.model';

export interface INotification {
  id?: string;
  entityId?: number;
  entityName?: keyof typeof EntityNameEnum | null;
  target?: keyof typeof NotificationTargetEnum;
  content?: string;
  status?: keyof typeof NotificationStatusEnum;
  timestamp?: dayjs.Dayjs;
}

export const defaultValue: Readonly<INotification> = {};
