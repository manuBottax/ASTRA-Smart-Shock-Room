import { Injectable } from '@angular/core';
// import { Socket } from 'ngx-socket-io';
import * as io from 'socket.io-client';
import { environment } from 'src/environments/environment';

import { Subject }    from 'rxjs';

export interface PatientData {
  position : string,
  type: string,
  name: string,
  value : string
}

@Injectable({
  providedIn: 'root'
})

export class WebSocketService {

  socket;

  private dataSource = new Subject<PatientData>();
  private statusSource = new Subject<String>();

  dataStream = this.dataSource.asObservable();
  statusStream = this.statusSource.asObservable();

  constructor () {}

  setupSocketConnection() {
    this.socket = io(environment.SOCKET_ENDPOINT);

    this.socket.on('display_data', (data: PatientData) => {
      console.log(data);
      console.log(data.position);
      console.log(data.value);
 
      this.dataSource.next(data);

    });

    this.socket.on('update_status', data => {
      this.statusSource.next(data.status);
    })
  }

  emitMessage(message : string) {
    this.socket.emit('display_message', message);
  }

}
