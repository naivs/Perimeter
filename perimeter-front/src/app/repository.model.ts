import {SimpleDataSource} from "./datasource.model";
import {Photo} from "./photobase/photobase.model";

export class Model {
  private dataSource: SimpleDataSource;
  private photos: Photo[];

  constructor() {
    this.dataSource = new SimpleDataSource();
    this.photos = [];
    this.dataSource.getPhotos().forEach(p => this.photos.push(p));
  }

  getPhotos(indexes: string[]): Photo[] {
    return this.dataSource.getPhotos().filter((photo) => {
      return photo.indexes.some(pInd => {
        return indexes.some(selInd => {
          return pInd === selInd;
        })
      })
    });
  }

  getIndexes(): string[] {
    return this.dataSource.getIndexes();
  }
}
