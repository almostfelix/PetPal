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
- **Adatbázis:** Lokális Room Database
- **Platform:** Android

## Használt technológiák

- **Kotlin:** A fő fejlesztési nyelv.
- **Room Database:** Lokális adatbázis a felhasználói adatok tárolására.
- **Jetpack Compose:** Modern UI fejlesztés Androidra.

## Rendszerarchitektúra

Az alkalmazás **MVVM** architektúrát követ, amely biztosítja a tiszta adatáramlást és elkülöníti az üzleti logikát a felhasználói felülettől.

- **Model:** A rendszer adatkezeléséért felelős réteg, amely a Room adatbázist.
- **View:** A felhasználói felület, amely Jetpack Compose segítségével van megvalósítva.
- **ViewModel:** Az üzleti logika és adatkezelés rétege, amely biztosítja az adatok frissítését és szinkronizálását a View és Model között.

## Fejlesztési és karbantartási információk

- Az alkalmazás kódja modulárisan van felépítve, ami lehetővé teszi a könnyű bővítést és karbantartást.
- A verziókezeléshez **Git**-et használunk, és a változtatásokat a **GitHub**-on tároljuk.

## Esettanulmány: Felhasználói Útmutató Kisállatok és Események Kezeléséhez

### Kisállatok kezelése
A felhasználó az alkalmazás főmenüjében az „+ Új Kedvenc” gombra kattintva hozhat létre egy kártyát, amelyben megadhatja a kisállata adatait.

![Screenshot1](/Screenshots/image-1-arrow.png)

Az új kisállat adatainak hozzáadásához a felhasználó az alábbi mezőket töltheti ki:
- **Név:** A kisállat neve (pl. Rex).
- **Faj:** A kisállat faja (pl. Kutya).
- **Fajta:** A kisállat fajtája (pl. Labrador).
- **Születési dátum:** A naptár ikonnal kiválasztható a születési idő.
- **Fénykép:** A „Change Image” gomb segítségével a felhasználó feltölthet vagy lecserélhet egy képet.
- **További információk:** Egyéb adatok, például allergiák, diéta vagy súly megadása.

![Screenshot1](/Screenshots/image-2.png)

A felhasználó a jobb felső sarokban lévő ✔️ ikonra kattintva mentheti az adatokat.

### Kisállat adatok megtekintése és szerkesztése
Amikor a felhasználó rákattint egy kedvencére, egy részletes adatlap jelenik meg. Az „Edit” gombra kattintva a felhasználó módosíthatja a kisállat adatait.

![Screenshot1](/Screenshots/image-3-edit-arrow.png)
### Események hozzáadása
A felhasználó kedvencéhez eseményeket adhat, amelyek lehetnek általános vagy orvosi jellegűek. Az események segítenek emlékeztetni a fontos időpontokra, mint például orvosi vizsgálatok, séták vagy egyéb teendők.

![Screenshot1](/Screenshots/image-3-event-arrow.png)

Az „Add Event” gombra kattintva a felhasználó megadhatja:
- **Az esemény nevét,**
- **Leírást,**
- **Az esemény dátumát és pontos idejét.**

![Screenshot1](/Screenshots/image-4.png)

Az események kategorizálva vannak fontosságuk szerint, ami lehet „általános” vagy „orvosi”.

### Kisállat adatok kategóriák szerint
A házi kedvencre kattintva a felhasználó megtekintheti a korábban megadott adatokat több kategóriára bontva.

![Screenshot1](/Screenshots/image-5.png)

- A **Medical** fül alatt láthatóak a korábban megadott egészségügyi információk, amelyek magukban foglalják az esetleges allergiákat, az étrenddel kapcsolatos tudnivalókat, valamint a kedvenc súlyát.

![Screenshot1](/Screenshots/image-6.png)

- A **Memories** fül alatt megtekinthetők a kedvencekről készült képek. A „+” ikonra kattintva tudjuk feltölteni a képeket, hosszan tartott kijelölés után pedig eltávolíthatók.

![Screenshot1](/Screenshots/image-7.png)

- A **Details** fül alatt a kedvencünkkel kapcsolatos összegző információk érhetők el, például a neve, az állatfaja, a típusa és a születési dátuma.

![Screenshot1](/Screenshots/image-8.png)
## License

Ez a projekt az **Apache License 2.0** alatt érhető el. További részletekért tekintsd meg a [LICENSE](https://www.apache.org/licenses/LICENSE-2.0.txt) fájlt.

---
