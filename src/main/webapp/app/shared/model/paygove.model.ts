export interface IPaygove {
  id?: number;
  cik?: string;
  ccc?: string | null;
  paymentAmount?: number | null;
  name?: string | null;
  email?: string | null;
  phone?: string | null;
}

export const defaultValue: Readonly<IPaygove> = {};
