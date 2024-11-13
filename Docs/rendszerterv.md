# PetPal alkalmazás - Rendszerterv

## 1. A rendszer célja
A **PetPal** alkalmazás célja, hogy átfogó platformot biztosítson a kisállattartók számára, amelyen keresztül kezelhetik kedvenceik egészségügyi nyilvántartásait, ellátási ütemterveit és állatorvosi időpontjait. Célunk az, hogy egyszerűsítsük és átláthatóvá tegyük a kisállatok gondozásával járó tevékenységeket.

## 2. Projektterv

### Projektszerepkörök és felelősségek:
- **Projektvezető:** Felelős a projekt irányításáért és az ütemterv betartásáért.
   - Borbély Félix
- **Fejlesztők:** Az alkalmazás különböző moduljainak fejlesztése.
   - Borbély Félix
   - Nagy Gergely
   - Juhász Dominik
   - Pelyák Zolt
   - Ribár Krisztián
- **UX/UI Designer:** Az alkalmazás felhasználói felületének és élményének megtervezése.
   - Borbély Félix
   - Nagy Gergely
- **Tesztelők:** A funkciók és az egész alkalmazás tesztelése a minőség biztosítására.
   - TODO

### Mérföldkövek

1. **Projektindítás (Kick-off meeting):** A projekt hivatalos indulása, ahol a projekt céljait, szerepköreit és a kommunikációs folyamatokat tisztázzuk.
   - **Dátum:** TODO
   - **Felelős:** Projektvezető

2. **Követelményspecifikáció elfogadása:** A megrendelő által jóváhagyott és véglegesített követelményspecifikáció, amely a fejlesztés alapját képezi.
   - **Dátum:** TODO
   - **Felelős:** Projektvezető

3. **Funkcionális tervezés lezárása:** Az összes funkcionális követelményről elkészült specifikációk elfogadása, beleértve a képernyőterveket és a használati eseteket.
   - **Dátum:** TODO
   - **Felelős:** UX/UI Designer

4. **Architekturális terv véglegesítése:** A technikai megoldásokat, rétegzett architektúrát és az alkalmazott technológiai eszközöket tartalmazó terv elfogadása.
   - **Dátum:** TODO
   - **Felelős:** Fejlesztők, Projekvezető

5. **Fejlesztési szakasz indítása:** A fejlesztők megkezdik az implementációt az elfogadott tervek alapján.
   - **Dátum:** TODO
   - **Felelős:** Fejlesztők

6. **Alfa verzió kiadása:** Az első működőképes verzió, amely tartalmazza az alapfunkciókat. Ezt követi az elsődleges hibák azonosítása.
   - **Dátum:** TODO
   - **Felelős:** Fejlesztők, Tesztelők

7. **Béta verzió kiadása:** Javításokkal és fejlesztésekkel kiegészült, közel végleges verzió, amelyet felhasználói tesztelésnek vetnek alá.
   - **Dátum:** TODO
   - **Felelős:** Fejlesztők, Tesztelők

8. **Végleges rendszer telepítése:** A rendszer végső változatának közzététele.
   - **Dátum:** TODO
   - **Felelős:** Fejlesztők

9. **Végleges átadás:** A projekt hivatalos lezárása valamint a dokumentációk átadása.
   - **Dátum:** TODO
   - **Felelős:** Projektvezető

## 3. Üzleti folyamatok modellje

### Üzleti szereplők:
- **Felhasználók:** Kisállattulajdonosok, akik szeretnék követni kedvenceik egészségügyi nyilvántartásait.

### Üzleti folyamatok:
- Kisállatok nyilvántartása és egészségügyi történetének rögzítése.
- Állatorvosi látogatások és gyógyszeres kezelések emlékeztetői.

## 4. Követelmények

### Funkcionális követelmények

#### 1. **Kisállat hozzáadása**

A **Pet Pal** alkalmazásnak képesnek kell lennie a felhasználók által tartott kisállatok hozzáadására és a hozzájuk kapcsolódó adatok kezelésére. Az alábbi funkciók szükségesek az egészségügyi nyilvántartás hatékony kezeléséhez:

- **Új kisállat regisztrálása**: A felhasználók képesek lesznek új kisállatokat hozzáadni az alkalmazásba. A regisztráció során a következő adatokat kell megadni:
  - Kisállat neve
  - Fajta, faj (pl. kutya, macska)
  - Nem
  - Születési dátum
  - Képek a kisállatról (opcionális)
  - Egyéb egyedi adatok (pl. allergiák)

#### 2. **Állatorvosi időpontok kezelése  és egészségügyi nyilvántartás vezetése**

A kisállatok rendszeres állatorvosi látogatása alapvető része az egészségmegőrzésnek, ezért az alkalmazásnak segítenie kell ezek kezelésében:

- **Állatorvosi időpontok felvétele**: A felhasználók beírhatják a közelgő állatorvosi látogatások időpontjait. Az időpontoknál az alábbi információkat lehet rögzíteni:
  - Dátum és idő
  - Állatorvos neve és elérhetősége
  - A látogatás célja (pl. rutinvizsgálat, oltás, betegség)
  - Jegyzetek (pl. speciális kérdések, tennivalók)

- **Időpontok kezelése és szerkesztése**: Az állatorvosi időpontok módosíthatók és törölhetők. A felhasználók figyelmeztetést kapnak, ha egy időpont közeleg, ezzel segítve az időben történő látogatást.

- **Egészségügyi dokumentumok tárolása**: Az alkalmazás lehetőséget biztosít arra, hogy a felhasználók állatorvosi dokumentumokat (pl. receptek, zárójelentések, vizsgálati eredmények) feltölthessenek és megtekinthessenek.

### Nem funkcionális követelmények:

#### 1. **Helyesség**
Az alkalmazásnak biztosítania kell, hogy a bevitt adatok, például a kisállat nyilvántartások és emlékeztetők mindig pontosak legyenek. Az egészségügyi információk helytelen megjelenítése komoly következményekkel járhat, ezért a rendszer folyamatos ellenőrzése és validációja alapvető fontosságú.

#### 2. **Használhatóság**
Az alkalmazás intuitív és könnyen kezelhető felületet biztosít a felhasználók számára. A felhasználói felületet úgy tervezzük, hogy az különböző technikai képzettségű emberek számára is egyértelmű legyen. Emellett a mobilon történő navigáció gyors és egyszerű lesz, figyelembe véve a képernyőméretet és az érintéses interakciókat.

#### 3. **Megbízhatóság**
Az alkalmazásnak folyamatosan elérhetőnek kell lennie, és minimalizálnia kell a rendszerhibákat vagy meghibásodásokat. Ezen felül a fontos adatok, mint például a kisállat egészségügyi nyilvántartásai és a kritikus emlékeztetők, megbízhatóan tárolódnak és érhetők el bármikor.

#### 4. **Adaptálhatóság / Hordozhatóság**
Az alkalmazást különböző mobil platformokra (Android) optimalizáljuk. Külön figyelmet fordítunk arra, hogy az alkalmazás zökkenőmentesen működjön a különböző operációs rendszereken és eszközökön, valamint könnyen átvihető legyen egy másik eszközre vagy platformra.

#### 5. **Karbantarthatóság**
Az alkalmazás kódjának moduláris felépítése biztosítja, hogy a fejlesztések, hibajavítások és frissítések könnyen végrehajthatók legyenek. A rendszer karbantartása hatékony lesz, és minimalizálja a szolgáltatás kieséseit a felhasználók számára.

#### 6. **Hatékonyság / Magas teljesítmény**
Az alkalmazás gyors válaszidőkkel és alacsony erőforrás-igénnyel működik, hogy minimalizálja az akkumulátor fogyasztást és a hálózati adatforgalmat. A kritikus funkciók, mint például az értesítések és az emlékeztetők, valós időben működnek, biztosítva a felhasználói élményt.

#### 7. **Hibatűrés / Robosztusság**
Az alkalmazás képes lesz a kisebb hibákat, adatbeviteli pontatlanságokat és hálózati problémákat kezelni anélkül, hogy összeomlana. Ezen felül, a rendszeren belüli hibák megfelelően jelentésre kerülnek, hogy a felhasználókat tájékoztassa a problémáról és annak megoldásáról.

#### 8. **Bővíthetőség / Flexibilitás**
A rendszer tervezése során figyelmet fordítunk arra, hogy később könnyen bővíthető legyen, például új funkciókkal vagy modulokkal (pl. állat biztosítási modul, további kisállat típusok támogatása).

#### ??? 9. **Újrahasznosíthatóság** ??? -> TODO
A kódtervezés során törekedünk arra, hogy a logikai elemek és komponensek újra felhasználhatóak legyenek a jövőbeli projektekben. Ez segíti a fejlesztők hatékonyságát és csökkenti a fejlesztési költségeket.

#### 10. **Kompatibilitás**
Az alkalmazás kompatibilis lesz különböző hardvereszközökkel és szoftverkörnyezetekkel.

#### 11. **Könnyen megvásárolható vagy letölthető**
Az alkalmazás elérhető lesz a Google Play áruházban. A felhasználók számára egy egyszerű és gyors telepítési folyamatot biztosítunk, amely minimális beállítást igényel.

## 5. Funkcionális terv

### Rendszerszereplők:
- **Felhasználók** (kisállattulajdonosok).

### Rendszerhasználati esetek:

1. **Kisállat hozzáadása:**
   - **Leírás:** A felhasználó új kisállatot adhat hozzá a rendszerhez, megadva alapvető adatokat, mint például a kisállat neve, fajtája, születési dátuma, és egyéb releváns információkat.
   - **Folyamat:**
     1. A felhasználó megnyitja a kisállat hozzáadás funkciót.
     2. Kitölti a szükséges mezőket, mint például a név, fajta, születési dátum.
     3. Az adatokat elmenti, és megjelenik a kisállat a főoldalon.

2. **Kisállat egészségügyi adatainak feltöltése, kezelése és előzmények megtekintése, valamint állatorvosi emlékeztetők beállítása:**

- **Leírás:** A felhasználó frissítheti, kezelheti és megtekintheti a kisállata teljes egészségügyi történetét, beleértve az állatorvosi látogatásokat, oltásokat, és egyéb kezeléseket. Emellett lehetősége van emlékeztetőket beállítani a közelgő állatorvosi vizitek és oltások időpontjaira.

- **Folyamat:**
  1. A felhasználó kiválasztja a kisállatot a főoldalon.
  2. Belép a kisállat adatlapjára, ahol hozzáfér a „Medical Records" szekciókhoz.
  3. A „Medical Records" szekcióban a felhasználó hozzáadhat új orvosi adatokat, például állatorvosi látogatások részleteit, oltások dátumát, és más kezeléseket.
  5. A rendszer értesítéseket küld a felhasználónak a megadott időpontok előtt, emlékeztetve a közelgő teendőkre.
  6. A felhasználó bármikor módosíthatja, frissítheti, vagy törölheti az emlékeztetőket, és megtekintheti az előzményeket.

3. **Kisállat adatainak frissítése:**
   - **Leírás:** A felhasználó bármikor módosíthatja a kisállat alapadatait, például frissítheti a kisállat fajtáját, nevét vagy egyéb személyes adatait.
   - **Folyamat:**
     1. A felhasználó kiválasztja a kisállatot a főoldalon.
     3. A jobb felső sarokban lévő szerkesztés ikonra nyom.
     2. Szerkeszti az adatokat, mint például név, születési dátum, fajta.
     3. Az adatokat elmenti a jobb felső sarokban lévő mentés ikonnal, és a rendszer azonnal frissíti a kisállat profilját.

### Képernyőtervek:
---TODO---

### 6. Fizikai környezet

#### Fejlesztési platform:
Az alkalmazás fejlesztése Kotlin nyelven történik, az Android Studio fejlesztői környezetben. Az alkalmazás felhasználói felülete XML segítségével készül.

#### Szoftverkomponensek:
Az alkalmazásban **nincs használatban vásárolt szoftverkomponens**. Az alkalmazás az Android SDK és az ingyenesen elérhető fejlesztési eszközök felhasználásával történik a fejlesztés.

#### Hardver és hálózati topológia:
- **Klienseszközök:** A célplatform elsősorban Android-alapú mobilkészülékek, amelyek támogatják a Kotlin-alapú alkalmazás futtatását.
- **Internetkapcsolat:** Bár az alkalmazás az interneten keresztül API-kat használ az állatok fajának és fajtájának beazonosítására, valamint "Facts about pets" megjelenítésére, nincs szükség folyamatos internetkapcsolatra. Az adatok lokálisan kerülnek megjelenítésre, és az API használata csak az azonosítási funkciókhoz és a "Facts about pets" betöltésére szükséges.

#### Fizikai alrendszerek:
- **Külső API-k:** Az alkalmazás **külső API-kat** használ kizárólag az állatfajok és fajták azonosításához, illetve "Facts about pets" megjelenítésére. Az API-k nem tárolnak adatokat, és nem történik adatfeldolgozás rajtuk keresztül. Az alkalmazás nem rendelkezik saját szerverrel vagy adatbázissal, az összes felhasználói adat helyben, az eszközön kerül mentésre.
  
#### Fejlesztő eszközök:
- **Fejlesztői környezet:** Az alkalmazás fejlesztése az **Android Studio** környezetben zajlik, amely a hivatalos Android fejlesztői eszköz. Az Android SDK-t és az XML alapú felhasználói felületet használja a fejlesztéshez.
- **Forráskódkezelés:** Git-alapú verziókezelési rendszerben történik a forráskód nyomon követése és karbantartása, lehetővé téve a kollaboratív fejlesztést és a változtatások követését.

#### Keretrendszer és könyvtárak:
- **Kotlin alapú fejlesztés:** Az alkalmazás fejlesztése Kotlin programozási nyelven történik, amely biztosítja a modern és hatékony Android alkalmazásfejlesztést.
- **XML alapú felhasználói felület:** Az alkalmazás UI/UX tervezése XML-en keresztül történik, amely támogatja a vizuális komponensek pontos és strukturált elrendezését.
- **Külső API integráció:** Az API használatával az alkalmazás állatfajok és fajták beazonosítását végzi, valamint érdekes tényeket ("Facts about pets") nyújt a felhasználóknak az állatokról. Az **API-k kizárólag információs célokat szolgálnak, nincs adatfeldolgozás vagy adatmentés.**

#### Operációs rendszer:
- **Célplatform:** Az alkalmazás Android operációs rendszert futtató mobilkészülékeken használható. A fejlesztés főként a legfrissebb Android verziókra optimalizált, de figyelembe veszi az idősebb verziók kompatibilitását is.

### 7. Architekturális terv

#### Architekturális minta:
Az alkalmazás **MVC (Model-View-Controller)** architektúrát követ, ahol a **Model** az adatkezelést és üzleti logikát, a **View** az XML-alapú felhasználói interfészt, a **Controller** pedig a kettő közötti kapcsolatot kezeli. Ez biztosítja a logikai és a megjelenítési rétegek elkülönítését.

#### Az alkalmazás rétegei és komponensei:
1. **Model (Adatkezelés és üzleti logika):**
   - Lokálisan tárolja és kezeli a kisállatok egészségügyi adatait.
   - Az API integrációk kezelésére szolgál (pl. fajta beazonosítás).
   
2. **View (Felhasználói interfész):**
   - Kotlin alapú fejlesztés XML felülettel Android Studio környezetben.
   - A Model-ben tárolt adatokat jeleníti meg és biztosítja az interakciókat.

3. **Controller (Vezérlés):**
   - A felhasználói interakciókat továbbítja a Model réteg felé.
   - Frissíti a View-t a Model módosításai alapján.

#### Változások kezelése:
Az MVC struktúra lehetővé teszi az egyes rétegek elkülönített fejlesztését és módosítását. Bármilyen felhasználói felület változtatás vagy logikai módosítás esetén csak a megfelelő réteget kell módosítani, anélkül hogy az egész rendszert érintené.

#### Rendszer bővíthetősége:
Az alkalmazás moduláris, így bővíthető új funkciókkal (pl. új egészségügyi modul). Az MVC felépítés miatt az új funkciókhoz elegendő az egyes komponensek kibővítése, anélkül hogy az egész rendszer architektúráját átalakítanánk.

#### Biztonsági funkciók:
A rendszer helyben tárol adatokat, és nem kezel érzékeny információkat. Az adatvédelemért az Android beépített biztonsági megoldásai felelnek, mivel az alkalmazás külső szerverrel nem kommunikál, és nincs adatfeldolgozás.

### 8. Implementációs terv

#### Perzisztencia osztályok:
Mivel az alkalmazás nem használ helyi adatbázist, a perzisztencia osztályok az API-kból érkező adatok feldolgozását és tárolását végzik átmenetileg, amíg az adatokra szükség van. Ezek az osztályok az alábbi funkciókat látják el:
- **API válaszfeldolgozás:** Az API-k (pl. fajta beazonosítás) válaszainak lekérése és feldolgozása.
- **Adatmodell osztályok:** A kisállatokkal kapcsolatos adatokat tartalmazzák, ideiglenesen tárolva a memóriában, amíg a felhasználóval való interakció tart.

#### Üzleti logika osztályai:
Az üzleti logika osztályai kezelik a felhasználói interakciók által kezdeményezett műveleteket, mint például az emlékeztetők beállítása és az adatok API-kal történő szinkronizálása. Az emlékeztető funkciók felelősek a következőért:
- **Emlékeztetők kezelése:** Emlékeztetők létrehozása és módosítása a felhasználó által megadott időpontok alapján (pl. oltások, állatorvosi látogatások).
- **API szinkronizáció:** Adatok lekérése és szinkronizálása az API-val.

#### Kliensoldal osztályai:
A kliensoldali logika Kotlinban íródik, XML-alapú felhasználói felülettel. A kliensoldali osztályok felelősek a felhasználói interakciók kezeléséért és a nézet frissítéséért. Főbb komponensek:
- **Activity-k és Fragment-ek:** Az alkalmazás fő felhasználói felületeit kezelik, kapcsolatban állnak a ViewModel-ekkel az adatok megjelenítéséhez.
- **ViewModel-ek:** Elkülönítik a felhasználói felületet az adatkezeléstől, biztosítva az adatok megfelelő kezelését a lifecycle-hez igazítva.
- **Adapterek és RecyclerView-ok:** Az adatok listázásáért és megjelenítéséért felelnek, például a kisállatok adatainak megjelenítésénél.

## 11. Tesztterv

### Egységtesztek
Az egységtesztek célja a kritikus funkciók ellenőrzése, különös tekintettel a következő területekre:
- **Kisállatkezelés:** A kisállatok hozzáadásának, szerkesztésének és törlésének helyes működésének ellenőrzése.
- **Egészségügyi adatok kezelése:** Az egészségügyi rekordok hozzáadásának, megjelenítésének és módosításának tesztelése, különös figyelemmel a felhasználói beviteli hibákra (pl. érvénytelen dátumok, hiányzó adatok).
- **Emlékeztetők beállítása és értesítések:** Az emlékeztetők helyes létrehozásának, értesítések ütemezésének és a felhasználó általi módosítások érvényesítésének ellenőrzése.

Mivel a rendszer nem használ adatbázist, fontos tesztelni az API-integráció helyes működését, ideértve az esetleges hibakezelést (pl. API-hibák, időtúllépés).

### Felhasználói tesztelés a béta fázisban
A béta tesztelés során a felhasználói visszajelzések alapján ellenőrizni kell az alkalmazás általános stabilitását és használhatóságát, különös figyelemmel a következőkre:
- **Felhasználói élmény:** A felhasználói interakciók zökkenőmentessége, az adatok könnyen elérhetősége, és az alkalmazás logikájának követhetősége.
- **Értesítések pontossága:** A felhasználókat a megfelelő időpontban figyelmezteti-e a rendszer a beállított eseményekre.
- **Különböző eszközökön való működés:** Android verziók és különböző képernyőméretek kompatibilitásának ellenőrzése, hogy a felület minden eszközön megfelelően működjön.

## 12. Telepítési terv

- **Play Store közzététel:**
  Az alkalmazás elérhetővé tétele a Play Store-ban a Play Console-on keresztül történik. A feltöltés során biztosítani kell a szükséges metaadatokat (pl. leírás, képernyőképek, ikon) és a Play Store irányelveinek való megfelelést (pl. adatvédelmi szabályzat). A feltöltés után következik a Play Store jóváhagyási folyamata, amelyet rendszeresen ellenőrizni kell. 

- **Frissítések kezelése:** Új verziók kiadása során figyelni kell a visszamenőleges kompatibilitásra, a felhasználók számára pedig automatikus értesítések biztosítása az új verziók elérhetőségéről.

## 13. Karbantartási terv

- **Hibajavítások:** A felhasználói visszajelzések és hibajelentések alapján folyamatosan nyomon kell követni a hibákat. A gyors hibajavítások elengedhetetlenek a felhasználói élmény fenntartása érdekében.

- **Új funkciók bevezetése:** A moduláris architektúra lehetővé teszi a jövőbeli bővítményeket és fejlesztéseket anélkül, hogy jelentősebb kódbázis-módosításra lenne szükség. Az új funkciókat sprint alapú fejlesztési ciklusban kell implementálni, a prioritásoknak megfelelően.