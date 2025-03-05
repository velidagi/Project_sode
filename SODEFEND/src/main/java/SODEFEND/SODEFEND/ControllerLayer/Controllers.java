package SODEFEND.SODEFEND.ControllerLayer;


import SODEFEND.SODEFEND.RegisterLayer.RegisterModel;
import SODEFEND.SODEFEND.ServiceLayer.ServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import SODEFEND.SODEFEND.RepositoryLayer.RepoInter;
import SODEFEND.SODEFEND.Secuirty.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

@RestController
public class Controllers {
    private final ServiceInterface servInter;

    @Autowired
    private RepoInter repoInter;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public Controllers(ServiceInterface servInter) {
        this.servInter = servInter;
    }
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/api/public/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        try {
            String username = loginData.get("userName");
            String password = loginData.get("password");

            if (username == null || password == null) {
                return new ResponseEntity<>("Kullanıcı adı veya şifre eksik", HttpStatus.BAD_REQUEST);
            }

            Optional<RegisterModel> optionalUser = repoInter.findByUserName(username);
            if (optionalUser.isEmpty()) {
                return new ResponseEntity<>("Kullanıcı bulunamadı", HttpStatus.UNAUTHORIZED);
            }

            RegisterModel user = optionalUser.get();
            // Şifre karşılaştır
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new ResponseEntity<>("Kullanıcı adı veya şifre hatalı", HttpStatus.UNAUTHORIZED);
            }

            // Doğruysa token üret
            String token = jwtUtil.generateToken(username);

            // Geriye token döndürebilirsin (JSON formatında)
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return new ResponseEntity<>("Bir hata oluştu: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/api/public/addUsers")
    public ResponseEntity<String> RegisterUser(@RequestBody RegisterModel registerModel){
       return servInter.Register(registerModel);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @DeleteMapping("/api/public/deleteUser")
    public ResponseEntity<String> DeleteUser(@RequestBody Map<String, String> body){
        try{
            String userName = body.get("userName");
            servInter.DeleteUserByUserName(userName);
            return new ResponseEntity<>("Kullanıcı silindi", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PutMapping("/api/public/UpdatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        String newPass = body.get("password");
        if (userName == null || userName.isEmpty()) {
            return new ResponseEntity<>("Kullanıcı adı boş olamaz", HttpStatus.BAD_REQUEST);
        }

        if (newPass == null || newPass.isEmpty()) {
            return new ResponseEntity<>("Şifre boş olamaz", HttpStatus.BAD_REQUEST);
        }
        return servInter.UpdatePassword(newPass, userName);

    }
}
