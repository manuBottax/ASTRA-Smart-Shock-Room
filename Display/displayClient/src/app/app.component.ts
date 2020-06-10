import { Component } from '@angular/core';
import { WebSocketService } from './services/web-socket.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  subscription: Subscription;
  
  dataList: Array<Object> = new Array(7);

  constructor(private socketService: WebSocketService){
    this.subscription = socketService.dataStream.subscribe(
      data => {
        var position = parseInt(data.position) - 1 ;

        if (position < 0 ) {position = 0};
        if (position > 6 ) {position = 6};

        this.dataList[position] = data.value;
    });
  }

  ngOnInit() {
    this.socketService.setupSocketConnection();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
