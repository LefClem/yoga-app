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
    
        cy.loginWithAdminStatus("yoga@studio.com", "test!1234", true);
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

    it('should display delete button', () => {
        cy.intercept('GET', '/api/user/1', (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    id: 1,
                    email: "yoga@studio.com",
                    lastName: "Admin",
                    firstName: "Admin",
                    admin: false,
                    createdAt: "2025-01-02T22:46:51",
                    updatedAt: "2025-01-02T22:46:51"
                }
            })
        })

        cy.loginWithAdminStatus('johndoe@mail.com', 'test!1234', false);
        cy.get('[data-test-id="account"]').click();

        cy.get('[data-test-id="delete"]').should('exist');
        cy.get('[data-test-id="delete"]').should('contain', 'Delete');
    })

    it('should delete the account', () => {
        cy.intercept('DELETE', 'api/user/1', req => {
            req.reply({
                statusCode: 200
            })
        }).as('deleteRequest')
        cy.intercept('GET', '/api/user/1', (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    id: 1,
                    email: "yoga@studio.com",
                    lastName: "Admin",
                    firstName: "Admin",
                    admin: false,
                    createdAt: "2025-01-02T22:46:51",
                    updatedAt: "2025-01-02T22:46:51"
                }
            })
        })

        cy.loginWithAdminStatus('johndoe@mail.com', 'test!1234', false);
        cy.get('[data-test-id="account"]').click();
        cy.get('[data-test-id="delete"]').click();

        cy.wait('@deleteRequest').then(interception => {
            expect(interception.response.statusCode).to.eq(200);
        })

        cy.url().should('include', '/')
    })

    it('should have a back to session page button', () => {
        cy.get('[data-test-id="back-button"]').should('exist');
        cy.get('[data-test-id="back-button"]').click();

        cy.url().should('include', '/sessions');
    })
})