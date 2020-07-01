import { Component, ComponentRef } from '@angular/core';
import { WebSocketService } from './services/web-socket.service';
import { Subscription } from 'rxjs';
import { DataItem } from './data-item';
import { TextDataComponent } from './dataComponent/text-data-component/text-data-component.component';
import { ImageDataComponent } from './dataComponent/image-data-component/image-data-component.component';
import { TraumaTeamDataComponent } from './dataComponent/trauma-team-data-component/trauma-team-data.component';
import { PrehDataComponent } from './dataComponent/preh-data/preh-data.component';
import { TraumaInfoDataComponent } from './dataComponent/trauma-info-data/trauma-info-data.component';
import { PatientInitialConditionDataComponent } from './dataComponent/patient-initial-condition-data/patient-initial-condition-data.component';
import { EventListDataComponent } from './dataComponent/event-list-data/event-list-data.component';
import { TacDataComponent } from './dataComponent/tac-data/tac-data.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  dataSubscription: Subscription;
  statusSubscription : Subscription;
  
  dataArray : Array<DataItem> = new Array();

  componentArray : Array<any> = new Array(8);

  displayStatus: String = "idle"

  imageDisplay : Boolean = true;

  constructor(private socketService: WebSocketService){
    // console.log(this.dataArray.length);
    
    this.dataSubscription = socketService.dataStream.subscribe( data => {
      var position = parseInt(data.position) - 1 ;

      if (position < 0 ) {position = 0};
      if (position > 7 ) {position = 7}

      // console.log(data)

      switch (data.type) {

        case 'trauma_team' : 
          this.dataArray[position] = new DataItem(TraumaTeamDataComponent, {value : data.value});
          break;

        case 'preh' : 
          this.dataArray[position] = new DataItem(PrehDataComponent, {value : data.value});
          break;

        case 'trauma_info' : 
          this.dataArray[position] = new DataItem(TraumaInfoDataComponent, {value : data.value});
          break; 

        case 'patient_initial_condition' : 
          position = 5 ; 
          this.imageDisplay = true;
          this.dataArray[position] = new DataItem(PatientInitialConditionDataComponent, {value : data.value});
          break; 

        case 'event_list' : 
          this.dataArray[position] = new DataItem(EventListDataComponent, {value : data.value});
          break; 

        case 'tac' :
          position = 5 ; 
          this.imageDisplay = true;
          this.dataArray[position] = new DataItem(TacDataComponent, {value: data.value});
          break;

        case 'image' :
          position = 5 ; 
          this.imageDisplay = true;
          this.dataArray[position] = new DataItem(ImageDataComponent, {available : true, path: data.value});
          break;

        case 'text':
          if(position == 5 || position == 6 || position == 7)
            this.imageDisplay = false;

          if (data.name.length > 0){
            var v = data.name + " : " + data.value;
          } else {
            var v = "";
  
          }
          this.dataArray[position] = new DataItem(TextDataComponent, {value: v});
          break;
      
      }

      this.componentArray[position].updateValue(this.dataArray[position]);

    });

    this.statusSubscription = socketService.statusStream.subscribe( status => {
        this.displayStatus = status;
      }
    )
  }

  ngOnInit() {

    for (let i = 0; i < 8; i++) {
      
      this.dataArray.push(new DataItem(TextDataComponent, {value : ''}));
      
    }

    this.socketService.setupSocketConnection();
  }

  ngOnDestroy() {
    this.dataSubscription.unsubscribe();
    this.statusSubscription.unsubscribe();
  }

  onBindedComponent(position, component) {

    this.componentArray[position] = component;

  }
}
