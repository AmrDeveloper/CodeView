Change Log
==========

Version 1.3.8 *(2023-03-27)*
-----------------------------

* Improve line number text padding with different text sizes
* Improve the doc for setLineNumberTextSize to be explicit that value is in pixel

Version 1.3.7 *(2022-10-28)*
-----------------------------

* Make highlighting current line number option off by default

Version 1.3.6 *(2022-10-28)*
-----------------------------

* Improve calculating line number padding from 3k ns to 1.5k ns per line.
* Add support for relative line number feature inspired from vim editor
* Add support for highlighting the current line and customize the color

Version 1.3.5 *(2022-05-13)*
-----------------------------

* Fix #28: auto complete on Android 6 and lower
* Remove un used get location call on auto complete

Version 1.3.4 *(2022-03-20)*
-----------------------------

* Feature #18: Add option to bring cursor at the middle of pair
* Feature #11: Add setLineNumberTypeface method to change line number font

Version 1.3.3 *(2022-02-28)*
-----------------------------

* Fix Issue #15: Improve Auto complete drop down position when cursor on the end of the view

Version 1.3.2 *(2022-02-19)*
-----------------------------

* Fix OnDraw not working issue when the line number is disabled

Version 1.3.1 *(2022-02-06)*
-----------------------------

* Fix calculate indentation issue when user insert on the end with no next char

Version 1.3.0 *(2022-01-27)*
-----------------------------

* Improve drawing line number performance
* Improve Auto Complete implementation to fix multi size drop down popup position
* Add setMaxSuggestionsSize to limit the number of suggestions
* Add setAutoCompleteItemHeightInDp take the auto complete list item to change the drop down final size
* Add support for Auto Pair complete
* Improve Auto indenting implementation and be able to support python indentation

Version 1.2.2 *(2022-01-20)*
-----------------------------

* Add the missing Javadoc comments for all the public methods
* Improve highlightSyntax implementation
* Add resetHighlighter method to un highlight all tokens
* Improve the example app

Version 1.2.1 *(2022-01-07)*
-----------------------------

* Improve Auto indenting implementation and make it more customizable
* Add support for find and replacement features and highlight match tokens

Version 1.1.1 *(2021-11-22)*
-----------------------------

* Improve auto-complete drop-down menu position to be directly below the current cursor position

Version 1.1.0 *(2021-11-19)*
-----------------------------

* Support code snippets and change them in runtime
* Support optional line number
* Deploy on MavenCentral

Version 1.0.1 *(2021-07-21)*
-----------------------------

* Added highlightWhileTextChanging option so you can control when the highlighter should to start, default value is true

Version 1.0.0 *(2020-08-16)*
-----------------------------

* Initial release.
