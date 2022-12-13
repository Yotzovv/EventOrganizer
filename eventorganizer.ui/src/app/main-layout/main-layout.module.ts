import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { MaterialModule } from "../material.module";
import { DialogAnimationsExampleDialog } from "../create-edit-event-dialog/create-event-dialog.component";
import { MainLayoutComponent } from "./main-layout.component";
import { MainRoutingModule } from "./main-layout.routing.module";

@NgModule({
    imports: [
        CommonModule,
        MaterialModule,
        RouterModule,
        MainRoutingModule,
    ],
    exports: [
        MainRoutingModule,
        MainLayoutComponent,
    ],
    declarations: [
        MainLayoutComponent,
        DialogAnimationsExampleDialog
    ],
})
export class MainLayoutModule {

}