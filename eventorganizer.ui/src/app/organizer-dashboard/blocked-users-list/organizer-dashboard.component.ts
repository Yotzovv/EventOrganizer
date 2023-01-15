import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { filter, mergeMap } from 'rxjs';
import { ConfirmationDialogComponent } from 'src/app/confirmation-dialog/confirmation-dialog.component';
import { RequestService } from 'src/app/request/request.service';
import { EventDto, EventStatus } from 'src/app/types/event.type';

@Component({
  selector: 'organizer-dashboard',
  templateUrl: './organizer-dashboard.component.html',
  styleUrls: ['./organizer-dashboard.component.scss']
})
export class OrganizerDashboardComponent {
  events: EventDto[] = [];
  displayedColumns = [
    'id', 'name', 'status', 'location', 'startDate', 'eventType', 'creator', 'description', 'actions'
  ]

  constructor(private request: RequestService, public dialog: MatDialog, private _snackBar: MatSnackBar) {
    this.getAllPendingEvents$().subscribe(res => {
      console.log(res);
      this.events=res;
    });
  }

  getAllPendingEvents$() {
    return this.request.getAllPendingEvents$();
  }

  approveEvent(event: EventDto) {
    this.dialog.open(ConfirmationDialogComponent, {
      data: {
          title: 'Reject event',
          message: 'Are you sure you want to approve the event?',
          width: '200px',
      }
    }).afterClosed()
      .pipe(
          filter(accept => !!accept),
          mergeMap(() =>
            this.request.acceptEvent$(event.id),
          ),
          mergeMap(() => this.getAllPendingEvents$())
      )
      .subscribe((events: EventDto[]) => {
          this.events = events;
          this._snackBar.open('Event successfully approved!', 'close', {
              duration: 3000,
          });
      })
  }

  rejectEvent(event: EventDto) {
    this.dialog.open(ConfirmationDialogComponent, {
      data: {
          title: 'Reject event',
          message: 'Are you sure you want to reject the event?',
          width: '200px',
      }
    }).afterClosed()
      .pipe(
          filter(accept => !!accept),
          mergeMap(() =>
            this.request.rejectEvent$(event.id),
          ),
          mergeMap(() => this.getAllPendingEvents$())
      )
      .subscribe((events: EventDto[]) => {
          this.events = events;
          this._snackBar.open('Event successfully rejected!', 'close', {
              duration: 3000,
          });
      })
    }

}