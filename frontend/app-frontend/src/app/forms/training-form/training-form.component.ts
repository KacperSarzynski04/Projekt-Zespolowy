import { Component, OnInit } from '@angular/core';
import { Training } from '../../models/training/training';
import {ActivatedRoute, NavigationEnd, NavigationStart, Router} from "@angular/router";
import {TrainingService} from "../../services/training-service/training-service.service";
import { AuthenticationService } from 'src/app/services/authentication-service/authentication.service';
import {Subscription} from "rxjs";
import {NotificationsEnum} from '../../enum/notifications.enum';
import { NotificationsService} from "../../services/notifications-service/notifications.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-training-form',
  templateUrl: './training-form.component.html',
  styleUrls: ['./training-form.component.css']
})
export class TrainingFormComponent implements OnInit {

  training: Training;
  selectedFile: File;
  selectedAttachment: File;
  private localUrl: string;
  private subscriptions: Subscription[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private notificationService: NotificationsService,
    private trainingService: TrainingService,
    private authService: AuthenticationService) {
    this.training = new Training();
    this.router.events.subscribe(e => {
      if(e instanceof NavigationEnd && this.router.url =="/addtraining"){

        if(this.authService.getUser()==null || this.authService.getUser().role != "ROLE_ADMIN"){
          this.router.navigateByUrl("");
        }
      }
    })
  }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log(this.selectedFile);
    console.log(this.selectedAttachment);
    const formData = this.trainingService.createTrainingFormDate(null, this.training, this.selectedFile, this.selectedAttachment);
    this.subscriptions.push(
      this.trainingService.addUser(formData).subscribe(
        (response: Training) => {
          this.selectedFile = null;
          //this.profileImage = null;
          this.sendNotification(NotificationsEnum.SUCCESS, `Added successfully`);
          this.gotoTrainingList();
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendNotification(NotificationsEnum.ERROR, errorResponse.error.message);
          this.selectedFile = null;
        }
      )
    );
  }

  private sendNotification(notificationType: NotificationsEnum, message: string): void {
    if (message) {
      this.notificationService.showMessage(notificationType, message);
    } else {
      this.notificationService.showMessage(notificationType, 'An error occurred. Please try again.');
    }
  }

  gotoTrainingList() {
    this.router.navigate(['/trainings']).then(r => window.location.reload());
  }

  onImageSelected(event) {
    this.selectedFile = event.target.files[0];
  }

  onFileSelected(event) {
    this.selectedAttachment = event.target.files[0];
  }
}
