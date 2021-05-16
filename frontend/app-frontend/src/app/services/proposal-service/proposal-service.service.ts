import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Proposal} from '../../models/proposal/proposal';
import {Observable, Subscription} from 'rxjs';
import {User} from '../../models/user/user';

@Injectable({
  providedIn: 'root'
})
export class ProposalService {
  private proposalsUrl: string;

  constructor(private http: HttpClient) {
    this.proposalsUrl = 'http://localhost:8080/topics';
  }
  listProposals(request){
    const endpoint = 'http://localhost:8080/topics';
    const params = request;
    return this.http.get(endpoint, {params});
  }
  public findAll(): Observable<Proposal[]> {
    return this.http.get<Proposal[]>(this.proposalsUrl);
  }

  public save(proposal: Proposal, assignAsTrainer: boolean) {
    return this.http.post<Proposal>(
      this.proposalsUrl + '?assignAsTrainer=' + assignAsTrainer, proposal, {withCredentials: true});
  }

  public updateVotes(id: number): Observable<any>{
    return this.http.put<any>(this.proposalsUrl + '/' + id, '');
  }
  public checkAssign(proposalId: string, userId: string): Observable<boolean>{
    return this.http.get<boolean>('http://localhost:8080/assigned' + '?userId=' + userId + '&proposalId=' + proposalId);
  }
  public checkVote(proposalId: string, userId: string): Observable<boolean>{
    return this.http.get<boolean>('http://localhost:8080/voted' + '?userId=' + userId + '&proposalId=' + proposalId);
  }

  public showAssignedUsers(proposalID: number) {
    return this.http.get<User[]>('http://localhost:8080/assignedUsers' + '?proposalId=' + proposalID);

  }
}
