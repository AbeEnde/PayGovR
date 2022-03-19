import React, { Component, isValidElement } from 'react';
import { Link, RouteComponentProps,useHistory} from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { isNumber, ValidatedField, ValidatedForm, ValidatedInput } from 'react-jhipster';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { values } from 'lodash';
import history from 'app/Components/history'
import Popup  from 'app/Components/Modal'
 import Modal from 'react-bootstrap/Modal' 

interface paygovstate {
 cik : string;
  ccc : string;
  paymentAmount : string;
  name: string;
  phone: string;
  email: string; 
//  fields:{};
updating:boolean
//  errors:any;
currentTime:any
tim:any
totMin:any
}
/*
const parseJwt = (token) => {
  try {
    return JSON.parse(atob(token.split('.')[1]));
  } catch (e) {
    return null;
  }
}; */

export default class Paygov extends React.Component<any, any> {

 
     timerId = null; 
    userData;
    updating =false;
    constructor(props) {
        super(props);
        const today =new Date()
        const time = today.getHours()
        const min = today.getMinutes()
        const sec = today.getSeconds()
        const totsec = (time*3600)+(min*60)+sec
       // const totMin = (time*60)+min

      /* props.history.listen(() => {
          const user = JSON.parse(localStorage.getItem("user"));
    
          if (user) {
            const decodedJwt = parseJwt(user.accessToken);
      // eslint-disable-next-line no-console
  console.log(decodedJwt);
            if (decodedJwt.exp * 1000 < today.getHours()) {
             props. history.push('/idlecompo');
            }
          }
        }); */

       
        this.onChangeCcc = this.onChangeCcc.bind(this);
        this.onChangeCik = this.onChangeCik.bind(this);
        this.onChangePaymentAmount = this.onChangePaymentAmount.bind(this);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangePhone = this.onChangePhone.bind(this);

        this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            cik: '',
            ccc: '',
            paymentAmount: '',
            name: '',
            email: '',
            phone: '',
            updating: false,
            currentTime: time,
            tim: totsec,
           totMin : (time*60)+min
        }
    }

    // form validation
   
  //   history = useHistory()
    // Form Events
    onChangeCcc(e) {
        this.setState({ ccc: e.target.value })
    }

    onChangeCik(e) {
        this.setState({ cik: e.target.value })
    }

    onChangePaymentAmount(e) {
        this.setState({ paymentAmount: e.target.value })
    }
    onChangeName(e) {
        this.setState({ name: e.target.value })
    }

    onChangeEmail(e) {
        this.setState({ email: e.target.value })
    }

    onChangePhone(e) {
      //  this.setState({ phone: e.target.value })
        if((e.target.value[e.target.value.length-1]>='0' && e.target.value[e.target.value.length-1]<='9') || !e.target.value) {
          this.setState({phone: e.target.value}); }
    }

    onSubmit(e) {

        this.userData = JSON.parse(sessionStorage.getItem('user'));
        e.preventDefault()

        this.setState({
            cik: this.userData.cik,
            ccc: this.userData.ccc,
            paymentAmount: this.userData.paymentAmount,
            name: this.userData.name,
            email: this.userData.email,
            phone: this.userData.phone
        })
    }
    
    handleReset = () => {
      this.setState({
        cik: '',
        ccc: '',
        paymentAmount: '',
        name: '',
        email: '',
        phone: ''
    })
    };
    
    componentDidMount() {
   /*  this.timerId = setInterval(() => {
        this.setState((prevState) => ({ tim: this.state.tim +1 }));
       if(this.state.tim ===16140){
        return(
          <Modal.Dialog>
          <Modal.Header closeButton>
            <Modal.Title>Modal title</Modal.Title>
          </Modal.Header>
        
          <Modal.Body>
            <p>Modal body text goes here.</p>
          </Modal.Body>
        
          <Modal.Footer>
            <Button variant="secondary">Close</Button>
            <Button variant="primary">Save changes</Button>
          </Modal.Footer>
        </Modal.Dialog>
          )
       }
        if(this.state.tim >=16200 && this.state.tim<18000){
          this.props. history.push('/idlecompo')
          sessionStorage.removeItem('user')
         
        }
        if(this.state.tim === 86400 ){
          this.setState(
           { tim:0 }
          )
        }
        
      }, 86400) */

        this.userData = JSON.parse(sessionStorage.getItem('user'));

        if (sessionStorage.getItem('user')) {
            this.setState({
                cik: this.userData.cik,
                ccc: this.userData.ccc,
                paymentAmount: this.userData.paymentAmount,
                name: this.userData.name,
                email: this.userData.email,
                phone: this.userData.phone
            })
        } else {
            this.setState({
                cik: '',
                ccc: '',
                paymentAmount: '',
                name: '',
                email: '',
                phone: ''
            })
        }
        this.isValid();
        // eslint-disable-next-line no-console
  console.log(this.state.tim);
    }

    UNSAFE_componentWillUpdate(nextProps, nextState) {
      sessionStorage.setItem('user', JSON.stringify(nextState));
    }
    isValid ():any{
      
      const ptrn =/^[0-9]+[A-Za-z0-9]+[@#$%^&*()!]/;
      const ptrnccc =/^[0-9]/
      const ptrnpay =/^[0-9]/
      const ptrneml = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i
      const ptrnphon =/^[0-9]/
      
      if(!this.state.cik || !ptrn.exec(this.state.cik)||!ptrnccc.exec(this.state.ccc)||this.state.ccc.length<6
      ||!ptrneml.exec(this.state.email)||!ptrnphon.exec(this.state.phone)||this.state.phone.length!==10
      ){
     return true
      }else{
        return false
      }


    }
 

    render() {
      if(this.state.totMin<=270 || this.state.totMin>300 ){
       
        return (
          
            <div>
              
                  <Row className="justify-content-center">
                    <Col md="8">
                      <h2 id="payGovRApp.pay.home.createOrEditLabel" data-cy="PayCreateUpdateHeading">
                        Create  your Payment
                      </h2>
                    </Col>
                  </Row>
                  <Row className="justify-content-center">
                    <Col md="8">

                        <ValidatedForm onSubmit={this.onSubmit} >

                          <ValidatedField
                            value={this.state.cik}
                            onChange={this.onChangeCik}
                            label="Cik"
                            htmlFore = "cik"
                            id="pay-cik"
                            name="cik"
                            data-cy="cik"    
                            type="text"
                            validate={{
                              required: { value: true, message: 'This field is required.' },
                              pattern: { value: /^[0-9]+[A-Za-z0-9]+[@#$%^&*()!]/, message: "This field must contain at least one special character." },
                            
                            }
                           
                          }
                          />
                          <ValidatedField
                           value={this.state.ccc}
                           onChange={this.onChangeCcc}
                            label="Ccc"
                            id="pay-ccc"
                            name="ccc"
                            data-cy="ccc"
                            type="text"
                            validate={{
                              required: { value: true, message: 'This field is required.' },
                              min: { value: 100000, message: 'This field should be at least 6.' },
                              validate: v => isNumber(v) || 'This field should be a number.',
                            }}
                          />
                          <ValidatedField
                            value={this.state.paymentAmount}
                            onChange={this.onChangePaymentAmount}
                            label="Payment Amount"
                            id="pay-paymentAmount"
                            name="paymentAmount"
                            data-cy="paymentAmount"
                            type="text"
                            validate={{
                              required: { value: true, message: 'This field is required.' },
                              validate: v => isNumber(v) || 'This field should be a number.',
                            }}
                          />
                          <ValidatedField
                            value={this.state.name}
                            onChange={this.onChangeName}
                            label="Name"
                            id="pay-name"
                            name="name"
                            data-cy="name"
                            type="text"
                            validate={{
                              required: { value: true, message: 'This field is required.' },
                            }}
                          />
                          <ValidatedField
                            value={this.state.email}
                            onChange={this.onChangeEmail}
                            label="Email"
                            id="pay-email"
                            name="email"
                            data-cy="email"
                            type="email"
                            validate={{
                              required: { value: true, message: 'This field is required.' },
                              pattern: { value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i, message: "This field should follow pattern for '(^[a-zA-Z])'." },
                            }}
                          />
                          <ValidatedField
                            value={this.state.phone}
                            onChange={this.onChangePhone}
                            label="Phone"
                            id="phone"
                            name="phone"
                            data-cy="phone"
                            type="text"
                            validate={{
                              

                              required: { value: true, message: 'This field is required.' },
                              min: { value: 100000000, message: 'This field should be at least 10.' },
                            
                             // validate: v => isNumber(v) || 'This field should be a number.',
                             validate: val => (val.length===10)&&isNumber(val) || 'This field should be ten digit number.'  ,                      
                             
                                        }}
                          />
                         

                          <Button type="reset" onClick={this.handleReset}>
                            <FontAwesomeIcon icon="arrow-left" />
                            &nbsp;
                            <span className="d-none d-md-inline">Cancel</span>
                          </Button>
                          &nbsp;
                          <Button tag={Link} to="/PagovConfrm" color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" 
                          disabled={ this.isValid()|| !this.state.cik||!this.state.ccc||!this.state.paymentAmount||!this.state.name||!this.state.email||!this.state.phone}>
                            <FontAwesomeIcon icon="forward"  />
                            &nbsp; Next
                          </Button>
                        </ValidatedForm>

                    </Col>
                  </Row>
                </div>
        )
               }
               else{
              return(
                <div className='idl'>
                <b >Sorry the system is down at the moment!!</b>
                </div>
              )

               }
    }
    

 
}
