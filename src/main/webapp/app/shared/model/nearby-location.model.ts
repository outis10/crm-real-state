import { IProperty } from 'app/shared/model/property.model';

export interface INearbyLocation {
  id?: number;
  name?: string;
  type?: string;
  distance?: number;
  coordinates?: string | null;
  property?: IProperty;
}

export const defaultValue: Readonly<INearbyLocation> = {};
