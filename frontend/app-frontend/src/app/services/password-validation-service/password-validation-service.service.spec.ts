import { TestBed } from '@angular/core/testing';

import { PasswordValidationServiceService } from './password-validation-service.service';

describe('PasswordValidationServiceService', () => {
  let service: PasswordValidationServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PasswordValidationServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
