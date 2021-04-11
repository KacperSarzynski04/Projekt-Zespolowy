import { Component, OnInit } from '@angular/core';
import { Proposal } from '../../models/proposal/proposal';
import {ProposalService} from '../../services/proposal-service/proposal-service.service';

@Component({
  selector: 'app-proposal-list',
  templateUrl: './proposal-list.component.html',
  styleUrls: ['./proposal-list.component.css']
})
export class ProposalListComponent implements OnInit {

  proposals: Proposal[];
  constructor(private proposalService: ProposalService) { }

  ngOnInit(): void {
    this.proposalService.findAll().subscribe(data => {
      this.proposals = data;
      console.log(data);
    });
  }

}
