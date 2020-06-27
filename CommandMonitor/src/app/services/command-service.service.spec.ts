import { TestBed } from '@angular/core/testing';

import { CommandServiceService } from './command-service.service';

describe('CommandServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CommandServiceService = TestBed.get(CommandServiceService);
    expect(service).toBeTruthy();
  });
});
