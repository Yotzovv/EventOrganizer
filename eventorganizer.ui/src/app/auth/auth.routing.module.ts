import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthGuardService } from "./auth.guard.service";
import { LoginFormComponent } from "./login-form/login-form.component";
import { RegisterComponent } from "./register-form/register.component";

const routes: Routes = [
  {
    path: 'auth',
    canActivate: [AuthGuardService],
    children: [
      {
        path: 'login',
        component: LoginFormComponent,
      },
      {
        path: 'register',
        component: RegisterComponent,
      },
    ]
  },
];
  
  @NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: [AuthGuardService]
  })
  export class AuthRoutingModule {
  }
  