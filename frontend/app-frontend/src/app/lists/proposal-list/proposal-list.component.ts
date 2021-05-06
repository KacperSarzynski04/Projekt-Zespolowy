import { Component, OnInit } from '@angular/core';
import { Proposal } from '../../models/proposal/proposal';
import {ProposalService} from '../../services/proposal-service/proposal-service.service';
import {AuthenticationService} from '../../services/authentication.service';

@Component({
  selector: 'app-proposal-list',
  templateUrl: './proposal-list.component.html',
  styleUrls: ['./proposal-list.component.css']
})
export class ProposalListComponent implements OnInit {

  proposals: Proposal[];
  constructor(private proposalService: ProposalService,
              private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
    this.proposalService.findAll().subscribe(data => {
      this.proposals = data;
      console.log(data);
    });
  }

  onAssignClicked(proposal: Proposal): void{
    proposal.assignedUsers.push(this.authenticationService.getUser().id);
    proposal.assigned++;
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
}
