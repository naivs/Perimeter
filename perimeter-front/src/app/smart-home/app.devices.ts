import { Component } from "@angular/core"
import {Device} from "./app.device.model";
import {Param} from "./app.param.model";

@Component({
  selector: "smart-home-devices",
  templateUrl: "app.devices.html"
})
export class AppDevices {
  devices: Device[] = [
    new Device(1, 42, "device_one"),
    new Device(2, 43, "device_two"),
    new Device(3, 63, "device_tree"),
    new Device(4, 13, "device_four"),
    new Device(5, 11, "device_five"),
    new Device(6, 71, "device_six"),
    new Device(7, 21, "device_seven")
  ];
  param: Param = new Param("test_param", "DX", "simple param");
}
