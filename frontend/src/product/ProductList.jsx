import React, {Fragment, useEffect, useState} from "react";
import axios from "axios";
import ProductListRow from "./ProductListRow";

export default function ProductList({history, setSuccessAlert, setCurrentTab}) {
  const [allProducts, setAllProducts] = useState([]);

  useEffect(() => {
    getAllProducts();
    setSuccessAlert('');
    setCurrentTab('productList');
  }, [setSuccessAlert, setCurrentTab]);

  function getAllProducts() {
    axios.get('/api/products')
        .then(response => {
          setAllProducts(response.data);
        });
  }

  return (
      <Fragment>
        <table className="table">
          <thead>
          <tr>
            <th>Product</th>
            <th>Details</th>
            <th/>
            <th/>
          </tr>
          </thead>
          <tbody>
          {allProducts.sort((a, b) => {
            return (a.name).localeCompare(b.name);
          }).map(product => {
            return <ProductListRow key={product.sku}
                                   setSuccessAlert={setSuccessAlert}
                                   product={product}
                                   history={history}/>
          })}
          </tbody>
        </table>
      </Fragment>
  );
}
