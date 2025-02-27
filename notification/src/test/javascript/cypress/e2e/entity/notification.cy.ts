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

describe('Notification e2e test', () => {
  const notificationPageUrl = '/notification/notification';
  const notificationPageUrlPattern = new RegExp('/notification/notification(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const notificationSample = {
    entityId: 21765,
    target: 'THANKS',
    content: 'back geez until',
    status: 'SENT',
    timestamp: '2025-02-26T07:38:41.352Z',
  };

  let notification;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/notification/api/notifications+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/notification/api/notifications').as('postEntityRequest');
    cy.intercept('DELETE', '/services/notification/api/notifications/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (notification) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/notification/api/notifications/${notification.id}`,
      }).then(() => {
        notification = undefined;
      });
    }
  });

  it('Notifications menu should load Notifications page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('notification/notification');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Notification').should('exist');
    cy.url().should('match', notificationPageUrlPattern);
  });

  describe('Notification page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(notificationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Notification page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/notification/notification/new$'));
        cy.getEntityCreateUpdateHeading('Notification');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', notificationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/notification/api/notifications',
          body: notificationSample,
        }).then(({ body }) => {
          notification = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/notification/api/notifications+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/notification/api/notifications?page=0&size=20>; rel="last",<http://localhost/services/notification/api/notifications?page=0&size=20>; rel="first"',
              },
              body: [notification],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(notificationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Notification page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('notification');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', notificationPageUrlPattern);
      });

      it('edit button click should load edit Notification page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Notification');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', notificationPageUrlPattern);
      });

      it('edit button click should load edit Notification page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Notification');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', notificationPageUrlPattern);
      });

      it('last delete button click should delete instance of Notification', () => {
        cy.intercept('GET', '/services/notification/api/notifications/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('notification').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', notificationPageUrlPattern);

        notification = undefined;
      });
    });
  });

  describe('new Notification page', () => {
    beforeEach(() => {
      cy.visit(`${notificationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Notification');
    });

    it('should create an instance of Notification', () => {
      cy.get(`[data-cy="entityId"]`).type('9876');
      cy.get(`[data-cy="entityId"]`).should('have.value', '9876');

      cy.get(`[data-cy="entityName"]`).select('CUSTOMER');

      cy.get(`[data-cy="target"]`).select('NEW');

      cy.get(`[data-cy="content"]`).type('gallery');
      cy.get(`[data-cy="content"]`).should('have.value', 'gallery');

      cy.get(`[data-cy="status"]`).select('VIEWED');

      cy.get(`[data-cy="timestamp"]`).type('2025-02-26T17:27');
      cy.get(`[data-cy="timestamp"]`).blur();
      cy.get(`[data-cy="timestamp"]`).should('have.value', '2025-02-26T17:27');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        notification = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', notificationPageUrlPattern);
    });
  });
});
