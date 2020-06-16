import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRation } from 'app/shared/model/ration.model';
import { RationService } from './ration.service';

@Component({
  templateUrl: './ration-delete-dialog.component.html',
})
export class RationDeleteDialogComponent {
  ration?: IRation;

  constructor(protected rationService: RationService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.rationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('rationListModification');
      this.activeModal.close();
    });
  }
}
