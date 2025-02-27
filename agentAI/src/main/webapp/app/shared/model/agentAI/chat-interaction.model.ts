import dayjs from 'dayjs';
import { EntityNameEnum } from 'app/shared/model/enumerations/entity-name-enum.model';

export interface IChatInteraction {
  id?: string;
  entityId?: number;
  entityName?: keyof typeof EntityNameEnum | null;
  customerQuestion?: string;
  chatbotAnswer?: string;
  timestamp?: dayjs.Dayjs;
}

export const defaultValue: Readonly<IChatInteraction> = {};
