import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';

import { FormComponent } from './form.component';
import { ActivatedRoute, Router } from '@angular/router';
import { Session } from '../../interfaces/session.interface';
import { of } from 'rxjs';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let router: Router;
  let sessionApiService: SessionApiService;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  } 

  const mockSession: Session = {
    name: 'Gym Session',
    description: 'Gym Description',
    date: new Date(),
    teacher_id: 1,
    users: [],
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({

      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule, 
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule,
        NoopAnimationsModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        SessionApiService
      ],
      declarations: [FormComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router);
    sessionApiService = TestBed.inject(SessionApiService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it("should redirect user to '/sessions' if user is not admin", () => {
    let navigateSpy = jest.spyOn(router, 'navigate');
    mockSessionService.sessionInformation.admin = false;

    component.ngOnInit();

    expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
  });

  it("should initialize in update mode if URL contains 'update'", () => {
  
    const detailSpy = jest
      .spyOn(sessionApiService, 'detail')
      .mockReturnValue(of(mockSession));
      
    const activatedRoute = TestBed.inject(ActivatedRoute);
    jest.spyOn(router, 'url', 'get').mockReturnValue('/sessions/update');
    jest.spyOn(activatedRoute.snapshot.paramMap, 'get').mockReturnValue('123'); // Simule l'ID
    const initFormSpy = jest.spyOn(component as any, 'initForm');

    component.ngOnInit();
  
    expect(component.onUpdate).toBe(true);
    expect(detailSpy).toHaveBeenCalledWith('123');
    expect(initFormSpy).toHaveBeenCalledWith(mockSession);
  });
  
  it('should initialize in create mode if URL does not contain "update"', () => {
    const initFormSpy = jest.spyOn(component as any, 'initForm');
  
    jest.spyOn(router, 'url', 'get').mockReturnValue('/sessions/create');
  
    component.ngOnInit();
  
    expect(component.onUpdate).toBe(false);
    expect(initFormSpy).toHaveBeenCalledWith();
  });
  it('should submit update form successfully', () => {
    let sessionApiServiceSpy = jest
      .spyOn(sessionApiService, 'update')
      .mockReturnValue(of(mockSession));
    component.onUpdate = true;

    component.submit();

    expect(sessionApiServiceSpy).toHaveBeenCalled();
  });

  it('should submit create form successfully', () => {
    let sessionApiServiceSpy = jest
    .spyOn(sessionApiService, 'create')
    .mockReturnValue(of(mockSession));

    component.onUpdate = false;

    component.submit();

    expect(sessionApiServiceSpy).toHaveBeenCalled();
  })

  it('should navigate back on click on arrow', () => {
    const navigateSpy = jest.spyOn(router, 'navigate');

    component['exitPage']('redirect to sessions');

    expect(navigateSpy).toHaveBeenCalledWith(['sessions']);
  });
});
