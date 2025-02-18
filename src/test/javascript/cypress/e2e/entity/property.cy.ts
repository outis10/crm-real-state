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
    name: 'cop waterlogged',
    type: 'outrun',
    operationType: 'SALES',
    location: 'ugh yeast gift',
    city: 'Ponferrada',
    state: 'misreport',
    postalCode: 'frankly partially than',
    price: 16923.1,
    area: 9120,
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
      cy.get(`[data-cy="name"]`).type('drat since smoothly');
      cy.get(`[data-cy="name"]`).should('have.value', 'drat since smoothly');

      cy.get(`[data-cy="codeName"]`).type('slipper unless elegantly');
      cy.get(`[data-cy="codeName"]`).should('have.value', 'slipper unless elegantly');

      cy.get(`[data-cy="type"]`).type('out oval versus');
      cy.get(`[data-cy="type"]`).should('have.value', 'out oval versus');

      cy.get(`[data-cy="operationType"]`).select('LEASE');

      cy.get(`[data-cy="location"]`).type('crushing aside so');
      cy.get(`[data-cy="location"]`).should('have.value', 'crushing aside so');

      cy.get(`[data-cy="city"]`).type('Guadalajara');
      cy.get(`[data-cy="city"]`).should('have.value', 'Guadalajara');

      cy.get(`[data-cy="state"]`).type('cross');
      cy.get(`[data-cy="state"]`).should('have.value', 'cross');

      cy.get(`[data-cy="postalCode"]`).type('unhappy onto');
      cy.get(`[data-cy="postalCode"]`).should('have.value', 'unhappy onto');

      cy.get(`[data-cy="price"]`).type('30469.11');
      cy.get(`[data-cy="price"]`).should('have.value', '30469.11');

      cy.get(`[data-cy="rentalPrice"]`).type('55.14');
      cy.get(`[data-cy="rentalPrice"]`).should('have.value', '55.14');

      cy.get(`[data-cy="area"]`).type('26590');
      cy.get(`[data-cy="area"]`).should('have.value', '26590');

      cy.get(`[data-cy="bedrooms"]`).type('21962');
      cy.get(`[data-cy="bedrooms"]`).should('have.value', '21962');

      cy.get(`[data-cy="bathrooms"]`).type('31153');
      cy.get(`[data-cy="bathrooms"]`).should('have.value', '31153');

      cy.get(`[data-cy="appreciationRate"]`).type('8096.97');
      cy.get(`[data-cy="appreciationRate"]`).should('have.value', '8096.97');

      cy.get(`[data-cy="features"]`).type('statue');
      cy.get(`[data-cy="features"]`).should('have.value', 'statue');

      cy.get(`[data-cy="status"]`).select('AVAILABLE');

      cy.get(`[data-cy="images"]`).type('pro minion');
      cy.get(`[data-cy="images"]`).should('have.value', 'pro minion');

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
