import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { RegisterComponent } from './register-form/register.component';
import { MaterialModule } from './material.module';
import { AppRoutingModule } from './app-routing.module';
import { MainLayoutModule } from './main-layout/main-layout.module';
import { HomePageComponent } from './home-page/home-page.component';
import { CommonModule } from '@angular/common';
import { AuthService } from './register-form/auth.service';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { RequestService } from './request/request.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports:      [ CommonModule, MainLayoutModule,
    AppRoutingModule, BrowserModule, ReactiveFormsModule, MaterialModule, BrowserAnimationsModule,
    HttpClientModule,
  ],
  declarations: [ AppComponent, LoginFormComponent, RegisterComponent, ProfilePageComponent ],
  bootstrap:    [ AppComponent ],
  providers:    [ AuthService,RequestService ],
})
export class AppModule { 
}
