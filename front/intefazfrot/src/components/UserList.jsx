import React, { useState, useEffect } from "react";

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  // Obtener la lista de usuarios desde el servidor
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await fetch("http://localhost:8080/users"); // URL del servidor Spring Boot
        if (response.ok) {
          const data = await response.json();
          setUsers(data);
        } else {
          alert("Error al obtener usuarios");
        }
      } catch (error) {
        console.error("Error:", error);
        alert("Hubo un error al obtener los usuarios");
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []); // El array vacío hace que esto solo se ejecute una vez cuando el componente se monta

  return (
    <div>
      <h2>Lista de Usuarios</h2>

      {loading ? (
        <p>Cargando...</p>
      ) : (
        <ul>
          {users.map((user, index) => (
            <li key={index}>
              <strong>{user.name}</strong> - {user.email}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default UserList;
