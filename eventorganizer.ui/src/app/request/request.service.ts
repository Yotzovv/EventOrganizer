import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environment/environment';
import { EventDto } from '../types/event.type';
import { CreateUserDto } from '../types/user.type';

@Injectable()
export class RequestService {
    API_URL = environment.API_URL;
    
    constructor(
        private http: HttpClient,
    ) {
        console.log(this.API_URL)
    }

    getAllEvents$(): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events`);
    }

    registerUser$(body: CreateUserDto) {
        return this.http.post(`${this.API_URL}/api/v1/registration`, {
            body,
        });
    }

}