import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { MaterialModule } from "../material.module";
import { DialogAnimationsExampleDialog } from "../create-edit-event-dialog/create-event-dialog.component";
import { MainLayoutComponent } from "./main-layout.component";
import { MainRoutingModule } from "./main-layout.routing.module";
import { ConfirmationDialogComponent } from "../confirmation-dialog/confirmation-dialog.component";
import { ImagePreviewDialogComponent } from "../event-gallery/image-preview-dialog/image-preview-dialog.component";
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { EventFeedbackModalComponent } from "../event-feedback/event-feedback-modal/event-feedback-modal.component";

@NgModule({
    imports: [
        CommonModule,
        MaterialModule,
        RouterModule,
        MainRoutingModule,
        MatFormFieldModule,
        MatInputModule,
    ],
    exports: [
        MainRoutingModule,
        MainLayoutComponent,
    ],
    declarations: [
        MainLayoutComponent,
        DialogAnimationsExampleDialog,
        ConfirmationDialogComponent,
        ImagePreviewDialogComponent,
        EventFeedbackModalComponent,
    ],
})
export class MainLayoutModule {

}