import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { RegisterComponent } from './register-form/register.component';
import { MaterialModule } from './material.module';
import { AppRoutingModule } from './app-routing.module';
import { AuthService } from './register-form/auth.service';
import { CommonModule } from '@angular/common';

@NgModule({
  imports:      [ CommonModule ,AppRoutingModule, BrowserModule, ReactiveFormsModule, MaterialModule, BrowserAnimationsModule],
  declarations: [ AppComponent, LoginFormComponent, RegisterComponent ],
  bootstrap:    [ AppComponent ],
  providers:    [ AuthService ],
})
export class AppModule { 
}
