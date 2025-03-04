package SODEFEND.SODEFEND.ServiceLayer;


import SODEFEND.SODEFEND.RegisterLayer.RegisterModel;
import SODEFEND.SODEFEND.RepositoryLayer.RepoInter;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ServiceClass implements ServiceInterface{

    private final RepoInter repoInter;
    private final PasswordEncoder passwordEncoder;

    public ServiceClass(RepoInter repoInter, PasswordEncoder passwordEncoder) {
        this.repoInter = repoInter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<String> Register(RegisterModel registerModel) {
        String pass = registerModel.getPassword();
        String userName = registerModel.getUserName();
        if(repoInter.existsByUserName(userName)) {
            return new ResponseEntity<>("Geçersiz şifre veya kullanıcı adı", HttpStatus.BAD_REQUEST);
        }
        String returnStatement = ValidatePassAndUserName(pass);
        if(!"true".equals(returnStatement)) {
            return new ResponseEntity<>(returnStatement, HttpStatus.BAD_REQUEST);
        }
        String encodedPass = passwordEncoder.encode(pass);
        registerModel.setPassword(encodedPass);
        repoInter.save(registerModel);
        return new ResponseEntity<>("Kullanıcı Eklendi", HttpStatus.CREATED);

    }

    @Override
    public void Login(String password, String userName) {

    }

    @Override
    public String ValidatePassAndUserName(String password) {
        if(password.length() < 8) {
            return "Şifre en az 8 karakterden oluşmalıdır";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Şifre en az bir büyük karakter içermelidir";
        }
        if (!password.matches(".*[0-9].*")) {
            return "Şifre en az bir sayı içermelidir";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Şifre en az bir küçük harf içermelidir";
        }
        if (!password.matches(".*[\\W_].*")) {
            return "Şifre en az bir özel karakter içermelidir";
        }
        return "true";
    }

    @Override
    public void DeleteUserByUserName(String userName) {
        RegisterModel registerModel = repoInter.findByUserName(userName).orElseThrow(() ->
                new RuntimeException("Kullanıcı adı bulunamadı"));
        repoInter.deleteByUserName(userName);
    }

    @Override
    public ResponseEntity<String> UpdatePassword(String password, String userName) {
        Optional<RegisterModel> registerModel1 = repoInter.findByUserName(userName);

        if(registerModel1.isEmpty()) {
            return new ResponseEntity<>("Kullanıcı adı bulunamadı", HttpStatus.NOT_FOUND);
        }
        RegisterModel user = registerModel1.get();
        String returnState = ValidatePassAndUserName(password);
        if(!"true".equals(returnState)) {
            return new ResponseEntity<>(returnState, HttpStatus.BAD_REQUEST);
        }
        String newPass = passwordEncoder.encode(password);
        user.setPassword(newPass);
        repoInter.save(user);
        return new ResponseEntity<>("Şifre güncellendi", HttpStatus.OK);
    }
}
