# Kolokvijum2 - Android Aplikacija

Kompletna Android aplikacija sa svim zahtjevima.

## Zahtjevi Zadatka

### 1. Inicijalizacija Projekta ✓
- Novi Android projekat "Kolokvijum2"
- MainActivity kao glavna aktivnost

### 2. UI Elementi ✓
- 2x CheckBox
- 1x Button sa tekstom "Slikaj"
- 1x ImageView
- 2x TextView jedno ispod drugog

### 3. Proximity Senzor ✓
- Prikazivanje vrednosti proximity senzora u prvom TextView
- Prag postavljen na 5.0 cm
- Toast poruka "Daleko!" kada je vrednost iznad praga

### 4. Kamera ✓
- Klikom na Button "Slikaj" pokreće se kamera
- Fotografija se čuva u files direktorijumu aplikacije
- Fotografija se prikazuje u ImageView

### 5. Retrofit i Baza Podataka ✓
- Model Role iz: https://app.beeceptor.com/mock-server/dummy-json
- Retrofit konfiguriran za GET zahtev
- Room baza za čuvanje uloga

### 6. Prvi Checkbox - Dobijanje Uloga ✓
- Dobija sve uloge sa API-ja
- Čuva samo uloge sa parnim ID-om u bazi

### 7. Drugi Checkbox - Prikaz Opisa ✓
- Prikazuje opis uloge sa najvećim ID-om iz baze
- Prikazuje ID, naslov i opis u drugom TextView

## Struktura Projekta

```
Kolokvijum2/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── AndroidManifest.xml
│   │       ├── java/com/example/kolokvijum2/
│   │       │   ├── MainActivity.java
│   │       │   ├── Role.java
│   │       │   ├── RoleDao.java
│   │       │   ├── RoleDatabase.java
│   │       │   ├── RoleApiService.java
│   │       │   ├── RoleResponse.java
│   │       │   └── RetrofitClient.java
│   │       └── res/
│   │           ├── layout/
│   │           │   └── activity_main.xml
│   │           └── values/
│   │               ├── strings.xml
│   │               └── themes.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Korišćeni Alati i Biblioteke

- **Retrofit 2.9.0** - REST API komunikacija
- **Gson** - JSON parsing
- **Room Database** - Lokalna baza podataka
- **AndroidX Camera** - Kamera funkcionalnost
- **Material Components** - UI

## Dozvole

Aplikacija zahteva sledeće dozvole:
- `CAMERA` - Za korišćenje kamere
- `WRITE_EXTERNAL_STORAGE` - Za čuvanje fotografija
- `READ_EXTERNAL_STORAGE` - Za čitanje datoteka
- `INTERNET` - Za Retrofit API pozive

## Instalacija i Pokretanje

1. Otvorite projekat u Android Studio
2. Povezujte Android uređaj ili kreirajte emulatora
3. Izvršite `gradle build` ili klikom na "Run" u Android Studio
4. Aplikacija će se instalirati i pokrenuti na uređaju

## Karakteristike

- **Proximity Senzor**: Prati blizinu korisnika, prikazuje vrednost u cm, i šalje obaveštenje kada je daleko
- **Kamera**: Integrisana kamera sa mogućnošću slicanja fotografija
- **Retrofit API**: Dobijanje uloga sa MockServer-a
- **Room Database**: Čuvanje uloga sa parnim ID-om
- **Dinamički UI**: CheckBox-i menjaju ishod aplikacije

## API Endpointi

- **GET** `https://app.beeceptor.com/mock-server/dummy-json` - Dobija sve uloge
  - Response: `{ "roles": [ { "id": int, "title": string, "description": string }, ... ] }`

## Baza Podataka

**Tabela: roles**
- `id` (PrimaryKey) - ID uloge
- `title` - Naziv uloge
- `description` - Opis uloge

## Korisničke Interakcije

1. **Proximity Senzor** - Automatski se pokreće, prikazuje vrednosti u realnom vremenu
2. **Button "Slikaj"** - Otvara kameru, korisnik slika fotografiju
3. **Checkbox "Dobavi uloge"** - Učitava uloge sa API-ja, čuva samo one sa parnim ID-om
4. **Checkbox "Prikaži opis"** - Prikazuje opis uloge sa najvećim ID-om iz baze

## Napomene

- Fotografije se čuvaju u aplikacijskom files direktorijumu (`/data/data/com.example.kolokvijum2/files/`)
- API zahtev se izvršava asinkriono kako ne bi blokirao UI
- Baza podataka se inicijalizuje pri prvom pokretanju aplikacije
- Proximity senzor se registruje u `onResume()` i uklanja u `onPause()`
