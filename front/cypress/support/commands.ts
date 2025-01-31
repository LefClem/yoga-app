// ***********************************************
// This example namespace declaration will help
// with Intellisense and code completion in your
// IDE or Text Editor.
// ***********************************************
// declare namespace Cypress {
//   interface Chainable<Subject = any> {
//     customCommand(param: any): typeof customCommand;
//   }
// }
//
// function customCommand(param: any): void {
//   console.warn(param);
// }
//
// NOTE: You can use it like so:
// Cypress.Commands.add('customCommand', customCommand);
//
// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add("login", (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add("drag", { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add("dismiss", { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite("visit", (originalFn, url, options) => { ... })

declare namespace Cypress {
    interface Chainable {
        login(email: string, password: string): void;
    }
}

Cypress.Commands.add('loginWithAdminStatus', (email, password, admin) => {
    cy.intercept('POST', '/api/auth/login', (req) => {
        req.reply({
          statusCode: 200,
          body: {
            id: 1,
            username: 'userName',
            firstName: 'firstName',
            lastName: 'lastName',
            admin: admin
          },
        });
      }).as('loginRequest');

    cy.visit('/login');
    cy.get('input[formControlName=email]').type(email);
    cy.get('input[formControlName=password]').type(`${password}{enter}{enter}`);
});

Cypress.Commands.add('interceptSessions', () => {
    cy.intercept('GET', '/api/session', req => {
        req.reply({
            statusCode: 200,
            body: [{
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
            ]
        })
    }).as('sessionRequest');
})

Cypress.Commands.add('interceptSession', () => {
    cy.intercept('GET', '/api/session/1', req => {
        req.reply({
            statusCode: 200,
            body: {
                createdAt: "2025-01-03T17:13:30",
                date: "2025-02-27T00:00:00.000+00:00",
                description: "Session de découverte",
                id: 1,
                name: "Session Lunel",
                teacher_id: 1,
                updatedAt: "2025-01-17T09:28:27",
                users: []
            }
        })
    })
})

Cypress.Commands.add('interceptCreateSessions', () => {
    cy.intercept('GET', '/api/session', req => {
        req.reply({
            statusCode: 200,
            body: [{
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
            },
            {
                createdAt: "2025-02-22T17:13:30",
                date: "2025-02-22T00:00:00.000+00:00",
                description: "une super session !",
                id: 3,
                name: "Session de yoga sur Avignon",
                teacher_id: 1,
                updatedAt: "2025-02-22T17:13:30",
                users: []
            }
            ]
        })
    }).as('sessionRequest');
})

Cypress.Commands.add('interceptTeacher', () => {
    cy.intercept('GET', '/api/teacher', req => {
        req.reply({
            statusCode: 200,
            body: [
                {
                    id: 1,
                    lastName: "DELAHAYE",
                    firstName: "Margot",
                    createdAt: "2025-01-20T19:58:57",
                    updatedAt: "2025-01-20T19:58:57"
                },
                {
                    id: 2,
                    lastName: "THIERCELIN",
                    firstName: "Hélène",
                    createdAt: "2025-01-20T19:58:57",
                    updatedAt: "2025-01-20T19:58:57"
                }
            ]
        })
    }).as('teacherRequest');
})