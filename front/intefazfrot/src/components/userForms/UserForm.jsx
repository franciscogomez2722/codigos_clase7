import React, { useState } from "react";
import "./UserForm.css"; // Asegúrate de que esta ruta sea correcta

const UserForm = () => {
  const [formData, setFormData] = useState({ name: "", email: "", password: "" });
  const [loading, setLoading] = useState(false); // Estado para manejar el cargado de la solicitud

  // Manejar cambios en los inputs
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // Enviar datos al backend
  const handleSubmit = async (e) => {
    e.preventDefault(); // Previene el comportamiento por defecto de recargar la página

    try {
      setLoading(true); // Inicia el cargado
      const { name, email, password } = formData;
      const url = "http://localhost:8080/users"; // La URL de tu API para guardar el usuario

      const response = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password }), // Envía nombre, correo y contraseña
      });

      if (response.ok) {
        alert("Usuario agregado con éxito");
        setFormData({ name: "", email: "", password: "" }); // Limpiar formulario
      } else {
        alert("Error al agregar usuario");
      }
    } catch (error) {
      console.error("Error:", error);
      alert("Hubo un error en la conexión con el servidor.");
    } finally {
      setLoading(false); // Detiene el cargado
    }
  };

  return (
    <div className="about-container">
      <h2>Registrar Usuario</h2>

      {/* Formulario para enviar los datos */}
      <form onSubmit={handleSubmit}>
        {/* Campo de nombre */}
        <input
          type="text"
          name="name"
          placeholder="Nombre"
          value={formData.name}
          onChange={handleChange}
        />

        {/* Campo de correo electrónico */}
        <input
          type="email"
          name="email"
          placeholder="Correo Electrónico"
          value={formData.email}
          onChange={handleChange}
        />

        {/* Campo de contraseña */}
        <input
          type="password"
          name="password"
          placeholder="Contraseña"
          value={formData.password}
          onChange={handleChange}
        />

        {/* Botón de envío */}
        <button type="submit" disabled={loading}>
          {loading ? "Enviando..." : "Registrar"}
        </button>
      </form>
    </div>
  );
};

export default UserForm;
