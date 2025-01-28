import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SessionService } from '../../services/session.service';

import { MeComponent } from './me.component';
import { By } from '@angular/platform-browser';
import { UserService } from '../../services/user.service';
import { of } from 'rxjs';
import { User } from '../../interfaces/user.interface';
import { Router } from '@angular/router';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let userService: UserService;
  let sessionService: SessionService;
  let router: Router;

  const mockUser: User = {
    id: 1,
    email: 'John@doe.com',
    lastName: 'Doe',
    firstName: 'John',
    admin: false,
    password: 'motdepasse',
    createdAt: new Date(),
  };

  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
    logOut(): void { },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        HttpClientModule,
        MatSnackBarModule,
        MatCardModule,
        MatIconModule,
        NoopAnimationsModule
      ],
      providers: [{ provide: SessionService, useValue: mockSessionService }],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    userService = TestBed.inject(UserService);
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch user on init', () => {
    let userServiceSpy = jest
      .spyOn(userService, 'getById')
      .mockReturnValue(of(mockUser));

    component.ngOnInit();

    expect(component.user?.firstName).toBe('John');
    expect(userServiceSpy).toHaveBeenCalledWith('1');
  });

  it('should navigate back on click on arrow', () => {
    const spy = jest.spyOn(component, 'back');
    const button = fixture.debugElement.query(
      By.css('button[mat-icon-button]')
    );

    button.nativeElement.click();
    expect(spy).toHaveBeenCalled();
  });

  it('should delete user', fakeAsync(() => {
    let userServiceSpy = jest
      .spyOn(userService, 'delete')
      .mockReturnValue(of(void 0));

    let sessionServiceSpy = jest.spyOn(mockSessionService, 'logOut');
    let routerSpy = jest.spyOn(router, 'navigate');

    component.delete();
    tick(3000);

    expect(sessionServiceSpy).toHaveBeenCalled();
    expect(routerSpy).toHaveBeenCalledWith(['/']);
    expect(userServiceSpy).toHaveBeenCalled();
  }));
});