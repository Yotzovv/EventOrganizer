import { Component, Input, OnChanges, SimpleChanges, ɵsetCurrentInjector } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RequestService } from '../request/request.service';
import { EventDto } from '../types/event.type';
import { Feedback, FeedbackDto } from '../types/feedback.dto';
import { ListUser } from '../types/listUser.type';
import { Star, StarRating } from '../types/star-rating.type';
import { EventFeedbackModalComponent } from './event-feedback-modal/event-feedback-modal.component';

@Component({
  selector: 'event-feedback',
  templateUrl: `./event-feedback.component.html`,
  styleUrls: ['./event-feedback.component.scss'],
})
export class EventFeedbackComponent implements OnChanges {

  @Input() event: EventDto;
  starRating: StarRating;

  @Input()
  currentUser: ListUser;

  avgRating: number = 0;

  constructor(
    public dialog: MatDialog,
    private request: RequestService,
  ) {
    
  }
  ngOnChanges(changes: SimpleChanges): void {
    this.calculateRating();
  }

  calculateRating() {
    const ratingsCnt = this.event.feedbacks.length;
    let ratingsSum = 0;
    if (this.event.feedbacks.length > 0) {
      ratingsSum = this.event.feedbacks.reduce((acc, current) => acc + current.rating, 0);
      this.avgRating = Math.round(ratingsSum / ratingsCnt);
    }
    this.starRating = new StarRating(this.avgRating);
  }

  starClicked(star: Star) {
    this.starRating.starClicked(star);
    const dialogRef = this.dialog.open(EventFeedbackModalComponent, {
      data: {
        eventId: this.event.id,
      }
    });
    dialogRef.afterClosed().subscribe((comment: string) => {
      if (comment !== null) {
        const feedbackDto: FeedbackDto = {
          eventId: this.event.id,
          rating: this.starRating.getNumberRating(),
          comment,
        }
        this.request.addFeedback(feedbackDto).subscribe(res => {
          this.event.feedbacks.push(<Feedback>{
            ...feedbackDto,
            ownerUsername: this.currentUser.username,
          });
          this.calculateRating();
        });
      } else {
        // reset to default rating;
        this.calculateRating();
      }
    })

  }

}
