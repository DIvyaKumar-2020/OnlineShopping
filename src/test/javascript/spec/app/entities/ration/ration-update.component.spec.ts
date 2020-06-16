import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { RationUpdateComponent } from 'app/entities/ration/ration-update.component';
import { RationService } from 'app/entities/ration/ration.service';
import { Ration } from 'app/shared/model/ration.model';

describe('Component Tests', () => {
  describe('Ration Management Update Component', () => {
    let comp: RationUpdateComponent;
    let fixture: ComponentFixture<RationUpdateComponent>;
    let service: RationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [RationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ration(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ration();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
