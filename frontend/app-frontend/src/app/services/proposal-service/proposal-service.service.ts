import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Proposal} from '../../models/proposal/proposal';
import {Observable, Subscription} from 'rxjs';
import {User} from '../../models/user/user';
import {environment} from '../../../environments/environment.prod';
import {CustomHttpRespone} from '../../models/custom-http-responce';

@Injectable({
  providedIn: 'root'
})
export class ProposalService {
  private proposalsUrl: string;
  private findProposalUrl: string;
  public host = environment.apiUrl;

  constructor(private http: HttpClient) {
    this.proposalsUrl = `${this.host}/topics`;
    this.findProposalUrl = `${this.host}/find_topic/`;
  }
  listProposals(request){
    const endpoint = `${this.host}/topics`;
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

  public updateVotes(userId: string, id: number): Observable<any>{
    return this.http.get<any>(`${this.host}/topics/vote` + '?userId=' + userId + '&proposalId=' + id);
  }
  public checkAssign(proposalId: string, userId: string): Observable<boolean>{
    return this.http.get<boolean>(`${this.host}/assigned` + '?userId=' + userId + '&proposalId=' + proposalId);
  }
  public checkVote(proposalId: string, userId: string): Observable<boolean>{
    return this.http.get<boolean>(`${this.host}/voted` + '?userId=' + userId + '&proposalId=' + proposalId);
  }

  public showAssignedUsers(proposalID: number) {
    return this.http.get<User[]>(`${this.host}/assignedUsers` + '?proposalId=' + proposalID);

  }
  public updateAssign(userId: string, id: number): Observable<any>{
    return this.http.get<any>(`${this.host}/topics/assign` + '?userId=' + userId + '&proposalId=' + id);
  }

  public delete(topicId: string): Observable<CustomHttpRespone> {
    return this.http.delete<CustomHttpRespone>(`${this.host}/topics/delete/${topicId}`);
  }

  public find(id: number): Observable<Proposal>{
    console.log(this.http.get<Proposal>(this.findProposalUrl + id));
    return this.http.get<Proposal>(this.findProposalUrl + id);
  }
}
