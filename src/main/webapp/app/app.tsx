import 'react-toastify/dist/ReactToastify.css';
import './app.scss';
import 'app/config/dayjs.ts';

import React, { useEffect, useState } from 'react';
import { Card } from 'reactstrap';
import { BrowserRouter as Router,useHistory } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getSession } from 'app/shared/reducers/authentication';
import { getProfile } from 'app/shared/reducers/application-profile';
import Header from 'app/shared/layout/header/header';
import Footer from 'app/shared/layout/footer/footer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import ErrorBoundary from 'app/shared/error/error-boundary';
import { AUTHORITIES } from 'app/config/constants';
import AppRoutes from 'app/routes';
import Popup  from 'app/Components/Modal'
import Modal from 'react-bootstrap/Modal' ;
import { Button, Row, Col, FormText } from 'reactstrap';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

export const App = (props:any) => {
  const today =new Date()
        const time = today.getHours()
        const min = today.getMinutes()
        const sec = today.getSeconds()
        const totsec = (time*3600)+(min*60)+sec
        const {initialSeconds = totsec} = props;
  const dispatch = useAppDispatch();
  const [seconds, setSeconds] = useState(0);
  const history = useHistory()
  const intervl = setInterval(() => {
    setSeconds(initialSeconds + 1)
   if(initialSeconds >=16140&&initialSeconds<=16150){
      return(
        <Modal.Dialog>
        <Modal.Header closeButton>
          <Modal.Title>The system is going down</Modal.Title>
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
        if(initialSeconds >=16200 && initialSeconds<18000){
          history.push('/idlecompo')
        }
  
  }, 86400)
 
 
  useEffect(() => {
    
    intervl;
    
     // eslint-disable-next-line no-console
    // console.log(totsec+ "  meee");
    dispatch(getSession());
    dispatch(getProfile());
    
  }, []);

  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const ribbonEnv = useAppSelector(state => state.applicationProfile.ribbonEnv);
  const isInProduction = useAppSelector(state => state.applicationProfile.inProduction);
  const isOpenAPIEnabled = useAppSelector(state => state.applicationProfile.isOpenAPIEnabled);

  const paddingTop = '60px';
  return (
    <Router basename={baseHref}>
      <div className="app-container" style={{ paddingTop }}>
        <ToastContainer position={toast.POSITION.TOP_LEFT} className="toastify-container" toastClassName="toastify-toast" />
        <ErrorBoundary>
          <Header
            isAuthenticated={isAuthenticated}
            isAdmin={isAdmin}
            ribbonEnv={ribbonEnv}
            isInProduction={isInProduction}
            isOpenAPIEnabled={isOpenAPIEnabled}
          />
        </ErrorBoundary>
        <div className="container-fluid view-container" id="app-view-container">
          <Card className="jh-card">
            <ErrorBoundary>
              <AppRoutes />
            </ErrorBoundary>
          </Card>
          <Footer />
        </div>
      </div>
    </Router>
  );
};

export default App;
