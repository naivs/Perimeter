import {Photo} from "./photobase/photobase.model";
import {Index} from "./photobase/index.model";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class RepositoryModel {
  private photos:Photo[];
  private indexes:string[];

  constructor(private http: HttpClient) {
    this.photos = [];
    this.indexes = [];
  }

  getPhotosByIndexes(indexes: string[]): Photo[] {
    this.http
      .post(`http://localhost:8090/photo/index`, indexes).subscribe(
      (response) => {
        let phts:Photo[] = [];
        Object.assign(phts, response);
        this.photos = phts;
          // .filter((photo) => {
          // return photo.indexes.some(pInd => {
          //   return indexes.some(selInd => {
          //     return pInd === selInd;
          //   })
          // })
        // });
      },
      (error) => console.log(error)
    );
    return this.photos;
  }

  getIndexes(): string[] {

    return this.indexes;
  }
}
