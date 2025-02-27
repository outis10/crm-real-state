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

describe('Sale e2e test', () => {
  const salePageUrl = '/crm/sale';
  const salePageUrlPattern = new RegExp('/crm/sale(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const saleSample = { propertyId: 8323, customerId: 15239, saleDate: '2025-02-26T14:46:13.734Z', totalAmount: 6991.5 };

  let sale;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/crm/api/sales+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/crm/api/sales').as('postEntityRequest');
    cy.intercept('DELETE', '/services/crm/api/sales/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (sale) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/crm/api/sales/${sale.id}`,
      }).then(() => {
        sale = undefined;
      });
    }
  });

  it('Sales menu should load Sales page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('crm/sale');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Sale').should('exist');
    cy.url().should('match', salePageUrlPattern);
  });

  describe('Sale page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(salePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Sale page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/crm/sale/new$'));
        cy.getEntityCreateUpdateHeading('Sale');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', salePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/crm/api/sales',
          body: saleSample,
        }).then(({ body }) => {
          sale = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/crm/api/sales+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/crm/api/sales?page=0&size=20>; rel="last",<http://localhost/services/crm/api/sales?page=0&size=20>; rel="first"',
              },
              body: [sale],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(salePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Sale page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('sale');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', salePageUrlPattern);
      });

      it('edit button click should load edit Sale page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Sale');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', salePageUrlPattern);
      });

      it('edit button click should load edit Sale page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Sale');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', salePageUrlPattern);
      });

      it('last delete button click should delete instance of Sale', () => {
        cy.intercept('GET', '/services/crm/api/sales/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('sale').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', salePageUrlPattern);

        sale = undefined;
      });
    });
  });

  describe('new Sale page', () => {
    beforeEach(() => {
      cy.visit(`${salePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Sale');
    });

    it('should create an instance of Sale', () => {
      cy.get(`[data-cy="propertyId"]`).type('20846');
      cy.get(`[data-cy="propertyId"]`).should('have.value', '20846');

      cy.get(`[data-cy="customerId"]`).type('11104');
      cy.get(`[data-cy="customerId"]`).should('have.value', '11104');

      cy.get(`[data-cy="oportunityId"]`).type('1280');
      cy.get(`[data-cy="oportunityId"]`).should('have.value', '1280');

      cy.get(`[data-cy="saleDate"]`).type('2025-02-26T01:40');
      cy.get(`[data-cy="saleDate"]`).blur();
      cy.get(`[data-cy="saleDate"]`).should('have.value', '2025-02-26T01:40');

      cy.get(`[data-cy="totalAmount"]`).type('27751.38');
      cy.get(`[data-cy="totalAmount"]`).should('have.value', '27751.38');

      cy.get(`[data-cy="status"]`).select('PENDING');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        sale = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', salePageUrlPattern);
    });
  });
});
