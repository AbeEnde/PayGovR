import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Paygove from './paygove';
import PaygoveDetail from './paygove-detail';
import PaygoveUpdate from './paygove-update';
import PaygoveDeleteDialog from './paygove-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PaygoveUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PaygoveUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PaygoveDetail} />
      <ErrorBoundaryRoute path={match.url} component={Paygove} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PaygoveDeleteDialog} />
  </>
);

export default Routes;
