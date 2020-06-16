import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'vegetables',
        loadChildren: () => import('./vegetables/vegetables.module').then(m => m.JhipsterSampleApplicationVegetablesModule),
      },
      {
        path: 'ration',
        loadChildren: () => import('./ration/ration.module').then(m => m.JhipsterSampleApplicationRationModule),
      },
      {
        path: 'meat',
        loadChildren: () => import('./meat/meat.module').then(m => m.JhipsterSampleApplicationMeatModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterSampleApplicationEntityModule {}
