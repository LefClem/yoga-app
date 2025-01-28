describe('Logout spec', () => {
    it('should logout successfully', () => {
        cy.login("yoga@studio.com", "test!1234");

        cy.url().should('include', '/sessions');
        cy.get('[data-test-id="logout"]').click();
        cy.url().should('not.include', '/session');
        cy.url().should('include', '/')

    })
})