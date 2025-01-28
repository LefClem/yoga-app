import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { ListComponent } from './list.component';
import { By } from '@angular/platform-browser';
import { SessionApiService } from '../../services/session-api.service';
import { Session } from '../../interfaces/session.interface';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';

describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;

  const mockSessionService = {
    sessionInformation: {
      admin: true
    }
  }

  const mockSessionApiService = {
    all: jest.fn(),
  };

  const mockSessions: Session[] = [
    { id: 1, name: 'Session 1', description: 'Description 1', date: new Date('2025-01-01'), teacher_id: 1, users: [1, 3], createdAt: new Date(), updatedAt: new Date() },
    { id: 2, name: 'Session 2', description: 'Description 2', date: new Date('2025-01-02'), teacher_id: 1, users: [1], createdAt: new Date(), updatedAt: new Date() },
    { id: 3, name: 'Session 3', description: 'Description 3', date: new Date('2025-01-03'), teacher_id: 1, users: [1], createdAt: new Date(), updatedAt: new Date() },
  ];

  beforeEach(async () => {
    mockSessionApiService.all.mockReturnValue(of(mockSessions));

    await TestBed.configureTestingModule({
      declarations: [ListComponent],
      imports: [HttpClientModule, MatCardModule, MatIconModule, RouterTestingModule],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService },
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display a list of sessions', () => {
    fixture.detectChanges();

    const sessionItems = fixture.debugElement.queryAll(By.css('.item'));
    expect(sessionItems.length).toBe(mockSessions.length);
    sessionItems.forEach((item, index) => {
      expect(item.nativeElement.textContent).toContain(mockSessions[index].name);
    });
  })
  it('should display the create and edit button if the user logged has admin status', () => {
    mockSessionService.sessionInformation.admin = true;
    fixture.detectChanges();

    // Vérifier si le bouton "Create" est présent
    const createButton = fixture.debugElement.query(By.css('[data-test-id="create-button"]'));
    expect(createButton).toBeTruthy();
    // Vérifier si le bouton "Edit" est présent
    const editButton = fixture.debugElement.query(By.css('[data-test-id="edit-button"]'));
    expect(editButton).toBeTruthy();

  })
});
