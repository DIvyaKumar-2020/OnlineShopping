import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVegetables } from 'app/shared/model/vegetables.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VegetablesService } from './vegetables.service';
import { VegetablesDeleteDialogComponent } from './vegetables-delete-dialog.component';

@Component({
  selector: 'jhi-vegetables',
  templateUrl: './vegetables.component.html',
})
export class VegetablesComponent implements OnInit, OnDestroy {
  vegetables: IVegetables[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected vegetablesService: VegetablesService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.vegetables = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.vegetablesService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IVegetables[]>) => this.paginateVegetables(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.vegetables = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVegetables();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVegetables): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInVegetables(): void {
    this.eventSubscriber = this.eventManager.subscribe('vegetablesListModification', () => this.reset());
  }

  delete(vegetables: IVegetables): void {
    const modalRef = this.modalService.open(VegetablesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vegetables = vegetables;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateVegetables(data: IVegetables[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.vegetables.push(data[i]);
      }
    }
  }
}
