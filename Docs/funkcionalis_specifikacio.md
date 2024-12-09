# Funkcionális Specifikáció

## 1. Jelenlegi helyzet
A vállalkozásunk egy mobilalkalmazás fejlesztését tervezi, amely segít a háziállat tulajdonosoknak abban, hogy könnyen nyomon követhessék kedvencük életkorát emberi években, valamint további hasznos funkciókat kínáljon, mint például a háziállat fejlődési szakaszainak követése, születésnapi emlékeztetők, és az állat egészségéhez kapcsolódó információk és feljegyzések.

## 2. Követelménylista

| Modul               | ID  | Név                                   | Verzió | Kifejtés                                                                          |
|---------------------|-----|---------------------------------------|--------|-----------------------------------------------------------------------------------|
| Felhasználói élmény | K01 | Könnyen kezelhető rendszer            | 1.0    | A rendszernek intuitív felhasználói felülettel kell rendelkeznie, hogy a felhasználók könnyen navigálhassanak az alkalmazásban. Az összes funkció könnyen elérhető legyen, és a használat során ne legyen szükség különösebb technikai tudásra. |
| Emlékeztetők        | K04 | Születésnapi és állatorvosi emlékeztetők | 1.0 | Az alkalmazásnak értesítéseket kell küldenie a közelgő születésnapokról, örökbefogadási évfordulókról, valamint állatorvosi ellenőrzésekről. A felhasználók beállíthatják az emlékeztetők időpontját, és az alkalmazásnak figyelmeztetéseket kell küldenie a megfelelő időben. |
| Digitális idővonal  | K05 | Képfeltöltés és idővonal             | 1.0    | A felhasználóknak lehetőséget kell biztosítani arra, hogy képeket tölthessenek fel háziállatukról különböző életkorokban. Az alkalmazásnak digitális idővonalon kell megjelenítenie ezeket a képeket, lehetővé téve a felhasználók számára, hogy visszatekintsenek az állatuk életére. |


## 3. Jelenlegi üzleti folyamatok modellje
A háziállat-tulajdonosok gyakran nehezen követik nyomon kedvenceik egészségi állapotát és életkorát. Jelenleg sokan papíralapú nyilvántartásokat használnak, ami időigényes és nem hatékony. Az ilyen módszerek nemcsak környezeti szempontból hátrányosak, hanem a felhasználók számára is bonyolulttá teszik az információk gyors elérését. Az állatorvosi látogatások és fontos események nyomon követése sok időt és energiát igényel, amelyet egy modern alkalmazás egyszerűsíthetne.

## 4. Igényelt üzleti folyamatok modellje
A **PetPal** alkalmazás célja, hogy egyszerűsítse a háziállat-tulajdonosok életét, lehetővé téve számukra, hogy könnyedén nyomon követhessék kedvenceik életkorát és egészségi állapotát. Az alkalmazás egyszerűsíti a folyamatokat: a felhasználóknak csak egyszer kell megadniuk kedvencük adatait, és az alkalmazás automatikusan biztosítja a szükséges információkat, emlékeztetőket és grafikus ábrázolásokat. A felhasználók azonnali visszajelzést kapnak, és könnyen ellenőrizhetik a háziállatuk fejlődését. A digitális formátum csökkenti a papíralapú költségeket és javítja az információk hozzáférhetőségét.

## 5. Használati esetek
- **Felhasználó**: A háziállat-tulajdonosok könnyedén hozzáférhetnek az alkalmazás funkcióihoz, mint például a Háziállat Életkor Átváltó, a növekedési diagram, a születésnapi emlékeztetők és a digitális idővonal.

## 6. Megfeleltetés, hogyan fedik le a használati esetek a követelményeket
- Az alkalmazás reszponzív dizájnja lehetővé teszi, hogy a felhasználók tableten is használhassák.
- A funkciók, mint a háziállat életkor átváltó és a növekedési diagram, közvetlenül kapcsolódnak a háziállatok életkorának és fejlődésének nyomon követéséhez.
- Az emlékeztetők és értesítések segítenek a felhasználóknak a fontos események nyomon követésében, biztosítva a rendszer hatékony működését.

## Fogalomszótár
- **[Reszponzív dizájn]**: Olyan felhasználói felület, amely különböző eszközök (mobil, tablet) képernyőméretéhez igazodik.