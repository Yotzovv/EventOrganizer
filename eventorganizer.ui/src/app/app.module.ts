import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LoginFormComponent } from './login-form/login-form.component';

import { MaterialModule } from './material.module';
import { AppRoutingModule } from './app-routing.module';
import { MainLayoutModule } from './main-layout/main-layout.module';
import { HomePageComponent } from './home-page/home-page.component';

@NgModule({
  imports:      [ MainLayoutModule, AppRoutingModule, BrowserModule, ReactiveFormsModule, MaterialModule, BrowserAnimationsModule],
  declarations: [ AppComponent, LoginFormComponent, HomePageComponent ],
  bootstrap:    [ AppComponent ],
})
export class AppModule { 
}
