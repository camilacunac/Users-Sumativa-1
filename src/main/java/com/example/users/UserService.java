package com.example.users;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private Map<String, User> users = new HashMap<>();
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService() {
        User user1 = new User("prueba@duoc.cl", encryptPassword("contraseniA1@"), "admin",
                "Calle hierba buena 132");
        User user2 = new User("prueba2@duoc.cl", encryptPassword("contraseniA2@"), "customer",
                "Calle san francisco 86");
        User user3 = new User("prueba3@duoc.cl", encryptPassword("contraseniA3@"), "customer",
                "Calle baquedano 57");
        users.put(user1.getEmail(), user1);
        users.put(user2.getEmail(), user2);
        users.put(user3.getEmail(), user3);
    }

    public Collection<User> getAll() {
        return users.values();
    }

    public Response registerUser(User user) {
        Response res;
        try {
            if (users.containsKey(user.getEmail())) {
                throw new IllegalArgumentException("El email ya está registrado");
            }

            if (!isValidEmail(user.getEmail())) {
                throw new IllegalArgumentException("El email ingresado no es valido");
            }

            if (!isValidPassword(user.getPassword())) {
                throw new IllegalArgumentException(
                        "La contraseña debe tener al menos 8 caracteres, 1 mayúscula, 1 minúscula y 1 caracter especial");
            }

            if (!isValidRole(user.getRole())) {
                throw new IllegalArgumentException(
                        "El rol ingresado no es valido");
            }

            String contraseñaEncriptada = encryptPassword(user.getPassword());
            user.setPassword(contraseñaEncriptada);

            users.put(user.getEmail(), user);
            res = new Response("success", user, "");
        } catch (IllegalArgumentException e) {
            res = new Response("error", "", e.getMessage());
        } catch (Exception e) {
            res = new Response("error", "", "Error al procesar la solicitud");
        }
        return res;

    }

    public Response login(String email, String password) {
        User user = users.get(email);
        if (user != null) {
            String encriptedPass = user.getPassword();
            if (passwordEncoder.matches(password, encriptedPass)) {
                return new Response("success", user, "");
            } else {
                return new Response("error", "", "contraseña incorrecta");
            }
        }
        return new Response("error", "", "Email o contraseña incorrecto");
    }

    private String encryptPassword(String contraseña) {
        return passwordEncoder.encode(contraseña);
    }

    public boolean isValidRole(String role) {
        String lowerCaseRole = role.toLowerCase();
        return lowerCaseRole.equals("admin") || lowerCaseRole.equals("customer");
    }

    public boolean isValidEmail(String email) {
        // Patrón regex para validar la estructura básica del correo electrónico
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}
