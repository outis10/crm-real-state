import dayjs from 'dayjs';

export interface IQuotation {
  id?: number;
  finalPrice?: number;
  validityDate?: dayjs.Dayjs;
  comments?: string | null;
}

export const defaultValue: Readonly<IQuotation> = {};
