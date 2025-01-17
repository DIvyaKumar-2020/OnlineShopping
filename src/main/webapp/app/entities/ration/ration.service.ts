import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRation } from 'app/shared/model/ration.model';

type EntityResponseType = HttpResponse<IRation>;
type EntityArrayResponseType = HttpResponse<IRation[]>;

@Injectable({ providedIn: 'root' })
export class RationService {
  public resourceUrl = SERVER_API_URL + 'api/rations';

  constructor(protected http: HttpClient) {}

  create(ration: IRation): Observable<EntityResponseType> {
    return this.http.post<IRation>(this.resourceUrl, ration, { observe: 'response' });
  }

  update(ration: IRation): Observable<EntityResponseType> {
    return this.http.put<IRation>(this.resourceUrl, ration, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
