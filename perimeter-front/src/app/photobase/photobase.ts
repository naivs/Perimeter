import { Component } from '@angular/core';
import { Model } from "../repository.model";
import {Photo} from "./photobase.model";

@Component({
  selector: 'photobase',
  templateUrl: './photobase.html',
  styleUrls: ['photobase.css']
})
export class Photobase {
  photos: Photo[] = [];
  model: Model = new Model();

  selectedIndexes: string[] = [];

  toggle() {
    this.photos = this.model.getPhotos(this.selectedIndexes)
  }
}
