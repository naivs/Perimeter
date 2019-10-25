import {Photo} from "./photobase/photobase.model";
import {Index} from "./photobase/index.model";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class RepositoryModel {
  private photos:Photo[];
  private indexes:string[];
  private url:string = "http://localhost:8090/photo";

  constructor(private http: HttpClient) {
    this.photos = [
      new Photo("test_1",
        "https://material.angular.io/assets/img/examples/shiba2.jpg",
        "Описание раз", "https://material.angular.io/assets/img/examples/shiba2.jpg",
        ["root", "travel"]),
      new Photo("test_2",
        "https://material.angular.io/assets/img/examples/shiba2.jpg",
        "Описание два", "https://material.angular.io/assets/img/examples/shiba2.jpg",
        ["root"]),
      new Photo("test_3",
        "https://material.angular.io/assets/img/examples/shiba2.jpg",
        "Описание три", "https://material.angular.io/assets/img/examples/shiba2.jpg",
        ["root", "travel"]),
      new Photo("test_4",
        "https://material.angular.io/assets/img/examples/shiba2.jpg",
        "Описание четыре", "https://material.angular.io/assets/img/examples/shiba2.jpg",
        ["root"]),
      new Photo("test_5",
        "https://material.angular.io/assets/img/examples/shiba2.jpg",
        "Описание пять", "https://material.angular.io/assets/img/examples/shiba2.jpg",
        ["root"]),
      new Photo("Future",
        "assets/test.jpg", "Это вобще супер картинка", "assets/test.jpg",
        ["root", "best"])
    ];

    this.indexes = ["root", "travel", "best"];
  }

  getPhotos(indexes: string[]): Photo[] {
    return this.photos.filter((photo) => {
      return photo.indexes.some(pInd => {
        return indexes.some(selInd => {
          return pInd === selInd;
        })
      })
    });
  }

  getIndexes(): string[] {
    this.http
      .get(`${this.url}/index`).subscribe(
      (response) => {
        let idx:Index[] = [];
        Object.assign(idx, response);
        this.indexes = idx.map(idxItem => idxItem.name);
      },
      (error) => console.log(error)
    );
    return this.indexes;
  }
}
