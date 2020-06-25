import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TraumaInfoDataComponent } from './trauma-info-data.component';

describe('TraumaInfoDataComponent', () => {
  let component: TraumaInfoDataComponent;
  let fixture: ComponentFixture<TraumaInfoDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TraumaInfoDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TraumaInfoDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
