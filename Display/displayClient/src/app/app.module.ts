import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';

import { SocketIoModule, SocketIoConfig } from 'ngx-socket-io';
import { WebSocketService } from './services/web-socket.service';
import { DataContainerComponent } from './data-container/data-container.component';
import { TextDataComponent } from './text-data-component/text-data-component.component';
import { ImageDataComponent } from './image-data-component/image-data-component.component';
import { DataDirective } from './data.directive';
import { TraumaTeamDataComponent } from './trauma-team-data-component/trauma-team-data.component';
import { PrehDataComponent } from './dataComponent/preh-data/preh-data.component';
import { TraumaInfoDataComponent } from './dataComponent/trauma-info-data/trauma-info-data.component';
import { PatientInitialConditionDataComponent } from './dataComponent/patient-initial-condition-data/patient-initial-condition-data.component';
import { EventListDataComponent } from './dataComponent/event-list-data/event-list-data.component';
import { EventListElementComponent } from './dataComponent/event-list-element/event-list-element.component';

const config: SocketIoConfig = { url: 'http://localhost:3001', options: {} };

@NgModule({
  declarations: [
    AppComponent,
    DataContainerComponent,
    TextDataComponent,
    ImageDataComponent,
    DataDirective,
    TraumaTeamDataComponent,
    PrehDataComponent,
    TraumaInfoDataComponent,
    PatientInitialConditionDataComponent,
    EventListDataComponent,
    EventListElementComponent
  ],
  entryComponents: [
    ImageDataComponent,
    TextDataComponent,
    TraumaTeamDataComponent,
    PrehDataComponent,
    TraumaInfoDataComponent,
    PatientInitialConditionDataComponent,
    EventListDataComponent
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
