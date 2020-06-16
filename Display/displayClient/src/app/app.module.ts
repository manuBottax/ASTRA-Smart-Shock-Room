import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

import { SocketIoModule, SocketIoConfig } from 'ngx-socket-io';
import { WebSocketService } from './services/web-socket.service';
import { DataContainerComponent } from './data-container/data-container.component';
import { TextDataComponent } from './text-data-component/text-data-component.component';
import { ImageDataComponent } from './image-data-component/image-data-component.component';
import { DataDirective } from './data.directive';

const config: SocketIoConfig = { url: 'http://localhost:3001', options: {} };

@NgModule({
  declarations: [
    AppComponent,
    DataContainerComponent,
    TextDataComponent,
    ImageDataComponent,
    DataDirective
  ],
  entryComponents: [
    ImageDataComponent,
    TextDataComponent
  ],
  imports: [
    BrowserModule,
    SocketIoModule.forRoot(config)
  ],
  providers: [
    WebSocketService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
