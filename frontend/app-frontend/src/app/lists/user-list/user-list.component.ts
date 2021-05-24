import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user/user';
import { UserService } from '../../services/user-service/user-service.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  admins = new Map();
  users: User[];
  isAdmin: boolean;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
      console.log(data);
      this.users.forEach(user => {
        this.userService.checkAdmin(user.id).subscribe(answer2 => {
          this.admins.set(user, answer2);
        });
      });
    });
  }

  save() {
  this.users.forEach(user => {
      this.userService.makeAdmin( user.id, this.admins.get(user)).subscribe();
  });
  }
    assign(event, user): void{
    this.admins.set(user , event.target.checked);
  }
}
