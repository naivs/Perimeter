import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppDevices } from "./smart-home/app.devices";
import {AppSettings} from "./smart-home/app.settings";

const routes: Routes = [
  { path: "dash", component: AppDevices },

  { path: "smart-home/devices", component: AppDevices },
  { path: "smart-home/reports", component: AppDevices },
  { path: "smart-home/settings", component: AppSettings }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
