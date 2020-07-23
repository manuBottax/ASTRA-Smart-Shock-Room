import { Component } from '@angular/core';
import { WebSocketService } from './services/web-socket.service';
import { Subscription } from 'rxjs';
import { FormGroup, FormControl, FormBuilder, AbstractControl, Validators } from '@angular/forms';
import { HTTPService } from './services/http.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

/**
 * Web app that simulate the TAC machine control system. 
 * Using this is possible to set the TAC status (unavailable, available, in use)
 * and create TAC Report for a patient using the dedicated form. 
 */
export class AppComponent {

  subscription: Subscription;
  
  status: string;

  examForm : FormGroup;
  idForm : AbstractControl;
  nameForm : AbstractControl;
  descriptionForm : AbstractControl;
  pathForm : AbstractControl;

  constructor(private socketService: WebSocketService, private http: HTTPService, fb: FormBuilder){

    this.examForm = fb.group({
      'patient_id' : ['', Validators.required],
      'name' : ['', Validators.required],
      'description' : ['', Validators.required],
      'path' : ['', Validators.required],
    });

    this.idForm = this.examForm.controls['patient_id'];
    this.nameForm = this.examForm.controls['name'];
    this.descriptionForm = this.examForm.controls['description'];
    this.pathForm = this.examForm.controls['path'];

    this.status = "available"

  }

  updateStatus(stat : string){
    this.status = stat;
    this.socketService.emitStatusUpdate(this.status);
  }

  onSubmit(data) {
    var postData = {
      patient_id : data.patient_id,
      name : data.name,
      description : data.description,
      path : data.path
    }

    console.log(postData);

    this.http.postTACData(postData).subscribe(res => {
      console.log(res);
    }, err => {
      console.log(err.message);
    })
  }

  ngOnInit() {
    this.socketService.setupSocketConnection();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
