import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { MaterialModule } from "../material.module";
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
        MainLayoutComponent
    ],
    declarations: [
        MainLayoutComponent
    ],
})
export class MainLayoutModule {

}