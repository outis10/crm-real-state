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

describe('Property e2e test', () => {
  const propertyPageUrl = '/property';
  const propertyPageUrlPattern = new RegExp('/property(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const propertySample = {
    name: 'enthusiastically',
    type: 'times creator',
    operationType: 'LEASE',
    location: 'climb in gah',
    city: 'Lorca',
    state: 'yum whether',
    postalCode: 'cook complication indeed',
    price: 119.58,
    area: 28456,
    status: 'LEASED',
  };

  let property;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/properties+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/properties').as('postEntityRequest');
    cy.intercept('DELETE', '/api/properties/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (property) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/properties/${property.id}`,
      }).then(() => {
        property = undefined;
      });
    }
  });

  it('Properties menu should load Properties page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('property');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Property').should('exist');
    cy.url().should('match', propertyPageUrlPattern);
  });

  describe('Property page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(propertyPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Property page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/property/new$'));
        cy.getEntityCreateUpdateHeading('Property');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', propertyPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/properties',
          body: propertySample,
        }).then(({ body }) => {
          property = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/properties+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/properties?page=0&size=20>; rel="last",<http://localhost/api/properties?page=0&size=20>; rel="first"',
              },
              body: [property],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(propertyPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Property page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('property');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', propertyPageUrlPattern);
      });

      it('edit button click should load edit Property page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Property');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', propertyPageUrlPattern);
      });

      it('edit button click should load edit Property page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Property');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', propertyPageUrlPattern);
      });

      it('last delete button click should delete instance of Property', () => {
        cy.intercept('GET', '/api/properties/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('property').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', propertyPageUrlPattern);

        property = undefined;
      });
    });
  });

  describe('new Property page', () => {
    beforeEach(() => {
      cy.visit(`${propertyPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Property');
    });

    it('should create an instance of Property', () => {
      cy.get(`[data-cy="name"]`).type('about helplessly');
      cy.get(`[data-cy="name"]`).should('have.value', 'about helplessly');

      cy.get(`[data-cy="codeName"]`).type('before angrily duh');
      cy.get(`[data-cy="codeName"]`).should('have.value', 'before angrily duh');

      cy.get(`[data-cy="type"]`).type('wisecrack');
      cy.get(`[data-cy="type"]`).should('have.value', 'wisecrack');

      cy.get(`[data-cy="operationType"]`).select('SALES');

      cy.get(`[data-cy="location"]`).type('cricket aha');
      cy.get(`[data-cy="location"]`).should('have.value', 'cricket aha');

      cy.get(`[data-cy="city"]`).type('Gecho');
      cy.get(`[data-cy="city"]`).should('have.value', 'Gecho');

      cy.get(`[data-cy="state"]`).type('best eek window');
      cy.get(`[data-cy="state"]`).should('have.value', 'best eek window');

      cy.get(`[data-cy="postalCode"]`).type('hairy unabashedly shrilly');
      cy.get(`[data-cy="postalCode"]`).should('have.value', 'hairy unabashedly shrilly');

      cy.get(`[data-cy="price"]`).type('6576.33');
      cy.get(`[data-cy="price"]`).should('have.value', '6576.33');

      cy.get(`[data-cy="rentalPrice"]`).type('21320.42');
      cy.get(`[data-cy="rentalPrice"]`).should('have.value', '21320.42');

      cy.get(`[data-cy="area"]`).type('28372');
      cy.get(`[data-cy="area"]`).should('have.value', '28372');

      cy.get(`[data-cy="bedrooms"]`).type('3692');
      cy.get(`[data-cy="bedrooms"]`).should('have.value', '3692');

      cy.get(`[data-cy="bathrooms"]`).type('16065');
      cy.get(`[data-cy="bathrooms"]`).should('have.value', '16065');

      cy.get(`[data-cy="appreciationRate"]`).type('6740.39');
      cy.get(`[data-cy="appreciationRate"]`).should('have.value', '6740.39');

      cy.get(`[data-cy="features"]`).type('under');
      cy.get(`[data-cy="features"]`).should('have.value', 'under');

      cy.get(`[data-cy="status"]`).select('AVAILABLE');

      cy.get(`[data-cy="images"]`).type('fervently');
      cy.get(`[data-cy="images"]`).should('have.value', 'fervently');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        property = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', propertyPageUrlPattern);
    });
  });
});
