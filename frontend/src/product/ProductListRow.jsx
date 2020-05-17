import React, {useState} from "react";
import axios from "axios";

export default function ProductListRow({product, setSuccessAlert}) {

  const [productCount, setProductCount] = useState('');

  function addItemHandler(event) {
    event.preventDefault();

    axios.post(`/api/shoppingCart/${product.sku}/${productCount}`)
        .then(() => {
          setProductCount('');
          setSuccessAlert(`Added ${productCount} ${product.name}(s) to your cart!`);
        });
  }

  function updateProductCount(count) {
    if (count === '' || count > 0) {
      setProductCount(count);
    }
  }

  return (
      <tr>
        <td style={{paddingTop: '20px'}}>{product.name}</td>
        <td style={{paddingTop: '20px'}}>{product.description}</td>
        <td style={{width: '120px'}}>
          <input className='form-control'
                 onChange={event => updateProductCount(event.target.value)}
                 placeholder=''
                 value={productCount}/>
        </td>
        <td>
          <button type="button"
                  disabled={!(productCount > 0)}
                  className='btn btn-primary'
                  onClick={addItemHandler}>
            Add Item
          </button>
        </td>
      </tr>
  );
};
