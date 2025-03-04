package SODEFEND.SODEFEND.RegisterLayer;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class RegisterModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;
    @Column(name = "isim")
    private String isim;
    @Column(name = "soyisim")
    private String soyisim;
    @Column(name = "password")
    private String password;
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;
    @Column(name = "mail")
    private String mail;
    @Column(name = "telefon")
    private String telefon;

    public RegisterModel(Long id, String isim, String soyisim, String password, String userName, String mail, String telefon) {
        this.id = id;
        this.isim = isim;
        this.soyisim = soyisim;
        this.password = password;
        this.userName = userName;
        this.mail = mail;
        this.telefon = telefon;
    }

    public RegisterModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
