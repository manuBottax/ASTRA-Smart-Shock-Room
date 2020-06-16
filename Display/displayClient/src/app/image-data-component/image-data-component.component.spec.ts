import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImageDataComponentComponent } from './image-data-component.component';

describe('ImageDataComponentComponent', () => {
  let component: ImageDataComponentComponent;
  let fixture: ComponentFixture<ImageDataComponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImageDataComponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImageDataComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
