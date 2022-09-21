# Tfarhida
A desktop , web and mobile solution for booking camping trips with e-shopping solution for purchasing camping gear  Technologies:  Web : Symfony Desktop : JavaFx Mobile : CodenameOne 
## Features
![tffarhida front screenshot](public/uploads/images/tffarhida.PNG)

* product management
 
  * Product
      * Category search 
      * Price range search
      * Product type and attributes
      * Product comments and feed back section
  * shopping cart management
* reservation management
    * Adding bed and breakfast offers
    * Adding camping offers
    * Managing reservation : approve or reject
    * Complex order and customer condition
    * Push notification
    * Comment section management 
    * User favorits section
* Customer management
    * Approuving hosters or bed and breakfast owners
    * Removing users for misbehavior

## Symfony4 : web application
##  Adding the Database
```
php bin/console doctrine:database:create
php bin/console make:migration
php bin/console doctrine:migrations:migrate
```

## Running the project
```
php bin/console server:run
```
### Prerequisites
Apache server, PHP 7.3, Mysql 5.6
