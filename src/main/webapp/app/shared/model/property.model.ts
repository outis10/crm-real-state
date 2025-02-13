import { OperationTypeEnum } from 'app/shared/model/enumerations/operation-type-enum.model';
import { PropertyStatusEnum } from 'app/shared/model/enumerations/property-status-enum.model';

export interface IProperty {
  id?: number;
  name?: string;
  codeName?: string | null;
  type?: string;
  operationType?: keyof typeof OperationTypeEnum;
  location?: string;
  city?: string;
  state?: string;
  postalCode?: string;
  price?: number;
  rentalPrice?: number | null;
  area?: number;
  bedrooms?: number | null;
  bathrooms?: number | null;
  appreciationRate?: number | null;
  features?: string | null;
  status?: keyof typeof PropertyStatusEnum;
  images?: string | null;
}

export const defaultValue: Readonly<IProperty> = {};
