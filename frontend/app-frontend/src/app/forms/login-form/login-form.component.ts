import {Component, EventEmitter, OnDestroy, OnInit, Output} from '@angular/core';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {AuthenticationService} from '../../services/authentication-service/authentication.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {User} from '../../models/user/user';
import {HeadersPrefix} from '../../enum/headers.enum';
import {NotificationsEnum} from '../../enum/notifications.enum';
import {NotificationsService} from '../../services/notifications-service/notifications.service';
import { ModalService } from 'src/app/modals/_modal';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  constructor(private router: Router, private authenticationService: AuthenticationService,
              private notificationsService: NotificationsService, private modalService: ModalService) { }

  @Output() loginEvent = new EventEmitter();


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
          this.notificationsService.showMessage(NotificationsEnum.SUCCESS, 'Logged in!');
          this.router.navigateByUrl('/home');
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendErrorNotification(NotificationsEnum.ERROR, errorResponse.error.message);
        }
      )
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  private sendErrorNotification(notificationType: NotificationsEnum, message: string): void {
    if (message) {
      this.notificationsService.showMessage(notificationType, message);
    } else {
      this.notificationsService.showMessage(notificationType, 'Bład. Sprobuj jeszcze raz');
    }
  }
  closeModal(id: string) {
    this.modalService.close(id);
  }
  openModal(id: string) {
    this.modalService.open(id);
  }

  sendPassword(mail: HTMLInputElement){
    this.modalService.send(mail.value).subscribe();
  }
}
