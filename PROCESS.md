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