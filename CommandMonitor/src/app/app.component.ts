import { Component, OnInit } from '@angular/core';
import { CommandService, Command } from './services/command-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

/**
 * Test system performance based on command handling time. 
 * 
 * This application retrieve every command issued to the system and compute average time and max time: 
 *    - from command creation to command acceptance (status :  pending -> in_process)
 *    - from command acceptance to completion (status : in_process -> completed)
 *    - from command acceptance to failure (status :  in_process -> failed)
 */
export class AppComponent implements OnInit{

  commandTotalAmount : number;

  pendingCommandList : Array<Command>;
  processingCommandList : Array<Command>;
  completedCommandList : Array<Command>;
  failedCommandList : Array<Command>;

  avgAcceptTime : number;
  maxAcceptTime : number;

  avgCompletionTime : number;
  maxCompletionTime : number;

  avgFailureTime : number;
  maxFailureTime : number;
 
  constructor(private commandService : CommandService) {
    this.pendingCommandList = new Array<Command> ();
    this.processingCommandList = new Array<Command> ();
    this.completedCommandList = new Array<Command> ();
    this.failedCommandList = new Array<Command> ();

    this.commandTotalAmount = 0;
  }

  ngOnInit(){

    this.commandService.getPendingCommand().subscribe(pendingList => {
      console.log("Pending List : ");
      console.log(pendingList);
      this.pendingCommandList = pendingList;

      this.commandTotalAmount += this.pendingCommandList.length;
    })

    this.commandService.getProcessingCommand().subscribe(processList => {
      console.log("In Processing List : ");
      console.log(processList);
      this.processingCommandList = processList;

      this.commandTotalAmount += this.processingCommandList.length;
    })

    this.commandService.getCompletedCommand().subscribe(completedList => {
      console.log("Completed List : ");
      console.log(completedList);
      this.completedCommandList = completedList;

      this.commandTotalAmount += this.completedCommandList.length;

      var processed = 0;
      var max = 0; 
      var tot = 0; 

      var maxWait = 0;
      var totWait = 0;

      this.avgCompletionTime = 0;
      this.maxCompletionTime = 0;
      this.avgAcceptTime = 0;
      this.maxAcceptTime = 0;

      this.completedCommandList.forEach( cmd => {

        var elapsed = parseInt("" + cmd.completed_on) - parseInt("" + cmd.accepted_on);

        var waitingTime = parseInt("" + cmd.accepted_on) - parseInt("" + cmd.timestamp);

        console.log("Command # " + cmd._id + " : " + elapsed + " ms | waited for " + waitingTime + "ms");

        tot = tot + elapsed;

        totWait = totWait + waitingTime;

        if (elapsed > max){
          max = elapsed;
        }

        if(waitingTime > maxWait){
          maxWait = waitingTime;
        }

        processed = processed + 1;

        if (processed == this.completedCommandList.length) {
          this.avgCompletionTime = ( Math.round((tot / this.completedCommandList.length)) / 1000 );
          this.maxCompletionTime = ( max / 1000) ;

          this.avgAcceptTime = ( Math.round((totWait / this.completedCommandList.length)) / 1000 );
          this.maxAcceptTime = ( maxWait / 1000) ;
        }

      })
    })

    this.commandService.getFailedCommand().subscribe(failedList => {
      console.log("Failed List : ");
      console.log(failedList);
      this.failedCommandList = failedList;

      this.commandTotalAmount += this.failedCommandList.length;

      var processed = 0;
      var max = 0; 
      var tot = 0; 

      this.avgFailureTime = 0;
      this.maxFailureTime = 0;

      this.failedCommandList.forEach( cmd => {

        var elapsed = parseInt("" + cmd.completed_on) - parseInt("" + cmd.accepted_on);

        console.log("Command # " + cmd._id + " failed in " + elapsed + " ms");

        tot = tot + elapsed;

        if (elapsed > max){
          max = elapsed;
        }

        processed = processed + 1;

        if (processed == this.failedCommandList.length) {
          this.avgFailureTime = ( Math.round((tot / this.failedCommandList.length)) / 1000 );
          this.maxFailureTime = ( max / 1000);
        }

      })
    })

  }

  onClearButtonClick(){
    console.log("Elimino i comandi dal DB");
    this.commandService.clearCommandCollection().subscribe(delRes => {
      console.log(delRes);
    })
  }

}
