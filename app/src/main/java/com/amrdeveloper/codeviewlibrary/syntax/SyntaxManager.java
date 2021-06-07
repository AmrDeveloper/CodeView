package com.amrdeveloper.codeviewlibrary.syntax;

import android.content.Context;

import com.amrdeveloper.codeview.CodeView;

public class SyntaxManager {

    public static void applyMonokaiTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case JAVA:
                JavaSyntaxUtils.applyMonokaiTheme(context, codeView);
                break;
            case PYTHON:
                PythonSyntaxUtils.applyMonokaiTheme(context, codeView);
                break;
            case GO_LANG:
                GoSyntaxUtils.applyMonokaiTheme(context, codeView);
                break;
        }
    }

    public static void applyNoctisWhiteTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case JAVA:
                JavaSyntaxUtils.applyNoctisWhiteTheme(context, codeView);
                break;
            case PYTHON:
                PythonSyntaxUtils.applyNoctisWhiteTheme(context, codeView);
                break;
            case GO_LANG:
                GoSyntaxUtils.applyNoctisWhiteTheme(context, codeView);
                break;
        }
    }

    public static void applyFiveColorsDarkTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case JAVA:
                JavaSyntaxUtils.applyFiveColorsDarkTheme(context, codeView);
                break;
            case PYTHON:
                PythonSyntaxUtils.applyFiveColorsDarkTheme(context, codeView);
                break;
            case GO_LANG:
                GoSyntaxUtils.applyFiveColorsDarkTheme(context, codeView);
                break;
        }
    }

    public static void applyOrangeBoxTheme(Context context, CodeView codeView, Language language) {
        switch (language) {
            case JAVA:
                JavaSyntaxUtils.applyOrangeBoxTheme(context, codeView);
                break;
            case PYTHON:
                PythonSyntaxUtils.applyOrangeBoxTheme(context, codeView);
                break;
            case GO_LANG:
                GoSyntaxUtils.applyOrangeBoxTheme(context, codeView);
                break;
        }
    }

}
