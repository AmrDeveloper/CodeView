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
import android.widget.TextView;

import com.amrdeveloper.codeview.Code;
import com.amrdeveloper.codeview.CodeView;
import com.amrdeveloper.codeviewlibrary.plugin.CommentManager;
import com.amrdeveloper.codeviewlibrary.plugin.SourcePositionListener;
import com.amrdeveloper.codeviewlibrary.syntax.ThemeName;
import com.amrdeveloper.codeviewlibrary.syntax.LanguageName;
import com.amrdeveloper.codeviewlibrary.syntax.LanguageManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private CodeView codeView;
    private LanguageManager languageManager;
    private CommentManager commentManager;

    private TextView languageNameText;
    private TextView sourcePositionText;

    private LanguageName currentLanguage = LanguageName.JAVA;
    private ThemeName currentTheme = ThemeName.MONOKAI;

    private final boolean useModernAutoCompleteAdapter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configCodeView();
        configCodeViewPlugins();
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

        // Setup the language and theme with SyntaxManager helper class
        languageManager = new LanguageManager(this, codeView);
        languageManager.applyTheme(currentLanguage, currentTheme);

        // Setup auto pair complete
        final Map<Character, Character> pairCompleteMap = new HashMap<>();
        pairCompleteMap.put('{', '}');
        pairCompleteMap.put('[', ']');
        pairCompleteMap.put('(', ')');
        pairCompleteMap.put('<', '>');
        pairCompleteMap.put('"', '"');
        pairCompleteMap.put('\'', '\'');

        codeView.setPairCompleteMap(pairCompleteMap);
        codeView.enablePairComplete(true);
        codeView.enablePairCompleteCenterCursor(true);

        // Setup the auto complete and auto indenting for the current language
        configLanguageAutoComplete();
        configLanguageAutoIndentation();
    }

    private void configLanguageAutoComplete() {
        if (useModernAutoCompleteAdapter) {
            // Load the code list (keywords and snippets) for the current language
            List<Code> codeList = languageManager.getLanguageCodeList(currentLanguage);

            // Use CodeViewAdapter or custom one
            CustomCodeViewAdapter adapter = new CustomCodeViewAdapter(this, codeList);

            // Add the odeViewAdapter to the CodeView
            codeView.setAdapter(adapter);
        } else {
            String[] languageKeywords = languageManager.getLanguageKeywords(currentLanguage);

            // Custom list item xml layout
            final int layoutId = R.layout.list_item_suggestion;

            // TextView id to put suggestion on it
            final int viewId = R.id.suggestItemTextView;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, layoutId, viewId, languageKeywords);

            // Add the ArrayAdapter to the CodeView
            codeView.setAdapter(adapter);
        }
    }

    private void configLanguageAutoIndentation() {
        codeView.setIndentationStarts(languageManager.getLanguageIndentationStarts(currentLanguage));
        codeView.setIndentationEnds(languageManager.getLanguageIndentationEnds(currentLanguage));
    }

    private void configCodeViewPlugins() {
        commentManager = new CommentManager(codeView);
        configCommentInfo();

        languageNameText = findViewById(R.id.language_name_txt);
        configLanguageName();

        sourcePositionText = findViewById(R.id.source_position_txt);
        sourcePositionText.setText(getString(R.string.source_position, 0, 0));
        configSourcePositionListener();
    }

    private void configCommentInfo() {
        commentManager.setCommentStart(languageManager.getCommentStart(currentLanguage));
        commentManager.setCommendEnd(languageManager.getCommentEnd(currentLanguage));
    }

    private void configLanguageName() {
        languageNameText.setText(currentLanguage.name().toLowerCase());
    }

    private void configSourcePositionListener() {
        SourcePositionListener sourcePositionListener = new SourcePositionListener(codeView);
        sourcePositionListener.setOnPositionChanged((line, column) -> {
            sourcePositionText.setText(getString(R.string.source_position, line, column));
        });
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
        else if (menuItemId == R.id.comment) commentManager.commentSelected();
        else if (menuItemId == R.id.un_comment) commentManager.unCommentSelected();
        else if (menuItemId == R.id.clearText) codeView.setText("");

        return super.onOptionsItemSelected(item);
    }

    private void changeTheEditorLanguage(int languageId) {
        final LanguageName oldLanguage = currentLanguage;
        if (languageId == R.id.language_java) currentLanguage = LanguageName.JAVA;
        else if (languageId == R.id.language_python) currentLanguage = LanguageName.PYTHON;
        else if(languageId == R.id.language_go) currentLanguage = LanguageName.GO_LANG;

        if (currentLanguage != oldLanguage) {
            languageManager.applyTheme(currentLanguage, currentTheme);
            configLanguageName();
            configLanguageAutoComplete();
            configLanguageAutoIndentation();
            configCommentInfo();
        }
    }
    
    private void changeTheEditorTheme(int themeId) {
        final ThemeName oldTheme = currentTheme;
        if (themeId == R.id.theme_monokia) currentTheme = ThemeName.MONOKAI;
        else if (themeId == R.id.theme_noctics) currentTheme = ThemeName.NOCTIS_WHITE;
        else if(themeId == R.id.theme_five_color) currentTheme = ThemeName.FIVE_COLOR;
        else if(themeId == R.id.theme_orange_box) currentTheme = ThemeName.ORANGE_BOX;

        if (currentTheme != oldTheme) {
            languageManager.applyTheme(currentLanguage, currentTheme);
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