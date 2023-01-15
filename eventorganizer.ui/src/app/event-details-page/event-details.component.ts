import { Component, Inject } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute, Router } from "@angular/router";
import { filter, mergeMap } from "rxjs";
import { ConfirmationDialogComponent } from "../confirmation-dialog/confirmation-dialog.component";
import { RequestService } from "../request/request.service";
import {Comment} from '../types/comment.type';
import { EventDto } from "../types/event.type";
import { Page } from "../types/page.type";

@Component({
    selector: 'event-details',
    templateUrl: './event-details.component.html',
    styleUrls: ['./event-details.component.css']
})


export class EventDetailsComponent {
    event: EventDto;
    requestService: RequestService;
    comment: string;

    constructor(_requestService: RequestService, private route: ActivatedRoute, public dialog: MatDialog, private _snackBar: MatSnackBar,
        private router: Router) {
        const eventId: number = Number(this.route.snapshot.paramMap.get('id'));
        this.requestService = _requestService;

        // TODO: Change to getEventById when API supports it.
        _requestService.getEventById$(eventId).subscribe((event: EventDto) => {
            this.event = event;
        });
    }
    
    addComment() {
        this.requestService.addComment(this.event.id, this.comment).subscribe((res) => { });
    }

    blacklistUser(email: string) {
        this.dialog.open(ConfirmationDialogComponent, {
            data: {
                title: 'Block user',
                message: 'Are you sure you want to block user with email: ' + email,
                width: '200px',
                // restoreFocus: false,
                // autoFocus: false,
            }
        }).afterClosed()
        .pipe(
            filter(accept => !!accept),
            mergeMap(() =>
              this.requestService.blockUser(email)
            ),
        )
        .subscribe(res => {
            console.log(res)
            this._snackBar.open('User successfully blocked!', 'close', {
                duration: 3000,
            });
            this.router.navigate(['home']);
        })
    }
}