import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authenticationService : AuthenticationService) { }

  ngOnInit(): void {
    this.loggedIn=this.authenticationService.isLogged();
  }

  public classes = {
    specialItem : true,
    item : true
  };

  public loggedIn;
  public reload(){
    console.log("navbar: reload called");
    this.loggedIn = this.authenticationService.isLogged();
  }
  
  public logout(){
    this.authenticationService.logOut();
    this.reload();
  }
}
