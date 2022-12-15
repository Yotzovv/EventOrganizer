import { Comment } from './comment.type'

export class EventDto {
    id: number;
    name: string;
    location: string;
    description: string;
    comments: Comment[];
    time: Date;
    status: string;
    interested: number;
    publisher: string;

    
    constructor(_id: number, _name: string, _time: Date, _status: string, _location: string, _description: string) {
        this.id = _id;
        this.name = _name;
        this.time = _time;
        this.status = _status;
        this.location = _location;
        this.description = _description;
    }
}