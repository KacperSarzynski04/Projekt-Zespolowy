import { Component, OnInit } from '@angular/core';
import { Training } from 'src/app/models/training/training';
import { TrainingService } from 'src/app/services/training-service/training-service.service';

@Component({
  selector: 'app-topic-list',
  templateUrl: './topic-list.component.html',
  styleUrls: ['./topic-list.component.css']
})
export class TopicListComponent implements OnInit {

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
