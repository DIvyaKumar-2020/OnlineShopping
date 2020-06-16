export interface IMeat {
  id?: number;
  name?: string;
  weight?: number;
  price?: number;
  imageContentType?: string;
  image?: any;
}

export class Meat implements IMeat {
  constructor(
    public id?: number,
    public name?: string,
    public weight?: number,
    public price?: number,
    public imageContentType?: string,
    public image?: any
  ) {}
}
