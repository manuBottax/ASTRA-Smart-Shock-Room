import { Component, OnInit, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  selector: 'app-trauma-info-data',
  templateUrl: './trauma-info-data.component.html',
  styleUrls: ['./trauma-info-data.component.css']
})

export class TraumaInfoDataComponent implements OnInit, DataInterface{

  @Input() data: any;

  traumaInfo;

  constructor() { }

  ngOnInit() {
    this.traumaInfo = this.data.data.value;
  }
}
