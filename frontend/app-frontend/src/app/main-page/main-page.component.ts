import { Component, OnInit } from '@angular/core';
import {Training} from "../models/training/training";
import {TrainingService} from "../services/training-service/training-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  trainings: Training[];

  constructor(private trainingService: TrainingService, private router: Router) {
  }

  ngOnInit(): void {
    this.trainingService.findThree().subscribe(data => {
      this.trainings = data;
      console.log(data);
    });
  }

}
