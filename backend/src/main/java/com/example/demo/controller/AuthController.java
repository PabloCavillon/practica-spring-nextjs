package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import com.example.demo.util.JwtUtil;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuarioRequest) {
        if (usuarioService.findByUsername(usuarioRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(null); // Usuario ya existe
        }
        Usuario savedUsuario = usuarioService.createUsuario(usuarioRequest);
        return ResponseEntity.ok(savedUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario loginRequest) {
        Optional<Usuario> usuario = usuarioService.findByUsername(loginRequest.getUsername());
        if (usuario.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), usuario.get().getPassword())) {
            String token = jwtUtil.generateToken(
                loginRequest.getUsername(),
                usuario.get().getRole().toString()    
            );
            return ResponseEntity.ok(token); // Devuelve el token
        }
        return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body("Token no válido");
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.isTokenValid(token, jwtUtil.extractUsername(token))) {
            return ResponseEntity.status(401).body("Token no válido");
        }

        String refreshedToken = jwtUtil.refreshToken(token);
        return ResponseEntity.ok(refreshedToken);
    }
}

