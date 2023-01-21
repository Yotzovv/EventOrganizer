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
    fileName: string = '';

    constructor(_requestService: RequestService, private route: ActivatedRoute, public dialog: MatDialog, private _snackBar: MatSnackBar,
        private router: Router) {
        const eventId: number = Number(this.route.snapshot.paramMap.get('id'));
        this.requestService = _requestService;

        // TODO: Change to getEventById when API supports it.
<<<<<<< HEAD
        _requestService.getAllEvents$().subscribe((res: EventDto[]) => {
            this.event = res.find(e => e.id === eventId);
            this.event.images = [
                {
                    uri: 'data:image/png;base64,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',
                },
                {
                    uri: 'data:image/png;base64,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',
                }
            ]
=======
        _requestService.getEventById$(eventId).subscribe((event: EventDto) => {
            this.event = event;
>>>>>>> main
        });
    }
    
    addComment() {
        this.requestService.addComment(this.event.id, this.comment).subscribe((res) => { });
    }

<<<<<<< HEAD
    onFileSelected(event) {
        const fileToUpload: File = event.target.files[0];

        if (fileToUpload) {

            this.fileName = fileToUpload.name;
            const reader = new FileReader();
            reader.readAsDataURL(fileToUpload);
            reader.onload = () => {
                this.requestService.addEventImage(reader.result, this.event.id).subscribe(res => {
                    console.log(res)
                });
            };
        }

=======
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
>>>>>>> main
    }
}