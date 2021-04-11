import { Component, OnInit } from '@angular/core';
import { Proposal } from '../../models/proposal/proposal';
import {ActivatedRoute, Router} from "@angular/router";
import {ProposalService} from "../../services/proposal-service/proposal-service.service";

@Component({
  selector: 'app-proposal-form',
  templateUrl: './proposal-form.component.html',
  styleUrls: ['./proposal-form.component.css']
})
export class ProposalFormComponent implements OnInit {

  proposal: Proposal;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private proposalService: ProposalService) {
    this.proposal = new Proposal();
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.proposalService.save(this.proposal).subscribe(result => this.gotoProposalList());
  }

  gotoProposalList() {
    this.router.navigate(['/proposals']);
  }
}
