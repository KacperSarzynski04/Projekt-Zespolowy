import { Component, OnInit } from '@angular/core';
import { Training } from '../../models/training/training';
import {TrainingService} from "../../services/training-service/training-service.service";

@Component({
  selector: 'app-training-list',
  templateUrl: './training-list.component.html',
  styleUrls: ['./training-list.component.css']
})
export class TrainingListComponent implements OnInit {

  trainings: Training[];

  constructor(private trainingService: TrainingService) {
  }

  ngOnInit(): void {
    this.trainingService.findAll().subscribe(data => {
      this.trainings = data;
      console.log(data);
    });
  }
}

