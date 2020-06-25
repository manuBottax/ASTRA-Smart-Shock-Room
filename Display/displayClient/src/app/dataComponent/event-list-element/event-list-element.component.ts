import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-event-list-element',
  templateUrl: './event-list-element.component.html',
  styleUrls: ['./event-list-element.component.css']
})
export class EventListElementComponent implements OnInit {

  @Input() event : Object

  constructor() { }

  ngOnInit() {
    // console.log("Event : ");
    // console.log(this.event);
  }

}
