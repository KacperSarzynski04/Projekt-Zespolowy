import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Proposal} from '../../models/proposal/proposal';
import {Observable, Subscription} from 'rxjs';

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
    console.log(this.proposalsUrl + '/' + id);
    return this.http.put<any>(this.proposalsUrl + '/' + id, '');
  }
}
