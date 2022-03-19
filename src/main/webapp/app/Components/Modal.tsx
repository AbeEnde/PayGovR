
import React from "react";
import './modal.css';
import { Link, RouteComponentProps,useHistory  } from 'react-router-dom';

const Popup = props => {
  const history = useHistory()
  return (
    <div className="popup-box">
      <div className="box">
        <span className="close-icon" onClick={props.handleClose}>x</span>
        
        {props.content}
      </div>
      
    </div>
  );
};

export default Popup;
