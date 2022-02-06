package com.amrdeveloper.codeviewlibrary.syntax;

import android.content.Context;

import com.amrdeveloper.codeview.Code;
import com.amrdeveloper.codeview.CodeView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LanguageManager {

    private final Context context;
    private final CodeView codeView;

    public LanguageManager(Context context, CodeView codeView) {
        this.context = context;
        this.codeView = codeView;
    }

    public void applyTheme(LanguageName language, ThemeName theme) {
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

    public String[] getLanguageKeywords(LanguageName language) {
        switch (language) {
            case JAVA: return JavaLanguage.getKeywords(context);
            case PYTHON: return PythonLanguage.getKeywords(context);
            case GO_LANG: return GoLanguage.getKeywords(context);
            default: return new String[]{};
        }
    }

    public List<Code> getLanguageCodeList(LanguageName language) {
        switch (language) {
            case JAVA: return JavaLanguage.getCodeList(context);
            case PYTHON: return PythonLanguage.getCodeList(context);
            case GO_LANG: return GoLanguage.getCodeList(context);
            default: return new ArrayList<>();
        }
    }

    public Set<Character> getLanguageIndentationStarts(LanguageName language) {
        switch (language) {
            case JAVA: return JavaLanguage.getIndentationStarts();
            case PYTHON: return PythonLanguage.getIndentationStarts();
            case GO_LANG: return GoLanguage.getIndentationStarts();
            default: return new HashSet<>();
        }
    }

    public Set<Character> getLanguageIndentationEnds(LanguageName language) {
        switch (language) {
            case JAVA: return JavaLanguage.getIndentationEnds();
            case PYTHON: return PythonLanguage.getIndentationEnds();
            case GO_LANG: return GoLanguage.getIndentationEnds();
            default: return new HashSet<>();
        }
    }

    private void applyMonokaiTheme(LanguageName language) {
        switch (language) {
            case JAVA:
                JavaLanguage.applyMonokaiTheme(context, codeView);
                break;
            case PYTHON:
                PythonLanguage.applyMonokaiTheme(context, codeView);
                break;
            case GO_LANG:
                GoLanguage.applyMonokaiTheme(context, codeView);
                break;
        }
    }

    private void applyNoctisWhiteTheme(LanguageName language) {
        switch (language) {
            case JAVA:
                JavaLanguage.applyNoctisWhiteTheme(context, codeView);
                break;
            case PYTHON:
                PythonLanguage.applyNoctisWhiteTheme(context, codeView);
                break;
            case GO_LANG:
                GoLanguage.applyNoctisWhiteTheme(context, codeView);
                break;
        }
    }

    private void applyFiveColorsDarkTheme(LanguageName language) {
        switch (language) {
            case JAVA:
                JavaLanguage.applyFiveColorsDarkTheme(context, codeView);
                break;
            case PYTHON:
                PythonLanguage.applyFiveColorsDarkTheme(context, codeView);
                break;
            case GO_LANG:
                GoLanguage.applyFiveColorsDarkTheme(context, codeView);
                break;
        }
    }

    private void applyOrangeBoxTheme(LanguageName language) {
        switch (language) {
            case JAVA:
                JavaLanguage.applyOrangeBoxTheme(context, codeView);
                break;
            case PYTHON:
                PythonLanguage.applyOrangeBoxTheme(context, codeView);
                break;
            case GO_LANG:
                GoLanguage.applyOrangeBoxTheme(context, codeView);
                break;
        }
    }

}
