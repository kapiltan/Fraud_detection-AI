package com.fraudintel.fraudDetection.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraudintel.fraudDetection.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final JwtService jwtService;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public static record GoogleAuthRequest(String accessToken) {
    }

    @PostMapping("/google")
    public ResponseEntity<?> login(@RequestBody GoogleAuthRequest request) throws Exception {
        if (request == null || request.accessToken() == null) {
            return ResponseEntity.badRequest().body("accessToken required");
        }

        HttpRequest userinfoReq = HttpRequest.newBuilder()
                .uri(URI.create("https://www.googleapis.com/oauth2/v3/userinfo"))
                .header("Authorization", "Bearer " + request.accessToken())
                .GET()
                .build();

        HttpResponse<String> userinfosRes = httpClient.send(userinfoReq, HttpResponse.BodyHandlers.ofString());
        System.out.println("Google userinfo response: " + userinfosRes.body());
        if (userinfosRes.statusCode() != 200) {
            return ResponseEntity.status(401).body("Invalid Google Token");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(userinfosRes.body());

        String email = json.has("email") ? json.get("email").asText() : "unknown@example.com";
        String name = json.has("name") ? json.get("name").asText() : "";

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", java.util.List.of("ROLE_USER"));
        claims.put("name", name);

        String jwt = jwtService.generateToken(email, claims);
        Map<String, String> resp = new HashMap<>();
        resp.put("jwt", jwt);
        resp.put("email", email);
        resp.put("name", name == null ? "" : name);

        System.out.println("Generated JWT = " + jwt);

        return ResponseEntity.ok(resp);
    }

    private String extractClaim(String key, String json) {
        String needle = "\"" + key + "\":\"";
        int idx = json.indexOf(needle);
        if (idx == -1)
            return null;
        int start = idx + needle.length();
        int end = json.indexOf("\"", start);

        if (end == -1)
            return null;
        return json.substring(start, end);
    }
}
