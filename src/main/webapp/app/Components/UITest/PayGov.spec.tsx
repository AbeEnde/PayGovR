import React from 'react';
import { shallow } from 'enzyme';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ReactDOM from 'react-dom';
import { render, fireEvent } from "@testing-library/react";
import {act} from 'react-dom/test-utils';
import { BrowserRouter as Router,Route,Link, BrowserRouter } from 'react-router-dom';
import  Paygov from 'app/Components/PayGov';
import { RiGameFill } from 'react-icons/ri';

configure ({
    adapter:new Adapter()
  });

describe("PayGovTest", () => {
    let wrapper;
      let mockSubmit;
      beforeEach(()=>{
        mockSubmit=jest.fn();
        wrapper=shallow(<Paygov onsubmit={mockSubmit} />);
      });

      test("snapshot matching",()=>{
        expect(wrapper).toMatchSnapshot();
      });
    test('changeing cik state', () => {
      const mockEvent = {
        target: {
          name: 'cik',
          value: '111'
        }
      };
     
      wrapper.instance().onChangeCik(mockEvent);
      expect(wrapper.state().cik).toEqual('111');
     
    });
    test('changeing ccc state', () => {
      const mockEvent = {
        target: {
          name: 'ccc',
          value: '222'
        }
      };
     
      wrapper.instance().onChangeCcc(mockEvent);
      expect(wrapper.state().ccc).toEqual('222');
     
    });
    test('changeing paymentAmount state', () => {
      const mockEvent = {
        target: {
          name: 'paymentAmount',
          value: 10
        }
      };
     
      wrapper.instance().onChangePaymentAmount(mockEvent);
      expect(wrapper.state().paymentAmount).toEqual(10);
     
    });
    test('changeing name state', () => {
      const mockEvent = {
        target: {
          name: 'name',
          value: 'Aaaa'
        }
      };
     
      wrapper.instance().onChangeName(mockEvent);
      expect(wrapper.state().name).toEqual('Aaaa');
     
    });
    test('changeing email state', () => {
      const mockEvent = {
        target: {
          name: 'email',
          value: "aaaa@RiGameFill.com"
        }
      };
     
      wrapper.instance().onChangeEmail(mockEvent);
      expect(wrapper.state().email).toEqual("aaaa@RiGameFill.com");
     
    });
    test('changeing phone state', () => {
      const mockEvent = {
        target: {
          name: 'phone',
          value: '1234567890'
        }
      };
     
      wrapper.instance().onChangePhone(mockEvent);
      expect(wrapper.state().phone).toEqual('1234567890');
     
    });
    test('check handlerest', () => {
      wrapper.instance().handleReset();
          expect(wrapper.instance().state.cik).toEqual('');
          expect(wrapper.instance().state.ccc).toEqual('');
          expect(wrapper.instance().state.paymentAmount).toEqual('');
          expect(wrapper.instance().state.name).toEqual('');
          expect(wrapper.instance().state.email).toEqual('');
          expect(wrapper.instance().state.phone).toEqual('');
     
    });
    test('field validation for cik', ()=>{
      wrapper.instance().state.cik = '123'
      wrapper.instance().state.ccc = 233333
      wrapper.instance().state.paymentAmount = 123
      wrapper.instance().state.name = 'abebe'
      wrapper.instance().state.phone = '1234567890'
      wrapper.instance().state.email = 'abeende@gmail.com'
      
      expect(wrapper.instance().isValid()).toEqual(true)
    })
    test('field validation for ccc', ()=>{
      wrapper.instance().state.cik = '123w@'
      wrapper.instance().state.ccc = '233'
      wrapper.instance().state.paymentAmount = 123
      wrapper.instance().state.name = 'abebe'
      wrapper.instance().state.phone = '1234567890'
      wrapper.instance().state.email = 'abeende@gmail.com'
      
      expect(wrapper.instance().isValid()).toEqual(true)
    })
   
   
    test('field validation for phone', ()=>{
      wrapper.instance().state.cik = '123w@'
      wrapper.instance().state.ccc = '233'
      wrapper.instance().state.paymentAmount = 123
      wrapper.instance().state.name = 'abebe'
      wrapper.instance().state.phone = 123
      wrapper.instance().state.email = 'abeende@gmail.com'
      
      expect(wrapper.instance().isValid()).toEqual(true)
    })
    test('store session storage', ()=>{
      const n = null;
     const data ={
      cik: '1111@',
      ccc: '222222',
      paymentAmount: 10,
      name: 'abebe',
      email: 'abeende@gmail.com',
      phone: '1234567890',
     }
    
     wrapper.instance().UNSAFE_componentWillUpdate(n, data)
     wrapper.instance().componentDidMount() ;
     expect(wrapper.instance().userData.cik).toEqual('1111@')
    })
   })