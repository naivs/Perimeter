import {Component, ɵSWITCH_COMPILE_INJECTABLE__POST_R3__} from '@angular/core';
import {Photo} from "./photobase.model";
import { RepositoryModel } from "../repository.model";

@Component({
  selector: 'photobase',
  templateUrl: './photobase.html',
  styleUrls: ['photobase.css']
})
export class Photobase {
  photos: Photo[] = [];
  indexes: String[] = [];
  selectedIndexes: string[] = [];

  constructor(private repository:RepositoryModel) {
  }

  toggle() {
    this.repository.getIndexes();
  }

  updateIndexes() {
    this.indexes = this.repository.getIndexes();
  }
}
