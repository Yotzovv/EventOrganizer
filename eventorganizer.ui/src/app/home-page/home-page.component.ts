import { RequestService } from './../request/request.service';
import { Component } from '@angular/core';
import { EventDto } from '../types/event.type';

@Component({
  selector: 'home-page',
  templateUrl: `./home-page.component.html`,
  styleUrls: ['./home-page.component.scss'],
})
export class HomePageComponent {
  public allEvents: EventDto[] = [];

  constructor(requestService: RequestService) {
    requestService.getAllEvents$().subscribe((res) => {
      this.allEvents = res; // TODO: Remove commenting after testing with mock data
    });
  }
}
