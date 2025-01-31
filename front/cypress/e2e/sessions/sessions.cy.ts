describe('Sessions spec', () => {
    it('Should display the list of session', () => {
        cy.interceptSessions();
        cy.loginWithAdminStatus("yoga@studio.com", "test!1234", true)

        cy.url().should('include', '/sessions')

        cy.wait('@sessionRequest').then(async (interception) => {
            expect(await interception.response.statusCode).to.eq(200);
            expect(await interception.response.body).to.deep.equal(
                [{
                    createdAt: "2025-01-03T17:13:30",
                    date: "2025-02-27T00:00:00.000+00:00",
                    description: "Session de découverte",
                    id: 1,
                    name: "Session Lunel",
                    teacher_id: 2,
                    updatedAt: "2025-01-17T09:28:27",
                    users: []
                },
                {
                    createdAt: "2025-01-03T17:15:12",
                    date: "2025-03-06T00:00:00.000+00:00",
                    description: "Venez si vous avez déjà participé à une session",
                    id: 2,
                    name: "Session confirmée Montpellier",
                    teacher_id: 1,
                    updatedAt: "2025-01-17T09:28:21",
                    users: []
                }
                ])
        })
    })

    it('should display create, edit and detail button when admin', () => {
        cy.interceptSessions();

        cy.loginWithAdminStatus("yoga@studio.com", "test!1234", true)

        cy.get('[data-test-id="create-button"]').should('exist');
        cy.get('[data-test-id="edit-button"]').should('exist');
        cy.get('[data-test-id="detail-button"]').should('exist');
    })

    it('should not display create, edit and detail button when not admin', () => {
        cy.interceptSessions();
        cy.loginWithAdminStatus("yoga@studio.com", "test!1234", false)

        cy.get('[data-test-id="create-button"]').should('not.exist');
        cy.get('[data-test-id="edit-button"]').should('not.exist');
        cy.get('[data-test-id="detail-button"]').should('exist');
    })

})