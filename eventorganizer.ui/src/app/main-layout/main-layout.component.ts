import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Component } from "@angular/core";
import { DialogAnimationsExample } from './create-event-dialog/create-event.component'

@Component({
    selector: 'main-layout',
    styleUrls: ['./main-layout.component.css'],
    templateUrl: './main-layout.component.html',
})
export class MainLayoutComponent {
    createEventDialog: MatDialogRef<DialogAnimationsExample>;

    constructor(private dialogModel: MatDialog) { }

    dialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
        this.createEventDialog = this.dialogModel.open(DialogAnimationsExample);
    }
}