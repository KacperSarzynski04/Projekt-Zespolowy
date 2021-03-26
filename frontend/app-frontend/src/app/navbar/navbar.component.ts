import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  public classes = {
    specialItem : true,
    item : true
  };

  public onClick(){
    
    document.querySelector('#training_list').scrollIntoView({behavior:'smooth'});
  }
}
