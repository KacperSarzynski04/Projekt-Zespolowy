import {User} from '../user/user';

export class PasswordChanger {
  public user: User;
  public oldPassword: string;
  public newPassword: string;


  constructor() {
    this.user = null;
    this.oldPassword = '';
    this.newPassword = '';
  }
}
