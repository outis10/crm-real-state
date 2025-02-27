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

describe('Quotation e2e test', () => {
  const quotationPageUrl = '/crm/quotation';
  const quotationPageUrlPattern = new RegExp('/crm/quotation(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const quotationSample = { finalPrice: 1410.52, validityDate: '2025-02-26T14:30:36.290Z' };

  let quotation;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/crm/api/quotations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/crm/api/quotations').as('postEntityRequest');
    cy.intercept('DELETE', '/services/crm/api/quotations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (quotation) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/crm/api/quotations/${quotation.id}`,
      }).then(() => {
        quotation = undefined;
      });
    }
  });

  it('Quotations menu should load Quotations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('crm/quotation');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Quotation').should('exist');
    cy.url().should('match', quotationPageUrlPattern);
  });

  describe('Quotation page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(quotationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Quotation page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/crm/quotation/new$'));
        cy.getEntityCreateUpdateHeading('Quotation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', quotationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/crm/api/quotations',
          body: quotationSample,
        }).then(({ body }) => {
          quotation = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/crm/api/quotations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/crm/api/quotations?page=0&size=20>; rel="last",<http://localhost/services/crm/api/quotations?page=0&size=20>; rel="first"',
              },
              body: [quotation],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(quotationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Quotation page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('quotation');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', quotationPageUrlPattern);
      });

      it('edit button click should load edit Quotation page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Quotation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', quotationPageUrlPattern);
      });

      it('edit button click should load edit Quotation page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Quotation');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', quotationPageUrlPattern);
      });

      it('last delete button click should delete instance of Quotation', () => {
        cy.intercept('GET', '/services/crm/api/quotations/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('quotation').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', quotationPageUrlPattern);

        quotation = undefined;
      });
    });
  });

  describe('new Quotation page', () => {
    beforeEach(() => {
      cy.visit(`${quotationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Quotation');
    });

    it('should create an instance of Quotation', () => {
      cy.get(`[data-cy="finalPrice"]`).type('10633.6');
      cy.get(`[data-cy="finalPrice"]`).should('have.value', '10633.6');

      cy.get(`[data-cy="validityDate"]`).type('2025-02-26T03:16');
      cy.get(`[data-cy="validityDate"]`).blur();
      cy.get(`[data-cy="validityDate"]`).should('have.value', '2025-02-26T03:16');

      cy.get(`[data-cy="comments"]`).type('despite wildly');
      cy.get(`[data-cy="comments"]`).should('have.value', 'despite wildly');

      cy.get(`[data-cy="createdBy"]`).type('5692');
      cy.get(`[data-cy="createdBy"]`).should('have.value', '5692');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        quotation = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', quotationPageUrlPattern);
    });
  });
});
