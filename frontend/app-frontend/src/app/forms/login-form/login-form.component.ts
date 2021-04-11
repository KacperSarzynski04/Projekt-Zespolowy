import {Component} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user-service/user-service.service';
import { User } from '../../models/user/user';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordValidationServiceService} from "../../services/password-validation-service/password-validation-service.service";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})

export class LoginFormComponent {

  user: User;
  match: Boolean;

  userForms = new FormGroup({

    userEmail: new FormControl('',[
      Validators.required,
      Validators.pattern("[a-z0-9._%+-]+@comarch+\.(com|pl)$")]),

    userPassword: new FormControl('',[
      Validators.required]),

    confirmPassword: new FormControl('',[
      Validators.required,
    ]),
  });

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService) {
    this.user = new User();
  }

  onSubmit() {

    this.user.email = this.userForms.get('userEmail').value;
    this.user.password = this.userForms.get('userPassword').value;
    this.userService.save(this.user).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/users']);
  }

  get userName() {return this.userForms.get('userName')}

  get userSurname() {return this.userForms.get('userSurname')}

  get userEmail() {return this.userForms.get('userEmail')}

  get userPassword() {return this.userForms.get('userPassword')}

  get confirmPassword() {return this.userForms.get('confirmPassword')}

  checkPassword(){
    const password = this.userForms.get('userPassword').value;
    const confirmPassword = this.userForms.get('confirmPassword').value;
    return password === confirmPassword ? false : { notSame: true }
  }
}
