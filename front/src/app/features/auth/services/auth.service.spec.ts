import { SessionInformation } from "src/app/interfaces/sessionInformation.interface";
import { LoginRequest } from "../interfaces/loginRequest.interface";
import { RegisterRequest } from "../interfaces/registerRequest.interface";
import { AuthService } from "./auth.service"
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from "@angular/core/testing";

describe('AuthService', () => {
    let authService: AuthService;
    let httpMock: HttpTestingController;

    const mockLoginRequest: LoginRequest = {
        email: 'john@doe.com',
        password: 'motdepasse',
      };
    
      const mockRegisterRequest: RegisterRequest = {
        email: 'john@doe.com',
        firstName: 'John',
        lastName: 'Doe',
        password: 'motdepasse',
      };
    
      const mockSessionInformation: SessionInformation = {
        token: '123456789',
        type: 'session',
        id: 1,
        username: 'Coach',
        firstName: 'John',
        lastName: 'Doe',
        admin: false,
      };

      beforeEach(() => {
        TestBed.configureTestingModule({
          imports: [HttpClientTestingModule],
          providers: [AuthService],
        });
        authService = TestBed.inject(AuthService);
        httpMock = TestBed.inject(HttpTestingController);
      });

      afterEach(() => {
        httpMock.verify();
      });
    
      it('should be created', () => {
        expect(authService).toBeDefined();
      });

      it('should register', () => {
        authService.register(mockRegisterRequest).subscribe((response) => {
          expect(response).toBeUndefined();
        });
    
        const req = httpMock.expectOne('api/auth/register');
    
        expect(req.request.method).toBe('POST');
        expect(req.request.body).toEqual(mockRegisterRequest);
        req.flush(null);
      });
    
      it('should login', () => {
        authService.login(mockLoginRequest).subscribe((response) => {
          expect(response).toEqual(mockSessionInformation);
        });
    
        const req = httpMock.expectOne('api/auth/login');
    
        expect(req.request.method).toBe('POST');
        expect(req.request.body).toEqual(mockLoginRequest);
        req.flush(mockSessionInformation);
      });


})