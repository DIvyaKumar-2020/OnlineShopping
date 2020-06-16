import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { VegetablesComponent } from './vegetables.component';
import { VegetablesDetailComponent } from './vegetables-detail.component';
import { VegetablesUpdateComponent } from './vegetables-update.component';
import { VegetablesDeleteDialogComponent } from './vegetables-delete-dialog.component';
import { vegetablesRoute } from './vegetables.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(vegetablesRoute)],
  declarations: [VegetablesComponent, VegetablesDetailComponent, VegetablesUpdateComponent, VegetablesDeleteDialogComponent],
  entryComponents: [VegetablesDeleteDialogComponent],
})
export class JhipsterSampleApplicationVegetablesModule {}
