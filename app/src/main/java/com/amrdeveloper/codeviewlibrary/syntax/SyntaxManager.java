package com.amrdeveloper.codeviewlibrary.syntax;

import android.content.Context;

import com.amrdeveloper.codeview.CodeView;

public class SyntaxManager {

    public static void applyMonokaiTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case JAVA:
                JavaSyntaxManager.applyMonokaiTheme(context, codeView);
                break;
            case PYTHON:
                PythonSyntaxManager.applyMonokaiTheme(context, codeView);
                break;
            case GO_LANG:
                GoSyntaxManager.applyMonokaiTheme(context, codeView);
                break;
        }
    }

    public static void applyNoctisWhiteTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case JAVA:
                JavaSyntaxManager.applyNoctisWhiteTheme(context, codeView);
                break;
            case PYTHON:
                PythonSyntaxManager.applyNoctisWhiteTheme(context, codeView);
                break;
            case GO_LANG:
                GoSyntaxManager.applyNoctisWhiteTheme(context, codeView);
                break;
        }
    }

    public static void applyFiveColorsDarkTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case JAVA:
                JavaSyntaxManager.applyFiveColorsDarkTheme(context, codeView);
                break;
            case PYTHON:
                PythonSyntaxManager.applyFiveColorsDarkTheme(context, codeView);
                break;
            case GO_LANG:
                GoSyntaxManager.applyFiveColorsDarkTheme(context, codeView);
                break;
        }
    }

    public static void applyOrangeBoxTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case JAVA:
                JavaSyntaxManager.applyOrangeBoxTheme(context, codeView);
                break;
            case PYTHON:
                PythonSyntaxManager.applyOrangeBoxTheme(context, codeView);
                break;
            case GO_LANG:
                GoSyntaxManager.applyOrangeBoxTheme(context, codeView);
                break;
        }
    }

}
