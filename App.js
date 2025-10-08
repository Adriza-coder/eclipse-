import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import ProtectedRoute from "./components/ProtectedRoute";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Products from "./pages/Products";
import Admin from "./pages/Admin";

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Products route accessible to both USER and ADMIN */}
        <Route
          path="/products"
          element={
            <ProtectedRoute roles={["USER", "ADMIN"]}>
              <Products />
            </ProtectedRoute>
          }
        />

        {/* Admin route only for ADMIN */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute roles={["ADMIN"]}>
              <Admin />
            </ProtectedRoute>
          }
        />

        {/* Catch-all: redirect to products */}
        <Route path="*" element={<ProtectedRoute roles={["USER","ADMIN"]}><Products /></ProtectedRoute>} />
      </Routes>
    </Router>
  );
}

export default App;
