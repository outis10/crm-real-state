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
  const propertyPageUrl = '/crm/property';
  const propertyPageUrlPattern = new RegExp('/crm/property(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const propertySample = {
    name: 'insignificant',
    type: 'out er',
    operationType: 'SALES',
    location: 'diligent',
    city: 'Málaga',
    state: 'anenst pave yet',
    postalCode: 'oxidise why',
    price: 13354.9,
    area: 20694,
    status: 'AVAILABLE',
    createdBy: 17510,
  };

  let property;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/crm/api/properties+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/crm/api/properties').as('postEntityRequest');
    cy.intercept('DELETE', '/services/crm/api/properties/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (property) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/crm/api/properties/${property.id}`,
      }).then(() => {
        property = undefined;
      });
    }
  });

  it('Properties menu should load Properties page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('crm/property');
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
        cy.url().should('match', new RegExp('/crm/property/new$'));
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
          url: '/services/crm/api/properties',
          body: propertySample,
        }).then(({ body }) => {
          property = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/crm/api/properties+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/crm/api/properties?page=0&size=20>; rel="last",<http://localhost/services/crm/api/properties?page=0&size=20>; rel="first"',
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
        cy.intercept('GET', '/services/crm/api/properties/*').as('dialogDeleteRequest');
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
      cy.get(`[data-cy="name"]`).type('glum deliberately');
      cy.get(`[data-cy="name"]`).should('have.value', 'glum deliberately');

      cy.get(`[data-cy="codeName"]`).type('ill');
      cy.get(`[data-cy="codeName"]`).should('have.value', 'ill');

      cy.get(`[data-cy="type"]`).type('tightly');
      cy.get(`[data-cy="type"]`).should('have.value', 'tightly');

      cy.get(`[data-cy="operationType"]`).select('LEASE');

      cy.get(`[data-cy="location"]`).type('though finally');
      cy.get(`[data-cy="location"]`).should('have.value', 'though finally');

      cy.get(`[data-cy="city"]`).type('Torrejón de Ardoz');
      cy.get(`[data-cy="city"]`).should('have.value', 'Torrejón de Ardoz');

      cy.get(`[data-cy="state"]`).type('disinherit excluding');
      cy.get(`[data-cy="state"]`).should('have.value', 'disinherit excluding');

      cy.get(`[data-cy="postalCode"]`).type('inasmuch fooey');
      cy.get(`[data-cy="postalCode"]`).should('have.value', 'inasmuch fooey');

      cy.get(`[data-cy="price"]`).type('8418.18');
      cy.get(`[data-cy="price"]`).should('have.value', '8418.18');

      cy.get(`[data-cy="rentalPrice"]`).type('2863.09');
      cy.get(`[data-cy="rentalPrice"]`).should('have.value', '2863.09');

      cy.get(`[data-cy="area"]`).type('16198');
      cy.get(`[data-cy="area"]`).should('have.value', '16198');

      cy.get(`[data-cy="bedrooms"]`).type('14198');
      cy.get(`[data-cy="bedrooms"]`).should('have.value', '14198');

      cy.get(`[data-cy="bathrooms"]`).type('32767');
      cy.get(`[data-cy="bathrooms"]`).should('have.value', '32767');

      cy.get(`[data-cy="appreciationRate"]`).type('19174.78');
      cy.get(`[data-cy="appreciationRate"]`).should('have.value', '19174.78');

      cy.get(`[data-cy="features"]`).type('terrible but hmph');
      cy.get(`[data-cy="features"]`).should('have.value', 'terrible but hmph');

      cy.get(`[data-cy="status"]`).select('SOLD');

      cy.get(`[data-cy="images"]`).type('season tight');
      cy.get(`[data-cy="images"]`).should('have.value', 'season tight');

      cy.get(`[data-cy="createdBy"]`).type('21856');
      cy.get(`[data-cy="createdBy"]`).should('have.value', '21856');

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
