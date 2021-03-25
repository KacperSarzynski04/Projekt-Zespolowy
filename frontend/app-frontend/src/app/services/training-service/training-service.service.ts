import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Training } from '../../models/training/training';
import { Observable } from 'rxjs';

@Injectable()
export class TrainingService {

  private trainingsUrl: string;

  constructor(private http: HttpClient) {
    this.trainingsUrl = 'http://localhost:8080/trainings';
  }

  public findAll(): Observable<Training[]> {
    return this.http.get<Training[]>(this.trainingsUrl);
  }

  public save(training: Training) {
    return this.http.post<Training>(this.trainingsUrl, training);
  }
}
