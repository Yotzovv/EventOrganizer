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

    getAllUsers(): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/getAllUsers`);
    }

    getEvent(id: number): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events`)
    }

    registerUser$(body: CreateUserDto) {
        return this.http.request('POST', `${this.API_URL}/api/v1/registration`, {
            body,
            responseType: 'text'
        })
    }

    updateEvent(body: EventDto) {
        return this.http.post(`${this.API_URL}/api/v1/updateEvent`, {
            body,
        })
    }

    createEvent(body: EventDto) {
        return this.http.post(`${this.API_URL}/api/v1/createEvent`, {
            body,
        })
    }

    addComment(eventId: number, comment: string): Observable<any> {
        return this.http.post(`${this.API_URL}/api/v1/addComment`, {
            eventId,
            comment,
        })
    }

    activateUser(email: string): Observable<any> {
        return this.http.post(`${this.API_URL}/api/v1/activateUser`, {
            email
        })
    }

    deactivateUser(email: string): Observable<any> {
        return this.http.post(`${this.API_URL}/api/v1/deactivateUser`, {
            email
        })
    }

    changeUserRole(email: string, newRole: string): Observable<any> {
        return this.http.post(`${this.API_URL}/api/v1/changeRole`, {
            email,
            newRole
        })
    }
}