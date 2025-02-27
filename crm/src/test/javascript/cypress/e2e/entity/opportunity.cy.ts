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
  const opportunityPageUrl = '/crm/opportunity';
  const opportunityPageUrlPattern = new RegExp('/crm/opportunity(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const opportunitySample = {
    name: 'informal parallel engage',
    amount: 9297.14,
    probability: 34,
    expectedCloseDate: '2025-02-26',
    stage: 'CLOSED_WON',
    createdAt: '2025-02-26T11:17:55.097Z',
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
      url: '/services/crm/api/customers',
      body: {
        firstName: 'Rosa',
        middleName: 'blissfully finally',
        lastName: 'Guardado Aguilar',
        email: 'y]<!R/@.,ex)3.]pb(t8',
        phone: '+758000',
        address: 'extremely',
        city: 'Cerdañola del Vallés',
        state: 'design',
        postalCode: '87640',
        country: 'Seychelles',
        socialMediaProfiles: 'for',
        notes: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        preferences: 'gosh where alongside',
        budget: 27101.59,
        rentalBudget: 12097.18,
        interactionHistory: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci50eHQ=',
        createdBy: 791,
      },
    }).then(({ body }) => {
      customer = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/crm/api/opportunities+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/crm/api/opportunities').as('postEntityRequest');
    cy.intercept('DELETE', '/services/crm/api/opportunities/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/services/crm/api/customers', {
      statusCode: 200,
      body: [customer],
    });

    cy.intercept('GET', '/services/crm/api/rentals', {
      statusCode: 200,
      body: [],
    });
  });

  afterEach(() => {
    if (opportunity) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/crm/api/opportunities/${opportunity.id}`,
      }).then(() => {
        opportunity = undefined;
      });
    }
  });

  afterEach(() => {
    if (customer) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/crm/api/customers/${customer.id}`,
      }).then(() => {
        customer = undefined;
      });
    }
  });

  it('Opportunities menu should load Opportunities page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('crm/opportunity');
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
        cy.url().should('match', new RegExp('/crm/opportunity/new$'));
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
          url: '/services/crm/api/opportunities',
          body: {
            ...opportunitySample,
            customer,
          },
        }).then(({ body }) => {
          opportunity = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/crm/api/opportunities+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/crm/api/opportunities?page=0&size=20>; rel="last",<http://localhost/services/crm/api/opportunities?page=0&size=20>; rel="first"',
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
        cy.intercept('GET', '/services/crm/api/opportunities/*').as('dialogDeleteRequest');
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
      cy.get(`[data-cy="name"]`).type('valentine');
      cy.get(`[data-cy="name"]`).should('have.value', 'valentine');

      cy.get(`[data-cy="budget"]`).type('6879.72');
      cy.get(`[data-cy="budget"]`).should('have.value', '6879.72');

      cy.get(`[data-cy="amount"]`).type('2039.44');
      cy.get(`[data-cy="amount"]`).should('have.value', '2039.44');

      cy.get(`[data-cy="probability"]`).type('97');
      cy.get(`[data-cy="probability"]`).should('have.value', '97');

      cy.get(`[data-cy="expectedCloseDate"]`).type('2025-02-26');
      cy.get(`[data-cy="expectedCloseDate"]`).blur();
      cy.get(`[data-cy="expectedCloseDate"]`).should('have.value', '2025-02-26');

      cy.get(`[data-cy="stage"]`).select('CLOSED_WON');

      cy.get(`[data-cy="description"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="description"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="createdAt"]`).type('2025-02-26T13:03');
      cy.get(`[data-cy="createdAt"]`).blur();
      cy.get(`[data-cy="createdAt"]`).should('have.value', '2025-02-26T13:03');

      cy.get(`[data-cy="modifiedAt"]`).type('2025-02-25T23:55');
      cy.get(`[data-cy="modifiedAt"]`).blur();
      cy.get(`[data-cy="modifiedAt"]`).should('have.value', '2025-02-25T23:55');

      cy.get(`[data-cy="closedAt"]`).type('2025-02-26T02:12');
      cy.get(`[data-cy="closedAt"]`).blur();
      cy.get(`[data-cy="closedAt"]`).should('have.value', '2025-02-26T02:12');

      cy.get(`[data-cy="createdBy"]`).type('17960');
      cy.get(`[data-cy="createdBy"]`).should('have.value', '17960');

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
