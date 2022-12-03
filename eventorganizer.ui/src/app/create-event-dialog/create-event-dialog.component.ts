import { RequestService } from './../request/request.service';
import {Component, Inject, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'create-event-dialog',
  styleUrls: ['./create-event-dialog.component.scss'],
  templateUrl: './create-event-dialog.component.html',
})
export class DialogAnimationsExampleDialog {
  requestService: RequestService;
    // TODO: Rename
  constructor(public dialogRef: MatDialogRef<DialogAnimationsExampleDialog>, @Inject(MAT_DIALOG_DATA) public data: any,
  requestService: RequestService) {
    this.requestService = requestService;
  }

  eventForm: FormGroup = new FormGroup({
      name: new FormControl(''),
      localDateTime: new FormControl(''),
      // description: new FormControl(''),  // TODO: Uncomment when api supports it
      // Type: new FormControl(''),       // TODO: uncomment when api supports is
  });
  
  submit() {
    if(this.eventForm.valid) {
      this.submitEM.emit(this.eventForm.value);
      this.requestService.createEvent(this.eventForm.value).subscribe((res) => {
        console.log(res);
      });
    }
  }

  @Output() submitEM = new EventEmitter();
}