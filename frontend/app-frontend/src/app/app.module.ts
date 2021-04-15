import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
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
import { RegisterConfirmationComponent } from './pages/register-confirmation/register-confirmation.component';

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
    RegisterConfirmationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [UserService, TrainingService, ProposalService],
  bootstrap: [AppComponent]
})
export class AppModule { }
