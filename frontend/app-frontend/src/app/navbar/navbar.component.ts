import { Component, OnInit } from '@angular/core';
import { NavigationStart, Router, RouterEvent } from '@angular/router';
import { AuthenticationService } from '../services/authentication-service/authentication.service';
import {NotificationsEnum} from '../enum/notifications.enum';
import {NotificationsService} from '../services/notifications-service/notifications.service';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {ModalService} from '../modals/_modal';
import {PasswordChanger} from '../models/PasswordChanger/PasswordChanger';
import {UserService} from '../services/user-service/user-service.service';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService,
              private router: Router, private notificationsService: NotificationsService,
              private modalService: ModalService,
              private userService: UserService) {
  }

  oldPassword: string;
  newPassword: string;
  newPasswordCopy: string;

  public classes = {
    specialItem : true,
    item : true
  };

  ngOnInit(): void {
  }



  public logout(): void{
    this.authenticationService.logOut().subscribe(
      res => {
        console.log('Logging out');
      },
      err => {
        console.log(err);
      },
      () => {
        this.gotoHome();
      });


  }

  public isAdmin(){
      const role = this.authenticationService.getUser().role;
      return role == 'ROLE_ADMIN';
  }

  public isLoggedIn(){
   return this.authenticationService.isLogged();
  }

  public getName(){
    return this.authenticationService.getUser().firstName;
  }

  gotoHome(): void {
    this.authenticationService.clearCache();
    console.log('Going to home');
    this.router.navigate(['/home']);
    this.notificationsService.showMessage(NotificationsEnum.DEFAULT, 'Logged out');
  }

  changePassword(id: string): void{
    console.log('oldPassword: ' + this.oldPassword);
    console.log('real oldPassword: ' + this.authenticationService.getUser().password);
    console.log('newPassword: ' + this.newPassword);
    console.log('newPasswordCopy: ' + this.newPasswordCopy);
    if (this.newPassword === this.newPasswordCopy){
      const passwordChanger = new PasswordChanger();
      passwordChanger.user = this.authenticationService.getUser();
      passwordChanger.oldPassword = this.oldPassword;
      passwordChanger.newPassword = this.newPassword;
      const x = this.userService.changePassword(passwordChanger).subscribe(r => {console.log(r.status); if (r.status === 200) {
        this.notificationsService.showMessage(NotificationsEnum.SUCCESS, 'Hasło zostało zmienione');
        }
      });
    }else {
      this.notificationsService.showMessage(NotificationsEnum.ERROR, "Hasła się nie zgadzają!");
    }
  }

  closeModal(id: string) {
    this.modalService.close(id);
  }
  openModal(id: string) {
    this.modalService.open(id);
  }
}
