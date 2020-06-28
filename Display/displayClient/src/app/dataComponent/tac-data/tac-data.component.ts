import { Component, OnInit, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  selector: 'app-tac-data',
  templateUrl: './tac-data.component.html',
  styleUrls: ['./tac-data.component.css']
})
export class TacDataComponent implements DataInterface {
  
  @Input() data: any;

  ngOnInit() {
    console.log(this.data);
  }
}
