import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Component } from "@angular/core";
import { Router } from '@angular/router';
import { DialogAnimationsExampleDialog } from '../create-event-dialog/create-event-dialog.component'

@Component({
    selector: 'main-layout',
    styleUrls: ['./main-layout.component.scss'],
    templateUrl: './main-layout.component.html',
})
export class MainLayoutComponent {
    createEventDialog: MatDialogRef<DialogAnimationsExampleDialog>;

    constructor(private dialogModel: MatDialog, private router: Router) { }

    openCreateEventDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
        this.createEventDialog = this.dialogModel.open(DialogAnimationsExampleDialog, {
            data: undefined
        });
    }

    logout() {
        window.localStorage.removeItem('user-token');
        this.router.navigate(['auth/login']);
    }

}