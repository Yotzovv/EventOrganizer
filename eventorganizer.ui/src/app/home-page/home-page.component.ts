import { RequestService } from './../request/request.service';
import { Component } from '@angular/core';
import { EventDto } from '../types/event.type';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DialogAnimationsExampleDialog } from '../create-edit-event-dialog/create-event-dialog.component'

@Component({
  selector: 'home-page',
  templateUrl: `./home-page.component.html`,
  styleUrls: ['./home-page.component.scss'],
})
export class HomePageComponent {
  createEventDialog: MatDialogRef<DialogAnimationsExampleDialog>;

  public allEvents: EventDto[] = [];

  constructor(requestService: RequestService, private dialogModel: MatDialog) {
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
}
