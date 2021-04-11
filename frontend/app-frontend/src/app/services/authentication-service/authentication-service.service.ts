import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {

  requestBody = {"email":"test@comarch.com","password":"Test"};
  headers = {'Authorization': 'Bearer token', 'Access-Control-Allow-Origin': 'localhost:4200'};
  private url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080/auth/login';
  }

  auth(){
    this.http.post<any>(this.url, this.requestBody, { headers: this.headers } ).subscribe(data =>
    {
      console.log(data);
    })
  }
}
