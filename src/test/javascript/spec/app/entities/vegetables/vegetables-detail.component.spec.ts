import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { VegetablesDetailComponent } from 'app/entities/vegetables/vegetables-detail.component';
import { Vegetables } from 'app/shared/model/vegetables.model';

describe('Component Tests', () => {
  describe('Vegetables Management Detail Component', () => {
    let comp: VegetablesDetailComponent;
    let fixture: ComponentFixture<VegetablesDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ vegetables: new Vegetables(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [VegetablesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VegetablesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VegetablesDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load vegetables on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vegetables).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
