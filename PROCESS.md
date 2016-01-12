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
- Can scan barcode and find it (scanned_barcode.png)

scanned_barcode.png  
![](doc/scanned_barcode.png)