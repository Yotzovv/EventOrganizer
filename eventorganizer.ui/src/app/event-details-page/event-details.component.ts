import { Component, Inject } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { ActivatedRoute } from "@angular/router";
import { RequestService } from "../request/request.service";
import {Comment} from '../types/comment.type';
import { EventDto } from "../types/event.type";

@Component({
    selector: 'event-details',
    templateUrl: './event-details.component.html',
    styleUrls: ['./event-details.component.css']
})


export class EventDetailsComponent {
    event: EventDto;
    requestService: RequestService;
    comment: string;

    constructor(_requestService: RequestService, private route: ActivatedRoute) {
        const eventId: number = Number(this.route.snapshot.paramMap.get('id'));
        
        this.requestService = _requestService;

        _requestService.getEvent(eventId).subscribe((res) => {
            this.event = res;
        });        
    }
    
    addComment() {
        this.requestService.addComment(this.event.id, this.comment).subscribe((res) => { });
    }
}