import {Component, Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

/**
 * @title Dialog Animations
 */
@Component({
  selector: 'create-event',
  styleUrls: ['create-event.css'],
  templateUrl: 'create-event-dialog.html',
})
export class DialogAnimationsExample {
  constructor(public dialog: MatDialog, @Inject(MAT_DIALOG_DATA) public data: any) {}

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    this.dialog.open(DialogAnimationsExampleDialog, {
      width: '250px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  }
}

@Component({
  selector: 'create-event-dialog',
  templateUrl: 'create-event-dialog.html',
})
export class DialogAnimationsExampleDialog {
  constructor(public dialogRef: MatDialogRef<DialogAnimationsExampleDialog>) {}
}