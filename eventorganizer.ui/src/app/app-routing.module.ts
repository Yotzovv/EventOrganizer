import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';
import { LoginFormComponent } from './auth/login-form/login-form.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { MainGuardService } from './main-layout/main-guard.service';

const routes: Routes = [
  // { path: '**', component: HomePageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [MainGuardService]
})
export class AppRoutingModule { }
