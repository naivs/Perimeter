import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppDevices } from "./smart-home/app.devices";
import { AppSettings } from "./smart-home/app.settings";
import { Photobase } from "./photobase/photobase";
import { LibraryComponent } from "./library/library.component";
import {Dash} from "./dash/dash.component";

const routes: Routes = [
  { path: "dash", component:  Dash},

  { path: "smart-home", component: AppDevices },
  { path: "smart-home/settings", component: AppSettings },

  { path: "photobase", component: Photobase },
  { path: "library", component: LibraryComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
