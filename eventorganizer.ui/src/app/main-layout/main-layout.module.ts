import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { MaterialModule } from "../material.module";
import { DialogAnimationsExample } from "./create-event-dialog/create-event.component";
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
        DialogAnimationsExample     // TODO: Rename this dialog
    ],
    declarations: [
        MainLayoutComponent,
        DialogAnimationsExample
    ],
    entryComponents: [
        DialogAnimationsExample
    ]
})
export class MainLayoutModule {

}