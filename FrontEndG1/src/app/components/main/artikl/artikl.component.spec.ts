import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtiklComponent } from './artikl.component';

describe('ArtiklComponent', () => {
  let component: ArtiklComponent;
  let fixture: ComponentFixture<ArtiklComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ArtiklComponent]
    });
    fixture = TestBed.createComponent(ArtiklComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
