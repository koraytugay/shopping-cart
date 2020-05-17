import React, {useState} from "react";
import axios from "axios";

export default function ShoppingCartProductRow({product, count, setSuccessAlert, getShoppingCart}) {
  const [productCount, setProductCount] = useState(count);

  function updateProductCount(count) {
    axios.post(`/api/shoppingCart/${product.sku}/${count}`)
        .then(() => {
          setProductCount(productCount + count);
          getShoppingCart();
        });
  }

  return (
      <tr>
        <td style={{paddingTop: '20px', width: '250px'}}>{product.name}</td>
        <td style={{width: '15px'}}>
          <button className='btn btn-outline-danger'
                  onClick={() => updateProductCount(-1)}
                  disabled={productCount === 1}>
-
          </button>
        </td>
        <td style={{width: '60px', paddingTop: '18px', paddingLeft: '25px'}}>
          <span>{productCount}</span>
        </td>
        <td>
          <button className='btn btn-outline-primary'
                  onClick={() => updateProductCount(1)}>
            +
          </button>
        </td>
        <td>
          <button type="button"
                  onClick={() => updateProductCount(-productCount)}
                  className='btn'>
            <span>&#x1F5D1;</span>️ {/* Waste basket */}
          </button>
        </td>
      </tr>
  );
};
