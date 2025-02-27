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

describe('RAGContext e2e test', () => {
  const rAGContextPageUrl = '/agentai/rag-context';
  const rAGContextPageUrlPattern = new RegExp('/agentai/rag-context(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const rAGContextSample = { entityId: 18462, contextText: 'viability' };

  let rAGContext;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/agentai/api/rag-contexts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/agentai/api/rag-contexts').as('postEntityRequest');
    cy.intercept('DELETE', '/services/agentai/api/rag-contexts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (rAGContext) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/agentai/api/rag-contexts/${rAGContext.id}`,
      }).then(() => {
        rAGContext = undefined;
      });
    }
  });

  it('RAGContexts menu should load RAGContexts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('agentai/rag-context');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RAGContext').should('exist');
    cy.url().should('match', rAGContextPageUrlPattern);
  });

  describe('RAGContext page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(rAGContextPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RAGContext page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/agentai/rag-context/new$'));
        cy.getEntityCreateUpdateHeading('RAGContext');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rAGContextPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/agentai/api/rag-contexts',
          body: rAGContextSample,
        }).then(({ body }) => {
          rAGContext = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/agentai/api/rag-contexts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/agentai/api/rag-contexts?page=0&size=20>; rel="last",<http://localhost/services/agentai/api/rag-contexts?page=0&size=20>; rel="first"',
              },
              body: [rAGContext],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(rAGContextPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RAGContext page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('rAGContext');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rAGContextPageUrlPattern);
      });

      it('edit button click should load edit RAGContext page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RAGContext');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rAGContextPageUrlPattern);
      });

      it('edit button click should load edit RAGContext page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RAGContext');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rAGContextPageUrlPattern);
      });

      it('last delete button click should delete instance of RAGContext', () => {
        cy.intercept('GET', '/services/agentai/api/rag-contexts/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('rAGContext').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rAGContextPageUrlPattern);

        rAGContext = undefined;
      });
    });
  });

  describe('new RAGContext page', () => {
    beforeEach(() => {
      cy.visit(`${rAGContextPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RAGContext');
    });

    it('should create an instance of RAGContext', () => {
      cy.get(`[data-cy="entityId"]`).type('5955');
      cy.get(`[data-cy="entityId"]`).should('have.value', '5955');

      cy.get(`[data-cy="entityName"]`).select('PROPERTY');

      cy.get(`[data-cy="contextText"]`).type('wash hippodrome sleet');
      cy.get(`[data-cy="contextText"]`).should('have.value', 'wash hippodrome sleet');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        rAGContext = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', rAGContextPageUrlPattern);
    });
  });
});
