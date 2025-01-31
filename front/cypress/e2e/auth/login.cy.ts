describe('Login spec', () => {
  it('Login successfull', () => {
    cy.intercept('POST', '/api/auth/login', (req) => {
      req.reply({
        statusCode: 200,
        body: {
          id: 1,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: true
        },
      });
    }).as('loginRequest');
    
    cy.visit('/login')

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.wait('@loginRequest').then((intercerption) => {
      expect(intercerption).to.not.be.null;

      expect(intercerption.response.statusCode).to.eq(200);
      expect(intercerption.response.body).to.deep.equal({
          id: 1,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: true
      });
    })

    cy.url().should('include', '/sessions')
  })

  it('should return an error if the password is incorrect', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', (req) => {
      req.reply({
        statusCode: 401
      });
    }).as('loginRequest');

    cy.get('input[formControlName=email]')
    .type(`${"yoga"}{enter}`)
    .should('have.class', 'ng-invalid')
    .and('have.css', 'caret-color', 'rgb(244, 67, 54)');

    cy.get('input[formControlName=email]').type('test@mail.com');

    cy.get('input[formControlName=password]').type(`${"test!"}{enter}{enter}`);
    cy.get('.error').should('exist').and('have.text', 'An error occurred');

    cy.wait('@loginRequest').then(interception => {
      expect(interception.response.statusCode).to.eq(401);
    })

    cy.url().should('include', '/login')
  })
});