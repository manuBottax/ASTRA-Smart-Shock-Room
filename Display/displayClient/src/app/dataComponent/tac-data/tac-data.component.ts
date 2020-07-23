import { Component, OnInit, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  selector: 'app-tac-data',
  templateUrl: './tac-data.component.html',
  styleUrls: ['./tac-data.component.css']
})

/**
 * Component to display TAC image and related info (patient name, timestamp, TAC report).
 */
export class TacDataComponent implements DataInterface {
  
  @Input() data: any;
  timestamp: string = "";

  ngOnInit() {

    var date: Date = new Date (parseInt(this.data.data.value.timestamp));
    this.timestamp = date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    // console.log(this.timestamp);
  }
}
