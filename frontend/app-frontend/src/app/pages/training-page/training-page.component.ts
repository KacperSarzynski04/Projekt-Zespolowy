import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {Observable, Subscription} from 'rxjs';
import {pluck} from 'rxjs/operators';
import {Training} from '../../models/training/training';
import {TrainingService} from '../../services/training-service/training-service.service';
import {environment} from '../../../environments/environment.prod';

@Component({
  selector: 'app-training-page',
  templateUrl: './training-page.component.html',
  styleUrls: ['./training-page.component.css']
})
export class TrainingPageComponent implements OnInit {

  public host = environment.apiUrl;
  training: Training;
  private routeSub: Subscription;
  private id: number;

  constructor(private route: ActivatedRoute, private service: TrainingService) {

  }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      this.id = params.id;
    });
    this.service.find(this.id).subscribe(data => {
      this.training = data;
      console.log(data);
    });
  }
}
