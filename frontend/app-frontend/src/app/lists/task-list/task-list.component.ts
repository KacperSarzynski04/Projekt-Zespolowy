import { Component, OnInit } from '@angular/core';
import { Task } from '../../models/task/task';
import {TaskService} from "../../services/task-service/task-service.service";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  tasks: Task[];

  constructor(private taskService: TaskService) { }

  ngOnInit(): void {
    this.taskService.findAll().subscribe(data => {
      this.tasks = data;
    });
  }
}
