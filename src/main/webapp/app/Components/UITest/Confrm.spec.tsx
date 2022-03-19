import React from 'react';
import { shallow,mount } from 'enzyme';
import { configure } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import ReactDOM from 'react-dom';
import { render, fireEvent } from "@testing-library/react";
import {act} from 'react-dom/test-utils';
import { BrowserRouter as Router,Route,Link, BrowserRouter } from 'react-router-dom';
import Confrm from 'app/Components/confrm';

import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';


let container;
let localStore;
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
configure ({
    adapter:new Adapter()
  });
 describe ('confirmatin page test', ()=>{
let wrapper;
let mockSubmit;
let props


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
  wrapper=shallow(<Confrm isOpen={undefined} setIsOpen={undefined} history={undefined}  />);
});

test("snapshot matching",()=>{
  expect(wrapper).toMatchSnapshot();
});

 test('shall save payment',  ()  => {
    const mock = new MockAdapter(axios);
    const datar = { statusText: "Created"}
    const data = { userData : jest.spyOn(window.sessionStorage, 'getItem'), };

  /*  try{
        mock.onPost('http://localhost:8080/api/paygoves').reply(200, data);
        const instance = wrapper.instance();
        expect(instance.createPost().then((Response)=>{
            Response.statusText
        })).toEqual(datar);
    }catch (error){
        done(error)
    }*/
  
    // const data = { userData : jest.spyOn(window.sessionStorage, 'getItem'), };
    mock.onPost('http://localhost:8080/api/paygoves').reply(200, data);
    const instance = wrapper.instance();
   return instance.createPost();  
     //  return instance.createPost();
}, 100000) 


}) 

/* describe('Test Apis', () => {
  describe('getResource', () => {
      describe('with success', () => {
        

        let wrapper;
let mockSubmit;
let props
          const url = 'http://localhost:8080/api/paygoves';
          const onComplete = jest.fn();
          const data = {};

          beforeEach(() => {
            (axios.get as jest.Mock).mockResolvedValue(data);
            window.sessionStorage.setItem('user', JSON.stringify({ cik: '111@',
                                                       ccc: '2222222',
                                                       paymentAmount: 10,
                                                       name: 'aaaa',
                                                       email: 'aaaa@gmail.com',
                                                       phone: '1234567890' }));
            
            wrapper=shallow(<Confrm isOpen={undefined} setIsOpen={undefined} history={undefined}  />)// history={undefined} location={undefined} match={undefined}
          });

          it('should call axios get with given url', () => {
            let mockhk;
            jest.mock('Confrm', () => ({
              mockhk: () => [300, 200, jest.fn()]
          }));
            mockhk.createPost(url, onComplete);
              expect(axios.post).toBeCalledWith(url);
          });

          it('should call onComplete callback with response', async () => { // do not forget 'async'
              await wrapper.instance().createPost(url, onComplete); // notice the 'await' because onComplete callback is called in '.then'
              expect(onComplete).toBeCalledWith(data);
          });
      });
  });
}); */

