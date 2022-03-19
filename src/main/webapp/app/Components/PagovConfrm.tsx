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


interface formI{
  cik:string,
  ccc:string,
  paymentAmount:string,
  name:string,
  email:string,
  phone:string,
 

}


class PagovConfrm extends React.Component {
  userData;
  payredires;
   parsdres;
   porotocol;
   tkn;
   tknjes;
  state = { 
    time: 10,
    loading:true,
    payres:{},
   cok:"",
   
   }
  timerId = null;

  constructor(props) {
    super(props);
    
    this.userData = JSON.parse(sessionStorage.getItem('user')) 
    this.state.cok =  document.cookie
    
  // this.payredires = getPayRedi();
    // eslint-disable-next-line no-console
    // console.log(this.payredires+"url here");
    
  }
 
 


 passAmnt(){
  const requestp = "http://localhost:8080/api/amt"; 
axios.post(requestp, this.userData )
          // eslint-disable-next-line no-console
           .then(res => console.log(res))

 }
  callApi() {
    const request = "http://localhost:8080/api/redirect";
    
    axios.get(request,
      {
        headers: {
          'Content-Type': 'application/json',
          
      }, 
      }
      )
      .then(res => {
        this.setState({respo: res.data});
        this.payredires =JSON.stringify(res.data);
         this.parsdres = JSON.parse(this.payredires);
        this.porotocol =  'https://payment.';
        // eslint-disable-next-line no-console
        console.log( this.parsdres);
  // console.log(this.porotocol+ this.parsdres.partialRedirectUrl);
      });
  }
  componentDidMount() {
    this.userData = JSON.parse(sessionStorage.getItem('user')) 
    this.timerId = setInterval(() => {
      this.setState((prevState) => ({ time: this.state.time -1 }));
     
      if(this.state.time===0){
        clearInterval(this.timerId);
        window.location.href = this.porotocol+ this.parsdres.partialRedirectUrl
        
      }
      
    }, 1000);
  // this. getCookie('csrf');
   // this.getToken();
    this.passAmnt();
    this.callApi();
  }
  componentWillUnmount() {
    if(this.state.time===0){
      clearInterval(this.timerId);
    }
   
  } 




  render() {
    return(
    
      <div > 
        <div className='confrm'> 
        <h2>your details are...</h2>
      CIK: {this.userData.cik}
        <br />
      CCC: {this.userData.ccc}
      <br />
      Payment Amount:{this.userData.paymentAmount}
      <br />
      Name:{this.userData.name}
      <br />
      Email:{this.userData.email}
      <br />
      Phone:{this.userData.phone}
      <br />
     
      <div className='timr'> <p > {this.state.time+"s"} </p>

      </div>
      <Button className='btnc' tag={Link} to="/PayGov" color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit">
                            <FontAwesomeIcon icon="arrow-left" />
                            &nbsp; Back
                          </Button> 
         </div>   
      </div>
      
        );  
      };
  }

export default PagovConfrm;