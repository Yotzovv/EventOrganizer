import { SearchBar } from './main-layout/search-bar/search-bar.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
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
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthModule } from './auth/auth.module';
import { EventDetailsComponent } from './event-details-page/event-details.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AuthInterceptor } from './auth/auth.interceptor';
import {MatChipsModule} from '@angular/material/chips';
import { BlockedUsersListComponent } from './blocked-users-list/blocked-users-list.component';

@NgModule({
  imports:      [ 
    CommonModule, 
    MainLayoutModule, 
    AuthModule,
    AppRoutingModule,
    BrowserModule,
    ReactiveFormsModule,
    MaterialModule, 
    BrowserAnimationsModule,
    HttpClientModule, 
    MatTabsModule,
    MatButtonToggleModule,
    MatChipsModule,
    MatFormFieldModule,
  ],
  declarations: [
    AppComponent, 
    ProfilePageComponent, 
    AdminPageComponent,
    BlockedUsersListComponent,
    SearchBar,
    HomePageComponent,
    EventDetailsComponent,
  ],
  bootstrap:    [ AppComponent ],
  providers:    [ 
    RequestService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    }
  ],
})
export class AppModule { 
}
