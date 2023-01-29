import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environment/environment';
import { EventDto } from '../types/event.type';
import { ListUser } from '../types/listUser.type';
import { CreateUserDto, LoginUserDto } from '../types/user.type';

@Injectable()
export class RequestService {
    API_URL = environment.API_URL;
    
    constructor(
        private http: HttpClient,
    ) {
        console.log(this.API_URL)
    }

    getAllEvents$(page: number, pageSize: number): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events`, {
            params: {
                page,
                pageSize
            }
        });
    }

    getAllPendingEvents$(): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events/pending`);
    }

    getEventById$(id: number | string): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events/${id}`);
    }

    acceptEvent$(eventId: string | number): Observable<any> {
        return this.http.post(`${this.API_URL}/api/v1/events/${eventId}/accept`, {});
    }

    rejectEvent$(eventId: string | number): Observable<any> {
        return this.http.post(`${this.API_URL}/api/v1/events/${eventId}/reject`, {});
    }

    getWeeklyEvents$(): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events/getWeeklyEvents`)
    }

    getMonthlyEvents$(): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events/getMonthlyEvents`)
    }

    getLocalEvents$(): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events/getLocalEvents`)
    }

    getInterestedInEvents$(): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events/getInterestedInEvents`)
    }

    getGoingEvents$(): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events/getInterestedInEvents`)
    }

    getHostingEvents$(): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events/getInterestedInEvents`)
    }

    getAllUsers(): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/account`);
    }

    getEvent(id: number): Observable<any> {
        return this.http.get(`${this.API_URL}/api/v1/events/${id}`)
    }

    registerUser$(body: CreateUserDto) {
        return this.http.request('POST', `${this.API_URL}/api/v1/registration`, {
            body,
            responseType: 'text'
        })
    }

    getCurrentLoggedInUser(): any {
        return this.http.get(`${this.API_URL}/api/v1/account/me`);
    }
    
    getReactedEvents() {
        return this.http.get(`${this.API_URL}/api/v1/events/getMyGoingToAndInterestedEvents`);
    }

    loginUser$(body: LoginUserDto) {
        const serializedForm = this.serializeLoginForm(body);
        // const serializedForm = `username=${body.username}&password=${body.password}`;
        return this.http.request('POST', `${this.API_URL}/api/v1/login`, {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: serializedForm,
            // responseType: 'arraybuffer',
            // withCredentials: true,
        });
    }

    blockUser(userEmail: string) {
        return this.http.put(`${this.API_URL}/api/v1/account/blockUser`, userEmail, {
            headers: {
                'Content-Type': 'text/plain',
            }
        });
    }

    unblockUser(userEmail: string) {
        return this.http.put(`${this.API_URL}/api/v1/account/unblockUser`, userEmail, {
            headers: {
                'Content-Type': 'text/plain',
            }
        });
    }

    serializeLoginForm(formToSerialize: Object) {
        // const formBody = [];
        // for (const property in formToSerialize) {
        //     const encodedKey = encodeURIComponent(property);
        //     const encodedValue = encodeURIComponent(formToSerialize[property]);
        //   formBody.push(encodedKey + "=" + encodedValue);
        // }
        // const formBodySerialized = formBody.join("&");
        // return formBodySerialized;
        const params = new URLSearchParams();
        const keys = Object.keys(formToSerialize);
        keys.forEach((key, index, array) => {
            params.append(key, formToSerialize[key]);
        });
        return params;
    }

    updateEvent(body: EventDto) {
        return this.http.put(`${this.API_URL}/api/v1/events`, JSON.stringify(body), {
            headers: {
                'Content-Type': 'application/json',
            },
        })
    }

    createEvent(eventRequestDto: EventDto) {
        return this.http.post(`${this.API_URL}/api/v1/events`, JSON.stringify(eventRequestDto), {
            headers: {
              'Content-Type': 'application/json',
            },
          })          
    }

    addComment(eventId: number, comment: string): Observable<any> {
        return this.http.post(`${this.API_URL}/api/v1/events/addComment`, {comment, eventId}, {
            headers: {
              'Content-Type': 'application/json',
            },
          });
    }

    activateUser(email: string): Observable<any> {
        return this.http.put(`${this.API_URL}/api/v1/account/changeStatus`, { email: email, enabled: true }, {
            headers: {
                'Content-Type': 'application/json',
              },
        })
    }

    deactivateUser(email: string): Observable<any> {
        return this.http.put(`${this.API_URL}/api/v1/account/changeStatus`, { email: email, enabled: false }, {
            headers: {
                'Content-Type': 'application/json',
              },
        })
    }

    changeUserRole(email: string, newRole: string[]): Observable<any> {
        return this.http.put(`${this.API_URL}/api/v1/account/changeRole`, { email, roles: newRole }, {
            headers: {
                'Content-Type': 'application/json',
              },
        });
    }

    userIsInterested(eventId: number) {
        return this.http.put(`${this.API_URL}/api/v1/events/addInterested`, JSON.stringify(eventId), {
            headers: {
              'Content-Type': 'application/json',
            },
        });
    }

    userIsNotInterested(eventId: number) {
        return this.http.put(`${this.API_URL}/api/v1/events/removeInterested`, JSON.stringify(eventId), {
            headers: {
              'Content-Type': 'application/json',
            },
        });
    }

    userIsGoing(eventId: number) {
        return this.http.put(`${this.API_URL}/api/v1/events/addGoing`, JSON.stringify(eventId), {
            headers: {
              'Content-Type': 'application/json',
            },
        });
    }

    addProfilePicture(imageBase64: string) {
        return this.http.put(`${this.API_URL}/api/v1/account/addProfilePicture`, JSON.stringify(imageBase64), {
            headers: {
              'Content-Type': 'application/json',
            },
        });
    }
}