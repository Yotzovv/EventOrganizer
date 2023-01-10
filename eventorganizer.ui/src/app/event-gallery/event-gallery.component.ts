import { Component, Input } from '@angular/core';
import { Image } from '../types/image.type';

@Component({
  selector: 'event-gallery',
  templateUrl: `./event-gallery.component.html`,
  styleUrls: ['./event-gallery.component.scss'],
})
export class EventGalleryComponent {
  @Input()
  public images: Array<Image>;

  constructor() {

  }
}
