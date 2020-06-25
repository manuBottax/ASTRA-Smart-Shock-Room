import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventListDataComponent } from './event-list-data.component';

describe('EventListDataComponent', () => {
  let component: EventListDataComponent;
  let fixture: ComponentFixture<EventListDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventListDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventListDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
