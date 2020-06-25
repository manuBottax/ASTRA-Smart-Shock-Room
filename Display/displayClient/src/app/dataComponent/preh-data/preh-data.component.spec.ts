import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrehDataComponent } from './preh-data.component';

describe('PrehDataComponent', () => {
  let component: PrehDataComponent;
  let fixture: ComponentFixture<PrehDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrehDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrehDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
