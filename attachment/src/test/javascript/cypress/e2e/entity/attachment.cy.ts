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

describe('Attachment e2e test', () => {
  const attachmentPageUrl = '/attachment/attachment';
  const attachmentPageUrlPattern = new RegExp('/attachment/attachment(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const attachmentSample = {
    file: 'Li4vZmFrZS1kYXRhL2Jsb2IvaGlwc3Rlci5wbmc=',
    fileContentType: 'unknown',
    entityId: 16423,
    createdBy: 25455,
  };

  let attachment;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/attachment/api/attachments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/attachment/api/attachments').as('postEntityRequest');
    cy.intercept('DELETE', '/services/attachment/api/attachments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (attachment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/attachment/api/attachments/${attachment.id}`,
      }).then(() => {
        attachment = undefined;
      });
    }
  });

  it('Attachments menu should load Attachments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('attachment/attachment');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Attachment').should('exist');
    cy.url().should('match', attachmentPageUrlPattern);
  });

  describe('Attachment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(attachmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Attachment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/attachment/attachment/new$'));
        cy.getEntityCreateUpdateHeading('Attachment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', attachmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/attachment/api/attachments',
          body: attachmentSample,
        }).then(({ body }) => {
          attachment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/attachment/api/attachments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/attachment/api/attachments?page=0&size=20>; rel="last",<http://localhost/services/attachment/api/attachments?page=0&size=20>; rel="first"',
              },
              body: [attachment],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(attachmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Attachment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('attachment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', attachmentPageUrlPattern);
      });

      it('edit button click should load edit Attachment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Attachment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', attachmentPageUrlPattern);
      });

      it('edit button click should load edit Attachment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Attachment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', attachmentPageUrlPattern);
      });

      it('last delete button click should delete instance of Attachment', () => {
        cy.intercept('GET', '/services/attachment/api/attachments/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('attachment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', attachmentPageUrlPattern);

        attachment = undefined;
      });
    });
  });

  describe('new Attachment page', () => {
    beforeEach(() => {
      cy.visit(`${attachmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Attachment');
    });

    it('should create an instance of Attachment', () => {
      cy.setFieldImageAsBytesOfEntity('file', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="entityId"]`).type('15330');
      cy.get(`[data-cy="entityId"]`).should('have.value', '15330');

      cy.get(`[data-cy="entityName"]`).select('RENTAL_CONTRACT');

      cy.get(`[data-cy="createdBy"]`).type('18788');
      cy.get(`[data-cy="createdBy"]`).should('have.value', '18788');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        attachment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', attachmentPageUrlPattern);
    });
  });
});
