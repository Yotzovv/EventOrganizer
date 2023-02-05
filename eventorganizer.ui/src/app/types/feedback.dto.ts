export class FeedbackDto {
    rating: number;
    comment: string;
    eventId: number;
}

export class Feedback {
    rating: number;
    comment: string;
    eventId: number;
    createdDate: Date;
    ownerUsername: string;
}