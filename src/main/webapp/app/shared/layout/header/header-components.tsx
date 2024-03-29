import React from 'react';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

// export const BrandIcon = props => (
//   <div {...props} className="brand-icon">
//     <img src="content/images/logo-jhipster.png" alt="Logo" />
//   </div>
// );

export const Brand = () => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <span className="brand-title">PayGovR</span>
    <span className="navbar-version">{VERSION}</span>
  </NavbarBrand>
);

export const Home = () => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>Home</span>
    </NavLink>
  </NavItem>
);

export const PayGov = () => (
  <NavItem>
    <NavLink tag={Link} to="/PayGov" className="d-flex align-items-center">
      <FontAwesomeIcon icon="th-list" />
      <span>pay</span>
    </NavLink>
  </NavItem>
);

