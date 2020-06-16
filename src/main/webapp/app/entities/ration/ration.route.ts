import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRation, Ration } from 'app/shared/model/ration.model';
import { RationService } from './ration.service';
import { RationComponent } from './ration.component';
import { RationDetailComponent } from './ration-detail.component';
import { RationUpdateComponent } from './ration-update.component';

@Injectable({ providedIn: 'root' })
export class RationResolve implements Resolve<IRation> {
  constructor(private service: RationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ration: HttpResponse<Ration>) => {
          if (ration.body) {
            return of(ration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ration());
  }
}

export const rationRoute: Routes = [
  {
    path: '',
    component: RationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.ration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RationDetailComponent,
    resolve: {
      ration: RationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.ration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RationUpdateComponent,
    resolve: {
      ration: RationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.ration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RationUpdateComponent,
    resolve: {
      ration: RationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.ration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
