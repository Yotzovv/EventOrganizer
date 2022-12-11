export class Comment {
    publisher: string;
    text: string;
    publishedDate: Date;

    constructor(_publisher: string, _content: string, _publishedDate: Date) {
        this.publisher = _publisher;
        this.text = _content;
        this.publishedDate = _publishedDate;
    }
}
