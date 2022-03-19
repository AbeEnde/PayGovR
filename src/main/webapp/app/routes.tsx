import React from 'react';
import { Switch } from 'react-router-dom';
import Loadable from 'react-loadable';

import Login from 'app/modules/login/login';
import Register from 'app/modules/account/register/register';
import Activate from 'app/modules/account/activate/activate';
import PasswordResetInit from 'app/modules/account/password-reset/init/password-reset-init';
import PasswordResetFinish from 'app/modules/account/password-reset/finish/password-reset-finish';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import Entities from 'app/entities';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';
import PayGov from 'app/Components/PayGov';
import PagovConfrm from 'app/Components/PagovConfrm';
import confrm from 'app/Components/confrm';
import idlecompo from 'app/Components/idlecompo';
import Modal from 'react-bootstrap/Modal' ;
import { Button, Row, Col, FormText } from 'reactstrap';

const Account = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ 'app/modules/account'),
  loading: () => <div>loading ...</div>,
});

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => <div>loading ...</div>,
});

const Routes = () => {
  const today =new Date()
        const time = today.getHours()
        const min = today.getMinutes()
        const sec = today.getSeconds()
        const totsec = (time*3600)+(min*60)+sec
        const totMin = (time*60)+min
       
        
        if(totMin<=270||totMin>300){
          if(totMin === 269 ){
            // data-mdb-dismiss="modal"
            return(
            <Modal.Dialog>
            <Modal.Header closeButton>
              <Modal.Title>The system is getting down</Modal.Title>
            </Modal.Header>
          
            <Modal.Body>
              <p>have a nice time.</p>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="primary" data-mdb-dismiss="modal">OK</Button>
            </Modal.Footer>
          </Modal.Dialog>
            )
          } 
          
        
  return (
       

    <div className="view-routes">
      
      <Switch>
        
      <ErrorBoundaryRoute path="/idlecompo" component={idlecompo} />
        <ErrorBoundaryRoute path="/confrm" component={confrm} />
        <ErrorBoundaryRoute path="/PagovConfrm" component={PagovConfrm} />
        <ErrorBoundaryRoute path="/PayGov" component={PayGov} />
        <ErrorBoundaryRoute path="/login" component={Login} />
        <ErrorBoundaryRoute path="/logout" component={Logout} />
        <ErrorBoundaryRoute path="/account/register" component={Register} />
        <ErrorBoundaryRoute path="/account/activate/:key?" component={Activate} />
        <ErrorBoundaryRoute path="/account/reset/request" component={PasswordResetInit} />
        <ErrorBoundaryRoute path="/account/reset/finish/:key?" component={PasswordResetFinish} />
        <PrivateRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
        <PrivateRoute path="/account" component={Account} hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.USER]} />
        <ErrorBoundaryRoute path="/" exact component={Home} />
        <PrivateRoute path="/" component={Entities} hasAnyAuthorities={[AUTHORITIES.USER]} />
        <ErrorBoundaryRoute component={PageNotFound} />
       
      </Switch>
    </div>
  );
}else{
  return(
    <>  
    
    <ErrorBoundaryRoute path="/idlecompo" component={idlecompo} />
    <ErrorBoundaryRoute path="/PayGov" component={PayGov} />
    </>
   )
  
}
};

export default Routes;
