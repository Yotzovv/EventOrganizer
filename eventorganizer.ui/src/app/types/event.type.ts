import { ListUser } from './listUser.type';
import { RequestService } from './../request/request.service';
import { Comment } from './comment.type'
import { Image } from './image.type';
import { Feedback } from './feedback.dto';

export class EventDto {
    id?: number;
    name: string;
    location: string;
    description: string;
    comments?: Comment[];
    startDate: Date;
    endDate: Date;
    status?: EventStatus;
    interestedPeple?: number;
    creator: ListUser;
    isCurrentUserInterested?: boolean;
    cantBeEdited?: boolean;
    type?: string;  // TODO: map in the database
    images: Array<Image>;
    feedbacks: Feedback[];

    constructor(_id: number, _name: string, _time: Date, _endDate: Date, _status: EventStatus, _location: string, _description: string, isCurrentUserInterested: boolean, cantBeEdited: boolean, type: string) {
        this.id = _id;
        this.name = _name;
        this.startDate = _time;
        this.endDate = _endDate;
        this.status = _status;
        this.location = _location;
        this.description = _description;
        this.isCurrentUserInterested = isCurrentUserInterested;
        this.cantBeEdited = cantBeEdited;
        this.type = type;
    }
}

export enum EventStatus {
    APPROVED = 'APPROVED',
    PENDING = 'PENDING',
    REJECTED = 'REJECTED'
}