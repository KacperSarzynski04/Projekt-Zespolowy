import { Component, OnInit } from '@angular/core';
import { Task } from '../../models/task/task';
import {ActivatedRoute, Router} from "@angular/router";
import {TaskService} from "../../services/task-service/task-service.service";

@Component({
  selector: 'app-task-form',
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.css']
})
export class TaskFormComponent implements OnInit {

  task: Task;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private taskService: TaskService) {
    this.task = new Task();
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.taskService.save(this.task).subscribe(result => this.gotoTaskList());
  }

  gotoTaskList() {
    this.router.navigate(['/tasks']);
  }
}
