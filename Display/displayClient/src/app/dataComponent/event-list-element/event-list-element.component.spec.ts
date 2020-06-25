import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventListElementComponent } from './event-list-element.component';

describe('EventListElementComponent', () => {
  let component: EventListElementComponent;
  let fixture: ComponentFixture<EventListElementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventListElementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventListElementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
