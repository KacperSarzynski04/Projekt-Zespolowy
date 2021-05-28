import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user/user';
import { UserService } from '../../services/user-service/user-service.service';
import {Training} from "../../models/training/training";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  admins = new Map();
  users: User[];
  isAdmin: boolean;
  totalElements: number = 0;
  loading: boolean;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.getUsers({page: "0", size: "10"});
  }
  private getUsers(request){
    this.loading = true;
    this.userService.listUsers(request)
      .subscribe(data => {
        this.users = data['content'];
        this.totalElements = data['totalElements'];
        this.loading = false;
        this.users.forEach(user => {
          this.userService.checkAdmin(user.id).subscribe(answer2 => {
            this.admins.set(user, answer2);
          });
        });
      }, error => {
        this.loading = false;
      });
  }
  save() {
  this.users.forEach(user => {
      this.userService.makeAdmin( user.id, this.admins.get(user)).subscribe();
  });
  }

  nextPage(event: PageEvent) {
    const request = {};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();
    this.getUsers(request);
  }

    assign(event, user): void{
    this.admins.set(user , event.target.checked);
  }
}
