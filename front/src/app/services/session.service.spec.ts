import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import { User } from '../interfaces/user.interface';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  const mockSessionInformation: SessionInformation = {
    token: 'mock-token',
    type: 'mock-type',
    id: 1,
    username: "User",
    firstName: "John",
    lastName: "Doe",
    admin: false
  }

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initialize with default values', () => {
    expect(service.isLogged).toBeFalsy();
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should emit the correct logged in state', () => {
    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBeFalsy();
    })
  })

  it('should log a user and update isLogged', () => {
    service.logIn(mockSessionInformation);

    expect(service.isLogged).toBeTruthy();
    expect(service.sessionInformation).toEqual(mockSessionInformation);
  })

  it('should logout a user and update isLogged', () => {
    service.logOut();

    expect(service.sessionInformation).toEqual(undefined);
    expect(service.isLogged).toBeFalsy();
  })
  
});
