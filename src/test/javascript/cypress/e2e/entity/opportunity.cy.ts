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

describe('Opportunity e2e test', () => {
  const opportunityPageUrl = '/opportunity';
  const opportunityPageUrlPattern = new RegExp('/opportunity(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const opportunitySample = {
    name: 'litter',
    amount: 13547.64,
    probability: 83,
    expectedCloseDate: '2025-02-12',
    stage: 'CLOSED_LOST',
    createdAt: '2025-02-12T13:04:26.444Z',
  };

  let opportunity;
  let customer;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/customers',
      body: {
        firstName: 'Jesús',
        middleName: 'thankfully',
        lastName: 'Garza Moya',
        email: '.qcI2@*^t$.%nG',
        phone: '951839137042',
        address: 'transparency',
        city: 'Vigo',
        state: 'sadly',
        postalCode: '88689',
        country: 'Turquia',
        socialMediaProfiles: 'decryption unrealistic',
        notes: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        preferences: 'sermon',
        budget: 16943.67,
        rentalBudget: 23968.92,
        interactionHistory: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
      },
    }).then(({ body }) => {
      customer = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/opportunities+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/opportunities').as('postEntityRequest');
    cy.intercept('DELETE', '/api/opportunities/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/customers', {
      statusCode: 200,
      body: [customer],
    });

    cy.intercept('GET', '/api/properties', {
      statusCode: 200,
      body: [],
    });
  });

  afterEach(() => {
    if (opportunity) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/opportunities/${opportunity.id}`,
      }).then(() => {
        opportunity = undefined;
      });
    }
  });

  afterEach(() => {
    if (customer) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/customers/${customer.id}`,
      }).then(() => {
        customer = undefined;
      });
    }
  });

  it('Opportunities menu should load Opportunities page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('opportunity');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Opportunity').should('exist');
    cy.url().should('match', opportunityPageUrlPattern);
  });

  describe('Opportunity page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(opportunityPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Opportunity page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/opportunity/new$'));
        cy.getEntityCreateUpdateHeading('Opportunity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', opportunityPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/opportunities',
          body: {
            ...opportunitySample,
            customer,
          },
        }).then(({ body }) => {
          opportunity = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/opportunities+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/opportunities?page=0&size=20>; rel="last",<http://localhost/api/opportunities?page=0&size=20>; rel="first"',
              },
              body: [opportunity],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(opportunityPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Opportunity page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('opportunity');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', opportunityPageUrlPattern);
      });

      it('edit button click should load edit Opportunity page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Opportunity');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', opportunityPageUrlPattern);
      });

      it('edit button click should load edit Opportunity page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Opportunity');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', opportunityPageUrlPattern);
      });

      it('last delete button click should delete instance of Opportunity', () => {
        cy.intercept('GET', '/api/opportunities/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('opportunity').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', opportunityPageUrlPattern);

        opportunity = undefined;
      });
    });
  });

  describe('new Opportunity page', () => {
    beforeEach(() => {
      cy.visit(`${opportunityPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Opportunity');
    });

    it('should create an instance of Opportunity', () => {
      cy.get(`[data-cy="name"]`).type('since fraternise');
      cy.get(`[data-cy="name"]`).should('have.value', 'since fraternise');

      cy.get(`[data-cy="budget"]`).type('23969.12');
      cy.get(`[data-cy="budget"]`).should('have.value', '23969.12');

      cy.get(`[data-cy="amount"]`).type('25103.08');
      cy.get(`[data-cy="amount"]`).should('have.value', '25103.08');

      cy.get(`[data-cy="probability"]`).type('70');
      cy.get(`[data-cy="probability"]`).should('have.value', '70');

      cy.get(`[data-cy="expectedCloseDate"]`).type('2025-02-12');
      cy.get(`[data-cy="expectedCloseDate"]`).blur();
      cy.get(`[data-cy="expectedCloseDate"]`).should('have.value', '2025-02-12');

      cy.get(`[data-cy="stage"]`).select('CLOSED_WON');

      cy.get(`[data-cy="description"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="description"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="createdAt"]`).type('2025-02-12T09:42');
      cy.get(`[data-cy="createdAt"]`).blur();
      cy.get(`[data-cy="createdAt"]`).should('have.value', '2025-02-12T09:42');

      cy.get(`[data-cy="modifiedAt"]`).type('2025-02-12T23:43');
      cy.get(`[data-cy="modifiedAt"]`).blur();
      cy.get(`[data-cy="modifiedAt"]`).should('have.value', '2025-02-12T23:43');

      cy.get(`[data-cy="closedAt"]`).type('2025-02-12T16:53');
      cy.get(`[data-cy="closedAt"]`).blur();
      cy.get(`[data-cy="closedAt"]`).should('have.value', '2025-02-12T16:53');

      cy.get(`[data-cy="customer"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        opportunity = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', opportunityPageUrlPattern);
    });
  });
});
