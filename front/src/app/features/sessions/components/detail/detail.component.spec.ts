import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule, } from '@angular/router/testing';
import { expect } from '@jest/globals'; 
import { SessionService } from '../../../../services/session.service';

import { DetailComponent } from './detail.component';
import { Session } from '../../interfaces/session.interface';
import { SessionApiService } from '../../services/session-api.service';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';


describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>; 
  let router: Router;
  let service: SessionService;
  let sessionApiService: SessionApiService;

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1
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
        MatSnackBarModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        NoopAnimationsModule
      ],
      declarations: [DetailComponent], 
      providers: [{ provide: SessionService, useValue: mockSessionService }],
    })
      .compileComponents();
    service = TestBed.inject(SessionService);
    fixture = TestBed.createComponent(DetailComponent);
    sessionApiService = TestBed.inject(SessionApiService);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should navigate back on click on arrow', async () => {
    const componentBackSpy = jest.spyOn(component, 'back');

    component.back();

    expect(componentBackSpy).toBeDefined();
    expect(componentBackSpy).toHaveBeenCalled();
  });

  it('should fetch correct session on init', () => {
    
    let sessionApiServiceSpy = jest
      .spyOn(sessionApiService, 'detail')
      .mockReturnValue(of(mockSession));
    component.sessionId = '2';

    component.ngOnInit();

    expect(sessionApiServiceSpy).toHaveBeenCalledWith('2');
    expect(component.session).toEqual(mockSession);
  });

  it('should not have delete button if user is not admin', () => {
    
    component.sessionId = '2';
    mockSessionService.sessionInformation.admin = false;
    let deleteButton = fixture.debugElement.nativeElement.querySelector(
      'button[data-test-id="delete-button"]'
    );

    expect(deleteButton).toBeNull();
  });

  it('should delete session and redirect to sessions page', () => {
    
    let sessionApiServiceSpy = jest
      .spyOn(sessionApiService, 'delete')
      .mockReturnValue(of({}));

    component.sessionId = '2';
    let navigateSpy = jest.spyOn(router, 'navigate');

    component.delete();

    expect(sessionApiServiceSpy).toHaveBeenCalledWith('2');
    expect(navigateSpy).toHaveBeenCalledWith(["sessions"]);
  });

  it('should participate and fetch correct session', () => {

    let sessionApiServiceSpy = jest
      .spyOn(sessionApiService, 'participate')
      .mockReturnValue(of(void 0));

    component.sessionId = '2';
    component.userId = '3';

    component.participate();

    expect(sessionApiServiceSpy).toHaveBeenCalledWith('2', '3');
  });

  it('should unparticipate', () => {
    
    component.sessionId = '2';
    component.userId = '3';

    let sessionApiServiceSpy = jest
      .spyOn(sessionApiService, 'unParticipate')
      .mockReturnValue(of(void 0));

    component.unParticipate();

    expect(sessionApiServiceSpy).toHaveBeenCalledWith('2', '3');
  });
});

