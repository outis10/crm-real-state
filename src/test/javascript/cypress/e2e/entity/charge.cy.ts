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
  const chargePageUrl = '/charge';
  const chargePageUrlPattern = new RegExp('/charge(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const chargeSample = { type: 'INSTALLMENT_PAYMENT', amount: 992.27, dueDate: '2025-02-12T07:31:35.994Z', status: 'PENDING' };

  let charge;
  let rentalContract;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/rental-contracts',
      body: {
        startDate: '2025-02-12T16:34:45.066Z',
        endDate: '2025-02-12T02:05:01.327Z',
        monthlyRent: 21121.02,
        securityDeposit: 25309.05,
        status: 'FINISHED',
      },
    }).then(({ body }) => {
      rentalContract = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/charges+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/charges').as('postEntityRequest');
    cy.intercept('DELETE', '/api/charges/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/customers', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/rental-contracts', {
      statusCode: 200,
      body: [rentalContract],
    });
  });

  afterEach(() => {
    if (charge) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/charges/${charge.id}`,
      }).then(() => {
        charge = undefined;
      });
    }
  });

  afterEach(() => {
    if (rentalContract) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/rental-contracts/${rentalContract.id}`,
      }).then(() => {
        rentalContract = undefined;
      });
    }
  });

  it('Charges menu should load Charges page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('charge');
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
        cy.url().should('match', new RegExp('/charge/new$'));
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
          url: '/api/charges',
          body: {
            ...chargeSample,
            rentalContract,
          },
        }).then(({ body }) => {
          charge = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/charges+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/charges?page=0&size=20>; rel="last",<http://localhost/api/charges?page=0&size=20>; rel="first"',
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
        cy.intercept('GET', '/api/charges/*').as('dialogDeleteRequest');
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

      cy.get(`[data-cy="amount"]`).type('12982.8');
      cy.get(`[data-cy="amount"]`).should('have.value', '12982.8');

      cy.get(`[data-cy="dueDate"]`).type('2025-02-12T21:23');
      cy.get(`[data-cy="dueDate"]`).blur();
      cy.get(`[data-cy="dueDate"]`).should('have.value', '2025-02-12T21:23');

      cy.get(`[data-cy="status"]`).select('PENDING');

      cy.get(`[data-cy="rentalContract"]`).select(1);

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
