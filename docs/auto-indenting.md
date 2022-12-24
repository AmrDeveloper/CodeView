# Auto Indenting

Starting From version 1.2.1 CodeView now have support for customizable Auto indenting.

### How auto indenting works in CodeView?
Basically this feature depend on two set of characters which are indentation starts and ends sets,
when user typed character from indentation starts, the indentation level will increased by the tab length, 
and if it from indentation ends, indentation level will decreased by the tab length,

In some cases the user editing the code from the middle so we can't use the global indentation level, and we need to find the level before this code and apply it.

Now after you understanding how auto indenting works, it's time to know how to config it.

### Set Indentations Starts set of characters

``` java
codeView.setIndentationStarts(indentationStart);
```

### Set Indentations Ends set of characters

``` java
codeView.setIndentationEnds(indentationEnds);
```

### Enable/Disable Auto Indentation

``` java
codeView.setEnableAutoIndentation(enableIndentation);
```

### Set Tab length

``` java
codeView.setTabLength(tabLength);
```
