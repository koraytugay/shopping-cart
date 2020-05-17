import React, {Fragment, useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";

export default function OrderConfirmation() {

  let {orderId} = useParams();

  const [orderDetails, setOrderDetails] = useState([]);

  useEffect(() => {
    axios.get(`/api/orders/${orderId}`)
        .then(response => {
          setOrderDetails(response.data);
        });
  }, [orderId]);

  return <Fragment>
    <div>
      Order Number: {orderId}
    </div>
    <table className="table">
      <thead>
      <tr>
        <th>Product</th>
        <th>Count</th>
      </tr>
      </thead>
      <tbody>
      {orderDetails.sort((a, b) => {
        return (a.productName).localeCompare(b.productName);
      }).map(orderDetail => {
        return <tr key={orderDetail.productName}>
          <td>{orderDetail.productName}</td>
          <td>{orderDetail.itemCount}</td>
        </tr>
      })}
      </tbody>
    </table>
  </Fragment>;
}
