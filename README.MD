# AnimatedEditText for Android

This is an EditText capable of performing 4 animation from transition in text as a users types.

## Features

- Text animates in as typed.
- PinEntryEditText which supports animations.
- TextDrawable which allows you to set and use text as a drawable.
- AnimatedEditText and PinEntryEditText let you specify any mask you want for the text.


## Usage

### How to include dependecy in gradle

`compile 'com.alimuzaffar.lib:animated-edit-text:0.0.1'`

### Setup in XML

        <com.alimuzaffar.lib.widgets.PinEntryEditText
            android:id="@+id/txt_pin_entry"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:cursorVisible="false"
            android:digits="1234567890"
            android:inputType="number"
            android:maxLength="4"
            android:textIsSelectable="false"
            android:textSize="20sp"
            android:visibility="visible"
			app:pinCharacterMask="*" //optional
            app:pinLineColors="@color/pin_line_colors" />

        <com.alimuzaffar.lib.widgets.AnimatedEditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:hint="Animate pop in"
            android:inputType="textNoSuggestions"
            app:animationType="popIn" />

### In Code:

        EditText regular = (EditText) findViewById(R.id.txt_regular);
        final PinEntryEditText pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        if (pinEntry != null) {
            pinEntry.setAnimateText(true); //needed, no animation by default for PinEntryEditText
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
			
## Supported Animations

1. PopIn (default)
2. Bottom Up
3. In from right
4. In from middle

You can see a YouTube video of all the animations here: [YouTube video](https://www.youtube.com/watch?v=VW6vFzniehU)

### Pop-in animation

![pop in animation](http://i.giphy.com/x1AsZJypT6rmw.gif)

### Bottom Up Animations

![bottom up animation](http://i.giphy.com/DwawaNNbE3UKQ.gif)

### In from right

![animate in from right](http://i.giphy.com/Nzd38Q57CJ5zq.gif|alt=animate in from right)

### In from middle

![animate in from middle](http://i.giphy.com/8WKKm4nTM3N60.gif)nimate in from middle)


## Known limitations

- Animation will work best if `android:inputType="textNoSuggestions"` is set.
- Only `android:gravity="left|right|center_horizontal"` is supported.
- No RTL language support.
- Animate in from middle doesn't work properly except from `android:gravity="left"`
- Not all features of EditText will work
- PinEntryView will only perform bottom up or pop-in animations.