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

describe('ChatInteraction e2e test', () => {
  const chatInteractionPageUrl = '/agentai/chat-interaction';
  const chatInteractionPageUrlPattern = new RegExp('/agentai/chat-interaction(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const chatInteractionSample = {
    entityId: 4096,
    customerQuestion: 'decision mean at',
    chatbotAnswer: 'from whitewash ugh',
    timestamp: '2025-02-26T02:41:28.791Z',
  };

  let chatInteraction;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/agentai/api/chat-interactions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/agentai/api/chat-interactions').as('postEntityRequest');
    cy.intercept('DELETE', '/services/agentai/api/chat-interactions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (chatInteraction) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/agentai/api/chat-interactions/${chatInteraction.id}`,
      }).then(() => {
        chatInteraction = undefined;
      });
    }
  });

  it('ChatInteractions menu should load ChatInteractions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('agentai/chat-interaction');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ChatInteraction').should('exist');
    cy.url().should('match', chatInteractionPageUrlPattern);
  });

  describe('ChatInteraction page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(chatInteractionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ChatInteraction page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/agentai/chat-interaction/new$'));
        cy.getEntityCreateUpdateHeading('ChatInteraction');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', chatInteractionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/agentai/api/chat-interactions',
          body: chatInteractionSample,
        }).then(({ body }) => {
          chatInteraction = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/agentai/api/chat-interactions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/agentai/api/chat-interactions?page=0&size=20>; rel="last",<http://localhost/services/agentai/api/chat-interactions?page=0&size=20>; rel="first"',
              },
              body: [chatInteraction],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(chatInteractionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ChatInteraction page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('chatInteraction');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', chatInteractionPageUrlPattern);
      });

      it('edit button click should load edit ChatInteraction page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ChatInteraction');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', chatInteractionPageUrlPattern);
      });

      it('edit button click should load edit ChatInteraction page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ChatInteraction');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', chatInteractionPageUrlPattern);
      });

      it('last delete button click should delete instance of ChatInteraction', () => {
        cy.intercept('GET', '/services/agentai/api/chat-interactions/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('chatInteraction').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', chatInteractionPageUrlPattern);

        chatInteraction = undefined;
      });
    });
  });

  describe('new ChatInteraction page', () => {
    beforeEach(() => {
      cy.visit(`${chatInteractionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ChatInteraction');
    });

    it('should create an instance of ChatInteraction', () => {
      cy.get(`[data-cy="entityId"]`).type('18391');
      cy.get(`[data-cy="entityId"]`).should('have.value', '18391');

      cy.get(`[data-cy="entityName"]`).select('PROPERTY');

      cy.get(`[data-cy="customerQuestion"]`).type('duh');
      cy.get(`[data-cy="customerQuestion"]`).should('have.value', 'duh');

      cy.get(`[data-cy="chatbotAnswer"]`).type('ah proud');
      cy.get(`[data-cy="chatbotAnswer"]`).should('have.value', 'ah proud');

      cy.get(`[data-cy="timestamp"]`).type('2025-02-26T07:55');
      cy.get(`[data-cy="timestamp"]`).blur();
      cy.get(`[data-cy="timestamp"]`).should('have.value', '2025-02-26T07:55');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        chatInteraction = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', chatInteractionPageUrlPattern);
    });
  });
});
