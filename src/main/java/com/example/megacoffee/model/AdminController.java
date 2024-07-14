package com.example.megacoffee.model;


import com.example.megacoffee.dto.ApiResponse1;
import com.example.megacoffee.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminDetailsService adminDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/api/admin/register")
    public ResponseEntity<String> register(@RequestBody Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        try {
            adminService.saveAdmin(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding car");
        }
    }

    @PostMapping("/api/admin/login")
    public ResponseEntity<ApiResponse1<String>> login(@RequestBody Admin admin, HttpServletResponse response) {
        try {
            UserDetails userDetails = adminDetailsService.loadUserByUsername(admin.getAdminId());

            if (userDetails == null || !passwordEncoder.matches(admin.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid admin ID or password");
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = JwtUtil.generateToken(admin);

            // JWT를 응답 헤더에 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwt);

            ApiResponse1<String> apiResponse = new ApiResponse1<>("success", 200, 1, "Login successful");
            return ResponseEntity.ok().headers(headers).body(apiResponse);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            ApiResponse1<String> apiResponse = new ApiResponse1<>("error", 401, 0, "Invalid admin ID or password");
            return ResponseEntity.status(401).body(apiResponse);
        } catch (Exception e) {
            ApiResponse1<String> apiResponse = new ApiResponse1<>("error", 500, 0, "Error during login");
            return ResponseEntity.status(500).body(apiResponse);
        }
    }

}
