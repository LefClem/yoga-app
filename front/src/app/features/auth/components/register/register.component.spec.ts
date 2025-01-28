import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import { AuthService } from '../../services/auth.service';
import { of, throwError } from 'rxjs';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authService: AuthService;

  beforeEach(async () => {
    authService = new AuthService(null as any);
    authService.register = jest.fn();

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
        { provide: AuthService, useValue: authService }
      ],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,  
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return an error if the value is incorrect', () => {
    component.form.controls["email"].setValue("");
    component.form.controls["password"].setValue("");
    expect(component.form.invalid).toBeTruthy();
  })
  
  it('should call AuthService.register when form is valid', () => {
    const registerData = { firstName: "John", lastName: "Doe", email: 'test@example.com', password: 'password123' };
    component.form.setValue(registerData);
    (authService.register as jest.Mock).mockReturnValue(of(registerData)); 
  
    component.submit();
  
    expect(authService.register).toHaveBeenCalledWith(registerData);
    expect(authService.register).toHaveBeenCalled();
  });

  it('should set onError to true if AuthService.register fails', () => {
    const registerData = { firstName: "John", lastName: "Doe", email: 'test', password: 'pass' };
    component.form.setValue(registerData);
    (authService.register as jest.Mock).mockReturnValue(throwError(() => new Error('Registration failed')));

    component.submit();

    expect(component.onError).toBe(true);
  });

  
});
