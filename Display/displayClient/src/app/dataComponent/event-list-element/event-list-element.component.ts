import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-event-list-element',
  templateUrl: './event-list-element.component.html',
  styleUrls: ['./event-list-element.component.css']
})

/**
 * A single row for the display of a single Trauma Tracker Event. The actual display depends on the event data available.
 * The actual layout is handled in event-list-element.component.html
 */
export class EventListElementComponent implements OnInit {

  @Input() event : Object

  constructor() { }

  ngOnInit() {}

}
