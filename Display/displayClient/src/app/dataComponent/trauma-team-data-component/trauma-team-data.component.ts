import { Component, OnInit, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  selector: 'app-trauma-team-data',
  templateUrl: './trauma-team-data.component.html',
  styleUrls: ['./trauma-team-data.component.css']
})
export class TraumaTeamDataComponent implements OnInit, DataInterface {

  @Input() data: any;
  traumaLeader : String;
  traumaTeam : String = "| ";

  constructor() { }

  ngOnInit() {

    // console.log("Trauma Team data : ");
    // console.log(this.data.data.value);
    var value = this.data.data.value

    this.traumaLeader = value.traumaLeader;

    value.traumaTeam.forEach(member => {
      console.log("Member : " + member)  ; 
      this.traumaTeam = this.traumaTeam + member + " | "  
    });
  }

}
