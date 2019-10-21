import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppDevices } from "./smart-home/app.devices";
import { AppSettings } from "./smart-home/app.settings";
import { Photobase } from "./photobase/photobase";
import { MyNavComponent } from './my-nav/my-nav.component';
import { LibraryComponent } from './library/library.component';
import { LayoutModule } from '@angular/cdk/layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { MatPaginatorModule } from "@angular/material";
import { MatCardModule } from "@angular/material";
import { MatGridListModule } from "@angular/material";
import { FormsModule } from "@angular/forms";
import { Dash } from "./dash/dash.component";

@NgModule({
  declarations: [
    AppDevices,
    AppSettings,
    Photobase,
    MyNavComponent,
    LibraryComponent,
    Dash
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    BrowserAnimationsModule,
    MatPaginatorModule,
    MatCardModule,
    MatGridListModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [MyNavComponent]
})
export class AppModule { }
