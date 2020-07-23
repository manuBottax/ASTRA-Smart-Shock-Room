import { Component, OnInit, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  selector: 'app-patient-initial-condition-data',
  templateUrl: './patient-initial-condition-data.component.html',
  styleUrls: ['./patient-initial-condition-data.component.css']
})

/**
 * Component to display patient initial condition information retrieved from Trauma Tracker
 * The actual layout is handled in patient-initial-condition-data.component.html
 */
export class PatientInitialConditionDataComponent implements OnInit, DataInterface{

  @Input() data: any;

  initialCondition;

  constructor() { }

  ngOnInit() {
    this.initialCondition = this.data.data.value;
  }
}
