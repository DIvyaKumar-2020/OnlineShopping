import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { VegetablesUpdateComponent } from 'app/entities/vegetables/vegetables-update.component';
import { VegetablesService } from 'app/entities/vegetables/vegetables.service';
import { Vegetables } from 'app/shared/model/vegetables.model';

describe('Component Tests', () => {
  describe('Vegetables Management Update Component', () => {
    let comp: VegetablesUpdateComponent;
    let fixture: ComponentFixture<VegetablesUpdateComponent>;
    let service: VegetablesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [VegetablesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VegetablesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VegetablesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VegetablesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vegetables(123);
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
        const entity = new Vegetables();
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
