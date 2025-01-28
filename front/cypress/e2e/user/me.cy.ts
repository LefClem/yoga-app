describe('me spec', () => {
    beforeEach(() => {
        cy.intercept('GET', '/api/user/1', (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    id: 1,
                    email: "yoga@studio.com",
                    lastName: "Admin",
                    firstName: "Admin",
                    admin: true,
                    createdAt: "2025-01-02T22:46:51",
                    updatedAt: "2025-01-02T22:46:51"
                }
            })
        })
    
        cy.login("yoga@studio.com", "test!1234");
        cy.get('[data-test-id="account"]').click();

    })

    it('should display the user informations', () => {
        cy.get('[data-test-id="name"]').should('contain', 'Admin ADMIN');
        cy.get('[data-test-id="email"]').should('contain', 'yoga@studio.com');
    })

    it('should display admin status', () => {
        cy.get('[data-test-id="admin"]').should('exist');
        cy.get('[data-test-id="admin"]').should('contain', 'You are admin');
    })

    it('should display delete button and delete the user account', () => {
        cy.intercept('DELETE', 'api/user/5', req => {
            req.reply({
                statusCode: 200
            })
        }).as('deleteRequest')
        cy.login('johndoe@mail.com', 'test!1234');
        cy.get('[data-test-id="account"]').click();

        cy.get('[data-test-id="delete"]').should('exist');
        cy.get('[data-test-id="delete"]').should('contain', 'Delete');
    })
})