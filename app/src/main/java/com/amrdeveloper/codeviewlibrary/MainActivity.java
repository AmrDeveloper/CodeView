package com.amrdeveloper.codeviewlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.amrdeveloper.codeview.CodeView;
import com.amrdeveloper.codeviewlibrary.syntax.Language;
import com.amrdeveloper.codeviewlibrary.syntax.SyntaxManager;

public class MainActivity extends AppCompatActivity {

    private CodeView mCodeView;

    private int mNextThemeIndex = 2;

    //To change themes easily
    private Language mCurrentLanguage = Language.JAVA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCodeView = findViewById(R.id.codeView);
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
            //Change CodeView Theme
            if(mNextThemeIndex > 4) {
                mNextThemeIndex = 1;
            }

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
                case 4:
                    SyntaxManager.applyOrangeBoxTheme(this, mCodeView, mCurrentLanguage);
                    Toast.makeText(this, "Orange Box", Toast.LENGTH_SHORT).show();
                    break;
            }

            mNextThemeIndex = mNextThemeIndex + 1;
        }
        return super.onOptionsItemSelected(item);
    }
}