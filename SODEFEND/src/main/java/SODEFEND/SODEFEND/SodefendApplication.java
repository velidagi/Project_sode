package SODEFEND.SODEFEND;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SodefendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SodefendApplication.class, args);
	}

}


/*

1. kullanici kayit olma API { tamamlandi }
2. sifre sifirlama API { tamamlandi }

transfer guvenligi icin
@Pathvariable yerine @RequestBody kullanildi
bunun sayesiyle veriler JSON body olarak gonderilecektir { SQL injection'a karsi bir onlem }
sifre kriptolama da kullanildi
ozel sifre olusturma fonksiyonu da kullanildi { en az 8 karakterli sifre istenmesi....vs }

Geri Kalan :

1. JWT ve Login
2. Mail ile dogrulama



 */