import { Component, OnInit } from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication-service/authentication.service';
import { Training } from '../../models/training/training';
import {TrainingService} from '../../services/training-service/training-service.service';
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-training-list',
  templateUrl: './training-list.component.html',
  styleUrls: ['./training-list.component.css']
})
export class TrainingListComponent implements OnInit {
  totalElements: number = 0;
  trainings: Training[] = [];
  loading: boolean;

  constructor(private trainingService: TrainingService, private router: Router) {
  }

  ngOnInit(): void {
    this.getTrainings({page: "0", size: "10"});
  }
  private getTrainings(request){
    this.loading = true;
    this.trainingService.listTrainings(request)
      .subscribe(data => {
        this.trainings = data['content'];
        this.totalElements = data['totalElements'];
        this.loading = false;
      }, error => {
      this.loading = false;
      });
  }
  nextPage(event: PageEvent) {
    const request = {};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();
    this.getTrainings(request);
  }
  goToTraining(id: string): void{
    this.router.navigateByUrl('find_training/' + id);
  }
}

