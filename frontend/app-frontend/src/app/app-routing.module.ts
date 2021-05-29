import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './lists/user-list/user-list.component';
import { UserFormComponent } from './forms/user-form/user-form.component';
import {TrainingListComponent} from './lists/training-list/training-list.component';
import {TrainingFormComponent} from './forms/training-form/training-form.component';
import {LoginFormComponent} from './forms/login-form/login-form.component';
import { MainPageComponent } from './main-page/main-page.component';
import { TopicListComponent } from './lists/topic-list/topic-list.component';
import {ProposalFormComponent} from './forms/proposal-form/proposal-form.component';
import {ProposalListComponent} from './lists/proposal-list/proposal-list.component';
import {AuthGuard} from './guard/guard.guard';
import {RoleGuard} from './guard/role-guard.guard';
import {TrainingPageComponent} from './pages/training-page/training-page.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {EditTrainingFormComponent} from './forms/edit-training-form/edit-training-form.component';

const routes: Routes = [
  { path: 'register', component: UserFormComponent },
  { path: 'trainings', component: TrainingListComponent },
  { path: 'addtraining', component: TrainingFormComponent , canActivate: [RoleGuard, AuthGuard]},
  { path: 'topics', component: ProposalListComponent, canActivate: [AuthGuard]},
  { path: 'home', component: MainPageComponent},
  { path: 'login', component: LoginFormComponent},
  { path: 'register', component: UserFormComponent},
  { path: '', component: MainPageComponent},
  { path: '*', component: MainPageComponent},
  { path: 'user/login', component: LoginFormComponent},
  { path: '', component: MainPageComponent},
  { path: 'proposals', component: ProposalListComponent, canActivate :[BrowserAnimationsModule]},
  { path: 'addproposal', component: ProposalFormComponent, canActivate: [AuthGuard]},
  { path: 'find_training/:id', component: TrainingPageComponent},
  { path: 'logout', component: MainPageComponent},
  { path: 'users', component: UserListComponent},
  { path: 'edit_training/:id', component: EditTrainingFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
