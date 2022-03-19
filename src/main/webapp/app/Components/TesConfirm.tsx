import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps,useHistory  } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import './paygovconf.scss';
import './modal.css';
import { getPayRedi,createEntity } from 'app/Components/paygovConf.reducer'
import { getEntity } from './paygove.reducer';
import axios from 'axios';
// import { useAppDispatch, useAppSelector } from 'app/config/store';
import Popup  from 'app/Components/Modal'
import { result } from 'lodash';
import Modal from 'app/Components/alert';