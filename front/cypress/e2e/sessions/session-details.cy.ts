describe('Session detail spec', () => {
    beforeEach(() => {
        cy.intercept(
            {
                method: 'GET',
                url: '/api/session/1',
            },
            {
                id: 1,
                name: 'Super Cool Session',
                date: '2024-05-29T00:00:00.000+00:00',
                teacher_id: 1,
                description: 'It is a Super Cool Session',
                users: [1],
                createdAt: '2024-05-29T17:03:24',
                updatedAt: '2024-05-29T17:03:24',
            }
        ).as('session-detail');

        cy.intercept({
            method: 'GET',
            url: '/api/teacher/1'
        },
        {
            "id": 1,
            "lastName": "DELAHAYE",
            "firstName": "Margot",
            "createdAt": "2025-01-20T19:58:57",
            "updatedAt": "2025-01-20T19:58:57"
        }).as('teacher-detail');
        
        cy.interceptSessions();
    });

    it('should show session infos on click on detail button as admin', () => {
        cy.interceptIsAdmin(true);
        cy.login('yoga@studio.com', 'test!1234');

        cy.get('[data-test-id="detail-button"]').first().click();

        cy.url().should('include', '/sessions/detail/1');

        cy.get('.mat-card-title').contains('Super Cool Session');
        cy.get('.description').contains('It is a Super Cool Session');
        cy.get('[data-test-id="number-of-attendees"]').contains('1 attendees');
        cy.get('[data-test-id="delete-button"]').should('exist');
    });

    it('should delete session on click on delete button as admin', () => {
        cy.interceptIsAdmin(true);
        cy.login('yoga@studio.com', 'test!1234');

        cy.get('[data-test-id="detail-button"]').first().click();
        cy.url().should('include', '/sessions/detail/1');
        cy.get('[data-test-id="delete-button"]').click();

        cy.url().should('include', '/sessions');
    });

    it('should not have delete button on session detail as non admin', () => {
        cy.login('johndoe@mail.com', 'test!1234');

        cy.get('[data-test-id="detail-button"]').first().click();
        cy.url().should('include', '/sessions/detail/1');

        cy.get('[data-test-id="delete-button"]').should('not.exist');
    });

    it('should have participate button on session detail as non admin', () => {
        cy.login('johndoe@mail.com', 'test!1234');

        cy.get('[data-test-id="detail-button"]').first().click();
        cy.url().should('include', '/sessions/detail/1');

        cy.get('[data-test-id="participate-button"]').should('exist');
    });
});