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
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { Dash } from "./dash/dash.component";
import { HttpClient, HttpClientModule } from "@angular/common/http";
import { RepositoryModel } from "./repository.model";
import { GridGalleryItemComponent, DialogOverviewExampleDialog } from "./photobase/grid-gallery/grid-item/grid-item.component";
import { GridGalleryComponent } from "./photobase/grid-gallery/grid-gallery.component";
import { MatButtonToggleModule } from "@angular/material";
import { MatTooltipModule } from "@angular/material";
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from "@angular/material";
import { ImageViewerModule } from "ngx-image-viewer";

@NgModule({
  declarations: [
    AppDevices,
    AppSettings,
    Photobase,
    MyNavComponent,
    LibraryComponent,
    Dash,
    GridGalleryItemComponent,
    GridGalleryComponent,
    DialogOverviewExampleDialog
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
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatButtonToggleModule,
    MatTooltipModule,
    MatDialogModule,
    MatFormFieldModule,
    ImageViewerModule.forRoot({
      wheelZoom: true,
      btnIcons: {
        zoomIn: 'fa fa-plus',
        zoomOut: 'fa fa-minus',
        rotateClockwise: 'fa fa-repeat',
        rotateCounterClockwise: 'fa fa-undo',
        next: 'fa fa-arrow-right',
        prev: 'fa fa-arrow-left',
        fullscreen: 'fa fa-arrows-alt'
      },
      btnShow: {
        zoomIn: true,
        zoomOut: true,
        rotateClockwise: false,
        rotateCounterClockwise: false,
        next: false,
        prev: false
      }
    })
  ],
  providers: [HttpClient, RepositoryModel],
  bootstrap: [MyNavComponent, DialogOverviewExampleDialog]
})
export class AppModule { }
