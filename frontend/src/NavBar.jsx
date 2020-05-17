import React, {Fragment} from "react";

import {Link} from "react-router-dom";

// This is the navigation bar component
export default function NavBar({setSuccessAlert, successAlert, currentTab}) {
  let alert =
      <div className="alert alert-success alert-dismissible fade show" role="alert">
        {successAlert}
        <button type="button"
                onClick={() => {
                  setSuccessAlert('')
                }}
                className="close"
                aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>;

  return (
      <Fragment>
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav mr-auto">
              <li className={"nav-item" + ((currentTab === 'productList') ? " active" : "")}>
                <Link className="nav-link" to="/products">Products</Link>
              </li>
            </ul>
            <ul className="navbar-nav ml-auto">
              <li className={"nav-item" + ((currentTab === 'shoppingCart') ? " active" : "")}>
                <Link className="nav-link" to="/shoppingCart">Shopping Cart</Link>
              </li>
            </ul>
          </div>
        </nav>
        <br/>
        {successAlert && successAlert !== '' && alert}
      </Fragment>
  );
}
