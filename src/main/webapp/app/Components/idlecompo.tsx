import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import './paygovconf.scss';
import { getPayRedi } from 'app/Components/paygovConf.reducer'
import { getEntity } from './paygove.reducer';
import axios from 'axios';
import { cleanEntity } from 'app/shared/util/entity-utils';

class idlecompo extends React.Component {


render(): React.ReactNode {
    return(
        <b> sorry the system is down at the moment!! </b>
    )
}

}
export default idlecompo


