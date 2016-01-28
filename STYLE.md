Based on [this](http://source.android.com/source/code-style.html#follow-field-naming-conventions), [this](https://github.com/futurice/android-best-practices) and [this](http://stackoverflow.com/questions/12870537/android-naming-convention).  

Name XML files:

- activity_<ACTIVITY NAME>.xml - for all activities
- fragment_<FRAGMENT_NAME>.xml - for all fragments


Name components in XML files:

- activity_login_btn_login
- activity_login_et_username
- activity_login_et_password
- info_fragment_tv_faq

Button - btn  
EditText - et  
TextView - tv  
Checkbox - chk  
RadioButton - rb  
ToggleButton - tb  
Spinner - spn  
Menu - mnu  
ListView - lv  
GalleryView - gv  
LinearLayout -ll  
RelativeLayout - rl  


Sort XML attributes alphabetically?  
Sort XML attributes id first, layout after, rest alphabetically last?  
/> at the end of last attribute?  
/> on new line after attributes?  

Layout/content (id, text, layout, orientation) attributes inside layout XML, rest in style XML.  

In strings.xml, use 'error.message.network', not 'network_error'.  

Java:  
In Java, use CamelCase (etUsername). Acronyms (like HTML or ID) as passHtml and getId.  
Don't user ALLCAPS with finals, it would cause a mix of CamelCase and SNAKE_CASE. It is obvious enough anyway, because of syntax highlighting.
Don't prefix with activity, because code is in a certain activity already anyway. (NOT: activityLoginEtUsername, BUT: etUsername)  
Temporary stuff can have really simple names, a ListView you only use once somewhere can be called 'lv'.  
No need to prefix Strings, floats, etc. with their type (NOT: stringId, BUT: id).  
Shorten commonly known/used words, like 'pass' instead of 'password'.  
Sort methods on first-use/alphabetically, combine and make sure flow makes sense.  

Check if methods/variables are private/public etc. Only make public if needed.  
Brackets like this:  
if (something) {  
    aMethod();  
} else {  
    //code  
}  

Comments above file and class with /\*\* \* \* \*/, above methods with // (start with returns, adds, creates, etc.)  
No need to comment **obvious, small methods** (don't comment 'sets variable X' if method is calles setX).  