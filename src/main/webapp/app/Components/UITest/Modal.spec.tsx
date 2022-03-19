import React from 'react';
import { shallow } from 'enzyme';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ReactDOM from 'react-dom';
import { render, fireEvent } from "@testing-library/react";
import {act} from 'react-dom/test-utils';
import { BrowserRouter as Router,Route,Link, BrowserRouter } from 'react-router-dom';
import  Popup from 'app/Components/Modal';
import { RiGameFill } from 'react-icons/ri';

configure ({
    adapter:new Adapter()
  });
  describe("modal", () => {
    let wrapper;
      let mockSubmit;
      beforeEach(()=>{
        mockSubmit=jest.fn();
        wrapper=shallow(<Popup  />);
      });

      test("snapshot matching",()=>{
        expect(wrapper).toMatchSnapshot();
      });
    })