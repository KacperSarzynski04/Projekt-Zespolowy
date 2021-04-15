import { Component, OnInit } from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { Training } from '../../models/training/training';
import {TrainingService} from '../../services/training-service/training-service.service';

@Component({
  selector: 'app-training-list',
  templateUrl: './training-list.component.html',
  styleUrls: ['./training-list.component.css']
})
export class TrainingListComponent implements OnInit {

  trainings: Training[];

  constructor(private trainingService: TrainingService, private router: Router,
    private authService : AuthenticationService) {
    this.router.events.subscribe(e => {
      if(e instanceof NavigationEnd && this.router.url == "/trainings"){
        this.admin = this.authService.getUser().role == "ROLE_ADMIN";
      }
    })
  }

  ngOnInit(): void {
    this.trainingService.findAll().subscribe(data => {
      this.trainings = data;
      console.log(data);
    });
  }

  admin = false;
  
  public navToAddTrainings(){
    if(this.admin){
      this.router.navigateByUrl("/addtraining");
    }
  }

  renderAdmin() :void {
    //TODO: handle checking for administrator by using token
    this.admin = true;
    if(this.admin){
      console.log("X");
    }
  }
}

