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
      this.allEvents = this.allEvents.filter(event => event.name.toLowerCase().includes(filter));

      if(this.allEvents.length == 0) {
        this.requestService.getAllEvents$().subscribe((res) => {
          this.allEvents = res.filter(event => event.name.toLowerCase().includes(filter));;
        });
      }
    }
  }

  userIsInterested(eventId: number): void {
    // TODO: fetch user jwt token
    var userToken: string = 'todo: take this fucking token';
    this.requestService.userIsInterested(userToken, eventId).subscribe((res) => { })
  }

  userIsNotInterested(eventId: number): void {
    // TODO: fetch user jwt token
    var userToken: string = 'todo: take this fucking token';
    this.requestService.userIsNotInterested(userToken, eventId).subscribe((res) => { })
  }
}
