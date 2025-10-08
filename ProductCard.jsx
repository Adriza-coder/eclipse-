import React from "react";

const ProductCard = ({ product, addToCart, isAdmin, editProduct, deleteProduct }) => {
  return (
    <div className="border rounded p-4 shadow hover:shadow-lg transition">
      <img src={product.image} alt={product.name} className="h-40 w-full object-cover mb-2" />
      <h3 className="font-bold">{product.name}</h3>
      <p className="text-gray-700">${product.price}</p>
      <div className="mt-2 flex space-x-2">
        {!isAdmin && (
          <button
            onClick={() => addToCart(product)}
            className="bg-blue-500 text-white px-2 py-1 rounded hover:bg-blue-600"
          >
            Add to Cart
          </button>
        )}
        {isAdmin && (
          <>
            <button
              onClick={() => editProduct(product)}
              className="bg-yellow-500 text-white px-2 py-1 rounded hover:bg-yellow-600"
            >
              Edit
            </button>
            <button
              onClick={() => deleteProduct(product.id)}
              className="bg-red-500 text-white px-2 py-1 rounded hover:bg-red-600"
            >
              Delete
            </button>
          </>
        )}
      </div>
    </div>
  );
};

export default ProductCard;
