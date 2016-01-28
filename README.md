# Vegan Product Scanner
Victor den Haan - 10118039  
Programmeerproject, Minor Programmeren, UvA  

This app aims to be the solution to the question 'is this product vegan?'  
With more and more people eating plant-based foods for various reasons, both
 those people and their acquaintances may find themselves asking this question
 more often. This app will allow you to scan the barcode of a product, and it
 will return a simple 'yes' or 'no'.  
This app tries to be as simple and clear as possible and will therefore not
 include too many variables (like allergy information or vegetarianism).
To fill up the database as fast as possible, users can **submit product
 information** on products they scan that are not in the database yet.
As an alternative to **scanning barcodes**, it is also possible to **manually
 search** for products by barcode or (part of a) name.

This app uses [Barcode Scanner]
(https://play.google.com/store/apps/details?id=com.google.zxing.client.android) 
 from [Zxing](https://github.com/zxing/zxing) for scanning barcodes. This app
 needs to be downloaded to be able to scan barcodes and use this app to its
 full extent. If Barcode Scanner is not yet installed, the user will be prompted
 to do so when they try to scan.  
[Zxing's License](https://github.com/zxing/zxing/blob/master/LICENSE)  
[Parse](https://www.parse.com/) is used for the database. This runs in the
background completely and doesn't interact with the user directly. From the
Parse online database, a local copy is created at the press of a button,
allowing the app to be used offline as well.  

The info tab shows some general information and allows for synchronising and
 clearing the database:  
 <img src="https://github.com/Victordh/VeganProductScanner/blob/master/doc/readme/info_fragment.png" width="200">  
The search tab allows for manually searching products:  
 <img src="https://github.com/Victordh/VeganProductScanner/blob/master/doc/readme/search_fragment.png" width="200">  
Each of these products can be tapped, redirecting to the result tab, which shows
 clearly if a product is vegan or not:  
 <img src="https://github.com/Victordh/VeganProductScanner/blob/master/doc/readme/result_fragment.png" width="200">  
After tapping the round button in the lower right corner, scanning a barcode is
 fast and easy:  
 <img src="https://github.com/Victordh/VeganProductScanner/blob/master/doc/readme/barcode_scanner.png" height="200">  
If a product is found, the result tab is shown again. If a product isn't in the
 database yet, it can be submitted:  
 <img src="https://github.com/Victordh/VeganProductScanner/blob/master/doc/readme/enter_fragment.png" width="200">  

In the spirit of open and free software, and in the inevitable prospect of a 
post-copyright world, this project is released into the public domain. See 
[UNLICENSE](UNLICENSE) for more information.
**This app is and will always be completely free of charge. It does not and will
never have advertisements.**