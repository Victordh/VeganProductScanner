Name XML files:

- activity_<ACTIVITY NAME>.xml - for all activities
- fragment_<FRAGMENT_NAME>.xml - for all fragments


Name components in XML files:

- activity_login_btn_login
- activity_login_et_username
- activity_login_et_password

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

Layout/content (id, text, layout, orientation) attributes inside layout XML, rest in style XML.

In strings.xml, use 'error.message.network', not 'network_error'.


In Java, use CamelCase (etUsername).
Don't prefix with activity, because code is in a certain activity already anyway. (NOT: activityLoginEtUsername, BUT: etUsername)
Temporary stuff can have really simple names, a ListView you only use once somewhere can be called 'lv'.
No need to prefix Strings, floats, etc. with their type (NOT: stringId, BUT: id).
Shorten commonly known/used words, like 'pass' instead of 'password'.