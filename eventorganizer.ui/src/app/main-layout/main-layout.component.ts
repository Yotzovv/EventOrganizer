import { UserProfile } from './../types/userProfile.type';
import { EventDto } from 'src/app/types/event.type';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Component, ViewChild, ViewEncapsulation } from "@angular/core";
import { Router } from '@angular/router';
import { DialogAnimationsExampleDialog } from '../create-edit-event-dialog/create-event-dialog.component'
import { MatCalendarCellClassFunction, MatDatepicker } from '@angular/material/datepicker';
import { RequestService } from '../request/request.service';

@Component({
    selector: 'main-layout',
    styleUrls: ['./main-layout.component.scss'],
    templateUrl: './main-layout.component.html',
    encapsulation: ViewEncapsulation.None,
})
export class MainLayoutComponent {
    createEventDialog: MatDialogRef<DialogAnimationsExampleDialog>;
    @ViewChild('dp3', { static: true }) dp3: MatDatepicker<any>;
    showCalendar: boolean = false;

    reactedEvents: EventDto[];
    currentUser: UserProfile;
    
    constructor(private requestService: RequestService, private dialogModel: MatDialog, private router: Router) { 
        this.requestService.getReactedEvents().subscribe((res: any) => {
           this.reactedEvents = res;
        })

        this.requestService.getCurrentLoggedInUser().subscribe((res: any) => {
            this.currentUser = res;
            this.currentUser.profilePicture = res.profilePicture.url;
        })
    }

    openCreateEventDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
        this.createEventDialog = this.dialogModel.open(DialogAnimationsExampleDialog, {
            data: undefined
        });
    }

    dateClass: MatCalendarCellClassFunction<Date> = (cellDate, view) => {
        console.log(this.reactedEvents);
        let dates = this.reactedEvents.map(event => new Date(event.startDate).toLocaleDateString());
        console.log(dates);

        // Only highligh dates inside the month view.
        if (view === 'month') {
          const includesDate = dates.includes(cellDate.toLocaleDateString());
          console.log(cellDate.toISOString());

          // Highlight the 1st and 20th day of each month.
          return includesDate ? 'example-custom-date-class' : '';
        }
    
        return '';
      };
      
    openMyCalendarDialog(event: any) {
        event.preventDefault();
        event.stopPropagation();
        
        this.dp3.open();
      }      

    logout() {
        window.localStorage.removeItem('user-token');
        this.router.navigate(['auth/login']);
    }

}