import { Component, OnInit } from '@angular/core';
import { Training } from '../../models/training/training';
import {ActivatedRoute, GuardsCheckStart, NavigationEnd, NavigationStart, Router} from "@angular/router";
import {TrainingService} from "../../services/training-service/training-service.service";

@Component({
  selector: 'app-training-form',
  templateUrl: './training-form.component.html',
  styleUrls: ['./training-form.component.css']
})
export class TrainingFormComponent implements OnInit {

  training: Training;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private trainingService: TrainingService) {
    this.training = new Training();
    this.router.events.subscribe(e => {
      if(e instanceof NavigationEnd){
        //TODO: check for admin
        let admin = true;
        if(!admin){
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
}
