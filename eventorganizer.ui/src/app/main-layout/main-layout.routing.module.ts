import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { HomePageComponent } from "../home-page/home-page.component";
import { LoginFormComponent } from "../login-form/login-form.component";
import { MainLayoutComponent } from "./main-layout.component";

const routes: Routes = [
    {
      path: 'login',
      component: LoginFormComponent,
    },
  ];
  
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
  })
  export class MainRoutingModule {
  }
  