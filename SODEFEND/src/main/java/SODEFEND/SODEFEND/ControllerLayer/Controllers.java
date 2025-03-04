package SODEFEND.SODEFEND.ControllerLayer;


import SODEFEND.SODEFEND.RegisterLayer.RegisterModel;
import SODEFEND.SODEFEND.ServiceLayer.ServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class Controllers {
    private final ServiceInterface servInter;

    public Controllers(ServiceInterface servInter) {
        this.servInter = servInter;
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
