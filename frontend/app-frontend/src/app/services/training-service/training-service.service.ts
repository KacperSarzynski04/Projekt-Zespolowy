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

  public addUser(formData: FormData): Observable<Training> {
    return this.http.post<Training>(`http://localhost:8080/trainings`, formData);
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

  public createTrainingFormDate(loggedInUsername: string, training: Training, trainingImage: File, trainingFile: File): FormData {
    const formData = new FormData();
    formData.append('topic', training.topic);
    formData.append('description', training.description);
    formData.append('trainer', training.trainer);
    formData.append('durationInMinutes', JSON.stringify(training.durationInMinutes));
    formData.append('date', JSON.stringify(training.date));
    formData.append('time', JSON.stringify(training.time));
    formData.append('trainingImage', trainingImage);
    formData.append('trainingFile', trainingFile);
    return formData;
  }

}
