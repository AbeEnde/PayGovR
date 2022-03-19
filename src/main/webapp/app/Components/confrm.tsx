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

 import { useAppDispatch, useAppSelector } from 'app/config/store';
import Popup  from 'app/Components/Modal'
import { result } from 'lodash';
import Modal from 'app/Components/alert';

// import  history  from 'app/Components/history';

/*
export const Confrm = (props: RouteComponentProps<{ id: string }>) => {
  const pup = null;
   //  const dispatch = useAppDispatch();
    const data = JSON.parse(sessionStorage.getItem('user'))
    const [isOpen, setIsOpen] = useState(false);
    const [post, setPost] = React.useState(null);
    const history = useHistory()
     const handleClick = () => {history.push('/PayGov')};
    const togglePopup = () => {
      setIsOpen(!isOpen);
    }
     const url = 'http://localhost:8080/api/paygoves';
    function createPost() {
      axios.post(url, data )
        .then((response) => {
          // eslint-disable-next-line no-console
          console.log(response);
          if(response.statusText === "Created"){
            setIsOpen(!isOpen);
            sessionStorage.removeItem('user')
          }
          setPost(response.data);
        });
    }  
  */
   /*  const saveEntity = values => {
          dispatch(createEntity(data))
          // eslint-disable-next-line no-console
          .then(res => {
            const reslt =res.meta;
            if(reslt.requestStatus === 'fulfilled'){
              setIsOpen(!isOpen);
              sessionStorage.removeItem('user')
            }
          });
     
        
      }; */
     interface cnf{
       isOpen:any,
       setIsOpen:any
       history:any
      
      }

      class Confrm extends React.Component<cnf> {
        
        userData
      //  history = useHistory() 
      
        state = { 
          isOpen:false,
          setIsOpen:false,
          
         
         }
        constructor(props) {
          super(props);
          
          // this.userData = JSON.parse(sessionStorage.getItem('user'))
          
        }
       
        togglePopup  () {
        // this.state.isOpen = false
        this.setState({
          isOpen:false
        })
         
        }
        
        
         
         createPost= () =>{
         const url = 'http://localhost:8080/api/paygoves';
         this.userData = JSON.parse(sessionStorage.getItem('user'))
          axios.post(url, this.userData )
            .then((response) => {
              // eslint-disable-next-line no-console
              console.log("response");
              if(response.statusText === "Created"){
                this.setState({
                  isOpen:true
                })
                sessionStorage.removeItem('user')
              }
            //  setPost(response.data);
            });
             // eslint-disable-next-line no-console
             console.log("response    "+this.userData);
        } 
      
         

        render(){
          return(
            <div className='conff'>
            <h1>click finish to complete your payment!!</h1>
            <Button  color="primary" className="saveb" data-cy="entityCreateSaveButton" type="submit" onClick={this.createPost}  >
                                        <FontAwesomeIcon icon="save" />
                                        &nbsp; Finish
                                      </Button>
                     <div className='mdl'>   
                     {this.state.isOpen && <Popup
                  content={<>
                    <b>Your payment is completed</b>
                    <p>have a nice time</p>
                    <button onClick = {() => this.props.history.push('/PayGov')} color="primary"  >OK</button>
                  </>}
                  handleClose={this.togglePopup}
                />}
               
                  </div> 
                   
            </div>
            
            )

        }
      }
/*
return(
<div className='conff'>
<h1>click finish to complete your payment!!</h1>
<Button  color="primary" className="saveb" data-cy="entityCreateSaveButton" type="submit" onClick={createPost}  {...pup}>
                            <FontAwesomeIcon icon="save" />
                            &nbsp; Finish
                          </Button>
         <div className='mdl'>   
         {isOpen && <Popup
      content={<>
        <b>Your payment is completed</b>
        <p>have a nice time</p>
        <button  color="primary" onClick = {() => history.push('/PayGov')} >OK</button>
      </>}
      handleClose={togglePopup}
    />}
   
      </div> 
       
</div>

)

} */

export default Confrm;