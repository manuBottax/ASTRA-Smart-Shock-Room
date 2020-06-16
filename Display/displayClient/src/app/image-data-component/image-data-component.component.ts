import { Component, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  templateUrl: './image-data-component.component.html'
})
export class ImageDataComponent implements DataInterface {
  
  @Input() data: any;

  ngOnInit() {
    console.log(this.data);
  }

}
