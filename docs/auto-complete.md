# Auto Complete

You have many options to provide an auto complete feature with CodeView

### Providing a simple auto complete from an array of strings

```
// Your language keywords
String[] languageKeywords = .....
// List item custom layout 
int layoutId = .....
// TextView id on your custom layout to put suggestion on it
int viewId = .....
// Create ArrayAdapter object that contain all information
ArrayAdapter<String> adapter = new ArrayAdapter<>(context, layoutId, viewId, languageKeywords);
// Set the adapter on CodeView object
codeView.setAdapter(adapter);
```

### Providing more advanced auto complete from list of Keyword class

- This option is better if you want to provide title and prefix for your keywords,
also it more easier to use it with snippets feature.

```
List<Code> codes = new ArrayList<>();
codes.add(new Keyword(..., ..., ...));

// Your language keywords
String[] languageKeywords = .....
// List item custom layout
int layoutId = .....
// TextView id on your custom layout to put suggestion on it
int viewId = .....

CodeViewAdapter codeAdapter = new CodeViewAdapter(context, layoutId, viewId, codes);
codeView.setAdapter(codeAdapter);
```

In both options you can provide custom layout and custom tokenizer if you need that.

    
```
codeView.setAutoCompleteTokenizer(tokenizer);
```

You can limit the number of suggestions result in the auto complete dialog

```
codeView.setMaxSuggestionsSize(maxSize);
```

Set the auto complete list item size in dp to use it to calculate the full dialog size

```
codeView.setAutoCompleteItemHeightInDp(50);
```