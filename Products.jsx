import React, { useEffect, useState } from "react";
import api from "../api/axiosConfig";
import ProductCard from "../components/ProductCard";

const Products = () => {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);
  const role = localStorage.getItem("role");

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const res = await api.get("/products");
      setProducts(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const addToCart = (product) => {
    setCart([...cart, product]);
    alert(`${product.name} added to cart`);
  };

  const editProduct = (product) => {
    alert(`Redirect to edit product: ${product.name}`);
  };

  const deleteProduct = async (id) => {
    if (window.confirm("Are you sure to delete this product?")) {
      try {
        await api.delete(`/products/${id}`);
        fetchProducts();
      } catch (err) {
        console.error(err);
      }
    }
  };

  return (
    <div className="p-4">
      <h2 className="text-2xl font-bold mb-4">Products</h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
        {products.map((product) => (
          <ProductCard
            key={product.id}
            product={product}
            addToCart={addToCart}
            isAdmin={role === "ADMIN"}
            editProduct={editProduct}
            deleteProduct={deleteProduct}
          />
        ))}
      </div>
    </div>
  );
};

export default Products;
