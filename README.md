# PetPal - README

## Projekt bemutatása

**PetPal** egy mobilalkalmazás, amely kisállattulajdonosok számára nyújt egy átfogó platformot kedvenceik egészségügyi nyilvántartásainak, ellátási ütemterveinek és állatorvosi időpontjainak kezelésére. Az alkalmazás célja, hogy egyszerűsítse és átláthatóvá tegye a kisállatok gondozásával járó tevékenységeket, lehetővé téve a felhasználóknak, hogy nyomon követhessék kisállataik egészségügyi állapotát és emlékeztetőket kapjanak a fontos időpontokról.

## Funkciók

- **Kisállat hozzáadása és nyilvántartás:** Lehetőség új kisállatok regisztrálására és azok alapadatainak (név, faj, születési dátum) megadására.
- **Állatorvosi időpontok kezelése:** Lehetőség a közelgő állatorvosi látogatások időpontjainak hozzáadására, szerkesztésére és törlésére.
- **Egészségügyi nyilvántartás:** A felhasználók kezelhetik kisállataik orvosi előzményeit, oltásait, kezeléseit, és tárolhatják az állatorvosi dokumentumokat.
- **Emlékeztetők:** Az alkalmazás értesítéseket küld a közelgő állatorvosi látogatásokról és egyéb fontos eseményekről.
- **Egyszerű és intuitív UI:** A felhasználói felület könnyen navigálható és a felhasználói élmény egyszerűsíti a kisállatgondozást.

## Telepítés

### Google Play Áruház

A PetPal alkalmazás könnyen telepíthető Android eszközökre a Google Play Áruházból. Kövesd az alábbi lépéseket:

1. Nyisd meg a **Google Play Áruházat** az Android eszközödön.
2. A keresőmezőbe írd be: **PetPal**.
3. Válaszd ki a megfelelő alkalmazást a találatok közül.
4. Kattints a **Telepítés** gombra, és várj, amíg az alkalmazás letöltődik és telepítésre kerül.


## Fejlesztési környezet

- **Fejlesztési nyelv:** Kotlin
- **Fejlesztői környezet:** Android Studio
- **UI/UX:** Jetpack Compose
- **Adatbázis:** Lokális Room Database + Firebase szinkronizálás
- **Platform:** Android

## Használt technológiák

- **Kotlin:** A fő fejlesztési nyelv.
- **Room Database:** Lokális adatbázis a felhasználói adatok tárolására.
- **Firebase:** Cloud backend a szinkronizált adatok számára.
- **Jetpack Compose:** Modern UI fejlesztés Androidra.

## Rendszerarchitektúra

Az alkalmazás **MVVM** architektúrát követ, amely biztosítja a tiszta adatáramlást és elkülöníti az üzleti logikát a felhasználói felülettől.

- **Model:** A rendszer adatkezeléséért felelős réteg, amely a Room adatbázist és a Firebase-t használja.
- **View:** A felhasználói felület, amely Jetpack Compose segítségével van megvalósítva.
- **ViewModel:** Az üzleti logika és adatkezelés rétege, amely biztosítja az adatok frissítését és szinkronizálását a View és Model között.

## Fejlesztési és karbantartási információk

- Az alkalmazás kódja modulárisan van felépítve, ami lehetővé teszi a könnyű bővítést és karbantartást.
- A verziókezeléshez **Git**-et használunk, és a változtatásokat a **GitHub**-on tároljuk.

## License

Ez a projekt az **MIT License** alatt érhető el. További részletekért tekintsd meg a [LICENSE](https://www.apache.org/licenses/LICENSE-2.0.txt) fájlt.