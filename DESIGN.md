## Minimum Viable Product
The minimum viable product (MVP) consists of one Activity with 2 fragments, a database of products and an Intent to another app (Barcode Scanner). 
In the first fragment, the user can push a button to scan a barcode, after which the app will search in the database for a matching product. If found, the app will show the product name and whether it is vegan or not.
The second fragment has an edittext in which users can enter a query. After pressing the search button, the app will show a list of all matches with that query below. The items in the list will show the product name on a green (vegan) or red (not vegan) background.

## Optional
The most important optional element would be something to allow the user to contribute to the database, probably by show a button when a product couldn't be found. After tapping this button, the user can fill in the name (and optionally if the product is vegan or not). This way, the database wouldn't depend on being filled by the developer, who now only needs to filter and approve user submissions.  
Other optional elements include pictures of the products, fragments for information (with FAQ, tutorial and feedback), history (last x viewed products) and settings (language, search preferences, sync database, etc).

## Activities and fragments
Activities:
- mainActivity

Fragments (inside mainActivity):
- result_fragment
- search_fragment
- info_fragment  
optional:
- history_fragment
- settings_fragment    

## Database
| Index | Barcode      | Vegan | Name   | (Picture)    |
|-------|--------------|-------|--------|--------------|
| INT   | STRING       | BOOL  | STRING | (JPG)        |
| 1     | 123456789012 | true  | Banana | (banana.jpg) |
| 2     | 112234567890 | false | Honey  | (honey.jpg)  |

## APIs and frameworks
This app uses an Intent to the app [Barcode Scanner](https://github.com/zxing/zxing) to scan a barcode.