import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { AuthService } from '../../services/auth.service';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let sessionService: SessionService;
  let router: Router;

  beforeEach(async () => {

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        SessionService,
        AuthService
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    authService = TestBed.inject(AuthService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    jest.spyOn(router, 'navigate');
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return an error if the value is incorrect', () => {
    component.form.controls["email"].setValue("");
    component.form.controls["password"].setValue("");
    expect(component.form.invalid).toBeTruthy();
  })

  it('should show error if an invalid email is entered', () => {
    component.form.controls['email'].setValue('invalid-email');
    component.form.controls['password'].setValue('password123');
    expect(component.form.controls['email'].invalid).toBeTruthy();
  });

  it('should call AuthService.login when form is valid', () => {
    /**
     * Call login from sessionService once
     * with value response as params
     * reponse params defined as SessionInformations
     * Call navigate to /sessions
    */
   
    const loginData = { email: 'test@example.com', password: 'password123' };
    component.form.setValue(loginData);
    const mockSessionInformation = {
      token: 'mock-token',
      type: 'mock-type',
      id: 1,
      username: "User",
      firstName: "John",
      lastName: "Doe",
      admin: false
    }
    const mockLogin = jest.spyOn(authService, 'login').mockReturnValue(of(mockSessionInformation));
    const mockSessionLogIn = jest.spyOn(sessionService, 'logIn');
    component.submit();

    expect(mockLogin).toHaveBeenCalledTimes(1);
    expect(mockLogin).toHaveBeenCalledWith(loginData);
    expect(mockSessionLogIn).toHaveBeenCalledTimes(1);
    expect(mockSessionLogIn).toHaveBeenCalledWith(mockSessionInformation);
    expect(router.navigate).toHaveBeenCalledWith(['/sessions'])
    expect(component.onError).toBeFalsy();
  });

  it('should set onError to true if AuthService.login fails', () => {
    const loginData = { email: 'test@example.com', password: 'password123' };
    component.form.setValue(loginData);
    const mockLogin = jest.spyOn(authService, 'login').mockReturnValue(throwError(() => new Error('Formulaire invalide')));
    component.submit();

    expect(mockLogin).toHaveBeenCalledTimes(1);
    expect(mockLogin).toHaveBeenCalledWith(loginData);
    expect(component.onError).toBe(true);
  });
});
