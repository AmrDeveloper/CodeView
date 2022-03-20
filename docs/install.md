# How to install?

#### Add CodeView from Maven Central

```
dependencies { 
    implementation 'io.github.amrdeveloper:codeview:1.3.4'
}
```

#### Or Add CodeView from JCentral

Step 1: Add it to your root build.gradle
```
allprojects {
    epositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2: Add the dependency
```
dependencies { 
    implementation 'com.github.AmrDeveloper:CodeView:1.3.4'
}
```