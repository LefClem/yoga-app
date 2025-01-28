describe('Session creation specs', () => {
    it('Should display the create button and the creation form for admin', () => {
        cy.login('yoga@studio.com', 'test!1234');
        cy.get('[data-test-id="create-button"]').should('exist');
        cy.get('[data-test-id="create-button"]').click();

        cy.url().should('include', '/sessions/create');
        cy.get('[data-test-id="form-name"]').should('exist');
        cy.get('[data-test-id="form-date"]').should('exist');
        cy.get('[data-test-id="form-teacher"]').should('exist');
        cy.get('[data-test-id="form-description"]').should('exist');
    })

    it('should create a session', () => {
        cy.interceptCreateSessions();
        cy.intercept('POST', '/api/session', (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    createdAt: "2025-02-22T17:13:30",
                    date: "2025-02-22T00:00:00.000+00:00",
                    description: "une super session !",
                    id: 3,
                    name: "Session de yoga",
                    teacher_id: 1,
                    updatedAt: "2025-02-22T17:13:30",
                    users: []
                }
            })
        }).as('createSession');

        cy.login('yoga@studio.com', 'test!1234');
        cy.get('[data-test-id="create-button"]').should('exist');
        cy.get('[data-test-id="create-button"]').click();

        cy.get('[data-test-id="form-name"]').type('Session de yoga sur Avignon');
        cy.get('[data-test-id="form-date"]').type('2025-02-22');
        cy.get('[data-test-id="form-teacher"]')
            .click()
            .get('mat-option')
            .contains('DELAHAYE')
            .click();

        cy.get('[data-test-id="form-description"]').type('une super session !');

        cy.get('[data-test-id="save-button"]').click();
        cy.wait('@createSession').then(interception => {
            expect(interception.response.statusCode).to.eq(200);
            expect(interception.response.body).to.deep.equal({
                createdAt: "2025-02-22T17:13:30",
                date: "2025-02-22T00:00:00.000+00:00",
                description: "une super session !",
                id: 3,
                name: "Session de yoga",
                teacher_id: 1,
                updatedAt: "2025-02-22T17:13:30",
                users: []
            })
        })
        cy.url().should('include', '/sessions')
    })

    it('should not display create button if logged user is not admin', () => {
        cy.interceptSessions();
        cy.login('johndoe@mail.com', 'test!1234');

        cy.get('[data-test-id="create-button"]').should('not.exist');
    })
})