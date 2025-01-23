package ed.cripto.okx_cripto.controller;

import ed.cripto.okx_cripto.entity.User;
import ed.cripto.okx_cripto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Cria um novo usuário.
     * Endpoint: POST /api/users
     * Exemplo de body (JSON):
     * {
     *   "name": "João",
     *   "email": "joao@example.com",
     *   "password": "123456"
     * }
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Obtém usuário por ID.
     * Endpoint: GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User foundUser = userService.getUserById(id);
        if (foundUser != null) {
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Atualiza dados de um usuário por ID.
     * Endpoint: PUT /api/users/{id}
     * Exemplo de body (JSON):
     * {
     *   "name": "João da Silva",
     *   "email": "joaosilva@example.com",
     *   "password": "novaSenha"
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User userDetails) {
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
            existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPassword(userDetails.getPassword());
            User updatedUser = userService.updateUser(existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deleta um usuário por ID.
     * Endpoint: DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Realiza login do usuário e retorna o walletId.
     * Endpoint: POST /api/users/login
     * Exemplo de body (JSON):
     * {
     *   "email": "joao@example.com",
     *   "password": "123456"
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            // Retorna o walletId e uma mensagem de sucesso
            LoginResponse response = new LoginResponse(user.getWallet().getKeyId(), "Login bem-sucedido!");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Credenciais inválidas.");
    }

    // DTO para requisição de login
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // DTO para resposta de login
    public static class LoginResponse {
        private UUID walletId;
        private String message;

        public LoginResponse(UUID walletId, String message) {
            this.walletId = walletId;
            this.message = message;
        }

        public UUID getWalletId() {
            return walletId;
        }

        public void setWalletId(UUID walletId) {
            this.walletId = walletId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
