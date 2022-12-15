import { AdminPageComponent } from './../admin-page/admin-page.component';
import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { EventDetailsComponent } from "../event-details-page/event-details.component";
import { HomePageComponent } from "../home-page/home-page.component";
import { ProfilePageComponent } from "../profile-page/profile-page.component";
import { MainGuardService } from "./main-guard.service";
import { MainLayoutComponent } from "./main-layout.component";

const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [MainGuardService],
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: '/home',
      },
      {
        path: 'home',
        component: HomePageComponent,
      },
      {
        path: 'profile',
        component: ProfilePageComponent,
      },
      {
        path: 'event-details/:id',
        component: EventDetailsComponent
      },
      {
        path: 'adminpage',
        component: AdminPageComponent
      }
    ]
  },
];
  
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [MainGuardService]
  })
  export class MainRoutingModule {
  }
  