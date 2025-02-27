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

describe('Contact e2e test', () => {
  const contactPageUrl = '/crm/contact';
  const contactPageUrlPattern = new RegExp('/crm/contact(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const contactSample = { firstName: 'Sergi', email: '/#_O*@3Wh~.ihV', createdBy: 855 };

  let contact;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/crm/api/contacts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/crm/api/contacts').as('postEntityRequest');
    cy.intercept('DELETE', '/services/crm/api/contacts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (contact) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/crm/api/contacts/${contact.id}`,
      }).then(() => {
        contact = undefined;
      });
    }
  });

  it('Contacts menu should load Contacts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('crm/contact');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Contact').should('exist');
    cy.url().should('match', contactPageUrlPattern);
  });

  describe('Contact page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(contactPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Contact page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/crm/contact/new$'));
        cy.getEntityCreateUpdateHeading('Contact');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contactPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/crm/api/contacts',
          body: contactSample,
        }).then(({ body }) => {
          contact = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/crm/api/contacts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/crm/api/contacts?page=0&size=20>; rel="last",<http://localhost/services/crm/api/contacts?page=0&size=20>; rel="first"',
              },
              body: [contact],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(contactPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Contact page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('contact');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contactPageUrlPattern);
      });

      it('edit button click should load edit Contact page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Contact');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contactPageUrlPattern);
      });

      it('edit button click should load edit Contact page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Contact');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contactPageUrlPattern);
      });

      it('last delete button click should delete instance of Contact', () => {
        cy.intercept('GET', '/services/crm/api/contacts/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('contact').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', contactPageUrlPattern);

        contact = undefined;
      });
    });
  });

  describe('new Contact page', () => {
    beforeEach(() => {
      cy.visit(`${contactPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Contact');
    });

    it('should create an instance of Contact', () => {
      cy.get(`[data-cy="firstName"]`).type('José Eduardo');
      cy.get(`[data-cy="firstName"]`).should('have.value', 'José Eduardo');

      cy.get(`[data-cy="middleName"]`).type('extremely');
      cy.get(`[data-cy="middleName"]`).should('have.value', 'extremely');

      cy.get(`[data-cy="lastName"]`).type('Jaramillo Olivera');
      cy.get(`[data-cy="lastName"]`).should('have.value', 'Jaramillo Olivera');

      cy.get(`[data-cy="email"]`).type('G@(R.G');
      cy.get(`[data-cy="email"]`).should('have.value', 'G@(R.G');

      cy.get(`[data-cy="phone"]`).type('+2880478875281');
      cy.get(`[data-cy="phone"]`).should('have.value', '+2880478875281');

      cy.get(`[data-cy="address"]`).type('how trouser although');
      cy.get(`[data-cy="address"]`).should('have.value', 'how trouser although');

      cy.get(`[data-cy="city"]`).type('Santa Coloma de Gramanet');
      cy.get(`[data-cy="city"]`).should('have.value', 'Santa Coloma de Gramanet');

      cy.get(`[data-cy="state"]`).type('afore');
      cy.get(`[data-cy="state"]`).should('have.value', 'afore');

      cy.get(`[data-cy="postalCode"]`).type('87719-7524');
      cy.get(`[data-cy="postalCode"]`).should('have.value', '87719-7524');

      cy.get(`[data-cy="country"]`).type('Pakistan');
      cy.get(`[data-cy="country"]`).should('have.value', 'Pakistan');

      cy.get(`[data-cy="socialMediaProfiles"]`).type('past ack');
      cy.get(`[data-cy="socialMediaProfiles"]`).should('have.value', 'past ack');

      cy.get(`[data-cy="notes"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="notes"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="createdBy"]`).type('5175');
      cy.get(`[data-cy="createdBy"]`).should('have.value', '5175');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        contact = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', contactPageUrlPattern);
    });
  });
});
