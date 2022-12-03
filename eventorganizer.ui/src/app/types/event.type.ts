export class EventDto {
    id: number;
    name: string;
    time: string;
    status: string;
    
    constructor(_id: number, _name: string, _time: string, _status: string) {
        this.id = _id;
        this.name = _name;
        this.time = _time;
        this.status = _status;
    }
}