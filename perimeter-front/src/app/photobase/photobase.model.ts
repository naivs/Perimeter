export class Photo {

  constructor(public id: number,
              public name: string,
              public timestamp: string,
              public added?: string,
              public description?: string,
              public indexes?: string[],
              public thumbnailName?: string) {}
}
