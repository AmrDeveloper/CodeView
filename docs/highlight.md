# Highlight

The main goal for creating the CodeView library is to not be limited by a list of highlighters that come with any library but to have the ability to create a highlighter for any set of data,
so you can highlight and provide other features for any programming language or data.

### To highlight pattern with color.

```
codeView.addSyntaxPattern(pattern, Color);
```

### You can add a Map<Pattern, Color> instead of adding patterns one by one

```
codeView.setSyntaxPatternsMap(syntaxPatterns);
```

### You can also remove pattern in the runtime

```
codeView.removeSyntaxPattern(pattern);
```

### Highlight the text depend on the new patterns

```
codeView.reHighlightSyntax();
```

### Clear all patterns from CodeView

```
codeView.resetSyntaxPatternList();
```

### Set highlighter update delay

```
codeView.setUpdateDelayTime();
```

### You can control when to highlight the text

```
codeView.highlightWhileTextChanging(highlightWhileTextChanging);
```

### Add error line with dynamic color to support error, hint, warn...etc

```
codeView.addErrorLine(lineNumber, color);
```

### Clear all error lines

```
codeView.removeAllErrorLines();
```

### Highlight the errors depend on the error lines

```
codeView.reHighlightErrors();
```

### Get the number of errors

```
int numberOfErrors = codeView.getErrorsSize();
```