export interface IVegetables {
  id?: number;
  name?: string;
  weight?: number;
  price?: number;
  imageContentType?: string;
  image?: any;
}

export class Vegetables implements IVegetables {
  constructor(
    public id?: number,
    public name?: string,
    public weight?: number,
    public price?: number,
    public imageContentType?: string,
    public image?: any
  ) {}
}
