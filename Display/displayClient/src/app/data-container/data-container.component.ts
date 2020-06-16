import { Component, OnInit, Input, ViewChild, ComponentFactoryResolver } from '@angular/core';
import { DataDirective } from '../data.directive';
import { DataInterface } from 'src/data.interface';
import { DataItem } from '../data-item';

@Component({
  selector: 'app-data-container',
  templateUrl: './data-container.component.html',
  styleUrls: ['./data-container.component.css']
})
export class DataContainerComponent implements OnInit {

  @Input() data: DataItem;
  @ViewChild(DataDirective, {static: true}) host: DataDirective;

  constructor(private componentFactoryResolver: ComponentFactoryResolver) {}

  ngOnInit() {
    this.loadComponent();
    console.log(this.data);
  }


  loadComponent() {

    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(this.data.component);

    const viewContainerRef = this.host.viewContainerRef;
    viewContainerRef.clear();

    const componentRef = viewContainerRef.createComponent(componentFactory);
    (<DataInterface>componentRef.instance).data = this.data;
  }

}
