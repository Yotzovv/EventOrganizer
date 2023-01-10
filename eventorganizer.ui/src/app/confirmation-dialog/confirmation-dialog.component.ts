import { EventDto } from '../types/event.type';
import { RequestService } from '../request/request.service';
import {Component, Inject, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'confirmation-dialog',
  styleUrls: ['./confirmation-dialog.component.scss'],
  templateUrl: './confirmation-dialog.component.html',
})

export class ConfirmationDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ConfirmationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { title?: string; message?: string }
  ) {

  }
}