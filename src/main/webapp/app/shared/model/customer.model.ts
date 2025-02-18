export interface ICustomer {
  id?: number;
  firstName?: string;
  middleName?: string | null;
  lastName?: string | null;
  email?: string;
  phone?: string | null;
  address?: string | null;
  city?: string | null;
  state?: string | null;
  postalCode?: string | null;
  country?: string | null;
  socialMediaProfiles?: string | null;
  notes?: string | null;
  preferences?: string | null;
  budget?: number | null;
  rentalBudget?: number | null;
  interactionHistory?: string | null;
}

export const defaultValue: Readonly<ICustomer> = {};
