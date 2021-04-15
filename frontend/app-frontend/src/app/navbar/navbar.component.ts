import { Component, OnInit } from '@angular/core';
import { NavigationStart, Router, RouterEvent } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authenticationService : AuthenticationService, 
    private router : Router) { 
    this.router.events.subscribe(e => {
      if (e instanceof NavigationStart){
        this.reload();
      }
    })
  }

  ngOnInit(): void {
    this.loggedIn =this.authenticationService.isLogged();
  }

  public classes = {
    specialItem : true,
    item : true
  };

  public loggedIn;
  public reload(){
    //console.log("navbar: reload called");
    this.loggedIn = this.authenticationService.isLogged();
  }

  public logout(){
    this.authenticationService.logOut();
    this.reload();
  }
}
