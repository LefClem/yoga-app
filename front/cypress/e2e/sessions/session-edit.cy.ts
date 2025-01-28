describe('Session edit specs', () => {
    it('should display edit button and edit form when admin', () => {
        cy.interceptSessions();
        cy.interceptSession();
        cy.login('yoga@studio.com', 'test!1234');

        cy.get('[data-test-id="edit-button"]').should('exist');
        cy.get('[data-test-id="edit-button"]').first().click();

        cy.url().should('include', '/sessions/update/1');
        cy.get('[formControlName="name"]').should('have.value', 'Session Lunel');
        cy.get('[formControlName="date"]').should('have.value', '2025-02-27');
        cy.get('[formControlName="description"]').should('have.value', 'Session de découverte');
    })
})