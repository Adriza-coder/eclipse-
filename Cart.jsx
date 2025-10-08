import React from "react";

const Cart = ({ cart }) => {
  const total = cart.reduce((sum, item) => sum + item.price, 0);

  return (
    <div className="p-4 border rounded shadow-md">
      <h3 className="font-bold text-xl mb-2">Shopping Cart</h3>
      {cart.length === 0 ? (
        <p>No items in cart</p>
      ) : (
        <>
          <ul>
            {cart.map((item, idx) => (
              <li key={idx} className="flex justify-between mb-1">
                <span>{item.name}</span>
                <span>${item.price}</span>
              </li>
            ))}
          </ul>
          <div className="mt-2 font-bold">Total: ${total}</div>
          <button className="bg-blue-500 text-white px-4 py-2 mt-2 rounded hover:bg-blue-600">
            Checkout
          </button>
        </>
      )}
    </div>
  );
};

export default Cart;
