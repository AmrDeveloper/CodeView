package com.amrdeveloper.codeviewlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.amrdeveloper.codeview.Code;
import com.amrdeveloper.codeview.CodeView;
import com.amrdeveloper.codeview.Keyword;
import com.amrdeveloper.codeviewlibrary.syntax.Theme;
import com.amrdeveloper.codeviewlibrary.syntax.Language;
import com.amrdeveloper.codeviewlibrary.syntax.SyntaxManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private CodeView codeView;
    private SyntaxManager syntaxManager;

    private Language currentLanguage = Language.JAVA;
    private Theme currentTheme = Theme.MONOKAI;

    private final boolean useModernAutoCompleteAdapter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configCodeView();
    }

    private void configCodeView() {
        codeView = findViewById(R.id.codeView);

        // Change default font to JetBrains Mono font
        Typeface jetBrainsMono = ResourcesCompat.getFont(this, R.font.jetbrains_mono_medium);
        codeView.setTypeface(jetBrainsMono);

        // Setup Line number feature
        codeView.setEnableLineNumber(true);
        codeView.setLineNumberTextColor(Color.GRAY);
        codeView.setLineNumberTextSize(25f);

        // Setup Auto indenting feature
        codeView.setTabLength(4);
        codeView.setEnableAutoIndentation(true);

        Set<Character> indentationStart = new HashSet<>();
        indentationStart.add('{');
        codeView.setIndentationStarts(indentationStart);

        Set<Character> indentationEnds = new HashSet<>();
        indentationEnds.add('}');
        codeView.setIndentationEnds(indentationEnds);

        // Setup the language and theme with SyntaxManager helper class
        syntaxManager = new SyntaxManager(this, codeView);
        syntaxManager.applyTheme(currentLanguage,currentTheme);

        // Setup the auto complete for the current language
        configLanguageAutoComplete();
    }

    private void configLanguageAutoComplete() {
        final String[] languageKeywords;
        switch (currentLanguage) {
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

        if (useModernAutoCompleteAdapter) {
            final List<Code> codeList = new ArrayList<>();
            for (String keyword : languageKeywords) {
                codeList.add(new Keyword(keyword, keyword));
            }
            // Here you can add snippets to the codeList
            CustomCodeViewAdapter adapter = new CustomCodeViewAdapter(this, codeList);
            codeView.setAdapter(adapter);
        } else {
            // Custom list item xml layout
            final int layoutId = R.layout.list_item_suggestion;

            // TextView id to put suggestion on it
            final int viewId = R.id.suggestItemTextView;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layoutId, viewId, languageKeywords);

            // Add Custom Adapter to the CodeView
            codeView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int menuItemId = item.getItemId();
        final int menuGroupId = item.getGroupId();

        if (menuGroupId == R.id.group_languages) changeTheEditorLanguage(menuItemId);
        else if (menuGroupId == R.id.group_themes) changeTheEditorTheme(menuItemId);
        else if (menuItemId == R.id.findMenu) launchEditorButtonSheet();
        else if (menuItemId == R.id.clearText) codeView.setText("");

        return super.onOptionsItemSelected(item);
    }

    private void changeTheEditorLanguage(int languageId) {
        final Language oldLanguage = currentLanguage;
        if (languageId == R.id.language_java) currentLanguage = Language.JAVA;
        else if (languageId == R.id.language_python) currentLanguage = Language.PYTHON;
        else if(languageId == R.id.language_go) currentLanguage = Language.GO_LANG;

        if (currentLanguage != oldLanguage) {
            syntaxManager.applyTheme(currentLanguage, currentTheme);
            configLanguageAutoComplete();
        }
    }
    
    private void changeTheEditorTheme(int themeId) {
        final Theme oldTheme = currentTheme;
        if (themeId == R.id.theme_monokia) currentTheme = Theme.MONOKAI;
        else if (themeId == R.id.theme_noctics) currentTheme = Theme.NOCTIS_WHITE;
        else if(themeId == R.id.theme_five_color) currentTheme = Theme.FIVE_COLOR;
        else if(themeId == R.id.theme_orange_box) currentTheme = Theme.ORANGE_BOX;

        if (currentTheme != oldTheme) {
            syntaxManager.applyTheme(currentLanguage, currentTheme);
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
                if (text.isEmpty()) codeView.clearMatches();
                codeView.findMatches(text);
            }
        });

        findPrevAction.setOnClickListener(v -> {
            codeView.findPrevMatch();
        });

        findNextAction.setOnClickListener(v -> {
            codeView.findNextMatch();
        });

        replacementAction.setOnClickListener(v -> {
            String regex = searchEdit.getText().toString();
            String replacement = replacementEdit.getText().toString();
            codeView.replaceAllMatches(regex, replacement);
        });

        dialog.setOnDismissListener(c -> codeView.clearMatches());
        dialog.show();
    }
}