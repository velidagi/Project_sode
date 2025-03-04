package SODEFEND.SODEFEND.ServiceLayer;


import SODEFEND.SODEFEND.RegisterLayer.RegisterModel;
import org.springframework.http.ResponseEntity;

public interface ServiceInterface{
    ResponseEntity<String> Register(RegisterModel registerModel); // POST
    void Login(String password, String userName); // POST
    String ValidatePassAndUserName(String password); // Not API
    void DeleteUserByUserName(String userName);
    ResponseEntity<String> UpdatePassword(String password, String userName);

}