import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PatientInitialConditionDataComponent } from './patient-initial-condition-data.component';

describe('PatientInitialConditionDataComponent', () => {
  let component: PatientInitialConditionDataComponent;
  let fixture: ComponentFixture<PatientInitialConditionDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PatientInitialConditionDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientInitialConditionDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
