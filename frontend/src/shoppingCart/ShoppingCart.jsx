import React, {Fragment, useEffect, useState} from "react";
import axios from "axios";
import ShoppingCartProductRow from './ShoppingCartProductRow';
import {useHistory} from "react-router-dom";

export default function ShoppingCart({setSuccessAlert, setCurrentTab}) {

  const [shoppingCart, setShoppingCart] = useState(null);
  const history = useHistory();

  useEffect(() => {
    getShoppingCart();
    setCurrentTab('shoppingCart');
  }, [setCurrentTab]);

  function getShoppingCart() {
    axios.get('/api/shoppingCart')
        .then(response => {
          setShoppingCart(response.data);
        });
  }

  function submitShoppingCart() {
    axios.get('/api/shoppingCart')
        .then(response => {
          // When user submits their shopping cart, first make sure the products and counts they see in the tab is up to date.
          // Implementation goes like this: We iterate through all the items we have
          let validationSet = new Set();

          shoppingCart.forEach(i => {
            validationSet.add(i.product.sku + '-' + i.count);
          });
          response.data.forEach(i => {
            validationSet.delete(i.product.sku + '-' + i.count);
          });
          if (validationSet.size !== 0) {
            setSuccessAlert('Shopping Cart Modified! Please refresh the page!');
            return;
          }

          response.data.forEach(i => {
            validationSet.add(i.product.sku + '-' + i.count);
          });
          shoppingCart.forEach(i => {
            validationSet.delete(i.product.sku + '-' + i.count);
          });
          if (validationSet.size !== 0) {
            setSuccessAlert('Shopping Cart Modified! Please refresh the page!');
            return;
          }

          setSuccessAlert('');
          axios.post('/api/orders').then((response) => {
            setSuccessAlert(
                `Your order number is: ${response.data}. Keep this number and bookmark this page for your records.`);
            history.push(`/orders/${response.data}`);
          });
        });
  }

  return (
      <Fragment>
        {
          shoppingCart !== null && shoppingCart.length === 0 && <div>Your Shopping Cart is empty.</div>
        }
        {
          shoppingCart !== null && shoppingCart.length !== 0 &&
          <div style={{width: '500px', margin: 'auto'}}>
            <table className="table">
              <thead>
              <tr>
                <th>Shopping Cart</th>
                <th/>
                <th/>
                <th/>
                <th/>
              </tr>
              </thead>
              <tbody>
              {shoppingCart.sort((a, b) => {
                return (a.product.name).localeCompare(b.product.name);
              }).map(sc => {
                return <ShoppingCartProductRow key={sc.product.sku}
                                               product={sc.product}
                                               count={sc.count}
                                               setSuccessAlert={setSuccessAlert}
                                               getShoppingCart={getShoppingCart}
                />
              })}
              </tbody>
            </table>
            <button type="button"
                    onClick={submitShoppingCart}
                    className='btn btn-success'>Submit Order️
            </button>
          </div>
        }
      </Fragment>
  );
}
