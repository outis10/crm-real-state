import { EntityNameEnum } from 'app/shared/model/enumerations/entity-name-enum.model';

export interface IAttachment {
  id?: number;
  fileContentType?: string;
  file?: string;
  entityId?: number;
  entityName?: keyof typeof EntityNameEnum | null;
}

export const defaultValue: Readonly<IAttachment> = {};
