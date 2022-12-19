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
  public eventId = new FormControl(null);
  private eventName = new FormControl(null);
  private startDate = new FormControl(null);
  private endDate = new FormControl(null);
  private status = new FormControl(null);
  private description = new FormControl(null);
  private location = new FormControl(null);
  private type = new FormControl(null);
   
  constructor(public dialogRef: MatDialogRef<DialogAnimationsExampleDialog>, @Inject(MAT_DIALOG_DATA) public data: any, requestService: RequestService) {
    this.requestService = requestService;
    if(data && data.event) {
      this.eventId.setValue(data.event.id)
      this.eventName.setValue(data.event.name);
      this.startDate.setValue(new Date(data.event.startDate));
      this.endDate.setValue(new Date(data.event.endDate));
      this.description.setValue(data.event.description);
      this.location.setValue(data.event.location);
      this.type.setValue(data.event.type);
    }
  }

  
  eventForm: FormGroup = new FormGroup({
      id: this.eventId,
      name: this.eventName,
      description: this.description,
      location: this.location,
      startDate: this.startDate,
      endDate: this.endDate,
      status: this.status,
  });
  
  submit() {
    if(this.eventForm.valid) {
      this.submitEM.emit(this.eventForm.value);

      if(this.eventId.value) {
        this.requestService.updateEvent(this.eventForm.value).subscribe((res) => {});
      } else {
        this.requestService.createEvent(this.eventForm.value).subscribe((res) => {});
      }
    }
  }

  @Output() submitEM = new EventEmitter();
}