import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';

import { SocketIoModule, SocketIoConfig } from 'ngx-socket-io';
import { WebSocketService } from './services/web-socket.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTPService } from './services/http.service';

const config: SocketIoConfig = { url: 'http://localhost:3003', options: {} };

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    SocketIoModule.forRoot(config)
  ],
  providers: [
    WebSocketService,
    HTTPService
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
