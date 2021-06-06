import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { User } from '../../models/user/user';
import { Observable } from 'rxjs';
import {environment} from '../../../environments/environment.prod';

@Injectable()
export class UserService {

  private usersUrl: string;
  public host = environment.apiUrl;

  constructor(private http: HttpClient) {
    this.usersUrl = `${this.host}/users`;
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public save(user: User) {
    return this.http.post<User>(this.usersUrl, user);
  }

  public checkAdmin(id: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.host}/checkAdmin` + '?userId=' + id);
  }

  public makeAdmin(id: string, admin: boolean): Observable<boolean> {
    return this.http.get<boolean>(`${this.host}/makeAdmin` + '?userId=' + id + '&admin=' + admin);
  }

  public listUsers(request){
    const endpoint = `${this.host}/users`;
    const params = request;
    return this.http.get(endpoint, {params});
  }
}
