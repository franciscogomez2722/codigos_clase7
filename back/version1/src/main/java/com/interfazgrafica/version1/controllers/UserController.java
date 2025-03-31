package com.interfazgrafica.version1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.interfazgrafica.version1.services.FirebaseServiceUser;  // Cambié el nombre del servicio

import java.util.concurrent.ExecutionException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173") // Permitir peticiones desde React
public class UserController {

    @Autowired
    private FirebaseServiceUser firebaseServiceUser;  // Inyección de Firebase

    // Endpoint para crear un nuevo usuario
    @PostMapping
    public String createUser(@RequestBody Map<String, Object> user) throws ExecutionException, InterruptedException {
        String collectionName = "users"; //Selecionamos la coleccion que utilizaremos
        
        // Guardar el nombre, correo electrónico y contraseña
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", user.get("name"));
        userData.put("email", user.get("email"));
        userData.put("password", user.get("password")); //La encriptaremos con BCrypt ;)

        /*Algoritmo	Seguro para contraseñas	Genera Salt	Resistente a fuerza bruta
            BCrypt	✅ Sí	✅ Sí	✅ Sí (lento por diseño)
            SHA-256	❌ No (rápido)	❌ No	❌ No (puede romperse con GPUs)
            MD5	❌ No (muy rápido)	❌ No	❌ No (vulnerable a ataques de diccionario) */

        firebaseServiceUser.saveData(collectionName, userData);  // Guardar los datos con el metodo de firebaseServiceUser
        return "Usuario agregado exitosamente";
    }

    // Endpoint para probar el guardado de un usuario
    @GetMapping("/user-save/{name}")
    public String testSaveData(
            @PathVariable String name, // Nombre que se envía en la URL
            @RequestParam String email, // Email como parámetro de la URL
            @RequestParam String password // Contraseña como parámetro de la URL
    ) throws Exception {
        String collectionName = "users";

        // Crear un mapa para simular el cuerpo de la solicitud con el nombre, correo y contraseña
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("email", email);
        data.put("password", password);

        firebaseServiceUser.saveData(collectionName, data);  // Usamos firebaseServiceUser
        return "Data saved successfully for user: " + name;
    }

    // Nuevo método para obtener todos los usuarios desde Firebase
    @GetMapping
    public List<Map<String, Object>> getUsers() throws ExecutionException, InterruptedException {
        String collectionName = "users";
        return firebaseServiceUser.getUsers(collectionName);  // Usamos firebaseServiceUser
    }
}
