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

describe('NearbyLocation e2e test', () => {
  const nearbyLocationPageUrl = '/nearby-location';
  const nearbyLocationPageUrlPattern = new RegExp('/nearby-location(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const nearbyLocationSample = { name: 'at wobbly badly', type: 'obediently ouch that', distance: 27233.19 };

  let nearbyLocation;
  let property;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/properties',
      body: {
        name: 'amid brush since',
        codeName: 'hyphenation whenever amid',
        type: 'so',
        operationType: 'SALES',
        location: 'especially forager',
        city: 'Tarrasa',
        state: 'seldom quarrelsomely than',
        postalCode: 'determined',
        price: 9585.14,
        rentalPrice: 27188.33,
        area: 9946,
        bedrooms: 1079,
        bathrooms: 6713,
        appreciationRate: 7513.85,
        features: 'oh',
        status: 'AVAILABLE',
        images: 'correctly guilty',
      },
    }).then(({ body }) => {
      property = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/nearby-locations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/nearby-locations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/nearby-locations/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/properties', {
      statusCode: 200,
      body: [property],
    });
  });

  afterEach(() => {
    if (nearbyLocation) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/nearby-locations/${nearbyLocation.id}`,
      }).then(() => {
        nearbyLocation = undefined;
      });
    }
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

  it('NearbyLocations menu should load NearbyLocations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('nearby-location');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('NearbyLocation').should('exist');
    cy.url().should('match', nearbyLocationPageUrlPattern);
  });

  describe('NearbyLocation page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(nearbyLocationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create NearbyLocation page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/nearby-location/new$'));
        cy.getEntityCreateUpdateHeading('NearbyLocation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', nearbyLocationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/nearby-locations',
          body: {
            ...nearbyLocationSample,
            property,
          },
        }).then(({ body }) => {
          nearbyLocation = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/nearby-locations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/nearby-locations?page=0&size=20>; rel="last",<http://localhost/api/nearby-locations?page=0&size=20>; rel="first"',
              },
              body: [nearbyLocation],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(nearbyLocationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details NearbyLocation page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('nearbyLocation');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', nearbyLocationPageUrlPattern);
      });

      it('edit button click should load edit NearbyLocation page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('NearbyLocation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', nearbyLocationPageUrlPattern);
      });

      it('edit button click should load edit NearbyLocation page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('NearbyLocation');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', nearbyLocationPageUrlPattern);
      });

      it('last delete button click should delete instance of NearbyLocation', () => {
        cy.intercept('GET', '/api/nearby-locations/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('nearbyLocation').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', nearbyLocationPageUrlPattern);

        nearbyLocation = undefined;
      });
    });
  });

  describe('new NearbyLocation page', () => {
    beforeEach(() => {
      cy.visit(`${nearbyLocationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('NearbyLocation');
    });

    it('should create an instance of NearbyLocation', () => {
      cy.get(`[data-cy="name"]`).type('new whoa');
      cy.get(`[data-cy="name"]`).should('have.value', 'new whoa');

      cy.get(`[data-cy="type"]`).type('oof ack stormy');
      cy.get(`[data-cy="type"]`).should('have.value', 'oof ack stormy');

      cy.get(`[data-cy="distance"]`).type('32031.62');
      cy.get(`[data-cy="distance"]`).should('have.value', '32031.62');

      cy.get(`[data-cy="coordinates"]`).type('colligate');
      cy.get(`[data-cy="coordinates"]`).should('have.value', 'colligate');

      cy.get(`[data-cy="property"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        nearbyLocation = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', nearbyLocationPageUrlPattern);
    });
  });
});
