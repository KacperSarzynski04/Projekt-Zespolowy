import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register-confirmation',
  templateUrl: './register-confirmation.component.html',
  styleUrls: ['./register-confirmation.component.css']
})
export class RegisterConfirmationComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  onClick(): void {
    this.router.navigateByUrl('/home');
  }
}
