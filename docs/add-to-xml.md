# Add CodeView to XML layout

It's easy to add CodeView in your XML layout, notes that CodeView is based on AppCompatMultiAutoCompleteTextView,
so you can easily customize it like any AutoCompleteTextView

``` xml
<com.amrdeveloper.codeview.CodeView
    android:id="@+id/codeView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/darkGrey"
    android:dropDownWidth="@dimen/dimen150dp"
    android:dropDownHorizontalOffset="0dp"
    android:dropDownSelector="@color/darkGrey"
    android:gravity="top|start" />
```
