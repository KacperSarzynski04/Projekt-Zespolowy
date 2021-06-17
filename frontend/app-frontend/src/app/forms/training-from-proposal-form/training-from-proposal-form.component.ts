import { Component, OnInit } from '@angular/core';
import {Training} from '../../models/training/training';
import {Subscription} from 'rxjs';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {NotificationsService} from '../../services/notifications-service/notifications.service';
import {TrainingService} from '../../services/training-service/training-service.service';
import {AuthenticationService} from '../../services/authentication-service/authentication.service';
import {NotificationsEnum} from '../../enum/notifications.enum';
import {HttpErrorResponse} from '@angular/common/http';
import {ProposalService} from "../../services/proposal-service/proposal-service.service";
import {Proposal} from "../../models/proposal/proposal";
import {User} from "../../models/user/user";
import {ModalService} from "../../modals/_modal";
import {CustomHttpRespone} from "../../models/custom-http-responce";

@Component({
  selector: 'app-edit-training-form',
  templateUrl: './training-from-proposal-form.component.html',
  styleUrls: ['./training-from-proposal-form.component.css']
})
export class TrainingFromProposalFormComponent implements OnInit {

  training: Training;
  proposal: Proposal;
  selectedFile: File;
  selectedAttachment: File;
  private localUrl: string;
  private subscriptions: Subscription[] = [];
  private routeSub: Subscription;
  private id: number;
  users: User[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private notificationService: NotificationsService,
    private trainingService: TrainingService,
    private modalService: ModalService,
    private proposalService: ProposalService,
    private authService: AuthenticationService) {
    this.training = new Training();
    this.router.events.subscribe(e => {
      if (e instanceof NavigationEnd && this.router.url == '/addtraining'){

        if (this.authService.getUser() == null || this.authService.getUser().role != 'ROLE_ADMIN'){
          this.router.navigateByUrl('');
        }
      }
    });
  }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      this.id = params.id;
      this.proposalService.showAssignedUsers(Number.parseInt(String(this.id))).subscribe(data => {
        this.users = data;
        console.log(data);
      });
    });
    this.proposalService.find(this.id).subscribe(data => {
      this.proposal = data;
      console.log(data.assignedUsers);
    });
  }

  onSubmit() {
    console.log(this.selectedFile);
    console.log(this.selectedAttachment);
    // this.trainingService.postFile(this.selectedFile).subscribe();
    // this.trainingService.save(this.training).subscribe(result => this.gotoTrainingList());
    this.trainingService.delete(this.training.id).subscribe();
    const formData = this.trainingService.createTrainingFormDate(null, this.training, this.selectedFile, this.selectedAttachment);
    this.subscriptions.push(
      this.trainingService.addUser(formData).subscribe(
        (response: Training) => {
          this.selectedFile = null;
          // this.profileImage = null;
          this.sendNotification(NotificationsEnum.SUCCESS, `Pomyślnie edytowano`);
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendNotification(NotificationsEnum.ERROR, errorResponse.error.message);
          this.selectedFile = null;
        }
      )
    );
    this.delete(String(this.id));
    this.gotoTrainingList();
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

  openModal(id: string) {
    this.modalService.open(id);
  }

  closeModal(id: string) {
    this.modalService.close(id);
  }

  onShowAssignedUsers(id: string): void{
    console.log(id);
    this.proposalService.showAssignedUsers(Number.parseInt(id)).subscribe(data => {
      this.users = data;
      console.log(this.users);
    });
  }

  delete(id: string){
    // this.trainingService.delete(id).subscribe();
    // window.location.reload();
    this.subscriptions.push(
      this.proposalService.delete(id).subscribe(
        (response: CustomHttpRespone) => {
          this.sendNotification(NotificationsEnum.SUCCESS, response.message);
          window.location.reload();
        },
        (error: HttpErrorResponse) => {
          this.sendNotification(NotificationsEnum.ERROR, error.error.message);
        }
      )
    );
  }
}
