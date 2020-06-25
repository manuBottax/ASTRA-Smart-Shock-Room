import { Component, OnInit, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  selector: 'app-preh-data',
  templateUrl: './preh-data.component.html',
  styleUrls: ['./preh-data.component.css']
})
export class PrehDataComponent implements OnInit, DataInterface{

  @Input() data: any;

  preh;

  constructor() { }

  ngOnInit() {
    this.preh = this.data.data.value;
  }
}
