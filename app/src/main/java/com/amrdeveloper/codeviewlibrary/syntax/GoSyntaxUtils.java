package com.amrdeveloper.codeviewlibrary.syntax;

import android.content.Context;

import com.amrdeveloper.codeview.CodeView;
import com.amrdeveloper.codeviewlibrary.R;

import java.util.regex.Pattern;

public class GoSyntaxUtils {

    //Language Keywords
    private static final Pattern PATTERN_KEYWORDS = Pattern.compile("\\b(break|default|func|interface|case|defer|" +
            "go|map|struct|chan|else|goto|package|switch|const" +
            "|fallthrough|if|range|type|continue|for|import|return|var|" +
            "string|true|false|new|nil|byte|bool|int|int8|int16|int32|int64)\\b");

    //Brackets and Colons
    private static final Pattern PATTERN_BUILTINS = Pattern.compile("[,:;[->]{}()]");

    //Data
    private static final Pattern PATTERN_NUMBERS = Pattern.compile("\\b(\\d*[.]?\\d+)\\b");
    private static final Pattern PATTERN_CHAR = Pattern.compile("'[a-zA-Z]'");
    private static final Pattern PATTERN_STRING = Pattern.compile("\".*\"");
    private static final Pattern PATTERN_HEX = Pattern.compile("0x[0-9a-fA-F]+");
    private static final Pattern PATTERN_TODO_COMMENT = Pattern.compile("//TODO[^\n]*");
    private static final Pattern PATTERN_COMMENT = Pattern.compile("//(?!TODO )[^\\n]*" + "|" + "/\\*(.|\\R)*?\\*/");
    private static final Pattern PATTERN_ATTRIBUTE = Pattern.compile("\\.[a-zA-Z0-9_]+");
    private static final Pattern PATTERN_OPERATION =Pattern.compile( ":|==|>|<|!=|>=|<=|->|=|>|<|%|-|-=|%=|\\+|\\-|\\-=|\\+=|\\^|\\&|\\|::|\\?|\\*");

    public static void applyMonokaiTheme(Context context, CodeView codeView) {
        codeView.resetSyntaxPatternList();
        //View Background
        codeView.setBackgroundColor(codeView.getResources().getColor(R.color.monokia_pro_black));

        //Syntax Colors
        codeView.addSyntaxPattern(PATTERN_HEX, context.getResources().getColor(R.color.monokia_pro_purple));
        codeView.addSyntaxPattern(PATTERN_CHAR, context.getResources().getColor(R.color.monokia_pro_green));
        codeView.addSyntaxPattern(PATTERN_STRING, context.getResources().getColor(R.color.monokia_pro_orange));
        codeView.addSyntaxPattern(PATTERN_NUMBERS, context.getResources().getColor(R.color.monokia_pro_purple));
        codeView.addSyntaxPattern(PATTERN_KEYWORDS, context.getResources().getColor(R.color.monokia_pro_pink));
        codeView.addSyntaxPattern(PATTERN_BUILTINS, context.getResources().getColor(R.color.monokia_pro_white));
        codeView.addSyntaxPattern(PATTERN_COMMENT, context.getResources().getColor(R.color.monokia_pro_grey));
        codeView.addSyntaxPattern(PATTERN_ATTRIBUTE, context.getResources().getColor(R.color.monokia_pro_sky));
        codeView.addSyntaxPattern(PATTERN_OPERATION, context.getResources().getColor(R.color.monokia_pro_pink));
        //Default Color
        codeView.setTextColor( context.getResources().getColor(R.color.monokia_pro_white));

        codeView.addSyntaxPattern(PATTERN_TODO_COMMENT, context.getResources().getColor(R.color.gold));

        codeView.reHighlightSyntax();
    }

    public static void applyNoctisWhiteTheme(Context context, CodeView codeView) {
        codeView.resetSyntaxPatternList();
        //View Background
        codeView.setBackgroundColor(codeView.getResources().getColor(R.color.noctis_white));

        //Syntax Colors
        codeView.addSyntaxPattern(PATTERN_HEX, context.getResources().getColor(R.color.noctis_purple));
        codeView.addSyntaxPattern(PATTERN_CHAR, context.getResources().getColor(R.color.noctis_green));
        codeView.addSyntaxPattern(PATTERN_STRING, context.getResources().getColor(R.color.noctis_green));
        codeView.addSyntaxPattern(PATTERN_NUMBERS, context.getResources().getColor(R.color.noctis_purple));
        codeView.addSyntaxPattern(PATTERN_KEYWORDS, context.getResources().getColor(R.color.noctis_pink));
        codeView.addSyntaxPattern(PATTERN_BUILTINS, context.getResources().getColor(R.color.noctis_dark_blue));
        codeView.addSyntaxPattern(PATTERN_COMMENT, context.getResources().getColor(R.color.noctis_grey));
        codeView.addSyntaxPattern(PATTERN_ATTRIBUTE, context.getResources().getColor(R.color.noctis_blue));
        codeView.addSyntaxPattern(PATTERN_OPERATION, context.getResources().getColor(R.color.monokia_pro_pink));

        //Default Color
        codeView.setTextColor( context.getResources().getColor(R.color.noctis_orange));

        codeView.addSyntaxPattern(PATTERN_TODO_COMMENT, context.getResources().getColor(R.color.gold));

        codeView.reHighlightSyntax();
    }

    public static void applyFiveColorsDarkTheme(Context context, CodeView codeView) {
        codeView.resetSyntaxPatternList();
        //View Background
        codeView.setBackgroundColor(codeView.getResources().getColor(R.color.five_dark_black));

        //Syntax Colors
        codeView.addSyntaxPattern(PATTERN_HEX, context.getResources().getColor(R.color.five_dark_purple));
        codeView.addSyntaxPattern(PATTERN_CHAR, context.getResources().getColor(R.color.five_dark_yellow));
        codeView.addSyntaxPattern(PATTERN_STRING, context.getResources().getColor(R.color.five_dark_yellow));
        codeView.addSyntaxPattern(PATTERN_NUMBERS, context.getResources().getColor(R.color.five_dark_purple));
        codeView.addSyntaxPattern(PATTERN_KEYWORDS, context.getResources().getColor(R.color.five_dark_purple));
        codeView.addSyntaxPattern(PATTERN_BUILTINS, context.getResources().getColor(R.color.five_dark_white));
        codeView.addSyntaxPattern(PATTERN_COMMENT, context.getResources().getColor(R.color.five_dark_grey));
        codeView.addSyntaxPattern(PATTERN_ATTRIBUTE, context.getResources().getColor(R.color.five_dark_blue));
        codeView.addSyntaxPattern(PATTERN_OPERATION, context.getResources().getColor(R.color.five_dark_purple));
        //Default Color
        codeView.setTextColor( context.getResources().getColor(R.color.five_dark_white));

        codeView.addSyntaxPattern(PATTERN_TODO_COMMENT, context.getResources().getColor(R.color.gold));

        codeView.reHighlightSyntax();
    }

    public static void applyOrangeBoxTheme(Context context, CodeView codeView) {
        codeView.resetSyntaxPatternList();
        //View Background
        codeView.setBackgroundColor(codeView.getResources().getColor(R.color.orange_box_black));

        //Syntax Colors
        codeView.addSyntaxPattern(PATTERN_HEX, context.getResources().getColor(R.color.gold));
        codeView.addSyntaxPattern(PATTERN_CHAR, context.getResources().getColor(R.color.orange_box_orange2));
        codeView.addSyntaxPattern(PATTERN_STRING, context.getResources().getColor(R.color.orange_box_orange2));
        codeView.addSyntaxPattern(PATTERN_NUMBERS, context.getResources().getColor(R.color.five_dark_purple));
        codeView.addSyntaxPattern(PATTERN_KEYWORDS, context.getResources().getColor(R.color.orange_box_orange1));
        codeView.addSyntaxPattern(PATTERN_BUILTINS, context.getResources().getColor(R.color.orange_box_grey));
        codeView.addSyntaxPattern(PATTERN_COMMENT, context.getResources().getColor(R.color.orange_box_dark_grey));
        codeView.addSyntaxPattern(PATTERN_ATTRIBUTE, context.getResources().getColor(R.color.orange_box_orange3));
        codeView.addSyntaxPattern(PATTERN_OPERATION, context.getResources().getColor(R.color.gold));
        //Default Color
        codeView.setTextColor(context.getResources().getColor(R.color.five_dark_white));

        codeView.addSyntaxPattern(PATTERN_TODO_COMMENT, context.getResources().getColor(R.color.gold));

        codeView.reHighlightSyntax();
    }

}
