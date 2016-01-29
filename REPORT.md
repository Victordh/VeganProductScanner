## Vegan Product Scanner
This app aims to be the solution to the question 'is this product vegan?'  
The app will allow you to scan the barcode of a product, and it will return a
 simple 'yes' or 'no'.  As an alternative to **scanning barcodes**, it is also
 possible to **manually search** for products by barcode or (part of a) name.  
To fill up the database as fast as possible, users can **submit product
 information** on products they scan that are not in the database yet. This app
 tries to be as simple and clear as possible and will therefore not include too
 many variables (like allergy information or vegetarianism).  

## Technical design
The four main functions of the app (scanning a barcode, searching for products,
 , loading the database and submitting a product) are separated into different
 packages with multiple classes. Below, I will explain the structure of these
 classes based on these packages.  
 
The app consists of only one Activity, containing multiple fragments. Each of
 these fragments has a separate function. On the top layer, there are three
 fragments: ContainerFragment, SearchFragment and InfoFragment.
 ContainerFragment always has one of four possible other fragments inside:
ResultFragment, AddFragment, EnterFragment or SentFragment.  

The main package contains this MainActivity and two classes handling the tabs
 and fragments (TabsFragmentPagerAdapter and PageFragment).  The fourth and last
 file directly in this package is ParseApplication, which makes sure the app can
 use the local Parse database.  

#### Data
In the data package (which is inside the main package), three files concerning
 data management are located: InfoFragment, LocalDatabase and OnlineDatabase.  
InfoFragment contains the UI allowing the user to synchronise and clear the
 local database.  
In LocalDatabase reside all methods relating to the local database. This
 includes methods looking up products, and clearing the local database.  
OnlineDatabase contains the methods concerning the online Parse tables,
 "Product" and "Submission". The most important methods are synchronising the
 "Product"-database and saving it locally, and sending a product added by a user
 to the "Submission"-database.  
These two tables are very similar, allowing for minimal effort during
importing of user submissions into the main table, after a verification
 check. Here you can see the format of tables "Product" and "Submission":  
"Product":

|  objectID  | productBarcode | productName | isVegan |      createdAt      |      updatedAt      |     ACL     |
|:----------:|:--------------:|:-----------:|:-------:|:-------------------:|:-------------------:|:-----------:|
|   STRING   |     STRING     |    STRING   | BOOLEAN |        DATE         |        DATE         |     ACL     |
| aaaAaaAAAa |  101010101010  |  Tangerine  |   true  | Jan 01, 1900, 00:01 | Jan 01, 1900, 00:11 | Public Read |
| ZZzzZzzzZZ |  999999999999  |    Honey    |  false  | Jan 01, 1900, 00:01 | Jan 01, 1900, 00:11 | Public Read |

"Submission":

|  objectID  | productBarcode |  productName | isVegan | productComment |      createdAt      |      updatedAt      |          ACL          |
|:----------:|:--------------:|:------------:|:-------:|:--------------:|:-------------------:|:-------------------:|:---------------------:|
|   STRING   |     STRING     |     STRING   |  STRING |     STRING     |        DATE         |        DATE         |          ACL          |
| aaaAaaAAAa |  101010101010  |   Tangerine  |   true  |  It is a fruit | Jan 01, 1900, 00:01 | Jan 01, 1900, 00:11 | Public Read and Write |
| ZZzzZzzzZZ |  999999999999  |     Honey    |  false  |  Bees make it  | Jan 01, 1900, 00:01 | Jan 01, 1900, 00:11 | Public Read and Write |
| UEIUeiieuE |  000000000000  | Pumpkin Soup | unknown | Could be cream | Jan 01, 1900, 00:01 | Jan 01, 1900, 00:11 | Public Read and Write |

#### Search
The search package (which is inside the data package) is comprised of two files:
 SearchFragment and SearchListArrayAdapter.  
SearchFragment contains the UI elements allowing the user to manually search for
 products and showing the results of that search.  
SearchListArrayAdapter is a custom adapter, which exists to colour the
 background of each item in the list.  

#### Scan
The scan package (which is also inside the data package) contains four files:
 ContainerFragment, ResultFragment, IntentIntegrator and IntentResult.  
ContainerFragment is an empty fragment except for the Floating Action Button
 that calls IntentIntegrator.  
IntentIntegrator handles the Intent to the app Barcode Scanner.  
IntentResult hands this app the result of that scan.  
ResultFragment is the most important fragment. Its purpose is to show very
 clearly if a single product is vegan or not.  

#### Submit
The last package, inside the scan package, is submit. This package contains the
 files concerning adding a product that is not in the database yet. AddFragment,
 EnterFragment and SentFragment are inside this package.  
AddFragment informs the user of the product being unknown and has a button to
 EnterFragment.  
EnterFragment contains the UI allowing the user to enter the details of the
 product, and asks OnlineDatabase to send those details to the "Submission"
 Parse table.  
SentFragment gives feedback to the user after they submit a product.  

### Frameworks/APIs
This app uses [Barcode Scanner]
(https://play.google.com/store/apps/details?id=com.google.zxing.client.android) 
 from [Zxing](https://github.com/zxing/zxing) to scan barcodes. If Barcode
 Scanner is not yet installed, the user will be prompted to do so when they
 try to scan.  
[Parse](https://www.parse.com/) is used for the database. This runs in the
background completely and doesn't interact with the user directly. From the
Parse online database, a local copy is created at the press of a button,
allowing the app to be used offline as well.  

## Process
