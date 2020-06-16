import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVegetables } from 'app/shared/model/vegetables.model';

type EntityResponseType = HttpResponse<IVegetables>;
type EntityArrayResponseType = HttpResponse<IVegetables[]>;

@Injectable({ providedIn: 'root' })
export class VegetablesService {
  public resourceUrl = SERVER_API_URL + 'api/vegetables';

  constructor(protected http: HttpClient) {}

  create(vegetables: IVegetables): Observable<EntityResponseType> {
    return this.http.post<IVegetables>(this.resourceUrl, vegetables, { observe: 'response' });
  }

  update(vegetables: IVegetables): Observable<EntityResponseType> {
    return this.http.put<IVegetables>(this.resourceUrl, vegetables, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVegetables>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVegetables[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
