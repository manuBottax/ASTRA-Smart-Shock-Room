import { Component, OnInit, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  selector: 'app-event-list-data',
  templateUrl: './event-list-data.component.html',
  styleUrls: ['./event-list-data.component.css']
})
export class EventListDataComponent implements OnInit, DataInterface{

  @Input() data: any;

  eventList;

  constructor() { }

  ngOnInit() {
    this.eventList = this.data.data.value.reverse();
  }
}
