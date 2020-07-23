import { Component, OnInit, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  selector: 'app-trauma-team-data',
  templateUrl: './trauma-team-data.component.html',
  styleUrls: ['./trauma-team-data.component.css']
})

/**
 * Component to display Trauma Team information retrieved from Trauma Tracker
 * The actual layout is handled in patient-initial-condition-data.component.html
 */

export class TraumaTeamDataComponent implements OnInit, DataInterface {

  @Input() data: any;
  traumaLeader : String;
  traumaTeam : String = "| ";

  constructor() { }

  ngOnInit() {

    var value = this.data.data.value

    this.traumaLeader = value.traumaLeader;

    value.traumaTeam.forEach(member => {
      // console.log("Member : " + member)  ; 
      this.traumaTeam = this.traumaTeam + member + " | "  
    });
  }

}
