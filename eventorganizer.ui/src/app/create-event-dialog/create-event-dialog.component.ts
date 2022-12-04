import { EventDto } from './../types/event.type';
import { RequestService } from './../request/request.service';
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
  private eventName = new FormControl('');
  private eventLocalTime = new FormControl(null);
   
  constructor(public dialogRef: MatDialogRef<DialogAnimationsExampleDialog>, @Inject(MAT_DIALOG_DATA) public data: any, requestService: RequestService) {
    this.requestService = requestService;
    if(data && data.event) {
      this.eventId.setValue(data.event.id)
      this.eventName.setValue(data.event.name);
      this.eventLocalTime.setValue(data.event.time);
    }
  }

  
  eventForm: FormGroup = new FormGroup({
      name: this.eventName,
      localDateTime: this.eventLocalTime,
      // description: new FormControl(''),  // TODO: Uncomment when api supports it
      // Location: new FormControl(''),
      // Type: new FormControl(''),
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