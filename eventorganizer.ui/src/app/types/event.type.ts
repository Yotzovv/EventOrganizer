import { RequestService } from './../request/request.service';
import { Comment } from './comment.type'

export class EventDto {
    id: number;
    name: string;
    location: string;
    description: string;
    comments: Comment[];
    time: Date;
    status: string;
    interestedPeple: number;
    publisher: string;
    isCurrentUserInterested: boolean;
    cantBeEdited: boolean;

    
    constructor(_id: number, _name: string, _time: Date, _status: string, _location: string, _description: string, isCurrentUserInterested: boolean, cantBeEdited: boolean) {
        this.id = _id;
        this.name = _name;
        this.time = _time;
        this.status = _status;
        this.location = _location;
        this.description = _description;
        this.isCurrentUserInterested = isCurrentUserInterested;
        this.cantBeEdited = cantBeEdited;
    }
}