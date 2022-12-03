import { RequestService } from './../request/request.service';
import { Component } from '@angular/core';
import { EventDto } from '../types/event.type';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { DialogAnimationsExampleDialog } from '../create-event-dialog/create-event-dialog.component'

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

  openCreateEventDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    this.createEventDialog = this.dialogModel.open(DialogAnimationsExampleDialog);
  }
}
