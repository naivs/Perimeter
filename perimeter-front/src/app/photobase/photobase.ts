import {Component, ɵSWITCH_COMPILE_INJECTABLE__POST_R3__} from '@angular/core';
import {Photo} from "./photobase.model";
import { RepositoryModel } from "../repository.model";
import {HttpClient} from "@angular/common/http";
import {Index} from "./index.model";

@Component({
  selector: 'photobase',
  templateUrl: './photobase.html',
  styleUrls: ['photobase.css']
})
export class Photobase {
  photos: Photo[] = [];
  indexes: String[] = [];
  selectedIndexes: string[] = [];

  constructor(private repository:RepositoryModel, private http: HttpClient) {
    this.getIndexes();
  }

  getIndexes() {
    let idxs:string[] = [];
    this.http.get(`http://localhost:8090//photo/index`).subscribe(
      (response) => {
        let idx:Index[] = [];
        Object.assign(idx, response);
        this.indexes = idx.map(idxItem => idxItem.name);
      },
      (error) => console.log(error)
    );

    return idxs;
  }

  toggle(index:string) {
    this.selectedIndexes = [];
    this.selectedIndexes.push(index);
    if (this.selectedIndexes.length == 0) {
      this.photos = [];
    } else {
      this.http
        .post(`http://localhost:8090/photo/index`, this.selectedIndexes).subscribe(
        (response) => {
          let phts:Photo[] = [];
          Object.assign(phts, response);
          this.photos = phts;
        },
        (error) => console.log(error)
      );
    }
  }

  getLocation():string {
    // return window.location.origin;
    return 'http://localhost:8090/';
  }
}
