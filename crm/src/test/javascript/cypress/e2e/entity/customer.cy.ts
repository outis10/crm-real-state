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

describe('Customer e2e test', () => {
  const customerPageUrl = '/crm/customer';
  const customerPageUrlPattern = new RegExp('/crm/customer(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const customerSample = { firstName: 'María Eugenia', email: 'U^3U=@*_#.uB6X' };

  let customer;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/crm/api/customers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/crm/api/customers').as('postEntityRequest');
    cy.intercept('DELETE', '/services/crm/api/customers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (customer) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/crm/api/customers/${customer.id}`,
      }).then(() => {
        customer = undefined;
      });
    }
  });

  it('Customers menu should load Customers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('crm/customer');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Customer').should('exist');
    cy.url().should('match', customerPageUrlPattern);
  });

  describe('Customer page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(customerPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Customer page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/crm/customer/new$'));
        cy.getEntityCreateUpdateHeading('Customer');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', customerPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/crm/api/customers',
          body: customerSample,
        }).then(({ body }) => {
          customer = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/crm/api/customers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/crm/api/customers?page=0&size=20>; rel="last",<http://localhost/services/crm/api/customers?page=0&size=20>; rel="first"',
              },
              body: [customer],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(customerPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Customer page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('customer');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', customerPageUrlPattern);
      });

      it('edit button click should load edit Customer page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Customer');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', customerPageUrlPattern);
      });

      it('edit button click should load edit Customer page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Customer');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', customerPageUrlPattern);
      });

      it('last delete button click should delete instance of Customer', () => {
        cy.intercept('GET', '/services/crm/api/customers/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('customer').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', customerPageUrlPattern);

        customer = undefined;
      });
    });
  });

  describe('new Customer page', () => {
    beforeEach(() => {
      cy.visit(`${customerPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Customer');
    });

    it('should create an instance of Customer', () => {
      cy.get(`[data-cy="firstName"]`).type('Arturo');
      cy.get(`[data-cy="firstName"]`).should('have.value', 'Arturo');

      cy.get(`[data-cy="middleName"]`).type('handy golden');
      cy.get(`[data-cy="middleName"]`).should('have.value', 'handy golden');

      cy.get(`[data-cy="lastName"]`).type('Lucio Yáñez');
      cy.get(`[data-cy="lastName"]`).should('have.value', 'Lucio Yáñez');

      cy.get(`[data-cy="email"]`).type('beA*@Xx?4.nsMb');
      cy.get(`[data-cy="email"]`).should('have.value', 'beA*@Xx?4.nsMb');

      cy.get(`[data-cy="phone"]`).type('44302052010681');
      cy.get(`[data-cy="phone"]`).should('have.value', '44302052010681');

      cy.get(`[data-cy="address"]`).type('merry boo');
      cy.get(`[data-cy="address"]`).should('have.value', 'merry boo');

      cy.get(`[data-cy="city"]`).type('Algeciras');
      cy.get(`[data-cy="city"]`).should('have.value', 'Algeciras');

      cy.get(`[data-cy="state"]`).type('ew');
      cy.get(`[data-cy="state"]`).should('have.value', 'ew');

      cy.get(`[data-cy="postalCode"]`).type('04338-9982');
      cy.get(`[data-cy="postalCode"]`).should('have.value', '04338-9982');

      cy.get(`[data-cy="country"]`).type('Malta');
      cy.get(`[data-cy="country"]`).should('have.value', 'Malta');

      cy.get(`[data-cy="socialMediaProfiles"]`).type('gnaw');
      cy.get(`[data-cy="socialMediaProfiles"]`).should('have.value', 'gnaw');

      cy.get(`[data-cy="notes"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="notes"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="preferences"]`).type('onto');
      cy.get(`[data-cy="preferences"]`).should('have.value', 'onto');

      cy.get(`[data-cy="budget"]`).type('29516.71');
      cy.get(`[data-cy="budget"]`).should('have.value', '29516.71');

      cy.get(`[data-cy="rentalBudget"]`).type('8771.71');
      cy.get(`[data-cy="rentalBudget"]`).should('have.value', '8771.71');

      cy.get(`[data-cy="interactionHistory"]`).type('../fake-data/blob/hipster.txt');
      cy.get(`[data-cy="interactionHistory"]`).invoke('val').should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="createdBy"]`).type('32053');
      cy.get(`[data-cy="createdBy"]`).should('have.value', '32053');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        customer = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', customerPageUrlPattern);
    });
  });
});
