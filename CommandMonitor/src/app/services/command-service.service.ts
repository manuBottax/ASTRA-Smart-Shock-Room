
import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Command {

  _id: String
  type: String,
  category: String, 
  target: String,
  issuer:  String, 
  status: String, 
  timestamp: String,
  completed_on : String,
  params : Object
}

@Injectable({
  providedIn: 'root'
})

export class CommandService {

  constructor(private http: HttpClient) { }

  getPendingCommand(): Observable<any> {
    return this.http.get(`http://localhost:3010/api/commands/pending`);
  }

  getProcessingCommand(): Observable<any> {
    return this.http.get(`http://localhost:3010/api/commands/in_processing`);
  }

  getCompletedCommand(): Observable<any> {
    return this.http.get(`http://localhost:3010/api/commands/completed`);
  }

  getFailedCommand(): Observable<any> {
    return this.http.get(`http://localhost:3010/api/commands/failed`);
  }
}