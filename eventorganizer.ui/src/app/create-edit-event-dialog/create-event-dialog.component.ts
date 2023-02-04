import { EventDto } from '../types/event.type';
import { RequestService } from '../request/request.service';
import {Component, Inject, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'create-event-dialog',
  styleUrls: ['./create-event-dialog.component.scss'],
  templateUrl: './create-event-dialog.component.html',
})

export class DialogAnimationsExampleDialog {   // TODO: Rename
  requestService: RequestService;
  eventForm: FormGroup = new FormGroup({
    id: new FormControl(null),
    name: new FormControl(null),
    description: new FormControl(null),
    location: new FormControl(null),
    startDate: new FormControl(null),
    endDate: new FormControl(null),
    type: new FormControl(null),
  });
   
  constructor(public dialogRef: MatDialogRef<DialogAnimationsExampleDialog>, @Inject(MAT_DIALOG_DATA) public data: any, requestService: RequestService) {
    this.requestService = requestService;
    if(data && data.event) {
      this.eventForm.get('id').setValue(data.event.id)
      this.eventForm.get('name').setValue(data.event.name);
      this.eventForm.get('startDate').setValue(new Date(data.event.startDate));
      this.eventForm.get('endDate').setValue(new Date(data.event.endDate));
      this.eventForm.get('description').setValue(data.event.description);
      this.eventForm.get('location').setValue(data.event.location);
      this.eventForm.get('type').setValue(data.event.type);
    }
  }
  
  submit() {
    if(this.eventForm.valid) {
      if(this.data?.event?.id) {
        this.requestService.updateEvent(this.eventForm.value).subscribe((res) => {
          this.dialogRef.close(true);
        });
      } else {
        this.requestService.createEvent(this.eventForm.value).subscribe((res) => {
          this.dialogRef.close(true);
        });
      }
    }
  }

}