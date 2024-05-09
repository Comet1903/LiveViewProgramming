enum Font { 
    ARIAL("Arial"),
    VERDANA("Verdana"),
    TIMES("Times New Roman"),
    COURIER("Courier New"),
    SERIF("serif"),
    SANSSERIF("sans-serif");

    final String fullName; 

    private Font(String fullName) { this.fullName = fullName; }
    
    public String toString() { return fullName;}

    static enum Align {
        CENTER, LEFT, RIGHT;
        public String toString() { return name().toLowerCase(); }    
    }
}

class Turtle implements Clerk {
    final String ID;
    LiveView view;
    final int width, height;
    Font textFont = Font.SANSSERIF;
    int  textSize = 10;
    Font.Align textAlign = Font.Align.CENTER;

    Turtle(LiveView view, int width, int height) {
        this.view = view;
        this.width  = Math.max(1, Math.abs(width));  // width is at least of size 1
        this.height = Math.max(1, Math.abs(height)); // height is at least of size 1
        ID = Clerk.getHashID(this);
        Clerk.load(view, "clerks/Turtle/turtle.js");
        Clerk.write(view, STR."""
            <canvas id="turtleCanvas\{ID}" width="\{this.width}" height="\{this.height}" style="border:1px solid #000;">
            </canvas>
            """);
        Clerk.script(view, STR."const turtle\{ID} = new Turtle(document.getElementById('turtleCanvas\{ID}'));");
    }

    Turtle(LiveView view) { this(view, 500, 500); }
    Turtle(int width, int height) { this(Clerk.view(), width, height); }
    Turtle() { this(Clerk.view()); }

    Turtle penDown() {
        Clerk.call(view, STR."turtle\{ID}.penDown();");
        return this;
    }

    Turtle penUp() {
        Clerk.call(view, STR."turtle\{ID}.penUp();");
        return this;
    }

    Turtle forward(double distance) {
        Clerk.call(view, STR."turtle\{ID}.forward(\{distance});");
        return this;
    }

    Turtle backward(double distance) {
        Clerk.call(view, STR."turtle\{ID}.backward(\{distance});");
        return this;
    }

    Turtle left(double degrees) {
        Clerk.call(view, STR."turtle\{ID}.left(\{degrees});");
        return this;
    }

    Turtle right(double degrees) {
        Clerk.call(view, STR."turtle\{ID}.right(\{degrees});");
        return this;
    }

    Turtle color(int red, int green, int blue) {
        Clerk.call(view, STR."turtle\{ID}.color('rgb(\{red & 0xFF}, \{green & 0xFF}, \{blue & 0xFF})');");
        return this;
    }

    Turtle color(int rgb) {
        color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
        return this;
    }

    Turtle lineWidth(double width) {
        Clerk.call(view, STR."turtle\{ID}.lineWidth('\{width}');");
        return this;
    }

    Turtle reset() {
        Clerk.call(view, STR."turtle\{ID}.reset();");
        return this;
    }

    Turtle text(String text, Font font, int size, Font.Align align) {
        textFont = font;
        textSize = size;
        textAlign = align;
        Clerk.call(view, STR."turtle\{ID}.text('\{text}', '\{"" + size + "px " + font}', '\{align}')");
        return this;
    }

    Turtle text(String text) { return text(text, textFont, textSize, textAlign); }
}
