package com.alimuzaffar.lib.widgets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import alimuzaffar.com.lib.widgets.PinEntryEditText;
import alimuzaffar.com.lib.widgets.TextDrawable;

public class AnimatedEditTextWidgetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        EditText regular = (EditText) findViewById(R.id.txt_regular);
        final PinEntryEditText pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        if (pinEntry != null) {
            pinEntry.setAnimateText(true);
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals("1234")) {
                        Toast.makeText(AnimatedEditTextWidgetsActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AnimatedEditTextWidgetsActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                        pinEntry.setText(null);
                    }
                }
            });
        }
        if (regular != null) {
            regular.setCompoundDrawables(new TextDrawable(regular, "+61 "), null, new TextDrawable(regular, "\u2605"), null);
        }

    }

}
