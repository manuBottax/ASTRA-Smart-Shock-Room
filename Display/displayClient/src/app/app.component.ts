import { Component, ComponentRef } from '@angular/core';
import { WebSocketService } from './services/web-socket.service';
import { Subscription } from 'rxjs';
import { DataItem } from './data-item';
import { TextDataComponent } from './text-data-component/text-data-component.component';
import { ImageDataComponent } from './image-data-component/image-data-component.component';

import { DataContainerComponent } from './data-container/data-container.component';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  subscription: Subscription;

  dataArray : Array<DataItem> = new Array();

  componentArray : Array<any> = new Array(7);

  constructor(private socketService: WebSocketService){
    console.log(this.dataArray.length);
    
    this.subscription = socketService.dataStream.subscribe(
      data => {
        var position = parseInt(data.position) - 1 ;

        if (position < 0 ) {position = 0};
        if (position > 6 ) {position = 6}

        console.log(data.value)

        //data.type = text | image --> switch DataItem Type

        this.dataArray[position] = new DataItem(TextDataComponent, {value: data.value}); 

        console.log("Updated Data Array Value")
        console.log(this.dataArray[position])

        console.log(this.componentArray[position]);

        this.componentArray[position].updateValue(this.dataArray[position]);
        // this.componentArray[position].instance.data = this.dataArray[position];

    });
  }

  ngOnInit() {

    var item = new DataItem(TextDataComponent, {value : ''}); 
    var item2 = new DataItem(ImageDataComponent, {available : false, path: 'https://via.placeholder.com/100x30'}); 
    var item3 = new DataItem(TextDataComponent, {value : ''}); 
    var item4 = new DataItem(ImageDataComponent, {available : true, path: 'https://via.placeholder.com/400'}); 
    var item5 = new DataItem(TextDataComponent, {value : ''}); 
    var item6 = new DataItem(ImageDataComponent, {available : false, path: 'https://via.placeholder.com/600x100'}); 
    var item7 = new DataItem(TextDataComponent, {value : ''});

    this.dataArray.push(item);
    this.dataArray.push(item2);
    this.dataArray.push(item3);
    this.dataArray.push(item4);
    this.dataArray.push(item5);
    this.dataArray.push(item6);
    this.dataArray.push(item7);

    this.socketService.setupSocketConnection();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onBindedComponent(position, component) {

    this.componentArray[position] = component;

  }
}
