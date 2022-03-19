import React from 'react';
import { shallow } from 'enzyme';
import { configure } from 'enzyme';
import MockAdapter from 'axios-mock-adapter';

import { mount } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ReactDOM from 'react-dom';
import { render, fireEvent } from "@testing-library/react";
import {act} from 'react-dom/test-utils';
import { BrowserRouter as Router,Route,Link, BrowserRouter } from 'react-router-dom';
import axios from "axios";
import PagovConfrm from 'app/Components/PagovConfrm'
import Paygov from 'app/Components/PayGov'

// jest.mock("axios");

configure ({
    adapter:new Adapter()
  });

describe("PayGovTest", () => {
    let wrapper;
    let wrapperP;
      let mockSubmit;
     
      const localStorageMock = (() => {
        let store = {};
    
        return {
          getItem(key) {
            return store[key] || null;
          },
          setItem(key, value) {
            store[key] = value.toString();
          },
          removeItem(key) {
            delete store[key];
          },
          clear() {
            store = {};
          }
        };
      })();
    
      Object.defineProperty(window, 'sessionStorage', {
        value: localStorageMock
      });

      beforeEach(()=>{
        mockSubmit=jest.fn();
        window.sessionStorage.clear();
      jest.restoreAllMocks();
      window.sessionStorage.setItem('user', JSON.stringify({ cik: '111@',
                                                             ccc: '2222222',
                                                             paymentAmount: 10,
                                                             name: 'aaaa',
                                                             email: 'aaaa@gmail.com',
                                                             phone: '1234567890' }));

     wrapper=shallow(<PagovConfrm />);
      });
     
      test('check session storage', () => {
        const checkStorage = jest.spyOn(window.sessionStorage, 'getItem');
        expect(wrapper.instance().userData.cik).toEqual('111@')
      });

      test('testing pass amount',  ()=>{
        const mock = new MockAdapter(axios);
    
        const data = { userData : jest.spyOn(window.sessionStorage, 'getItem'), };
        mock.onPost('http://localhost:8080/api/amt').reply(200, data);
        const instance = wrapper.instance();
       return instance.passAmnt();

    }, 7000)
    test('testing getting redirect url',  ()=>{
        const mock = new MockAdapter(axios);
    
        const data = {hostedCheckoutId: "06202255-1a29-71ff-957b-c98f5ee13476",
        invalidTokens: null,
        merchantReference: null,
        partialRedirectUrl: "pay1.sandbox.secured-by-ingenico.com/checkout/1378-45065ce445984ad080571c6f1d967595:06202255-1a29-71ff-957b-c98f5ee13476:85cec2636a4f49ac94c1b31f55ce0f2c",
        returnmac: "df7c8e77-0a1f-4100-a540-ff25d497cce4" };
        mock.onGet('http://localhost:8080/api/redirect').reply(200, data);
        const instance = wrapper.instance();
       return instance.callApi();

    }, 7000)
    test(' test timer correctly ', () => {
        jest.useFakeTimers();
        const component = shallow(<PagovConfrm/>);
        expect(component.state('time')).toBe(10);
        jest.advanceTimersByTime(1000);
        expect(component.state('time')).toBe(9);

        const instance = wrapper.instance();
        return instance.componentDidMount();

        
     });
});

    