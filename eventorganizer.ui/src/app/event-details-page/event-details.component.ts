import { Component } from "@angular/core";
import {Comment} from '../types/comment.type';

@Component({
    selector: 'event-details',
    templateUrl: './event-details.component.html',
    styleUrls: ['./event-details.component.css']
})

export class EventDetailsComponent {
    constructor() {}

    comments = [];
    
    addComment() {
        
    }
}