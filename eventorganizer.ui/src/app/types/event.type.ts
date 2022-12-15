export class EventDto {
    id: number;
    name: string;
    time: Date;
    status: string;
    
    constructor(_id: number, _name: string, _time: Date, _status: string) {
        this.id = _id;
        this.name = _name;
        this.time = _time;
        this.status = _status;
    }
}