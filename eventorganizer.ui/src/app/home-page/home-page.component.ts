import { RequestService } from './../request/request.service';
import { Component, Output } from '@angular/core';
import { EventDto } from '../types/event.type';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DialogAnimationsExampleDialog } from '../create-edit-event-dialog/create-event-dialog.component'
import { MainLayoutComponent } from '../main-layout/main-layout.component';

@Component({
  selector: 'home-page',
  templateUrl: `./home-page.component.html`,
  styleUrls: ['./home-page.component.scss'],
})
export class HomePageComponent {
  createEventDialog: MatDialogRef<DialogAnimationsExampleDialog>;
  requestService: RequestService;

  public allEvents: EventDto[] = [];

  constructor(requestService: RequestService, private dialogModel: MatDialog) {
    this.requestService = requestService;

    requestService.getAllEvents$().subscribe((res) => {
      this.allEvents = res;
    });
  }

  openEditEventDialog(enterAnimationDuration: string, exitAnimationDuration: string, event: EventDto): void {
    this.createEventDialog = this.dialogModel.open(DialogAnimationsExampleDialog, {
      data: {
        event: event
      }
    });
  }

  onFilter(filter: string) {
    if(filter === null || filter === '') {
      this.requestService.getAllEvents$().subscribe((res) => {
        this.allEvents = res;
      });
    } else {
      if(filter == 'week') {
        this.requestService.getWeeklyEvents$().subscribe((res) => {
          this.allEvents = res.filter(event => event.name.toLowerCase().includes(filter));;
        });
      } else if (filter == 'monthly') {
        this.requestService.getMonthlyEvents$().subscribe((res) => {
          this.allEvents = res.filter(event => event.name.toLowerCase().includes(filter));;
        });
      } else if (filter == 'local') {
        this.requestService.getLocalEvents$().subscribe((res) => {
          this.allEvents = res.filter(event => event.name.toLowerCase().includes(filter));;
        });
      } else if (filter == "interested") {
        this.requestService.getInterestedInEvents$().subscribe((res) => {
          this.allEvents = res.filter(event => event.name.toLowerCase().includes(filter));;
        });
      } else if (filter == "going") {
        this.requestService.getGoingEvents$().subscribe((res) => {
          this.allEvents = res.filter(event => event.name.toLowerCase().includes(filter));;
        });
      } else if (filter == "hosting") {
        this.requestService.getHostingEvents$().subscribe((res) => {
          this.allEvents = res.filter(event => event.name.toLowerCase().includes(filter));;
        });
      } else {
        this.allEvents = this.allEvents.filter(event => event.name.toLowerCase().includes(filter));
        
        if(this.allEvents.length == 0) {
          this.requestService.getAllEvents$().subscribe((res) => {
            this.allEvents = res.filter(event => event.name.toLowerCase().includes(filter));;
          });
        }
      }
    }
  }

  userIsInterested(eventId: number): void {
    this.requestService.userIsInterested(eventId).subscribe((res) => { });
  }

  userIsNotInterested(eventId: number): void {
    this.requestService.userIsNotInterested(eventId).subscribe((res) => { });
  }

  userIsGoing(eventId: number): void {
    this.requestService.userIsGoing(eventId).subscribe((res) => { });
  }
}
