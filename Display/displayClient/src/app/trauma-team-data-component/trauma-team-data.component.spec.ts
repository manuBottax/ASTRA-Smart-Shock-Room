import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TraumaTeamDataComponent } from './trauma-team-data.component';

describe('TraumaTeamDataComponent', () => {
  let component: TraumaTeamDataComponent;
  let fixture: ComponentFixture<TraumaTeamDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TraumaTeamDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TraumaTeamDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
