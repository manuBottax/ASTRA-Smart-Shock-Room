import { Component, OnInit, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  selector: 'app-event-list-data',
  templateUrl: './event-list-data.component.html',
  styleUrls: ['./event-list-data.component.css']
})

/**
 * This component display a list of Trauma Tracker Event, one for each row, from the most recent to the oldest. 
 */
export class EventListDataComponent implements OnInit, DataInterface{

  @Input() data: any;

  eventList;

  constructor() { }

  ngOnInit() {
    this.eventList = this.data.data.value.reverse();
  }
}
