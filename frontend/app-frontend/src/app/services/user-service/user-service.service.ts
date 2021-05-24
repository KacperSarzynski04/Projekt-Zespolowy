import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { User } from '../../models/user/user';
import { Observable } from 'rxjs';

@Injectable()
export class UserService {

  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/users';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public save(user: User) {
    return this.http.post<User>(this.usersUrl, user);
  }

  public checkAdmin(id: string): Observable<boolean> {
    return this.http.get<boolean>('http://localhost:8080/checkAdmin' + '?userId=' + id);
  }

  public makeAdmin(id: string, admin: boolean): Observable<boolean> {
    return this.http.get<boolean>('http://localhost:8080/makeAdmin' + '?userId=' + id + '&admin=' + admin);
  }
}
