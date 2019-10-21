import {Photo} from "./photobase/photobase.model";

export class SimpleDataSource {
  private photos:Photo[];
  private indexes:string[];

  constructor() {
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

  getPhotos(): Photo[] {
    return this.photos;
  }

  getIndexes(): string[] {
    return this.indexes;
  }
}
