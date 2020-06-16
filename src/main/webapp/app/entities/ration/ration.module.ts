import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { RationComponent } from './ration.component';
import { RationDetailComponent } from './ration-detail.component';
import { RationUpdateComponent } from './ration-update.component';
import { RationDeleteDialogComponent } from './ration-delete-dialog.component';
import { rationRoute } from './ration.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(rationRoute)],
  declarations: [RationComponent, RationDetailComponent, RationUpdateComponent, RationDeleteDialogComponent],
  entryComponents: [RationDeleteDialogComponent],
})
export class JhipsterSampleApplicationRationModule {}
