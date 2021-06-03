import { Injectable } from '@angular/core';
import {Proposal} from "../../models/proposal/proposal";
import {Observable} from "rxjs";
import {HttpClient} from '@angular/common/http';
import {User} from '../../models/user/user';

@Injectable({
  providedIn: 'root'
})
@Injectable({ providedIn: 'root' })
export class ModalService {
    private modals: any[] = [];
  constructor(private http: HttpClient) {
  }
    add(modal: any) {
        // add modal to array of active modals
        this.modals.push(modal);
    }

    remove(id: string) {
        // remove modal from array of active modals
        this.modals = this.modals.filter(x => x.id !== id);
    }

    open(id: string) {
        // open modal specified by id
        const modal = this.modals.find(x => x.id === id);
        modal.open();
    }

    close(id: string) {
        // close modal specified by id
        const modal = this.modals.find(x => x.id === id);
        modal.close();
    }

  send(value: string) {
      return this.http.get<any>('http://localhost:8080/send' + '?mail=' + value);
  }
}
