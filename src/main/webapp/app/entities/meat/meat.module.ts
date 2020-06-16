import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { MeatComponent } from './meat.component';
import { MeatDetailComponent } from './meat-detail.component';
import { MeatUpdateComponent } from './meat-update.component';
import { MeatDeleteDialogComponent } from './meat-delete-dialog.component';
import { meatRoute } from './meat.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(meatRoute)],
  declarations: [MeatComponent, MeatDetailComponent, MeatUpdateComponent, MeatDeleteDialogComponent],
  entryComponents: [MeatDeleteDialogComponent],
})
export class JhipsterSampleApplicationMeatModule {}
