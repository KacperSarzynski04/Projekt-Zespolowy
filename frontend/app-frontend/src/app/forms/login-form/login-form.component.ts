import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/authentication.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {User} from '../../models/user/user';
import { HeadersPrefix } from '../../enum/headers.enum';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  constructor(private router: Router, private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    if (this.authenticationService.isLogged()) {
      this.router.navigateByUrl('');
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  onLogin(user: User): void {
    this.subscriptions.push(
      this.authenticationService.login(user).subscribe(
        (response: HttpResponse<User>) => {
          const token = response.headers.get(HeadersPrefix.JWT_TOKEN);
          this.authenticationService.saveToken(token);
          console.log(token);
          this.authenticationService.saveUser(response.body);
          this.router.navigateByUrl('/home');
        },
      )
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

}
