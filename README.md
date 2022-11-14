# 1. Audio Knob View for controlling Volume, Adjustment Knob Widget, Meter View
##### Canvas/app/src/main/java/com/dotrinh/canvas/draw_arc/draw_knob/DrawKnobActivity.java

<img src="https://user-images.githubusercontent.com/8064517/201647699-3148583f-b791-466e-99f2-86b02725ca45.png" width="40%">

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

<img src="https://user-images.githubusercontent.com/8064517/193721801-f47185e3-39de-4547-8f90-f8bd609ae4dc.png" width="40%">



# License
See the license file and any additional license information attached to each sample.

# @dotrinh
