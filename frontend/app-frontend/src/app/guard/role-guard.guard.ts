import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from '../services/authentication.service';
import {NotificationsService} from '../services/notifications.service';
import {NotificationsEnum} from '../enum/notifications.enum';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(private authenticationService: AuthenticationService, private router: Router,
              private notificationsService: NotificationsService) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.isAdmin();
  }


  private isAdmin(): boolean {
    if (this.authenticationService.isAdmin()) {
      return true;
    }
    this.notificationsService.showMessage(NotificationsEnum.DEFAULT, `You need to log in as an admin ad to access this page`);
    this.router.navigate(['/home']);
    console.log('Not an admin');
    return false;
  }
}
