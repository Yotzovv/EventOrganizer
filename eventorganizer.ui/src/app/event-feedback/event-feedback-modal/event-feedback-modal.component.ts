import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'event-feedback-modal',
  templateUrl: `./event-feedback-modal.component.html`,
  styleUrls: ['./event-feedback-modal.component.scss'],
})
export class EventFeedbackModalComponent {
    feedbackForm: FormGroup;

    constructor(
        public dialogRef: MatDialogRef<EventFeedbackModalComponent>,
    ) {
        this.feedbackForm = new FormGroup({
            comment: new FormControl(''),
        });
    }

    submit() {
        if (this.feedbackForm.valid) {
            this.dialogRef.close(this.feedbackForm.get('comment').value);
        }
    }

    close() {
        this.dialogRef.close(null);
    }

}
