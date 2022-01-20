package com.amrdeveloper.codeviewlibrary.syntax;

import android.content.Context;

import com.amrdeveloper.codeview.CodeView;

public class SyntaxManager {

    private final Context context;
    private final CodeView codeView;

    public SyntaxManager(Context context, CodeView codeView) {
        this.context = context;
        this.codeView = codeView;
    }

    public void applyTheme(Language language, Theme theme) {
        switch (theme) {
            case MONOKAI:
                applyMonokaiTheme(language);
                break;
            case NOCTIS_WHITE:
                applyNoctisWhiteTheme(language);
                break;
            case FIVE_COLOR:
                applyFiveColorsDarkTheme(language);
                break;
            case ORANGE_BOX:
                applyOrangeBoxTheme(language);
                break;
        }
    }

    private void applyMonokaiTheme(Language language) {
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

    private void applyNoctisWhiteTheme(Language language) {
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

    private void applyFiveColorsDarkTheme(Language language) {
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

    private void applyOrangeBoxTheme(Language language) {
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
