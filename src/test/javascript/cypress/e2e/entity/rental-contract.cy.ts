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

describe('RentalContract e2e test', () => {
  const rentalContractPageUrl = '/rental-contract';
  const rentalContractPageUrlPattern = new RegExp('/rental-contract(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const rentalContractSample = {
    startDate: '2025-02-12T19:15:12.125Z',
    endDate: '2025-02-12T19:21:58.897Z',
    monthlyRent: 31703.59,
    status: 'CANCELED',
  };

  let rentalContract;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/rental-contracts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/rental-contracts').as('postEntityRequest');
    cy.intercept('DELETE', '/api/rental-contracts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (rentalContract) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/rental-contracts/${rentalContract.id}`,
      }).then(() => {
        rentalContract = undefined;
      });
    }
  });

  it('RentalContracts menu should load RentalContracts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('rental-contract');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RentalContract').should('exist');
    cy.url().should('match', rentalContractPageUrlPattern);
  });

  describe('RentalContract page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(rentalContractPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RentalContract page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/rental-contract/new$'));
        cy.getEntityCreateUpdateHeading('RentalContract');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalContractPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/rental-contracts',
          body: rentalContractSample,
        }).then(({ body }) => {
          rentalContract = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/rental-contracts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/rental-contracts?page=0&size=20>; rel="last",<http://localhost/api/rental-contracts?page=0&size=20>; rel="first"',
              },
              body: [rentalContract],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(rentalContractPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RentalContract page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('rentalContract');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalContractPageUrlPattern);
      });

      it('edit button click should load edit RentalContract page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RentalContract');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalContractPageUrlPattern);
      });

      it('edit button click should load edit RentalContract page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RentalContract');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalContractPageUrlPattern);
      });

      it('last delete button click should delete instance of RentalContract', () => {
        cy.intercept('GET', '/api/rental-contracts/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('rentalContract').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalContractPageUrlPattern);

        rentalContract = undefined;
      });
    });
  });

  describe('new RentalContract page', () => {
    beforeEach(() => {
      cy.visit(`${rentalContractPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RentalContract');
    });

    it('should create an instance of RentalContract', () => {
      cy.get(`[data-cy="startDate"]`).type('2025-02-12T04:57');
      cy.get(`[data-cy="startDate"]`).blur();
      cy.get(`[data-cy="startDate"]`).should('have.value', '2025-02-12T04:57');

      cy.get(`[data-cy="endDate"]`).type('2025-02-12T21:10');
      cy.get(`[data-cy="endDate"]`).blur();
      cy.get(`[data-cy="endDate"]`).should('have.value', '2025-02-12T21:10');

      cy.get(`[data-cy="monthlyRent"]`).type('9071.41');
      cy.get(`[data-cy="monthlyRent"]`).should('have.value', '9071.41');

      cy.get(`[data-cy="securityDeposit"]`).type('2692.62');
      cy.get(`[data-cy="securityDeposit"]`).should('have.value', '2692.62');

      cy.get(`[data-cy="status"]`).select('FINISHED');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        rentalContract = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', rentalContractPageUrlPattern);
    });
  });
});
