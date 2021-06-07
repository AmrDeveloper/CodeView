package com.amrdeveloper.codeviewlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.amrdeveloper.codeview.CodeView;
import com.amrdeveloper.codeviewlibrary.syntax.Language;
import com.amrdeveloper.codeviewlibrary.syntax.SyntaxManager;

public class MainActivity extends AppCompatActivity {

    private CodeView mCodeView;

    //Index of next theme to load it when user click change theme
    private int mNextThemeIndex = 2;

    //To change themes easily
    private final Language mCurrentLanguage = Language.JAVA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCodeView = findViewById(R.id.codeView);

        configLanguageAutoComplete();

        //Config the default theme
        SyntaxManager.applyMonokaiTheme(this, mCodeView, mCurrentLanguage);
    }

    private void configLanguageAutoComplete() {
        final String[] languageKeywords;
        switch (mCurrentLanguage) {
            case JAVA:
                languageKeywords = getResources().getStringArray(R.array.java_keywords);
                break;
            case PYTHON:
                languageKeywords = getResources().getStringArray(R.array.python_keywords);
                break;
            default:
                languageKeywords = getResources().getStringArray(R.array.go_keywords);
                break;
        }

        //Custom list item xml layout
        final int layoutId = R.layout.suggestion_list_item;

        //TextView id to put suggestion on it
        final int viewId = R.id.suggestItemTextView;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layoutId, viewId, languageKeywords);

        //Add Custom Adapter to the CodeView
        mCodeView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.changeMenu) {
            changeCodeViewTheme();
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeCodeViewTheme() {
        if (mNextThemeIndex > 4) mNextThemeIndex = 1;
        loadNextTheme();
        mNextThemeIndex = mNextThemeIndex + 1;
    }

    private void loadNextTheme() {
        switch (mNextThemeIndex) {
            case 1:
                SyntaxManager.applyMonokaiTheme(this, mCodeView, mCurrentLanguage);
                Toast.makeText(this, "Monokai", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                SyntaxManager.applyNoctisWhiteTheme(this, mCodeView, mCurrentLanguage);
                Toast.makeText(this, "Noctis White", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                SyntaxManager.applyFiveColorsDarkTheme(this, mCodeView, mCurrentLanguage);
                Toast.makeText(this, "5 Colors Dark", Toast.LENGTH_SHORT).show();
                break;
            default:
                SyntaxManager.applyOrangeBoxTheme(this, mCodeView, mCurrentLanguage);
                Toast.makeText(this, "Orange Box", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}