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

describe('Payment e2e test', () => {
  const paymentPageUrl = '/crm/payment';
  const paymentPageUrlPattern = new RegExp('/crm/payment(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const paymentSample = { amount: 28860.82, paymentDate: '2025-02-26T11:24:50.213Z', paymentMethod: 'CARD' };

  let payment;
  let rental;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/services/crm/api/rentals',
      body: {
        startDate: '2025-02-25T23:06:54.079Z',
        endDate: '2025-02-25T23:17:25.710Z',
        monthlyRent: 22178.5,
        securityDeposit: 4998.55,
        contractStatus: 'CANCELED',
        createdBy: 18865,
      },
    }).then(({ body }) => {
      rental = body;
    });
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/crm/api/payments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/crm/api/payments').as('postEntityRequest');
    cy.intercept('DELETE', '/services/crm/api/payments/*').as('deleteEntityRequest');
  });

  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/services/crm/api/rentals', {
      statusCode: 200,
      body: [rental],
    });
  });

  afterEach(() => {
    if (payment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/crm/api/payments/${payment.id}`,
      }).then(() => {
        payment = undefined;
      });
    }
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

  it('Payments menu should load Payments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('crm/payment');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Payment').should('exist');
    cy.url().should('match', paymentPageUrlPattern);
  });

  describe('Payment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(paymentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Payment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/crm/payment/new$'));
        cy.getEntityCreateUpdateHeading('Payment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/crm/api/payments',
          body: {
            ...paymentSample,
            rental,
          },
        }).then(({ body }) => {
          payment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/crm/api/payments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/crm/api/payments?page=0&size=20>; rel="last",<http://localhost/services/crm/api/payments?page=0&size=20>; rel="first"',
              },
              body: [payment],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(paymentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Payment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('payment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentPageUrlPattern);
      });

      it('edit button click should load edit Payment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Payment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentPageUrlPattern);
      });

      it('edit button click should load edit Payment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Payment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentPageUrlPattern);
      });

      it('last delete button click should delete instance of Payment', () => {
        cy.intercept('GET', '/services/crm/api/payments/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('payment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', paymentPageUrlPattern);

        payment = undefined;
      });
    });
  });

  describe('new Payment page', () => {
    beforeEach(() => {
      cy.visit(`${paymentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Payment');
    });

    it('should create an instance of Payment', () => {
      cy.get(`[data-cy="amount"]`).type('12017.24');
      cy.get(`[data-cy="amount"]`).should('have.value', '12017.24');

      cy.get(`[data-cy="paymentDate"]`).type('2025-02-26T00:16');
      cy.get(`[data-cy="paymentDate"]`).blur();
      cy.get(`[data-cy="paymentDate"]`).should('have.value', '2025-02-26T00:16');

      cy.get(`[data-cy="paymentMethod"]`).select('TRANSFER');

      cy.get(`[data-cy="reference"]`).type('past ew');
      cy.get(`[data-cy="reference"]`).should('have.value', 'past ew');

      cy.get(`[data-cy="createdBy"]`).type('22214');
      cy.get(`[data-cy="createdBy"]`).should('have.value', '22214');

      cy.get(`[data-cy="rental"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        payment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', paymentPageUrlPattern);
    });
  });
});
