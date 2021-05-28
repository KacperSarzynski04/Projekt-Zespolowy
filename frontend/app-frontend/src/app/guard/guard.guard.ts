import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from '../services/authentication-service/authentication.service';
import {NotificationsService} from '../services/notifications-service/notifications.service';
import {NotificationsEnum} from '../enum/notifications.enum';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authenticationService: AuthenticationService, private router: Router, private notificationsService: NotificationsService) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.isUserLoggedIn();
  }

  private isUserLoggedIn(): boolean {
    if (this.authenticationService.isLogged()) {
      return true;
    }
    console.log("Guard");
    this.router.navigate(['/login']);

    this.notificationsService.showMessage(NotificationsEnum.WARNING, "Login first");
    return false;
  }
}
