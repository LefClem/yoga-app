describe('Logout spec', () => {
    it('should logout successfully', () => {
        cy.interceptSessions();
        cy.loginWithAdminStatus("yoga@studio.com", "test!1234", true);

        cy.url().should('include', '/sessions');
        cy.get('[data-test-id="logout"]').click();
        cy.url().should('not.include', '/session');
        cy.url().should('include', '/')

    })
})