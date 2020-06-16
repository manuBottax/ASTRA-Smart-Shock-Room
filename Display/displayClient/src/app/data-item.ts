import { Type } from '@angular/core';

export class DataItem {
  constructor(public component: Type<any>, public data: any) {}
}