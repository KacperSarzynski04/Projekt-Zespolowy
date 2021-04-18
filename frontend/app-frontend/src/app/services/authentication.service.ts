import { Injectable } from '@angular/core';
import { environment} from '../../environments/environment.prod';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {User} from '../models/user/user';
import {Observable} from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import {applySourceSpanToExpressionIfNeeded} from '@angular/compiler/src/output/output_ast';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public host = environment.apiUrl;
  private token: string;
  private loggedInUserEmail: string;
  private  jwtHelperService = new JwtHelperService();
  constructor(private http: HttpClient) {}


  public login(user: User): Observable<HttpResponse<User>> {
    return this.http.post<User>(`${this.host}/login`, user, { observe: 'response' });
  }

  public register(user: User): Observable<User | HttpErrorResponse> {
    return this.http.post<User | HttpErrorResponse>
    (`${this.host}/register`, user);
  }

  public logOut(): void {
    this.token = null;
    this.loggedInUserEmail = null;
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    localStorage.removeItem('users');
  }

  public saveToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  public saveUser(user: User): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  public getUser(): User {
    return JSON.parse(localStorage.getItem('user'));
  }

  public isAdmin(): boolean {
    return JSON.parse(localStorage.getItem('user')).role === 'ROLE_ADMIN';
  }

  public loadToken(): void {
    this.token = localStorage.getItem('token');
    console.log(this.token);
  }

  public getToken(): string {
    return this.token;
  }

  public isLogged(): boolean {
    this.loadToken();
    if (this.token != null && this.token !== ''){
      if (this.jwtHelperService.decodeToken(this.token).sub != null || '') {
        if (!this.jwtHelperService.isTokenExpired(this.token)) {
          this.loggedInUserEmail = this.jwtHelperService.decodeToken(this.token).sub;
          return true;
        }
      }
    } else {
      this.logOut();
      return false;
    }
  }

}
