import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMeat, Meat } from 'app/shared/model/meat.model';
import { MeatService } from './meat.service';
import { MeatComponent } from './meat.component';
import { MeatDetailComponent } from './meat-detail.component';
import { MeatUpdateComponent } from './meat-update.component';

@Injectable({ providedIn: 'root' })
export class MeatResolve implements Resolve<IMeat> {
  constructor(private service: MeatService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMeat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((meat: HttpResponse<Meat>) => {
          if (meat.body) {
            return of(meat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Meat());
  }
}

export const meatRoute: Routes = [
  {
    path: '',
    component: MeatComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.meat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MeatDetailComponent,
    resolve: {
      meat: MeatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.meat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MeatUpdateComponent,
    resolve: {
      meat: MeatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.meat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MeatUpdateComponent,
    resolve: {
      meat: MeatResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.meat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
