import { Component, OnInit } from '@angular/core';
import { Proposal } from '../../models/proposal/proposal';
import {ActivatedRoute, Router} from '@angular/router';
import {ProposalService} from '../../services/proposal-service/proposal-service.service';
import { AuthenticationService } from '../../services/authentication-service/authentication.service';

@Component({
  selector: 'app-proposal-form',
  templateUrl: './proposal-form.component.html',
  styleUrls: ['./proposal-form.component.css']
})
export class ProposalFormComponent implements OnInit {

  proposal: Proposal;
  assignTrainer: boolean;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private proposalService: ProposalService,
    private authenticationService: AuthenticationService
    ) {
    this.proposal = new Proposal();
  }

  ngOnInit(): void {
    this.assignTrainer = false;
  }

  assign(event): void{
    this.assignTrainer = event.target.checked;
    console.log(this.assignTrainer);
  }

  onSubmit(): void {
    this.proposal.authorId = this.authenticationService.getUser().id;
    this.proposalService.save(this.proposal,
        this.assignTrainer).subscribe(result => this.gotoProposalList());
  }

  gotoProposalList(): void {
    this.router.navigate(['/topics']);
  }
}
