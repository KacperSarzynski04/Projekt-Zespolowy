import { Component, OnInit } from '@angular/core';
import { NavigationStart, Router, RouterEvent } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import {NotificationsEnum} from '../enum/notifications.enum';
import {NotificationsService} from '../services/notifications.service';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authenticationService : AuthenticationService,
    private router : Router, private notificationsService: NotificationsService) {
  }

  ngOnInit(): void {
  }

  public classes = {
    specialItem : true,
    item : true
  };

  

  public logout(){
    this.authenticationService.logOut();
    this.notificationsService.showMessage(NotificationsEnum.DEFAULT, "Logged out");
    this.router.navigate(['/home']);
  }

  public isAdmin(){
      let role = this.authenticationService.getUser().role; 
      return role == "ROLE_ADMIN";
  }

  public isLoggedIn(){
   return this.authenticationService.isLogged();
  }

  
}
