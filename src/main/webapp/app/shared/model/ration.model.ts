export interface IRation {
  id?: number;
  name?: string;
  weight?: number;
  price?: number;
  imageContentType?: string;
  image?: any;
}

export class Ration implements IRation {
  constructor(
    public id?: number,
    public name?: string,
    public weight?: number,
    public price?: number,
    public imageContentType?: string,
    public image?: any
  ) {}
}
