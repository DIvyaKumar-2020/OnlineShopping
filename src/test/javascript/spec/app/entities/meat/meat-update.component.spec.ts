import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { MeatUpdateComponent } from 'app/entities/meat/meat-update.component';
import { MeatService } from 'app/entities/meat/meat.service';
import { Meat } from 'app/shared/model/meat.model';

describe('Component Tests', () => {
  describe('Meat Management Update Component', () => {
    let comp: MeatUpdateComponent;
    let fixture: ComponentFixture<MeatUpdateComponent>;
    let service: MeatService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [MeatUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MeatUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeatUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeatService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Meat(123);
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
        const entity = new Meat();
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
