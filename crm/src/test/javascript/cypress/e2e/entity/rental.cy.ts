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

describe('Rental e2e test', () => {
  const rentalPageUrl = '/crm/rental';
  const rentalPageUrlPattern = new RegExp('/crm/rental(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const rentalSample = {
    startDate: '2025-02-26T15:31:45.971Z',
    endDate: '2025-02-26T07:47:17.835Z',
    monthlyRent: 13881.44,
    contractStatus: 'FINISHED',
  };

  let rental;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/crm/api/rentals+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/crm/api/rentals').as('postEntityRequest');
    cy.intercept('DELETE', '/services/crm/api/rentals/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (rental) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/crm/api/rentals/${rental.id}`,
      }).then(() => {
        rental = undefined;
      });
    }
  });

  it('Rentals menu should load Rentals page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('crm/rental');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Rental').should('exist');
    cy.url().should('match', rentalPageUrlPattern);
  });

  describe('Rental page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(rentalPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Rental page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/crm/rental/new$'));
        cy.getEntityCreateUpdateHeading('Rental');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/crm/api/rentals',
          body: rentalSample,
        }).then(({ body }) => {
          rental = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/crm/api/rentals+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/crm/api/rentals?page=0&size=20>; rel="last",<http://localhost/services/crm/api/rentals?page=0&size=20>; rel="first"',
              },
              body: [rental],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(rentalPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Rental page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('rental');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalPageUrlPattern);
      });

      it('edit button click should load edit Rental page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Rental');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalPageUrlPattern);
      });

      it('edit button click should load edit Rental page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Rental');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalPageUrlPattern);
      });

      it('last delete button click should delete instance of Rental', () => {
        cy.intercept('GET', '/services/crm/api/rentals/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('rental').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalPageUrlPattern);

        rental = undefined;
      });
    });
  });

  describe('new Rental page', () => {
    beforeEach(() => {
      cy.visit(`${rentalPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Rental');
    });

    it('should create an instance of Rental', () => {
      cy.get(`[data-cy="startDate"]`).type('2025-02-26T19:28');
      cy.get(`[data-cy="startDate"]`).blur();
      cy.get(`[data-cy="startDate"]`).should('have.value', '2025-02-26T19:28');

      cy.get(`[data-cy="endDate"]`).type('2025-02-26T06:54');
      cy.get(`[data-cy="endDate"]`).blur();
      cy.get(`[data-cy="endDate"]`).should('have.value', '2025-02-26T06:54');

      cy.get(`[data-cy="monthlyRent"]`).type('30388.6');
      cy.get(`[data-cy="monthlyRent"]`).should('have.value', '30388.6');

      cy.get(`[data-cy="securityDeposit"]`).type('9966.58');
      cy.get(`[data-cy="securityDeposit"]`).should('have.value', '9966.58');

      cy.get(`[data-cy="contractStatus"]`).select('ACTIVE');

      cy.get(`[data-cy="createdBy"]`).type('24278');
      cy.get(`[data-cy="createdBy"]`).should('have.value', '24278');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        rental = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', rentalPageUrlPattern);
    });
  });
});
