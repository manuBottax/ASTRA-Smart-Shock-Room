import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TacDataComponent } from './tac-data.component';

describe('TacDataComponent', () => {
  let component: TacDataComponent;
  let fixture: ComponentFixture<TacDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TacDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TacDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
