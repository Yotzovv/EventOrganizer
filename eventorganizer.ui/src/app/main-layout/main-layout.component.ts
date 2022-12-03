import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Component } from "@angular/core";
import { DialogAnimationsExampleDialog } from '../create-event-dialog/create-event-dialog.component'

@Component({
    selector: 'main-layout',
    styleUrls: ['./main-layout.component.scss'],
    templateUrl: './main-layout.component.html',
})
export class MainLayoutComponent {
    createEventDialog: MatDialogRef<DialogAnimationsExampleDialog>;

    constructor(private dialogModel: MatDialog) { }

    openCreateEventDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
        this.createEventDialog = this.dialogModel.open(DialogAnimationsExampleDialog);
    }
}