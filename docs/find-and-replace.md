# Find and Replace

Starting From version 1.2.1 CodeView now have support for find and replace feature easily.

- To get a list of tokens that matchs your regex, you can use findMatches method.

``` java
List<Token> tokens = codeView.findMatches(regex);
```

- To highlight and get the next matching token you can use findNextMatch
 
``` java
Token token = codeView.findNextMatch();
```

- To highlight and get the previous matching token you can use findPrevMatch

``` java
Token token = codeView.findPrevMatch();
```

- You can set differnt color for highlighting matching token depend on your theme

``` java
codeView.setMatchingHighlightColor(color);
```

- To clear all the matches tokens

``` java
codeView.clearMatches();
```

- You can replace the first string that matching the regex with other string.

``` java
codeView.replaceFirstMatch(regex, replacement);
```

- You can replace all strings that matching the regex with other string.

``` java
codeView.replaceAllMatches(regex, replacement);
```

You will find a full example with UI dialog for this feature in the example app
