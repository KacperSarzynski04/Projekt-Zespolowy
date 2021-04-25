import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Training } from '../../models/training/training';
import { Observable } from 'rxjs';

@Injectable()
export class TrainingService {

  private trainingsUrl: string;
  private threeTrainingsUrl: string;

  constructor(private http: HttpClient) {
    this.trainingsUrl = 'http://localhost:8080/trainings';
    this.threeTrainingsUrl = 'http://localhost:8080/three_trainings';
  }

  public findAll(): Observable<Training[]> {
    return this.http.get<Training[]>(this.trainingsUrl);
  }

  public findThree(): Observable<Training[]> {
    return this.http.get<Training[]>(this.threeTrainingsUrl);
  }

  public save(training: Training) {
    return this.http.post<Training>(this.trainingsUrl, training);
  }
}
