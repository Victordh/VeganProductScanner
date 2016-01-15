# day 2

- Use API for barcode scanner and fix that first.
- After, focus on user submitted data, because the possible data set is huge. This way I can focus on app functionality instead of filling a database.


| Index | Barcode      | Vegan | Name   | (Picture)    |
|-------|--------------|-------|--------|--------------|
| INT   | STRING       | BOOL  | STRING | JPG          |
| 1     | 123456789012 | true  | Banana | (banana.jpg) |
| 2     | 112234567890 | false | Honey  | (honey.jpg)  |

# day 3

- Made good start with prototype.
- Switched from Actionbar.Tabs to TabLayout, because the first is deprecated.
- Decided to add pictures of products later on, optionally, because it's secondary information.
- Made 'result', 'manual search' and 'information' into fragments (were Activities).

# day 4

- Decided to use [Barcode Scanner](https://github.com/zxing/zxing) via an Intent instead of making my own in an Activity.
- Finished fragments.

# day 5

- Finished barcode Intent, with prompt to install Barcode Scanner app if not installed yet. (see below)
- Decided to probably use [Parse](https://parse.com/) for the database.
- Completed Design Document.
- Updated README.

![](doc/scanner_intent.png)

# day 6

- Spent entire day trying to figure out local and network databases with Parse. Still not done.
- Added internet connection check (for syncing database).

# day 7

- Seperated code into different classes for clarity.
- Network database is working.
- Local database is working (can download network database into local and erase the local database).
- Can scan barcode and find it (product_scanned.png)

product_scanned.png  
![](doc/product_scanned.png)

# day 8

- Manual search now works (manual_search_result.png)
- More tweaking on code in different classes.
- Added some TODOs on manual search (more relevant results on top, if 1 result go to resultFragment, give listItem correct colour, input case-insensitive)

manual_search_result.png  
![](doc/manual_search_result.png)

# day 9

- Added user submitted data. User can add a product (to a seperate database) when a product is not found. (add_product.png, enter_product.png, thanks.png)
- Added seperate (but similar) database for user submissions, for easy export/import.
- MVP almost finished! (Sort of, needs a lot of tweaking).

add_product.png  
![](doc/add_product.png)
enter_product.png  
![](doc/enter_product.png)
thanks.png  
![](doc/thanks.png)

# day 10

- Been busy with getting listview to work with correct background and onClick, not working yet.
- If manual search has one result, goes to ResultFragment immediately.