import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Image } from 'src/app/types/image.type';

@Component({
  selector: 'image-preview-dialog',
  templateUrl: `./image-preview-dialog.component.html`,
  styleUrls: ['./image-preview-dialog.component.scss'],
})
export class ImagePreviewDialogComponent {
    images: Image[] = [];
    focusedImageIdx = 0;

  constructor(
    public dialogRef: MatDialogRef<ImagePreviewDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { images: Image[], focusedImageIdx: number }
  ) {
    this.images = data.images;
    this.focusedImageIdx = data.focusedImageIdx;
  }
  
  moveLeft() {
    this.focusedImageIdx = Math.max(0, this.focusedImageIdx-1)
  }

  moveRight() {
    this.focusedImageIdx = Math.min(this.images.length, this.focusedImageIdx+1)
  }
}
