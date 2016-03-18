# AnimatedEditText for Android

This is an EditText capable of performing 4 animation from transition in text as a users types.

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



## Other features

- Specify any mask you want for the text.

## Known limitations

- Only `android:gravity="left|right|center_horizontal"` is supported.
- No RTL language support.
- Animate in from middle doesn't work properly except from `android:gravity="left"`
- Not all features of EditText will work
- PinEntryView will only perform bottom up or pop-in animations.