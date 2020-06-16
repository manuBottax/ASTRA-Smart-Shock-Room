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
  
  dataList: Array<Object> = new Array(7);

  dataArray : Array<DataItem> = new Array();

  constructor(private socketService: WebSocketService){
    console.log(this.dataArray.length);
    
    this.subscription = socketService.dataStream.subscribe(
      data => {
        var position = parseInt(data.position) - 1 ;

        if (position < 0 ) {position = 0};
        if (position > 6 ) {position = 6}

    });
  }

  ngOnInit() {

    var item = new DataItem(TextDataComponent, {name: 'Bombasto', bio: 'Brave as they come'}); 
    var item2 = new DataItem(ImageDataComponent, {name: 'Bombasto', test: 'Brave as they come'}); 

    this.dataArray.push(item);
    this.dataArray.push(item2);

    this.socketService.setupSocketConnection();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
