import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { AppComponent } from './app.component';
import { UserListComponent } from './lists/user-list/user-list.component';
import { UserFormComponent } from './forms/user-form/user-form.component';
import { UserService } from './services/user-service/user-service.service';
import { TrainingListComponent } from './lists/training-list/training-list.component';
import { TrainingFormComponent } from './forms/training-form/training-form.component';
import { TrainingService } from './services/training-service/training-service.service';
import { NavbarComponent } from './navbar/navbar.component';
import { MainPageComponent } from './main-page/main-page.component';
import { LoginFormComponent } from './forms/login-form/login-form.component';
import { TopicListComponent } from './lists/topic-list/topic-list.component';
import { ProposalFormComponent } from './forms/proposal-form/proposal-form.component';
import { ProposalListComponent } from './lists/proposal-list/proposal-list.component';
import {ProposalService} from './services/proposal-service/proposal-service.service';
import {AuthenticationService} from './services/authentication-service/authentication.service';
import {AuthInterceptor} from './intercept/auth.interceptor';
import {AuthGuard} from './guard/guard.guard';
import {NotificationModule} from './notification.module';
import {NotificationsService} from './services/notifications-service/notifications.service';
import {TrainingPageComponent} from "./pages/training-page/training-page.component";
import {  MatPaginatorModule } from '@angular/material/paginator';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ModalModule } from './modals/_modal';
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import { EditTrainingFormComponent } from './forms/edit-training-form/edit-training-form.component';
import { FooterComponent } from './footer/footer.component';
import { UserGuideComponent } from './user-guide/user-guide.component';


@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    UserFormComponent,
    TrainingListComponent,
    TrainingFormComponent,
    MainPageComponent,
    NavbarComponent,
    LoginFormComponent,
    TopicListComponent,
    UserFormComponent,
    ProposalFormComponent,
    ProposalListComponent,
    TrainingPageComponent,
    EditTrainingFormComponent,
    FooterComponent,
    UserGuideComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        NotificationModule,
        MatPaginatorModule,
        BrowserAnimationsModule,
        ModalModule,
        MatButtonModule,
        MatIconModule
    ],
  providers: [UserService, TrainingService, ProposalService, AuthGuard, NotificationsService, AuthenticationService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
