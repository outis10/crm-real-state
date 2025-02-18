import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Alert, Col, Row } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9">
        <h1 className="display-4">
          <Translate contentKey="home.title">Welcome, CRM Real State!</Translate>
        </h1>
        <p className="lead">
          <Translate contentKey="home.subtitle">This is your homepage</Translate>
        </p>
        {account?.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>

              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
            </Alert>

            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
          </div>
        )}
        <p>
          <Translate contentKey="home.question">If you have any question on:</Translate>
        </p>

        <ul>
          <li>
            <a href="mailto:outis10@gmail.com.com" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.contact">Contact me!:</Translate>
            </a>
          </li>
          <li>
            <a href="https://github.com/outis10/crm-real-state/issues?state=open" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.link.bugtracker">Bugs,Todos</Translate>
            </a>
          </li>
        </ul>

        <p>
          <Translate contentKey="home.contribute">Contribute</Translate>{' '}
          <li>
            <a href="https://github.com/outis10/crm-real-state" target="_blank" rel="noopener noreferrer">
              <Translate contentKey="home.github">Repository on Github</Translate>
            </a>
          </li>
        </p>
      </Col>
    </Row>
  );
};

export default Home;
