import { Component, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  templateUrl: './image-data-component.component.html',
  styleUrls: ['./image-data-component.component.css']
})

/**
 * Component for displaying simple image data. 
 * Complex image data, such as TAC has a dedicated component.
 */

export class ImageDataComponent implements DataInterface {
  
  @Input() data: any;

  ngOnInit() {}

}
