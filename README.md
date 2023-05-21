# Pokemon

Ce projet est le fruit de notre travail pour le projet de Technologies Web Serveur (L2)
(** N.B. Ce README ne constitue pas le rapport du projet. Le rapport en format pdf a été rendu a coté du code du projet**)

```plantuml
@startuml
start
:Page d'accueil;
if (Utilisateur connecté ?) then (oui)
  :Page principale;
else (non)
  :Page de connexion;
  if (Mot de passe correct ?) then (oui)
    :Page principale;
  else (non)
    :Afficher message d'erreur;
    :Page de connexion;
  endif
endif
:Page de profil;
stop
@enduml
```
