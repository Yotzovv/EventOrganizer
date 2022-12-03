import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { MaterialModule } from './material.module';
import { AppRoutingModule } from './app-routing.module';
import { MainLayoutModule } from './main-layout/main-layout.module';
import { HomePageComponent } from './home-page/home-page.component';
import { CommonModule } from '@angular/common';
import { ProfilePageComponent } from './profile-page/profile-page.component';
import { RequestService } from './request/request.service';
import { HttpClientModule } from '@angular/common/http';
import { AuthModule } from './auth/auth.module';

@NgModule({
  imports:      [ CommonModule, MainLayoutModule, AuthModule,
    AppRoutingModule, BrowserModule, ReactiveFormsModule, MaterialModule, BrowserAnimationsModule,
    HttpClientModule,
  ],
  declarations: [ AppComponent, ProfilePageComponent ],
  bootstrap:    [ AppComponent ],
  providers:    [ RequestService ],
})
export class AppModule { 
}
