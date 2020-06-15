import { Injectable } from '@angular/core';
// import { Socket } from 'ngx-socket-io';
import * as io from 'socket.io-client';
import { environment } from 'src/environments/environment';

import { Subject }    from 'rxjs';

export interface PatientData {
  position : string,
  value : string
}

@Injectable({
  providedIn: 'root'
})

export class WebSocketService {

  socket;

  private dataSource = new Subject<PatientData>();

  dataStream = this.dataSource.asObservable();

  constructor () {}

  setupSocketConnection() {
    this.socket = io(environment.SOCKET_ENDPOINT);

    // this.socket.on('display_data', (data: PatientData) => {
    //   console.log(data);
    //   console.log(data.position);
    //   console.log(data.value);
 
    //   this.dataSource.next(data);

    // });
  }

  emitStatusUpdate(status: string) {
    this.socket.emit('update_status', status);
  }

  emitTACMessage(message: string) {
    this.socket.emit('tac_message', message);
  }

}
