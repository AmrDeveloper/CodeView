# Snippets

Starting From version 1.1.1 CodeView now have support for snippts.

In the CodeView library keywords and snippets are classes that implementing the Code interface.

``` java
public interface Code {
    String getCodeTitle();
    String getCodePrefix();
    String getCodeBody();
}
```

This class has three attributes title, prefix and body, It’s important to know the difference between them

- The title is that text that you see on the autocomplete dropdown menu so it can be for example "Keyword Package".

- The prefix is that text that we use it for filtering in the autocomplete adapter for example "package"

- The body is what we inserted in the code when the user types a string that is a subset of the prefix and then he clicks on the title for example "package main;"

Add Custom AutoComplete Adapter that support Snippets

``` java
List<Code> codes = new ArrayList<>();
codes.add(new Snippet(..., ..., ...));

// Your language keywords
String[] languageKeywords = .....
// List item custom layout
int layoutId = .....
// TextView id on your custom layout to put suggestion on it R.layout.yourlayout
int viewId = .....

CodeViewAdapter codeAdapter = new CodeViewAdapter(context, layoutId, viewId, codes);
codeView.setAdapter(codeAdapter);
```
