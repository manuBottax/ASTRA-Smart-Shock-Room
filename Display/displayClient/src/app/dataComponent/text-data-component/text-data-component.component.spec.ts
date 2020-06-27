import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TextDataComponentComponent } from './text-data-component.component';

describe('TextDataComponentComponent', () => {
  let component: TextDataComponentComponent;
  let fixture: ComponentFixture<TextDataComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TextDataComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TextDataComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
