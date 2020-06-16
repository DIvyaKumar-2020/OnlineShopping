import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMeat } from 'app/shared/model/meat.model';
import { MeatService } from './meat.service';

@Component({
  templateUrl: './meat-delete-dialog.component.html',
})
export class MeatDeleteDialogComponent {
  meat?: IMeat;

  constructor(protected meatService: MeatService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.meatService.delete(id).subscribe(() => {
      this.eventManager.broadcast('meatListModification');
      this.activeModal.close();
    });
  }
}
