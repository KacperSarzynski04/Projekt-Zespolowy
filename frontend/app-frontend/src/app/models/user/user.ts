export class User {
  public id: string;
  public firstName: string;
  public lastName: string;
  public email: string;
  public password: string;
  public lastLoginDate: Date;
  public active: boolean;
  public notLocked: boolean;
  public role: string;
  public authorities: [];

  constructor() {
    this.id = '';
    this.firstName = '';
    this.lastName = '';
    this.email = '';
    this.password = '';
    this.lastLoginDate = null;
    this.active = false;
    this.notLocked = false;
    this.role = '';
    this.authorities = [];
  }

}
