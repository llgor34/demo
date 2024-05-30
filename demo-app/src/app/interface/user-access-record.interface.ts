import { UserAccess } from './user-access.interface';

export interface UserAccessRecord extends UserAccess {
  date: string;
  value: number;
}
