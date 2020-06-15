import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable,} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class HTTPService {

  constructor(private http: HttpClient) { }

  postTACData(tacData): Observable<any> {
    return this.http.post("http://localhost:3003/api/tac_data", tacData);
  } 
}
