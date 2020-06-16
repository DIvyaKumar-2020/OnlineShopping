import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IVegetables, Vegetables } from 'app/shared/model/vegetables.model';
import { VegetablesService } from './vegetables.service';
import { VegetablesComponent } from './vegetables.component';
import { VegetablesDetailComponent } from './vegetables-detail.component';
import { VegetablesUpdateComponent } from './vegetables-update.component';

@Injectable({ providedIn: 'root' })
export class VegetablesResolve implements Resolve<IVegetables> {
  constructor(private service: VegetablesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVegetables> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vegetables: HttpResponse<Vegetables>) => {
          if (vegetables.body) {
            return of(vegetables.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Vegetables());
  }
}

export const vegetablesRoute: Routes = [
  {
    path: '',
    component: VegetablesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.vegetables.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VegetablesDetailComponent,
    resolve: {
      vegetables: VegetablesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.vegetables.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VegetablesUpdateComponent,
    resolve: {
      vegetables: VegetablesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.vegetables.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VegetablesUpdateComponent,
    resolve: {
      vegetables: VegetablesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.vegetables.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
