import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { filter, mergeMap } from 'rxjs';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { RequestService } from '../request/request.service';
import { ListUser } from '../types/listUser.type';

@Component({
  selector: 'blocked-users-list',
  templateUrl: './blocked-users-list.component.html',
  styleUrls: ['./blocked-users-list.component.scss']
})
export class BlockedUsersListComponent {
  user: ListUser;
  displayedColumns = [
    'email', 'actions'
  ]

  constructor(private request: RequestService, public dialog: MatDialog, private _snackBar: MatSnackBar) {
    this.getCurrentUser$().subscribe(res => this.user=res);
  }

  getCurrentUser$() {
    return this.request.getCurrentLoggedInUser();
  }

  unblockUser(email: string) {
    this.dialog.open(ConfirmationDialogComponent, {
      data: {
          title: 'Unblock user',
          message: 'Are you sure you want to unblock user with email: ' + email,
          width: '200px',
          // restoreFocus: false,
          // autoFocus: false,
      }
  }).afterClosed()
  .pipe(
      filter(accept => !!accept),
      mergeMap(() =>
        this.request.unblockUser(email)
      ),
      mergeMap(() => this.getCurrentUser$())
  )
  .subscribe((currentUser: ListUser) => {
      this.user = currentUser;
      this._snackBar.open('User successfully unblocked!', 'close', {
          duration: 3000,
      });
  })
  }
}