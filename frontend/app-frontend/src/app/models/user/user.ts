export class User {
  public firstName: string;
  public lastName: string;
  public email: string;
  public lastLoginDate: Date;
  public active: boolean;
  public notLocked: boolean;
  public role: string;
  public authorities: [];

  constructor() {
    this.firstName = '';
    this.lastName = '';
    this.email = '';
    this.lastLoginDate = null;
    this.active = false;
    this.notLocked = false;
    this.role = '';
    this.authorities = [];
  }

}
