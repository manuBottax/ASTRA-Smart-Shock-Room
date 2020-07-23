
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
  accepted_on: String ,
  completed_on : String,
  params : Object
}

@Injectable({
  providedIn: 'root'
})

export class CommandService {

  constructor(private http: HttpClient) { }

  /**
   * Retrieve from RoomCommandManager every command with status: pending
   */
  getPendingCommand(): Observable<any> {
    return this.http.get(`http://localhost:3010/api/commands/pending`);
  }

  /**
   * Retrieve from RoomCommandManager every command with status: in_processing
   */
  getProcessingCommand(): Observable<any> {
    return this.http.get(`http://localhost:3010/api/commands/in_processing`);
  }

  /**
   * Retrieve from RoomCommandManager every command with status: completed
   */
  getCompletedCommand(): Observable<any> {
    return this.http.get(`http://localhost:3010/api/commands/completed`);
  }

  /**
   * Retrieve from RoomCommandManager every command with status: failed
   */
  getFailedCommand(): Observable<any> {
    return this.http.get(`http://localhost:3010/api/commands/failed`);
  }

  /**
   * Request the deletion of every command in the RoomCommandManager service
   */
  clearCommandCollection(): Observable<any> {
    return this.http.delete(`http://localhost:3010/api/commands`);
  }
}