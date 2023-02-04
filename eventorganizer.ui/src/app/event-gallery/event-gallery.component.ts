import { AfterViewInit, Component, ElementRef, Input, OnChanges, QueryList, SimpleChanges, ViewChild, ViewChildren } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Image } from '../types/image.type';
import { ImagePreviewDialogComponent } from './image-preview-dialog/image-preview-dialog.component';

@Component({
  selector: 'event-gallery',
  templateUrl: `./event-gallery.component.html`,
  styleUrls: ['./event-gallery.component.scss'],
})
export class EventGalleryComponent implements AfterViewInit, OnChanges {
  @Input()
  public images: Array<Image>;
  @ViewChildren('imagesRef')
  imagesRef: QueryList<any>;
  _imagesNextInx: number = 0;
  displayedImages: Array<Image>;

  public get imagesNextInx() {
    return this._imagesNextInx;
  }

  public set imagesNextInx(index: number) {
    this._imagesNextInx = Math.max(Math.min(this.images.length - 1, index), 0);
  }

  constructor(
    public dialog: MatDialog,
  ) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['images']) {
      let startIndex = Math.max(this.images.length - 4, 0);
      let endIndex = this.images.length;
      this.displayedImages = this.images.slice(startIndex,endIndex);
    }
  }

  ngAfterViewInit(){
    console.log(this.imagesRef.toArray());
  }

  openImagePreview(img: Image) {
    this.dialog.open(ImagePreviewDialogComponent, {
      data: {
        images: this.images,
        focusedImageIdx: this.images.findIndex(i => i.id === img.id),
      },
      width: '70vw',
      height: '80vh',
      panelClass: 'custom-modalbox',
      autoFocus: false,
    })
  }

  scrollToNext() {
    this.imagesNextInx += 4;
    this.imagesRef.toArray()[this.imagesNextInx].nativeElement.scrollIntoView({behavior: 'smooth'});
  }

  scrollToPrevious() {
    this.imagesNextInx -= 4;
    this.imagesRef.toArray()[this.imagesNextInx].nativeElement.scrollIntoView({behavior: 'smooth'});
  }

}
