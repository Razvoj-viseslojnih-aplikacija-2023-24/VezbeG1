import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DobavljacComponent } from './dobavljac.component';

describe('DobavljacComponent', () => {
  let component: DobavljacComponent;
  let fixture: ComponentFixture<DobavljacComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DobavljacComponent]
    });
    fixture = TestBed.createComponent(DobavljacComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
