import { Component, OnInit, Input, ViewChild, ComponentFactoryResolver, Output, EventEmitter } from '@angular/core';
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
  @Output() bindedComponent: EventEmitter<any> = new EventEmitter();

  constructor(private componentFactoryResolver: ComponentFactoryResolver) {}

  ngOnInit() {
    this.loadComponent();
  }

  componentRef;

  loadComponent() {

    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(this.data.component);

    const viewContainerRef = this.host.viewContainerRef;
    viewContainerRef.clear();

    this.componentRef = viewContainerRef.createComponent(componentFactory);
    (<DataInterface> this.componentRef.instance).data = this.data;

    this.bindedComponent.emit(this);
  }

  updateValue(updatedValue: DataItem) {

    this.data = updatedValue;

    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(this.data.component);

    const viewContainerRef = this.host.viewContainerRef;
    viewContainerRef.clear();

    this.componentRef = viewContainerRef.createComponent(componentFactory);
    (<DataInterface> this.componentRef.instance).data = this.data;
    this.bindedComponent.emit(this);
  }

}
