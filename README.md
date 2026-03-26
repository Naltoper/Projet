# Rapport de Conception : Simulateur Firefighter

**Auteurs :** FABRETTI Florent, FABRETTI Vincent

---

## Architecture Générale du Modèle

Le cœur du simulateur repose sur un board nommé **FirefighterBoard**. 
* Il implémente à la fois l'interface `Board<List<ModelElement>>` et l'interface de contexte `BoardContext`.
* Le board délègue entièrement la gestion des entités à une liste de `BoardElement`.
* Chaque type d'élément est encapsulé dans un **manager dédié** qui gère leurs positions respectives.

### Séparation des Éléments
Le système utilise différents contrats pour définir le comportement des éléments :
* **BoardElement** : Contrat commun à tous les éléments.
* **MobileElement** : Pour les éléments capables de se déplacer.
* **Extinguishable** : Spécifique au feu (seul élément éteignable).
* **Burnable** : Pour les rochers (offrant une résistance temporaire au feu).

---

## Justification des choix et Principes SOLID

### SRP (Single Responsibility Principle)
Chaque classe a une et une seule raison de changer.

### OCP (Open/Closed Principle)
Le système est ouvert à l'extension mais fermé à la modification grâce au **pattern Strategy** :
* Les **managers** gèrent les listes de positions.
* Les **UpdateStrategy** gèrent la logique de déplacement.
* **Exemples de stratégies injectées** :
    * `FireFighterUpdateStrategy` : Déplacement des pompiers.
    * `RandomMoveUpdateStrategy` : Déplacement aléatoire des nuages.
    * `FirePropagationStrategy` : Propagation du feu.
    * `MountainUpdateStrategy` & `RockUpdateStrategy` : Effets des montagnes et rochers.
* **Avantage** : Ajouter un nouveau comportement ne nécessite aucune modification des managers existants.

### LSP (Liskov Substitution Principle)
Toutes les implémentations de `MovingUpdateStrategy`, par exemple, sont parfaitement interchangeables.

### ISP (Interface Segregation Principle)
Les interfaces sont petites et ciblées. Aucune classe n'est forcée d'implémenter des méthodes inutiles :
* `MountainManager` n'implémente pas `MobileElement`.
* `FireManager` implémente `Extinguishable` et `MobileElement`.
* `RockManager` implémente `Burnable` avec la méthode spécialisée `getPropagationDelay()`.

### DIP (Dependency Inversion Principle)
* Les classes de haut niveau dépendent uniquement d'abstractions.
* Aucune dépendance directe vers une classe concrète n'est établie, sauf via la **Factory**.

---

## Points de Conception Principaux

* **Gestion déléguée** : Le Board s'occupe de la liste globale, tandis que les managers gèrent la logique propre à chaque type d'élément.
* **Référence Bidirectionnelle** : Le `FireFighterBoard` injecte le `BoardContext` dans les managers après leur création, permettant à chaque manager d'obtenir des informations sur les autres.
* **Pattern Factory** : Permet de déléguer la création des éléments et de simplifier l'ajout de nouvelles entités.
* **Visualisation** : Un diagramme UML simplifié est joint au rendu pour illustrer cette structure.