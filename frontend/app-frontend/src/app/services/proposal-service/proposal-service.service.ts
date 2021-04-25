import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Proposal} from '../../models/proposal/proposal';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProposalService {
  private proposalsUrl: string;

  constructor(private http: HttpClient) {
    this.proposalsUrl = 'http://localhost:8080/topics';
  }

  public findAll(): Observable<Proposal[]> {
    return this.http.get<Proposal[]>(this.proposalsUrl);
  }

  public save(proposal: Proposal, assignAsTrainer: boolean) {
    return this.http.post<Proposal>(
      this.proposalsUrl + '?assignAsTrainer=' + assignAsTrainer, proposal, {withCredentials: true});
  }
}
