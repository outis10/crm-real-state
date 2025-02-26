import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('Charge e2e test', () => {
  const chargePageUrl = '/propertymanagement/charge';
  const chargePageUrlPattern = new RegExp('/propertymanagement/charge(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const chargeSample = { type: 'LEASE', amount: 25570.21, dueDate: '2025-02-25T21:24:02.819Z', status: 'PAID' };

  let charge;
  let rental;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/propertymanagement/api/rentals',
      body: {
        propertyId: 19237,
        customerId: 9084,
        oportunityId: 15918,
        startDate: '2025-02-25T14:02:52.671Z',
        endDate: '2025-02-25T19:34:23.585Z',
        monthlyRent: 30142.62,
        securityDeposit: 28331.92,
        contractStatus: 'ACTIVE',
      },
    }).then(({ body }) => {
      rental = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/propertymanagement/api/charges+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/propertymanagement/api/charges').as('postEntityRequest');
    cy.intercept('DELETE', '/services/propertymanagement/api/charges/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/services/propertymanagement/api/rentals', {
      statusCode: 200,
      body: [rental],
    });
  });

  afterEach(() => {
    if (charge) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/propertymanagement/api/charges/${charge.id}`,
      }).then(() => {
        charge = undefined;
      });
    }
  });

  afterEach(() => {
    if (rental) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/propertymanagement/api/rentals/${rental.id}`,
      }).then(() => {
        rental = undefined;
      });
    }
  });

  it('Charges menu should load Charges page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('propertymanagement/charge');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Charge').should('exist');
    cy.url().should('match', chargePageUrlPattern);
  });

  describe('Charge page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(chargePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Charge page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/propertymanagement/charge/new$'));
        cy.getEntityCreateUpdateHeading('Charge');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', chargePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/propertymanagement/api/charges',
          body: {
            ...chargeSample,
            rental,
          },
        }).then(({ body }) => {
          charge = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/propertymanagement/api/charges+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/propertymanagement/api/charges?page=0&size=20>; rel="last",<http://localhost/services/propertymanagement/api/charges?page=0&size=20>; rel="first"',
              },
              body: [charge],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(chargePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Charge page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('charge');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', chargePageUrlPattern);
      });

      it('edit button click should load edit Charge page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Charge');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', chargePageUrlPattern);
      });

      it('edit button click should load edit Charge page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Charge');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', chargePageUrlPattern);
      });

      it('last delete button click should delete instance of Charge', () => {
        cy.intercept('GET', '/services/propertymanagement/api/charges/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('charge').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', chargePageUrlPattern);

        charge = undefined;
      });
    });
  });

  describe('new Charge page', () => {
    beforeEach(() => {
      cy.visit(`${chargePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Charge');
    });

    it('should create an instance of Charge', () => {
      cy.get(`[data-cy="type"]`).select('OTHER');

      cy.get(`[data-cy="amount"]`).type('23926.18');
      cy.get(`[data-cy="amount"]`).should('have.value', '23926.18');

      cy.get(`[data-cy="dueDate"]`).type('2025-02-25T06:10');
      cy.get(`[data-cy="dueDate"]`).blur();
      cy.get(`[data-cy="dueDate"]`).should('have.value', '2025-02-25T06:10');

      cy.get(`[data-cy="status"]`).select('IN_REVIEW');

      cy.get(`[data-cy="rental"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        charge = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', chargePageUrlPattern);
    });
  });
});
