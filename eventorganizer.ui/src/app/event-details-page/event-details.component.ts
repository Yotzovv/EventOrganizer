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
    
    constructor(requestService: RequestService, private route: ActivatedRoute) {
        const eventId: number = Number(this.route.snapshot.paramMap.get('id'));
        
        requestService.getEvent(eventId).subscribe((res) => {
            event = res;
        });        
    }
    
    addComment() {
        
    }
}