import { Component, ComponentRef } from '@angular/core';
import { WebSocketService } from './services/web-socket.service';
import { Subscription } from 'rxjs';
import { DataItem } from './data-item';
import { TextDataComponent } from './text-data-component/text-data-component.component';
import { ImageDataComponent } from './image-data-component/image-data-component.component';

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

        if (data.type == 'text'){
          var v = data.name + " : " + data.value;
          this.dataArray[position] = new DataItem(TextDataComponent, {value: v});
        } else if (data.type == 'image'){
          //In this development state you can visualise image only in the big slot for layout management reason.
          position = 3 ; 
          this.dataArray[position] = new DataItem(ImageDataComponent, {available : true, path: data.value});
        }


        console.log("Updated Data Array Value")
        console.log(this.dataArray[position])

        console.log(this.componentArray[position]);

        this.componentArray[position].updateValue(this.dataArray[position]);

    });
  }

  ngOnInit() {

    for (let i = 0; i < 7; i++) {
      
      this.dataArray.push(new DataItem(TextDataComponent, {value : ''}));
      
    }

    this.socketService.setupSocketConnection();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onBindedComponent(position, component) {

    this.componentArray[position] = component;

  }
}
