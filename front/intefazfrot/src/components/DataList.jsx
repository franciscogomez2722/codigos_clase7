import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

const DataList = () => {
  const [data, setData] = useState([]); // Estado para almacenar los datos

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws"); // Usa SockJS
    const client = new Client({
      webSocketFactory: () => socket, // Usa SockJS como fábrica de WebSocket
      onConnect: () => {
        console.log("Conexión establecida con el WebSocket");
        // Suscribirse al tema "/topic/data"
        client.subscribe("/topic/data", (message) => {
          const newData = JSON.parse(message.body); // Convertir el mensaje a JSON
          setData((prevData) => [...prevData, newData]); // Agregar los nuevos datos al estado
        });
      },
      onDisconnect: () => {
        console.log("Desconectado del WebSocket");
      },
      onStompError: (error) => {
        console.error("Error en la conexión WebSocket:", error);
      },
    });

    // Activar el cliente
    client.activate();

    // Limpiar la conexión al desmontar el componente
    return () => {
      client.deactivate();
    };
  }, []);

  return (
    <div>
      <h1>Lista de datos en tiempo real</h1>
      <ul>
        {data.map((item, index) => (
          <li key={index}>
            {item.name} - {item.email} - {item.age}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default DataList;