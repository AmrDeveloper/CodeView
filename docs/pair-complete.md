# Pair Complete

Starting From version 1.3.0 CodeView now has support for auto pair complete,
this feature can help you to implement some features easily such as quote or double quote complete,
or closing braces complete.

This features is disabled by default to enable or disable it

```
codeView.enablePairComplete(enableFeature);
```

To use this feature you need to create a Map that contains the pairs keys and values for example

```
Map<Character, Character> pairCompleteMap = new HashMap<>();
pairCompleteMap.put('{', '}');
pairCompleteMap.put('[', ']');
pairCompleteMap.put('(', ')');
pairCompleteMap.put('<', '>');
pairCompleteMap.put('"', '"');
```

To add your full pairs map

```
codeView.setPairCompleteMap(pairCompleteMap);
```

To add a single pair

```
codeView.addPairCompleteItem('[', ']');
```

To remove a single pair

```
codeView.removePairCompleteItem('[');
```

To remove all the pairs

```
codeView.clearPairCompleteMap();
```