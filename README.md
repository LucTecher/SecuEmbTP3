# TP3 du cours de Sécurité embarqué

## Calcul de la clé de dérivation MK_TVV

La clé de dérivation est calculée grâce à la méthode *keyDerivation*. Cette méthode prend en paramètre une clé principale à partir de laquelle seront dérivées d'autres clés, un PAN et un PSN.

L'implémentation du test de cette fonction suit rigoureusement l'énoncé du TP. Une itération sur les vecteurs permet de donner les bons paramètres à la méthode afin d'obtenir la MK_TVV correspondante, les résultats sont affichés dans un tableau dans la console de manière similaire à l'énoncé.

**Table des MK_TVV :**


| Vecteur |   Diversifiant   |             MK_TVV               |
|:-------:|:----------------:|:--------------------------------:|
|   000   | 0012345678987400 | adbe78d1c974eb2a7e7ea5a32ef8dc26 |
|   001   | 0012345678987412 | 7e1b54ddb13c91c8fc3c48cb98bd1195 |
|   010   | 1123456789876400 | d3e7107f868b8e5aea2a8d55c20a02a9 |
|   011   | 1123456789876412 | 7ab6119611f7433dd829c5889cdccae4 |
|   020   | 1234567898765500 | a49cc81db0eb583eb7b939de7b2396a0 |
|   021   | 1234567898765512 | 31b7763d66f360a226624e48765389a7 |
|   030   | 2345678987654700 | 62f60d0359a86bdf7f2106e98658b7ec |
|   031   | 2345678987654712 | cd24aa6ff283bf7f9ce842db0ca9659d |
|   040   | 6414000000036700 | 864d29a020a50fea0e67f206e59bd002 |
|   041   | 6414000000036712 | 7295fafb3c5a6da896310679d82c6f7e |
|   050   | 3456789876543700 | 7bf2e15403e338cfd9c45546fc255d37 |
|   051   | 3456789876543712 | 53dc8a35143f7e3835ac14a438890df1 |
|   060   | 4567898765432600 | 03e8375e3d09632b1f88959748c117f8 |
|   061   | 4567898765432612 | cf3c6843492572e3fed937ee98c73f9a |
|   070   | 5678987654321400 | f459f57a77c04e3c0a3310f0619659ea |
|   071   | 5678987654321412 | ceae745a17a4225c78ce7182a269a238 |
|   100   | 0012345678987400 | 67d9edc85f2ad4a395deef90e390a064 |
|   101   | 0012345678987412 | 081b87cb0b8339ecbf87c913d55202e8 |
|   110   | 1123456789876400 | 7c680bfbc5f5d1a8d5de3f16838261ef |
|   111   | 1123456789876412 | 20c185a46eb54b301adb5691fd3173f9 |
|   120   | 1234567898765500 | 3f8f6f246681fae4497040f6bb44a7bf |
|   121   | 1234567898765512 | b6f2b00887e880516969830738c31219 |
|   130   | 2345678987654700 | 3e3452f5a5f0736cb71e1f9d49a1eec4 |
|   131   | 2345678987654712 | 4e5782a9619ba7b0d0973d41e3e25231 |
|   140   | 6414000000036700 | 17279c4b2425637c4d03c028b68bbf48 |
|   141   | 6414000000036712 | 9bed6c87d3bde6c729ffa37835c82fba |
|   150   | 3456789876543700 | 35d3a14103bbbfb1945fc06d597a0225 |
|   151   | 3456789876543712 | 0d6fddc2ef42e8d0af63224f8953f191 |
|   160   | 4567898765432600 | e984eef8c7360afc35819c6902b1b2d3 |
|   161   | 4567898765432612 | 6ead9dee6b81d3014fbd56b416ce2b85 |
|   170   | 5678987654321400 | 78d05067decd632dc3198df0680aa27a |
|   171   | 5678987654321412 | db19bf3c12d8dade52b22a1f310983cc |

## Calcul du cryptogramme TVV

Afin de pouvoir calculer le cryptogramme TVV, on doit dans un premier temps générer MK_TVV. C'est pourquoi un appel à *keyDerivation* est effectué. La méthode *getTVV* est ensuite appelée. Cette méthode est relativement simple, puisqu'elle consiste à modifier légèrement le PAN et le Service Code puis elle fait appel à *getCVV*. L'implémentation de *getCVV* suit les intructions de l'énoncé en sautant l'étape du découpage en quartet de c2 qui est inutile.

Afin de vérifier le bon fonctionnement de la méthode *getCVV*, j'ai généré le tableau d'exemples.

**Tableau exemple de TVV**

| Profil - CTC |         Donnée à chiffrer         |   Cryptogramme   | TVV |
|:------------:|:---------------------------------:|:----------------:|:---:|
| P03 - 020040 |  00405678901231216002000000000000 | 67af1cd58a2582b9 | 671 |
| P03 - 363945 |  39455678901231216036000000000000 | 35dbe8a52aa6d918 | 358 |
| P13 - 020040 |  00405678901231216602000000000000 | 48188e2b24eb0dcb | 481 |
| P13 - 363945 |  39455678901231216636000000000000 | 9a06c252961dca7c | 906 |
| P06 - 020040 |  00405678901234561216002000000000 | 01eeed1ad1985b21 | 011 |
| P06 - 363945 |  39455678901234561216036000000000 | 8215d230280c85bd | 821 |
| P16 - 020040 |  00405678901234561216602000000000 | 960334d12f861e8b | 960 |
| P16 - 363945 |  39455678901234561216636000000000 | fd3d9a6d31b27439 | 396 |
| P09 - 020040 |  00405678901234567891216002000000 | 61ab22524c336f10 | 612 |
| P09 - 363945 |  39455678901234567891216036000000 | 24b9da1c5a74babe | 249 |
| P19 - 020040 |  00405678901234567891216602000000 | 7466291aad17ac80 | 746 |
| P19 - 363945 |  39455678901234567891216636000000 | 3b1f9718399a0a2f | 319 |


Parmi ce qui était déjà fait, la partie concernant le hachage de m sur un bloc c2 de 8 octets contenait un bug. Pour certaines valeurs de test, notamment P09 - 000000, la valeur en hexadécimal de la variable bigxox avait une longueur de 15 caractères. Ceci faisait que la fonction de chiffrement *TDES* retournait une String composée de 14 "0". Afin de corriger ce bug, j'ai pris la liberté de padder le bigInteger bigxox avec des 0 à gauche afin que celui-ci ait une taille de 16 caractères. Je ne sais cependant pas si cette solution produit le résultat final attendu pour les TVV mais elle ne modifie pas les résultats obtenus précédemment.

**Tableau des TVV**

| CTC / Pij | P03 | P04 | P05 | P06 | P07 | P08 | P09 | P13 | P14 | P15 | P16 | P17 | P18 | P19 |
|:---------:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
|   000000  | 404 | 239 | 098 | 464 | 359 | 844 | 793 | 229 | 097 | 844 | 087 | 372 | 148 | 401 | 
|   100000  | 221 | 654 | 529 | 972 | 780 | 246 | 381 | 218 | 360 | 611 | 901 | 563 | 782 | 342 | 
|   020000  | 230 | 797 | 225 | 816 | 177 | 071 | 240 | 587 | 161 | 309 | 400 | 267 | 714 | 228 | 
|   003000  | 126 | 475 | 685 | 905 | 128 | 986 | 609 | 809 | 715 | 664 | 540 | 297 | 184 | 969 | 
|   000400  | 810 | 291 | 027 | 577 | 433 | 736 | 431 | 007 | 813 | 896 | 423 | 642 | 997 | 415 | 
|   000050  | 448 | 210 | 545 | 307 | 425 | 382 | 020 | 506 | 093 | 977 | 038 | 801 | 510 | 609 | 
|   000006  | 705 | 455 | 850 | 786 | 018 | 026 | 362 | 896 | 578 | 280 | 232 | 405 | 744 | 451 | 
|   123456  | 696 | 142 | 219 | 399 | 808 | 473 | 012 | 534 | 350 | 230 | 051 | 406 | 447 | 764 | 
|   234561  | 417 | 793 | 589 | 387 | 975 | 547 | 510 | 237 | 099 | 588 | 138 | 468 | 498 | 044 | 
|   363945  | 358 | 820 | 445 | 821 | 631 | 270 | 249 | 906 | 371 | 065 | 396 | 579 | 581 | 319 | 
|   589611  | 750 | 210 | 128 | 249 | 844 | 254 | 386 | 218 | 410 | 940 | 782 | 783 | 057 | 824 | 
|   999999  | 915 | 546 | 943 | 110 | 382 | 718 | 827 | 262 | 965 | 821 | 008 | 159 | 801 | 100 | 

## Note

J'ai pris la liberté de modifier les signatures des méthodes *getCVV*, *getTVV* et *keyDerivation* afin d'intégrer un paramètre qui me permettrait d'afficher les tableaux tels que demandés dans l'énoncé.