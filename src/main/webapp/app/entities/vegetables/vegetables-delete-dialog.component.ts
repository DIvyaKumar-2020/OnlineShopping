import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVegetables } from 'app/shared/model/vegetables.model';
import { VegetablesService } from './vegetables.service';

@Component({
  templateUrl: './vegetables-delete-dialog.component.html',
})
export class VegetablesDeleteDialogComponent {
  vegetables?: IVegetables;

  constructor(
    protected vegetablesService: VegetablesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vegetablesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('vegetablesListModification');
      this.activeModal.close();
    });
  }
}
