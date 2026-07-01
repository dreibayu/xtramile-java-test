export type Gender = 'MALE' | 'FEMALE' | 'OTHER';

export interface Patient {
  id?: number;
  pid: string;
  firstName: string;
  lastName: string;
  fullName?: string;
  dateOfBirth: string;
  gender: Gender;
  phoneNo: string;
  address: string;
  suburb: string;
  state: string;
  postcode: string;
}

export interface PagedResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
}