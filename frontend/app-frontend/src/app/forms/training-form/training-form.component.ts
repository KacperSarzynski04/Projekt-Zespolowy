import { Component, OnInit } from '@angular/core';
import { Training } from '../../models/training/training';
import {ActivatedRoute, NavigationEnd, NavigationStart, Router} from "@angular/router";
import {TrainingService} from "../../services/training-service/training-service.service";
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-training-form',
  templateUrl: './training-form.component.html',
  styleUrls: ['./training-form.component.css']
})
export class TrainingFormComponent implements OnInit {

  training: Training;
  selectedFile = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private trainingService: TrainingService,
    private authService: AuthenticationService) {
    this.training = new Training();
    this.router.events.subscribe(e => {
      if(e instanceof NavigationEnd && this.router.url =="/addtraining"){

        if(this.authService.getUser()==null || this.authService.getUser().role != "ROLE_ADMIN"){
          this.router.navigateByUrl("");
        }
      }
    })
  }

  ngOnInit(): void {
  }

  onSubmit() {

    this.trainingService.save(this.training).subscribe(result => this.gotoTrainingList());
  }

  gotoTrainingList() {
    this.router.navigate(['/trainings']);
  }

  onFileSelected(event){
    this.selectedFile = event.target.files[0];
  }
}
