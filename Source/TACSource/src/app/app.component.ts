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
export class AppComponent {

  subscription: Subscription;
  
  // dataList: Array<Object> = new Array(7);
  status: string;

  examForm : FormGroup;
  idForm : AbstractControl;
  pathForm : AbstractControl;

  constructor(private socketService: WebSocketService, private http: HTTPService, fb: FormBuilder){

    this.examForm = fb.group({
      'patient_id' : ['', Validators.required],
      'path' : ['', Validators.required],
    });

    this.idForm = this.examForm.controls['patient_id'];
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
