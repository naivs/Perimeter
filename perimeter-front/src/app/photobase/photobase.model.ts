export class Photo {

  constructor(public name?: string,
              public filename?: string,
              public path?: string,
              public timestamp?: string,
              public added?: string,
              public description?: string,
              public indexes?: string[],
              public thumbnail?: string,
              public hash?: string,
              public uuid?: string) {}
}
