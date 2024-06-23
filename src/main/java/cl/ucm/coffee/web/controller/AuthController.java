package cl.ucm.coffee.web.controller;

import cl.ucm.coffee.persitence.entity.UserEntity;
import cl.ucm.coffee.service.IUserService;
import cl.ucm.coffee.service.UserSecurityService;
import cl.ucm.coffee.service.dto.LoginDto;
import cl.ucm.coffee.web.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtUtil jwtUtil;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private IUserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);

        UserDetails userDetails = userSecurityService.loadUserByUsername(loginDto.getUsername());

        String role = userDetails.getAuthorities().stream()
                .filter(auth -> auth.getAuthority().startsWith("ROLE_"))
                .findFirst()
                .map(auth -> auth.getAuthority().substring(5))
                .orElse("USER"); // Default role

        String jwt = this.jwtUtil.create(loginDto.getUsername(), role);

        Map map = new HashMap<>();
        map.put("token",jwt);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getUsers(){
        try {
            return ResponseEntity.ok(userService.getUsers());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody UserEntity userEntity) {
        try {
            UserEntity savedUser = userService.registerUser(userEntity);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/update/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserEntity userEntity) {
        try {
            UserEntity updatedUser = userService.updateUser(username, userEntity);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
            Optional<UserEntity> user = userService.getUser(username);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(404).body("Usuario no encontrado.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

}
