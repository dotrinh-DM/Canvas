# 1. Audio Knob View for controlling Volume, Adjustment Knob Widget, Meter View
##### Canvas/app/src/main/java/com/dotrinh/canvas/draw_arc/draw_knob/DrawKnobActivity.java

<p align="center">
<img src="https://user-images.githubusercontent.com/8064517/201647699-3148583f-b791-466e-99f2-86b02725ca45.png" width="40%">
</p>

##### Ez to use
        MyKnob myKnob = findViewById(R.id.knob_1);
        myKnob.relative_size = (int) convertDpToPx(70);
        myKnob.current_content = MyKnob.CONTENT_TYPE.TEXT_ONLY;

        MyKnob myKnob2 = findViewById(R.id.knob_2);
        myKnob2.relative_size = (int) convertDpToPx(70);
        myKnob2.center_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.verified);
        myKnob2.current_content = MyKnob.CONTENT_TYPE.IMG_ONLY;

        MyKnob myKnob3 = findViewById(R.id.knob_3);
        myKnob3.relative_size = (int) convertDpToPx(70);
        myKnob3.left_bmp = BitmapFactory.decodeResource(getResources(), R.drawable.editampnote);
        myKnob3.current_content = MyKnob.CONTENT_TYPE.IMG_AND_TEXT;

# 2. Circular progress
##### Canvas/app/src/main/java/com/dotrinh/canvas/circle_progress/CPActivity.java

<p align="center">
<img src="https://user-images.githubusercontent.com/8064517/193721801-f47185e3-39de-4547-8f90-f8bd609ae4dc.png" width="40%">
</p>


# 3. Draw Lines (Wave)
##### Canvas/app/src/main/java/com/dotrinh/canvas/draw_lines/MyDrawLine.java

<p align="center">
<img src="https://user-images.githubusercontent.com/8064517/203308322-c981647d-e1de-4a04-bead-13376954ba24.png" width="40%">
</p>

# 4. Draw Texts (Vertical - Horizontal)
##### Canvas/app/src/main/java/com/dotrinh/canvas/draw_text/DrawTextActivity.java
##### Japanese Texts

<p align="center">
<img src="https://user-images.githubusercontent.com/8064517/203309685-07177d45-88fc-4fa6-abb9-669345e1677b.png" width="40%">
</p>

# License
free

# @dotrinh
