import React, {useState} from 'react';
import ProductList from './product/ProductList';
import ShoppingCart from './shoppingCart/ShoppingCart';
import NavBar from './NavBar';
import {BrowserRouter as Router, Route} from "react-router-dom";
import {Switch} from "react-router";
import OrderConfirmation from "./order/OrderConfirmation";

export default function App() {

  const [successAlert, setSuccessAlert] = useState('');
  const [currentTab, setCurrentTab] = useState('');

  return (
      <Router>
        <NavBar setSuccessAlert={setSuccessAlert}
                successAlert={successAlert}
                currentTab={currentTab}
        />
        <div className='container'>
          <Switch>
            <Route exact path="/">
              <ProductList setSuccessAlert={setSuccessAlert}
                           setCurrentTab={setCurrentTab}/>
            </Route>
            <Route exact path="/products">
              <ProductList setSuccessAlert={setSuccessAlert}
                           setCurrentTab={setCurrentTab}/>
            </Route>
            <Route exact path="/shoppingCart">
              <ShoppingCart setSuccessAlert={setSuccessAlert}
                            setCurrentTab={setCurrentTab}/>
            </Route>
            <Route exact path="/orders/:orderId">
              <OrderConfirmation/>
            </Route>
          </Switch>
        </div>
      </Router>
  );
}
