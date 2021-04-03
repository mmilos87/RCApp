package com.example.demo.helpers.classes;

import com.example.demo.exception.JmbgIsNotValidException;
import com.example.demo.helpers.enums.AppMessages;
import com.example.demo.helpers.enums.GenderType;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class UniqueMasterCitizenNumberHelper {

  private String uniqueMasterCitizenNumber;
  private GenderType gender;
  private String stateOfBirth;
  private String regionOfBirth;
  private LocalDateTime dateOfBirth;

  public UniqueMasterCitizenNumberHelper(String uniqueMasterCitizenNumber) throws JmbgIsNotValidException {
 calculateUniqueMasterCitizenNumber(uniqueMasterCitizenNumber);
    dateOfBirth=calculateDateOfBirth(uniqueMasterCitizenNumber);
    gender=calculateGender(uniqueMasterCitizenNumber);
    stateOfBirth=calculateStateRegion(uniqueMasterCitizenNumber)
        .entrySet().stream().findFirst().get().getKey();
    regionOfBirth=calculateStateRegion(uniqueMasterCitizenNumber)
        .entrySet().stream().findFirst().get().getValue();
    this.uniqueMasterCitizenNumber = uniqueMasterCitizenNumber;
  }
  private void calculateUniqueMasterCitizenNumber(String jmbg) throws JmbgIsNotValidException {
    if (jmbg.length()==13) {
      Integer A = Integer.parseInt(jmbg.substring(0, jmbg.length() - 12));
      Integer B = Integer.parseInt(jmbg.substring(1, jmbg.length() - 11));
      Integer C = Integer.parseInt(jmbg.substring(2, jmbg.length() - 10));
      Integer D = Integer.parseInt(jmbg.substring(3, jmbg.length() - 9));
      Integer E = Integer.parseInt(jmbg.substring(4, jmbg.length() - 8));
      Integer F = Integer.parseInt(jmbg.substring(5, jmbg.length() - 7));
      Integer G = Integer.parseInt(jmbg.substring(6, jmbg.length() - 6));
      Integer H = Integer.parseInt(jmbg.substring(7, jmbg.length() - 5));
      Integer I = Integer.parseInt(jmbg.substring(8, jmbg.length() - 4));
      Integer J = Integer.parseInt(jmbg.substring(9, jmbg.length() - 3));
      Integer K = Integer.parseInt(jmbg.substring(10, jmbg.length() - 2));
      Integer L = Integer.parseInt(jmbg.substring(11, jmbg.length() - 1));
      Integer M = Integer.parseInt(jmbg.substring(12, jmbg.length() - 0));
      int checkSum = 11 - ((7 * (A + G) + 6 * (B + H) + 5 * (C + I)
          + 4 * (D + J) + 3 * (E + K) + 2 * (F + L)) % 11);
        if (checkSum != M) {
         throw new JmbgIsNotValidException(AppMessages.JMBG_CHECKSUM_IS_NOT_CORRECT);
        }
      }else {throw new JmbgIsNotValidException(AppMessages.JMBG_IS_TOO_LONG); }
    }

  private LocalDateTime calculateDateOfBirth(String uniqueMasterCitizenNumber)
      throws  JmbgIsNotValidException {
    Integer dayOfBirth = Integer.parseInt(uniqueMasterCitizenNumber
        .substring(0, uniqueMasterCitizenNumber.length() - 11));
    Integer monOfBirth = Integer.parseInt(uniqueMasterCitizenNumber
        .substring(2, uniqueMasterCitizenNumber.length() - 9));
    Integer yearOfBirth = 1000 + Integer.parseInt(uniqueMasterCitizenNumber
        .substring(4, uniqueMasterCitizenNumber.length() - 6));
    LocalDateTime dateTime = LocalDateTime.of(yearOfBirth, monOfBirth, dayOfBirth, 0, 0);
    if(dateTime.plusYears(120).isBefore(LocalDateTime.now())){
      throw new JmbgIsNotValidException(AppMessages.JMBG_DATE_IS_NOT_VALID);
    }
    return dateTime;

  }

  private GenderType calculateGender(String uniqueMasterCitizenNumber) {
    Integer genderNumber = Integer
        .parseInt(uniqueMasterCitizenNumber.substring(9, uniqueMasterCitizenNumber.length() - 1));
    return genderNumber >= 500 ? GenderType.FEMALE : GenderType.MALE;
  }

  private Map<String, String> calculateStateRegion(String uniqueMasterCitizenNumber) {
    Integer regionNumber = Integer.parseInt(uniqueMasterCitizenNumber.substring(7,
        uniqueMasterCitizenNumber.length() - 4));
    Map<String, String> stateRegion = new HashMap<>();
    String state;
    String region;
    switch (regionNumber) {

      case 10:
        state = "Bosna i Hercegovina";
        region = "Banja Luka";
        break;

      case 11:
        state = "Bosna i Hercegovina";
        region = "Bihac";
        break;

      case 12:
        state = "Bosna i Hercegovina";
        region = "Doboj";
        break;

      case 13:
        state = "Bosna i Hercegovina";
        region = "Gorazde";
        break;

      case 14:
        state = "Bosna i Hercegovina";
        region = "Livno";
        break;

      case 15:
        state = "Bosna i Hercegovina";
        region = "Mostar";
        break;

      case 16:
        state = "Bosna i Hercegovina";
        region = "Prijedoro";
        break;

      case 17:
        state = "Bosna i Hercegovina";
        region = "Sarajevo";
        break;

      case 18:
        state = "Bosna i Hercegovina";
        region = "Tuzla";
        break;

      case 19:
        state = "Bosna i Hercegovina";
        region = "Zenica";
        break;

      case 21:
        state = "Crna Gora";
        region = " Podgorica, Danilovgrad, Kolasin";
        break;

      case 22:
        state = "Crna Gora";
        region = "Bar, Ulcinj";
        break;

      case 23:
        state = "Crna Gora";
        region = "Budva, Kotor, Tivat";
        break;

      case 24:
        state = "Crna Gora";
        region = "Herceg Novit";
        break;

      case 25:
        state = "Crna Gora";
        region = "Cetinje";
        break;

      case 26:
        state = "Crna Gora";
        region = " Nikšić, Plužine, Šavnik";
        break;

      case 27:
        state = "Crna Gora";
        region = "Barane, Rozaje, Plav, Andrijevica";
        break;

      case 28:
        state = "Crna Gora";
        region = "Bijelo Polje, Mojkovac";
        break;

      case 29:
        state = "Crna Gora";
        region = "Pljevlja, Zabljak";
        break;

      case 30:
        state = "Hrvatska";
        region = "Osijek, Slavonia";
        break;

      case 31:
        state = "Hrvatska";
        region = "Bjelovar, Virovitica, Koprivnica, Pakrac, Podravina ";
        break;

      case 32:
        state = "Hrvatska";
        region = "Varaždin, Međimurje ";
        break;

      case 33:
        state = "Hrvatska";
        region = "Zagreb";
        break;

      case 34:
        state = "Hrvatska";
        region = "Karlovac, Kordun ";
        break;

      case 35:
        state = "Hrvatska";
        region = " Gospić, Lika";
        break;

      case 36:
        state = "Hrvatska";
        region = "Rijeka, Pula, Gorski kotar, Istria,Croatian Littoral ";
        break;

      case 37:
        state = "Hrvatska";
        region = "Sisak, Banovina";
        break;

      case 38:
        state = "Hrvatska";
        region = " Split, Zadar, Šibenik, Dubrovnik, Dalmatia ";
        break;

      case 39:
        state = "Hrvatska";
        region = "Hrvatsko Zagorje";
        break;

      case 41:
        state = "Makedonija";
        region = "Bitola";
        break;

      case 42:
        state = "Makedonija";
        region = "Kumanovo";
        break;

      case 43:
        state = "Makedonija";
        region = "Ohrid";
        break;

      case 44:
        state = "Makedonija";
        region = "Prilep";
        break;

      case 45:
        state = "Makedonija";
        region = "Skoplje";
        break;

      case 46:
        state = "Makedonija";
        region = "Strumica";
        break;

      case 47:
        state = "Makedonija";
        region = "Tetovo";
        break;

      case 48:
        state = "Makedonija";
        region = "Veles";
        break;

      case 49:
        state = "Makedonija";
        region = "Stip";
        break;

      case 50:
        state = "Slovenija";
        region = "Slovenija";
        break;

      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      case 66:
      case 67:
      case 68:
      case 69:
        state = "Citizens with temporary residence";
        region = "Citizens with temporary residence";
        break;

      case 70:
        state = "Srbija";
        region = "Serbian citizens registered abroad at a Serbian diplomatic/consular post";
        break;
      case 71:
        state = "Srbija";
        region = "Beograd";
        break;

      case 72:
        state = "Srbija";
        region = "Kragujevac, Jagodina";
        break;

      case 73:
        state = "Srbija";
        region = "Nis, Pirot, Toplica";
        break;

      case 74:
        state = "Srbija";
        region = "Leskovac, Vranje";
        break;

      case 75:
        state = "Srbija";
        region = "Bor, Zajecar";
        break;

      case 76:
        state = "Srbija";
        region = "Smederevo, Pozarevac";
        break;

      case 77:
        state = "Srbija";
        region = "Macva, Kolubara";
        break;

      case 78:
        state = "Srbija";
        region = "Kraljevo, Krusevac";
        break;

      case 79:
        state = "Srbija";
        region = "Uzice";
        break;

      case 80:
        state = "Srbija";
        region = "Novi Sad";
        break;

      case 81:
        state = "Srbija";
        region = "Sombor";
        break;

      case 82:
        state = "Srbija";
        region = "Subotica";
        break;

      case 83:
        state = "Srbija";
        region = "Vrbas";
        break;

      case 84:
        state = "Srbija";
        region = "Kikinda";
        break;

      case 85:
        state = "Srbija";
        region = "Zrenjanin";
        break;

      case 86:
        state = "Srbija";
        region = "Pancevo";
        break;

      case 87:
        state = "Srbija";
        region = "Vrsac";
        break;

      case 88:
        state = "Srbija";
        region = "Ruma";
        break;

      case 89:
        state = "Srbija";
        region = "Sremska Mitrovica";
        break;

      case 90:
        state = "Srbija";
        region = "Kosovo";
        break;

      case 91:
        state = "Srbija";
        region = "Pristina";
        break;

      case 92:
        state = "Srbija";
        region = "Kosovksa Mitrovica";
        break;

      case 93:
        state = "Srbija";
        region = "Pec";
        break;

      case 94:
        state = "Srbija";
        region = "Djakovica";
        break;

      case 95:
        state = "Srbija";
        region = "Prizren";
        break;

      case 96:
        state = "Srbija";
        region = "Gnjilane";
        break;

      default:
        state = "Not Known";
        region = "Not Known";
        break;

    }
    stateRegion.put(state, region);
    return stateRegion;
  }


}



