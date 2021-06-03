import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthenticationService} from '../services/authentication-service/authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService) {}

  intercept(httpRequest: HttpRequest<unknown>, handler: HttpHandler): Observable<HttpEvent<unknown>> {
    if (httpRequest.url.includes(`${this.authenticationService.host}/login`)) {
      return handler.handle(httpRequest);
    }
    if (httpRequest.url.includes(`${this.authenticationService.host}/register`)) {
      return handler.handle(httpRequest);
    }
    if (httpRequest.url.includes(`${this.authenticationService.host}/trainings/images`)) {
      return handler.handle(httpRequest);
    }
    // if (httpRequest.url.includes(`${this.authenticationService.host}/trainings`)) {
    //   return handler.handle(httpRequest);
    // }
    if (httpRequest.url.includes(`${this.authenticationService.host}/three_trainings`)) {
      return handler.handle(httpRequest);
    }
    if (httpRequest.url.includes(`${this.authenticationService.host}/send`)) {
      return handler.handle(httpRequest);
    }
    //if (httpRequest.url.includes(`${this.authenticationService.host}/assigned`)) {
      //return handler.handle(httpRequest);
    //}
    //if (httpRequest.url.includes(`${this.authenticationService.host}/voted`)) {
      //return handler.handle(httpRequest);
    //}
    if (httpRequest.url.includes(`${this.authenticationService.host}/topics/{id}`)) {
      return handler.handle(httpRequest);
    }
    /*
    if (httpRequest.url.includes(`${this.authenticationService.host}/topics`)) {
      return handler.handle(httpRequest);
    }


     */
    if (httpRequest.url.includes(`${this.authenticationService.host}/images`)) {
      return handler.handle(httpRequest);
    }
    if (httpRequest.url.includes(`${this.authenticationService.host}/find_training/{id}`)) {
      return handler.handle(httpRequest);
    }
    this.authenticationService.loadToken();
    const token = this.authenticationService.getToken();
    const request = httpRequest.clone({ setHeaders: { Authorization: `Bearer ${token}` }});
    return handler.handle(request);
  }
}
