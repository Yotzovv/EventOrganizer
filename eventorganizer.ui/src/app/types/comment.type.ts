export class Comment {
    publisher: string;
    content: string;
    publishedDate: Date;
    ownersUsername: string;

    constructor(_publisher: string, _content: string, _publishedDate: Date, _ownerUsername: string) {
        this.publisher = _publisher;
        this.content = _content;
        this.publishedDate = _publishedDate;
        this.ownersUsername = _ownerUsername
    }
}
