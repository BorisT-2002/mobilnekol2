# Kolokvijum2 - Uputstvo za Postavljanje

Detaljno uputstvo za postavljanje i pokretanje Android projekta.

## Preduslov

- Android Studio (verzija 4.1+)
- Java Development Kit (JDK) verzija 11+
- Android SDK 33+
- Gradle 7.0+

## Instalacija

### 1. Otvaranje Projekta

1. Otvorite Android Studio
2. Izaberite "Open an existing project"
3. Navigirajte do `c:\Users\Dusan\Desktop\projekat` i kliknite "Open"
4. Čekajte da Android Studio indeksira fajlove (može potrajati 1-2 minute)

### 2. Sinhronizacija Gradle-a

1. Android Studio će automatski početi sa download-om potrebnih dependencija
2. Ako se ne desilo automatski, idite u `File > Sync Now`
3. Čekajte da se svi download-i završe

### 3. Konfiguracija SDK-a

Ako dobijate grešku da nedostaje SDK:

1. Idite u `File > Settings > Appearance & Behavior > System Settings > Android SDK`
2. Instalirajte SDK Level 33 (ako nije već instaliran)
3. Kliknite "Apply" i "OK"

## Pokretanje Aplikacije

### Option 1: Na Fizičkom Uređaju

1. Omogućite Developer Mode na vašem Android uređaju:
   - Otvorite Settings
   - Idite na About phone
   - Kliknite na Build number 7 puta
   - Vratite se u Settings i otvorite Developer options
   - Omogućite "USB debugging"

2. Spojite uređaj sa USB kablom

3. U Android Studio:
   - Kliknite na "Run" ili pritisnite `Shift + F10`
   - Izaberite svoj uređaj iz liste
   - Kliknite "OK"

### Option 2: Na Emulatoru

1. Otvorite Android Virtual Device Manager:
   - Idite u `Tools > Device Manager`
   - Kliknite "+ Create Device"
   - Izaberite model telefona
   - Izaberite Android 13 (API 33) sliku
   - Kliknite "Finish"

2. U Android Studio:
   - Kliknite na "Run" ili pritisnite `Shift + F10`
   - Izaberite emulator iz liste
   - Emulator će se pokrenuti ako nije već pokrenut

## Testiranje Funkcionalnosti

Nakon pokretanja aplikacije:

### 1. Proximity Senzor Test
- Pristupite svom prstom do gornjeg dela telefona
- Trebalo bi da vidite promene u TextView-u
- Kada dovoljno udaljenite prst, trebalo bi da vidite "Daleko!" Toast poruku

### 2. Kamera Test
- Kliknite na dugme "Slikaj"
- Sistem će tražiti pristup kameri - kliknite "Allow"
- Fotografija će biti slika sa kamere
- Kliknite na dugme za snimanje
- Fotografija bi trebala da se pojavi u ImageView-u

### 3. Retrofit API Test
- Kliknite na prvi CheckBox "Dobavi uloge"
- Aplikacija će pozvati API https://app.beeceptor.com/mock-server/dummy-json
- Trebalo bi da vidite Toast poruku "Uloge sa parnim ID-om sačuvane"
- Uloge će biti sačuvane u lokalnoj bazi (samo one sa parnim ID-om)

### 4. Baza Podataka Test
- Kliknite na drugi CheckBox "Prikaži opis"
- Trebalo bi da se prikaže opis uloge sa najvećim ID-om iz baze
- Trebalo bi da vidite ID, naslov i opis u drugom TextView-u

## Česte Greške i Rešenja

### Greška: "Gradle sync failed"
**Rešenje:**
1. Idite u `File > Invalidate Caches > Invalidate and Restart`
2. Kliknite "Invalidate and Restart"
3. Android Studio će se restartati i ponovo indeksirati

### Greška: "SDK not found"
**Rešenje:**
1. Idite u `File > Settings > Android SDK`
2. Kliknite na "SDK Manager"
3. Instalirajte Android 13 (API 33)
4. Kliknite "Apply" i "OK"

### Greška: "No connected devices"
**Rešenje:**
1. Proverite da li je uređaj/emulator priključen
2. Ako koristite fizički uređaj, proverite da je "USB debugging" omogućen
3. Pokrenite `adb devices` u terminalu da vidite listu uređaja

### Greška: "Cannot connect to API"
**Rešenje:**
1. Proverite da je uređaj/emulator povezan na internet
2. Proverite da je `INTERNET` dozvola omogućena u AndroidManifest.xml
3. Proverite da je `android:usesCleartextTraffic="true"` u AndroidManifest.xml

### Greška: "Camera permission denied"
**Rešenje:**
1. Restartujte aplikaciju
2. Kliknite "Allow" kada tražimo dozvole
3. Ako i dalje ne radi, deinstalirajte aplikaciju i ponovo je instalirajte

## Struktura Direktorijuma

```
projekat/
├── .gradle/                    # Gradle cache
├── .idea/                      # Android Studio konfiguracija
├── app/                        # Glavna aplikacija
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/com/example/kolokvijum2/
│   │   │   │   ├── MainActivity.java
│   │   │   │   ├── Role.java
│   │   │   │   ├── RoleDao.java
│   │   │   │   ├── RoleDatabase.java
│   │   │   │   ├── RoleApiService.java
│   │   │   │   ├── RoleResponse.java
│   │   │   │   └── RetrofitClient.java
│   │   │   └── res/
│   │   │       ├── drawable/
│   │   │       ├── layout/
│   │   │       ├── mipmap/
│   │   │       └── values/
│   │   ├── test/                # Unit testovi
│   │   └── androidTest/          # Instrumentirani testovi
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
├── gradle.properties
├── README.md
└── SETUP_GUIDE.md
```

## Dodatne Komande

### Build APK
```
./gradlew build
```
APK će biti u: `app/build/outputs/apk/debug/app-debug.apk`

### Pokrenite Unit Testove
```
./gradlew test
```

### Pokrenite Instrumentirane Testove
```
./gradlew connectedAndroidTest
```

### Clean Projekat
```
./gradlew clean
```

## Zaključak

Aplikacija bi sada trebala biti potpuno funkcionalna. Ako naletite na probleme, proverite:
1. Sve dozvole su odobrene
2. Internet je dostupan
3. Android SDK je ispravan
4. Gradle je sinhronizovan
