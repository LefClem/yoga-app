describe('Register spec', () => {
    it('Register successfull', () => {
        cy.visit('/register')

        cy.intercept('POST', '/api/auth/register', (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    message: "User registered successfully!"
                }
            })
        }).as('registerRequest');

        cy.get('input[formControlName=firstName]').type('John')
        cy.get('input[formControlName=lastName]').type('Doe')
        cy.get('input[formControlName=email]').type('test@mail.com')
        cy.get('input[formControlName=password]').type(`${"test1234"}{enter}{enter}`)

        cy.wait('@registerRequest').then(interception => {
            expect(interception.response.statusCode).to.eq(200);
            expect(interception.response.body).to.deep.equal({
                message: "User registered successfully!"
            })
        })

        cy.url().should('include', '/login')
    })

    it('Register fail', () => {
        cy.visit('/register');

        cy.intercept('POST', '/api/auth/register', req => {
            req.reply({
                statusCode: 400
            })
        }).as('registerRequest');

        cy.get('input[formControlName=email]').click();
        cy.get('.register').click();

        cy.get('input[formControlName=email]')
        .should('have.class', 'ng-invalid')
        .and('have.css', 'caret-color', 'rgb(244, 67, 54)');

        cy.get('input[formControlName=firstName]').type('John')
        cy.get('input[formControlName=lastName]').type('Doe')
        cy.get('input[formControlName=email]').type('test@mail.com')
        cy.get('input[formControlName=password]').type(`${"test"}{enter}{enter}`)

        cy.get('.error').should('exist').and('have.text', 'An error occurred');

        cy.wait('@registerRequest').then((interception) => {
            expect(interception.response.statusCode).to.eq(400);

        })
    })
})