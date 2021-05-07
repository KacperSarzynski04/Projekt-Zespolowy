import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Training } from '../../models/training/training';
import { Observable } from 'rxjs';

@Injectable()
export class TrainingService {

  private trainingsUrl: string;
  private threeTrainingsUrl: string;
  private trainingUrl: string;

  constructor(private http: HttpClient) {
    this.trainingsUrl = 'http://localhost:8080/trainings';
    this.threeTrainingsUrl = 'http://localhost:8080/three_trainings';
    this.trainingUrl = 'http://localhost:8080/find_training/';
  }

  public find(id: number): Observable<Training>{
    console.log(this.http.get<Training>(this.trainingUrl + id));
    return this.http.get<Training>(this.trainingUrl + id);
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
  public listTrainings(request){
    const endpoint = 'http://localhost:8080/trainings';
    const params = request;
    return this.http.get(endpoint, {params});
  }
  // tslint:disable-next-line:ban-types
  public postFile(fileToUpload: File): Observable<Object> {
    return this.http.post<any>('http://localhost:8080/images', fileToUpload);
  }
  
}
