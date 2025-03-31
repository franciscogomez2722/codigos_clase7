package com.interfazgrafica.version1.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class FirebaseServiceUser {

    // Método para guardar los datos del usuario en Firebase
    /*collectionName (String)

        Es el nombre de la colección en la base de datos donde se guardarán los datos.

        En el contexto de Firebase Firestore o bases de datos NoSQL, una colección es como una "tabla" que agrupa documentos.

        userData (Map<String, Object>)

        Es un mapa de claves y valores donde:

        La clave (String) representa el nombre de un campo.

        El valor (Object) puede ser cualquier tipo de dato como String, Integer, Boolean, List, etc. */

    /*Map<String, Object> user = new HashMap<>();
        user.put("name", "Juan");
        user.put("email", juanis123@gmail.com);
        user.put("password", "*********");

        saveData("users", user);
 */
    public void saveData(String collectionName, Map<String, Object> userData) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();  // Obtiene la instancia de Firestore
        db.collection(collectionName)                    // Selecciona la colección de usuarios
          .add(userData)                                 // Agrega los datos del usuario
          .get();                                        // Espera a que la operación se complete
    }

    // Método para obtener todos los usuarios desde Firebase
    public List<Map<String, Object>> getUsers(String collectionName) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();  // Obtiene la instancia de Firestore
        List<Map<String, Object>> userList = new ArrayList<>();
        
        // Realiza la consulta para obtener todos los documentos de la colección de usuarios
        ApiFuture<QuerySnapshot> future = db.collection(collectionName).get();
        QuerySnapshot querySnapshot = future.get();
        
        // Recorre los documentos obtenidos y los agrega a la lista
        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
            userList.add(document.getData());
        }
        return userList;
    }

    // Método para obtener los datos de un usuario específico por su ID
    public Map<String, Object> getUserById(String collectionName, String documentId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();  // Obtiene la instancia de Firestore
        return db.collection(collectionName).document(documentId).get().get().getData();  // Obtiene los datos del documento
    }
}
