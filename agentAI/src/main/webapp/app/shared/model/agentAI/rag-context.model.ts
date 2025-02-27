import { EntityNameEnum } from 'app/shared/model/enumerations/entity-name-enum.model';

export interface IRAGContext {
  id?: string;
  entityId?: number;
  entityName?: keyof typeof EntityNameEnum | null;
  contextText?: string;
}

export const defaultValue: Readonly<IRAGContext> = {};
