import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserListComponent } from './lists/user-list/user-list.component';
import { UserFormComponent } from './forms/user-form/user-form.component';
import {TrainingListComponent} from "./lists/training-list/training-list.component";
import {TrainingFormComponent} from "./forms/training-form/training-form.component";
import { MainPageComponent } from './main-page/main-page.component';

const routes: Routes = [
  { path: 'users', component: UserListComponent },
  { path: 'adduser', component: UserFormComponent },
  { path: 'trainings', component: TrainingListComponent },
  { path: 'addtraining', component: TrainingFormComponent },
  { path: 'home', component: MainPageComponent},
  { path: '', component: MainPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
