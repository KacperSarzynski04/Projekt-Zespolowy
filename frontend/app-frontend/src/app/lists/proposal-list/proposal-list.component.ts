import { Component, OnInit } from '@angular/core';
import { Proposal } from '../../models/proposal/proposal';
import {ProposalService} from '../../services/proposal-service/proposal-service.service';
import {AuthenticationService} from '../../services/authentication.service';
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-proposal-list',
  templateUrl: './proposal-list.component.html',
  styleUrls: ['./proposal-list.component.css']
})
export class ProposalListComponent implements OnInit {
  proposalMapping = new Map();
  proposalVoted = new Map();
  totalElements: number = 0;
  proposals: Proposal[] = [];
  loading: boolean;
  assignedUser: boolean = true;
  constructor(private proposalService: ProposalService,
              private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.getProposals({page: '0', size: '10'});
    this.proposals
  }

  onAssignClicked(proposal: Proposal): void{
    proposal.assignedUsers.push(this.authenticationService.getUser().id);
    proposal.assigned++;
    this.proposals.push(proposal);
  }

  onInterestedClicked(id: string): void{
    this.proposalService.updateVotes(Number.parseFloat(id)).subscribe(response => {});
    window.location.reload();
  }

  public isAdmin(){
    let role = this.authenticationService.getUser().role;
    return role == "ROLE_ADMIN";
  }

  public isLoggedIn(): boolean{
    return this.authenticationService.isLogged();
  }
  private getProposals(request) {
    this.loading = true;
    this.proposalService.listProposals(request)
      .subscribe(data => {
        this.proposals = data['content'];
        this.totalElements = data['totalElements'];
        this.loading = false;
        const UserID = this.authenticationService.getUser().id;
        this.proposals.forEach(proposal => {
          this.proposalService.checkVote(proposal.id, UserID).subscribe(answer2 => {
            this.proposalVoted.set(proposal, answer2);
          });
          this.proposalService.checkAssign(proposal.id, UserID).subscribe( answer => {
            this.proposalMapping.set(proposal, answer);
          });
        });
        console.log(this.proposalVoted);
      }, error => {
        this.loading = false;
      });
  }
  nextPage(event: PageEvent) {
    const request = {};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();
    this.getProposals(request);
  }
}
