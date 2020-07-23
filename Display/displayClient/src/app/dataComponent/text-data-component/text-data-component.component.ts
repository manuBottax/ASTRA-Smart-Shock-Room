import { Component, Input } from '@angular/core';
import { DataInterface } from 'src/data.interface';

@Component({
  templateUrl: './text-data-component.component.html'
})

/**
 * Component to display simple textual information
 */
export class TextDataComponent implements DataInterface {

  @Input() data: any;
}
