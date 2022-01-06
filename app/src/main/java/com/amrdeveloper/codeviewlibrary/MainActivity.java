package com.amrdeveloper.codeviewlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.amrdeveloper.codeview.CodeView;
import com.amrdeveloper.codeviewlibrary.syntax.Language;
import com.amrdeveloper.codeviewlibrary.syntax.SyntaxManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.HashSet;
import java.util.Set;

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

        configCodeView();
        configLanguageAutoComplete();

        //Config the default theme
        SyntaxManager.applyMonokaiTheme(this, mCodeView, mCurrentLanguage);
    }

    private void configCodeView() {
        mCodeView = findViewById(R.id.codeView);

        mCodeView.setEnableLineNumber(true);
        mCodeView.setLineNumberTextColor(Color.GRAY);
        mCodeView.setLineNumberTextSize(25f);

        mCodeView.setTabLength(4);
        mCodeView.setEnableAutoIndentation(true);

        Set<Character> indentationStart = new HashSet<>();
        indentationStart.add('{');
        mCodeView.setIndentationStarts(indentationStart);

        Set<Character> indentationEnds = new HashSet<>();
        indentationEnds.add('}');
        mCodeView.setIndentationEnds(indentationEnds);
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
        if (id == R.id.changeMenu) changeCodeViewTheme();
        else if (id == R.id.findMenu) launchEditorButtonSheet();
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

    private void launchEditorButtonSheet() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.bottom_sheet_dialog);
        dialog.getWindow().setDimAmount(0f);

        final EditText searchEdit = dialog.findViewById(R.id.search_edit);
        final EditText replacementEdit = dialog.findViewById(R.id.replacement_edit);

        final ImageButton findPrevAction = dialog.findViewById(R.id.find_prev_action);
        final ImageButton findNextAction = dialog.findViewById(R.id.find_next_action);
        final ImageButton replacementAction = dialog.findViewById(R.id.replace_action);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.isEmpty()) mCodeView.clearMatches();
                mCodeView.findMatches(text);
            }
        });

        findPrevAction.setOnClickListener(v -> {
            mCodeView.findPrevMatch();
        });

        findNextAction.setOnClickListener(v -> {
            mCodeView.findNextMatch();
        });

        replacementAction.setOnClickListener(v -> {
            String regex = searchEdit.getText().toString();
            String replacement = replacementEdit.getText().toString();
            mCodeView.replaceAllMatches(regex, replacement);
        });

        dialog.setOnDismissListener(c -> mCodeView.clearMatches());
        dialog.show();
    }
}