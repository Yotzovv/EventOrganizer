import { RequestService } from './../request/request.service';
import { Component, Output } from '@angular/core';
import { EventDto } from '../types/event.type';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DialogAnimationsExampleDialog } from '../create-edit-event-dialog/create-event-dialog.component'
import { MainLayoutComponent } from '../main-layout/main-layout.component';
import { Page } from '../types/page.type';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'home-page',
  templateUrl: `./home-page.component.html`,
  styleUrls: ['./home-page.component.scss'],
})
export class HomePageComponent {
  createEventDialog: MatDialogRef<DialogAnimationsExampleDialog>;
  length = 50;
  pageSize = 6;
  pageIndex = 0;
  pageSizeOptions = [3, 6, 12, 24];
  pageEvent: PageEvent;

  public eventPage: Page<EventDto>;

  constructor(private requestService: RequestService, private dialogModel: MatDialog) {
    this.loadEvents();
  }

  loadEvents() {
    this.requestService.getAllEvents$(this.pageIndex, this.pageSize, "").subscribe((res) => {
      this.eventPage = res;
    });

    this.setReactedEvents();

    this.requestService.getHostingEvents$().subscribe((res: EventDto[]) => {
      this.eventPage.content.forEach((event) => {
        event.cantBeEdited = res.find(e => e.id == event.id) !== undefined;
      })
    });
  }

  setReactedEvents() {
    this.requestService.getReactedEvents().subscribe((reactedEvents: EventDto[]) => {     
      this.eventPage.content.forEach((event) => {
        event.isCurrentUserInterested = reactedEvents.find(e => e.id === event.id) !== undefined;
      });
    });    
  }
  
  openEditEventDialog(enterAnimationDuration: string, exitAnimationDuration: string, event: EventDto): void {
    this.createEventDialog = this.dialogModel.open(DialogAnimationsExampleDialog, {
      data: {
        event: event
      }
    });
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.loadEvents();
  }

  onFilter(filter: string) {
    this.requestService.getAllEvents$(this.pageIndex, this.pageSize, filter).subscribe((page: Page<EventDto>) => {
      this.eventPage = page;
    });
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
