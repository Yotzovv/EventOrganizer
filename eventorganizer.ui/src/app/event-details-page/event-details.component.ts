import { Component, Inject } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute, Router } from "@angular/router";
import { filter, mergeMap } from "rxjs";
import { ConfirmationDialogComponent } from "../confirmation-dialog/confirmation-dialog.component";
import { RequestService } from "../request/request.service";
import {Comment} from '../types/comment.type';
import { EventDto } from "../types/event.type";
import { ListUser } from "../types/listUser.type";
import { Page } from "../types/page.type";
import { ChangeDetectorRef } from '@angular/core';

@Component({
    selector: 'event-details',
    templateUrl: './event-details.component.html',
    styleUrls: ['./event-details.component.scss']
})


export class EventDetailsComponent {
    event: EventDto;
    requestService: RequestService;
    comment: string;
    fileName: string = '';
    currentUser: ListUser;


    constructor(private _requestService: RequestService, private route: ActivatedRoute, public dialog: MatDialog, private _snackBar: MatSnackBar,
        private router: Router) {
        const eventId: number = Number(this.route.snapshot.paramMap.get('id'));
        this.requestService = _requestService;
        this.getEvent(eventId);
        this.getCurrentUser$().subscribe(res => this.currentUser=res);
    }

    getEvent(eventId: number) {
        this._requestService.getEventById$(eventId).subscribe((event: EventDto) => {
            event.images = event.images.map(i => {
                i.url = 'data:image/jpg;base64,' + i.url;
                return i;
            });
            event.images.sort(function(a, b) { 
                return a.id - b.id;
            });
            this.event = event;
        });
    }

    getCurrentUser$() {
        return this._requestService.getCurrentLoggedInUser();
    }
    
    addComment() {
        this.requestService.addComment(this.event.id, this.comment).subscribe((res) => { 
            // reset comment textarea after submitting
            this.comment = '';
         });
        this.getEvent(this.event.id);
    }

    onFileSelected(event) {
        const formData: FormData = new FormData();
        formData.append('file', event.target.files[0]);

        const fileToUpload: File = event.target.files[0];

        this.requestService.addEventImage(formData, this.event.id)
        .subscribe(res => {
            this.getEvent(this.event.id)
        });
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