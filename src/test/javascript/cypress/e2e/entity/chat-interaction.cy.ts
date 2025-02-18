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
  const chatInteractionPageUrl = '/chat-interaction';
  const chatInteractionPageUrlPattern = new RegExp('/chat-interaction(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const chatInteractionSample = {
    customerMessage: 'extract worth reluctantly',
    chatbotResponse: 'lest',
    timestamp: '2025-02-12T02:18:13.226Z',
  };

  let chatInteraction;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/chat-interactions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/chat-interactions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/chat-interactions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (chatInteraction) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/chat-interactions/${chatInteraction.id}`,
      }).then(() => {
        chatInteraction = undefined;
      });
    }
  });

  it('ChatInteractions menu should load ChatInteractions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('chat-interaction');
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
        cy.url().should('match', new RegExp('/chat-interaction/new$'));
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
          url: '/api/chat-interactions',
          body: chatInteractionSample,
        }).then(({ body }) => {
          chatInteraction = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/chat-interactions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/chat-interactions?page=0&size=20>; rel="last",<http://localhost/api/chat-interactions?page=0&size=20>; rel="first"',
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
        cy.intercept('GET', '/api/chat-interactions/*').as('dialogDeleteRequest');
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
      cy.get(`[data-cy="customerMessage"]`).type('piglet like millet');
      cy.get(`[data-cy="customerMessage"]`).should('have.value', 'piglet like millet');

      cy.get(`[data-cy="chatbotResponse"]`).type('until');
      cy.get(`[data-cy="chatbotResponse"]`).should('have.value', 'until');

      cy.get(`[data-cy="timestamp"]`).type('2025-02-12T03:36');
      cy.get(`[data-cy="timestamp"]`).blur();
      cy.get(`[data-cy="timestamp"]`).should('have.value', '2025-02-12T03:36');

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
