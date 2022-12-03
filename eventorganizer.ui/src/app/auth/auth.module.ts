import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { MaterialModule } from "../material.module";
import { AuthRoutingModule } from "./auth.routing.module";
import { LoginFormComponent } from "./login-form/login-form.component";
import { RegisterComponent } from "./register-form/register.component";

@NgModule({
    imports: [
        MaterialModule,
        AuthRoutingModule
    ],
    exports: [
        AuthRoutingModule
    ],
    declarations: [
        LoginFormComponent,
        RegisterComponent
    ],
})
export class AuthModule {

}