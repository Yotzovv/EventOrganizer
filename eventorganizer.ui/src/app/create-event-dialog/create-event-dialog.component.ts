import {Component, Inject, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'create-event-dialog',
  styleUrls: ['./create-event-dialog.component.scss'],
  templateUrl: './create-event-dialog.component.html',
})
export class DialogAnimationsExampleDialog {  // TODO: Rename
  constructor(public dialogRef: MatDialogRef<DialogAnimationsExampleDialog>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  eventForm: FormGroup = new FormGroup({
      name: new FormControl(''),
      date: new FormControl(''),
      description: new FormControl(''),
      Type: new FormControl(''),
  });

  submit() {
    if(this.eventForm.valid) {
      this.submitEM.emit(this.eventForm.value);
    }
  }

  @Output() submitEM = new EventEmitter();
}