import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMeat } from 'app/shared/model/meat.model';

type EntityResponseType = HttpResponse<IMeat>;
type EntityArrayResponseType = HttpResponse<IMeat[]>;

@Injectable({ providedIn: 'root' })
export class MeatService {
  public resourceUrl = SERVER_API_URL + 'api/meats';

  constructor(protected http: HttpClient) {}

  create(meat: IMeat): Observable<EntityResponseType> {
    return this.http.post<IMeat>(this.resourceUrl, meat, { observe: 'response' });
  }

  update(meat: IMeat): Observable<EntityResponseType> {
    return this.http.put<IMeat>(this.resourceUrl, meat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMeat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMeat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
