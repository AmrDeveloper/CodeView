package com.amrdeveloper.codeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.text.Editable;

import android.text.InputFilter;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeView extends AppCompatMultiAutoCompleteTextView implements Findable, Replaceable {

    private int tabWidth = 0;
    private int tabLength = 0;
    private int tabWidthInCharacters;
    private int mUpdateDelayTime = 500;

    private boolean modified = true;
    private boolean highlightWhileTextChanging = true;

    private boolean hasErrors = false;
    private boolean mRemoveErrorsWhenTextChanged = true;

    private Rect lineNumberRect;
    private Paint lineNumberPaint;
    private boolean enableLineNumber = false;

    private int currentIndentation = 0;
    private boolean enableAutoIndentation = false;
    private final Set<Character> indentationStarts = new HashSet<>();
    private final Set<Character> indentationEnds = new HashSet<>();

    private int currentMatchedIndex = -1;
    private int matchingColor = Color.YELLOW;
    private CharacterStyle currentMatchedToken;
    private final List<Token> matchedTokens = new ArrayList<>();

    private final Handler mUpdateHandler = new Handler();
    private MultiAutoCompleteTextView.Tokenizer mAutoCompleteTokenizer;

    private static final Pattern PATTERN_LINE = Pattern.compile("(^.+$)+", Pattern.MULTILINE);
    private static final Pattern PATTERN_TRAILING_WHITE_SPACE = Pattern.compile("[\\t ]+$", Pattern.MULTILINE);

    private final SortedMap<Integer, Integer> mErrorHashSet = new TreeMap<>();
    private final Map<Pattern, Integer> mSyntaxPatternMap = new HashMap<>();

    public CodeView(Context context) {
        super(context);
        initEditorView();
    }

    public CodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditorView();
    }

    public CodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEditorView();
    }

    private void initEditorView() {
        if(mAutoCompleteTokenizer == null)
            mAutoCompleteTokenizer = new KeywordTokenizer();

        setTokenizer(mAutoCompleteTokenizer);
        setHorizontallyScrolling(true);
        setFilters(new InputFilter[]{mInputFilter});
        addTextChangedListener(mEditorTextWatcher);

        lineNumberRect = new Rect();
        lineNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineNumberPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (enableLineNumber) {
            int baseline;
            int lineCount = getLineCount();
            int lineNumber = 1;

            for (int i = 0; i < lineCount; ++i) {
                baseline = getLineBounds(i, null);
                if (i == 0 || getText().charAt(getLayout().getLineStart(i) - 1) == '\n') {
                    canvas.drawText(String.format(Locale.ENGLISH, " %d", lineNumber), lineNumberRect.left, baseline, lineNumberPaint);
                    ++lineNumber;
                }
            }

            int paddingLeft = 40 + (int) (Math.log10(lineCount) + 1) * 10;
            setPadding(paddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onDraw(canvas);
    }

    @Override
    public List<Token> findMatches(String regex) {
        matchedTokens.clear();
        if (regex.isEmpty()) return matchedTokens;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(getText());
        while (matcher.find()) matchedTokens.add(new Token(matcher.start(), matcher.end()));
        return matchedTokens;
    }

    @Override
    public Token findNextMatch() {
        if (matchedTokens.isEmpty()) return null;
        currentMatchedIndex++;
        if (currentMatchedIndex >= matchedTokens.size()) currentMatchedIndex = 0;
        Token currentMatch = matchedTokens.get(currentMatchedIndex);
        clearHighlightingMatchingToken();
        highlightMatchingToken(currentMatch);
        return currentMatch;
    }

    @Override
    public Token findPrevMatch() {
        if (matchedTokens.isEmpty()) return null;
        currentMatchedIndex--;
        if (currentMatchedIndex < 0) currentMatchedIndex = 0;
        Token currentMatch = matchedTokens.get(currentMatchedIndex);
        clearHighlightingMatchingToken();
        highlightMatchingToken(currentMatch);
        return currentMatch;
    }

    @Override
    public void clearMatches() {
        clearHighlightingMatchingToken();
        currentMatchedToken = null;
        currentMatchedIndex = -1;
        matchedTokens.clear();
    }

    @Override
    public void replaceFirstMatch(String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        String text = pattern.matcher(getText().toString()).replaceFirst(replacement);
        setTextHighlighted(text);
    }

    @Override
    public void replaceAllMatches(String regex, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        String text = pattern.matcher(getText().toString()).replaceAll(replacement);
        setTextHighlighted(text);
    }

    private void highlightSyntax(Editable editable) {
        if(mSyntaxPatternMap.isEmpty()) return;

        for(Pattern pattern : mSyntaxPatternMap.keySet()) {
            int color = mSyntaxPatternMap.get(pattern);
            for (Matcher m = pattern.matcher(editable); m.find();) {
                createForegroundColorSpan(editable, m, color);
            }
        }
    }

    private void highlightErrorLines(Editable editable) {
        if(mErrorHashSet.isEmpty()) return;
        int maxErrorLineValue = mErrorHashSet.lastKey();

        int lineNumber = 0;
        Matcher matcher = PATTERN_LINE.matcher(editable);
        while (matcher.find()) {
            if(mErrorHashSet.containsKey(lineNumber)) {
                int color = mErrorHashSet.get(lineNumber);
                createBackgroundColorSpan(editable, matcher, color);
            }
            lineNumber = lineNumber + 1;
            if(lineNumber > maxErrorLineValue) break;
        }
    }

    private void createForegroundColorSpan(Editable editable, Matcher matcher, @ColorInt int color) {
        editable.setSpan(new ForegroundColorSpan(color),
                matcher.start(), matcher.end(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void createBackgroundColorSpan(Editable editable, Matcher matcher, @ColorInt int color) {
        editable.setSpan(new BackgroundColorSpan(color),
                matcher.start(), matcher.end(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void highlightMatchingToken(Token token) {
        currentMatchedToken = new BackgroundColorSpan(matchingColor);
        getEditableText().setSpan(currentMatchedToken,
                token.getStart(), token.getEnd(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void clearHighlightingMatchingToken() {
        if (currentMatchedToken == null) return;
        getEditableText().removeSpan(currentMatchedToken);
    }

    private Editable highlight(Editable editable) {
        if(editable.length() == 0) return editable;
        try {
            clearSpans(editable);
            highlightErrorLines(editable);
            highlightSyntax(editable);
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return editable;
    }

    private void highlightWithoutChange(Editable editable) {
        modified = false;
        highlight(editable);
        modified = true;
    }

    public void setTextHighlighted(CharSequence text) {
        if (text == null || text.length() == 0) return;

        cancelHighlighterRender();

        removeAllErrorLines();

        modified = false;
        setText(highlight(new SpannableStringBuilder(text)));
        modified = true;
    }

    public void setTabLength(int length) {
        tabLength = length;
    }

    public void setTabWidth(int characters) {
        if (tabWidthInCharacters == characters) return;
        tabWidthInCharacters = characters;
        tabWidth = Math.round(getPaint().measureText("m") * characters);
    }

    private void clearSpans(Editable editable) {
        int length = editable.length();
        ForegroundColorSpan[] foregroundSpans = editable.getSpans(
                0,length, ForegroundColorSpan.class);

        for (int i = foregroundSpans.length; i-- > 0;)
            editable.removeSpan(foregroundSpans[i]);

        BackgroundColorSpan[] backgroundSpans = editable.getSpans(
                0, length, BackgroundColorSpan.class);

        for (int i = backgroundSpans.length; i-- > 0;)
            editable.removeSpan(backgroundSpans[i]);
    }

    public void cancelHighlighterRender() {
        mUpdateHandler.removeCallbacks(mUpdateRunnable);
    }

    private void convertTabs(Editable editable, int start, int count) {
        if (tabWidth < 1) return;

        String s = editable.toString();

        for (int stop = start + count;
             (start = s.indexOf("\t", start)) > -1 && start < stop;
             ++start) {
            editable.setSpan(new CodeView.TabWidthSpan(),
                    start,
                    start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public void setSyntaxPatternsMap(Map<Pattern, Integer> syntaxPatterns) {
        if(!mSyntaxPatternMap.isEmpty()) mSyntaxPatternMap.clear();
        mSyntaxPatternMap.putAll(syntaxPatterns);
    }

    public void addSyntaxPattern(Pattern pattern, @ColorInt int Color) {
        mSyntaxPatternMap.put(pattern, Color);
    }

    public void removeSyntaxPattern(Pattern pattern) {
        mSyntaxPatternMap.remove(pattern);
    }

    public int getSyntaxPatternsSize() {
        return mSyntaxPatternMap.size();
    }

    public void resetSyntaxPatternList() {
        mSyntaxPatternMap.clear();
    }

    public void setEnableAutoIndentation(boolean enableAutoIndentation) {
        this.enableAutoIndentation = enableAutoIndentation;
    }

    public void setIndentationStarts(Set<Character> characters) {
        indentationStarts.clear();
        indentationStarts.addAll(characters);
    }

    public void setIndentationEnds(Set<Character> characters) {
        indentationEnds.clear();
        indentationEnds.addAll(characters);
    }

    public void addErrorLine(int lineNum, int color) {
        mErrorHashSet.put(lineNum, color);
        hasErrors = true;
    }

    public void removeErrorLine(int lineNum) {
        mErrorHashSet.remove(lineNum);
        hasErrors = mErrorHashSet.size() > 0;
    }

    public void removeAllErrorLines() {
        mErrorHashSet.clear();
        hasErrors = false;
    }

    public int getErrorsSize() {
        return mErrorHashSet.size();
    }

    public String getTextWithoutTrailingSpace() {
        return PATTERN_TRAILING_WHITE_SPACE
                .matcher(getText())
                .replaceAll("");
    }

    public void setAutoCompleteTokenizer(MultiAutoCompleteTextView.Tokenizer tokenizer) {
        mAutoCompleteTokenizer = tokenizer;
    }

    public void setRemoveErrorsWhenTextChanged(boolean removeErrors) {
        mRemoveErrorsWhenTextChanged = removeErrors;
    }

    public void reHighlightSyntax() {
        highlightSyntax(getEditableText());
    }

    public void reHighlightErrors() {
        highlightErrorLines(getEditableText());
    }

    public boolean isHasError() {
        return hasErrors;
    }

    public void setUpdateDelayTime(int time) {
        mUpdateDelayTime = time;
    }

    public int getUpdateDelayTime() {
        return mUpdateDelayTime;
    }

    public void setHighlightWhileTextChanging(boolean updateWhileTextChanging) {
        this.highlightWhileTextChanging = updateWhileTextChanging;
    }

    public void setEnableLineNumber(boolean enableLineNumber) {
        this.enableLineNumber = enableLineNumber;
    }

    public boolean isLineNumberEnabled() {
        return enableLineNumber;
    }

    public void setLineNumberTextColor(int color) {
        lineNumberPaint.setColor(color);
    }

    public void setLineNumberTextSize(float size) {
        lineNumberPaint.setTextSize(size);
    }

    public void setMatchingHighlightColor(int color) {
        matchingColor = color;
    }

    @Override
    public void showDropDown() {
        int[] screenPoint = new int[2];
        getLocationOnScreen(screenPoint);

        final Rect displayFrame = new Rect();
        getWindowVisibleDisplayFrame(displayFrame);

        int position = getSelectionStart();
        Layout layout = getLayout();
        int line = layout.getLineForOffset(position);
        int lineButton = layout.getLineBottom(line);
        int dropDownVerticalOffset = lineButton + 140;
        int dropDownHorizontalOffset = (int) layout.getPrimaryHorizontal(position);
        setDropDownVerticalOffset(dropDownVerticalOffset);
        setDropDownHorizontalOffset(dropDownHorizontalOffset);

        super.showDropDown();
    }

    private final Runnable mUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            Editable source = getText();
            highlightWithoutChange(source);
        }
    };

    private final TextWatcher mEditorTextWatcher = new TextWatcher() {

        private int start;
        private int count;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            this.start = start;
            this.count = count;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (!modified) return;

            if(highlightWhileTextChanging) {
                if (mSyntaxPatternMap.size() > 0) {
                    convertTabs(getEditableText(), start, count);
                    mUpdateHandler.postDelayed(mUpdateRunnable, mUpdateDelayTime);
                }
            }

            if (mRemoveErrorsWhenTextChanged) removeAllErrorLines();

            if (enableAutoIndentation && count == 1) {
                if (indentationStarts.contains(charSequence.charAt(start)))
                    currentIndentation += tabLength;
                else if (indentationEnds.contains(charSequence.charAt(start)))
                    currentIndentation -= tabLength;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(!highlightWhileTextChanging) {
                if (!modified) return;

                cancelHighlighterRender();

                if (mSyntaxPatternMap.size() > 0) {
                    convertTabs(getEditableText(), start, count);
                    mUpdateHandler.postDelayed(mUpdateRunnable, mUpdateDelayTime);
                }
            }
        }
    };

    private final class TabWidthSpan extends ReplacementSpan {

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text,
                           int start, int end, Paint.FontMetricsInt fm) {
            return tabWidth;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text,
                         int start, int end, float x,
                         int top, int y, int bottom, @NonNull Paint paint) {
        }
    }

    private final InputFilter mInputFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dStart, int dEnd) {
            if (modified && enableAutoIndentation && start < source.length()) {
                boolean isInsertedAtEnd = dest.length() == dEnd;
                if (source.charAt(start) == '\n') {
                    int indentation = isInsertedAtEnd
                            ? currentIndentation
                            : calculateSourceIndentation(dest.subSequence(0, dStart));
                    return applyIndentation(source, indentation);
                }
            }
            return source;
        }
    };

    private CharSequence applyIndentation(CharSequence source, int indentation) {
        StringBuilder sourceCode = new StringBuilder();
        sourceCode.append(source);
        for (int i = 0; i < indentation; i++) sourceCode.append(" ");
        return sourceCode.toString();
    }

    private int calculateSourceIndentation(CharSequence source) {
        int indentation = 0;
        String[] lines = source.toString().split("\n");
        for (String line : lines) {
            indentation += calculateExtraIndentation(line);
        }
        return indentation;
    }

    private int calculateExtraIndentation(String line) {
        if (line.isEmpty()) return 0;
        char firstChar = line.charAt(line.length() - 1);
        if (indentationStarts.contains(firstChar)) return tabLength;
        else if (indentationEnds.contains(firstChar)) return -tabLength;
        return 0;
    }
}
