export class User {
  public id: string;
  public firstName: string;
  public lastName: string;
  public email: string;
  public lastLoginDate: Date;
  public role: string;
  public authorities: [];
  public active: boolean;
  public notLocked: boolean;

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
