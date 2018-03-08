package ke.co.sombo.apps.circleimage;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resources = getResources();

        ImageView imageView = findViewById(R.id.image);

        Bitmap myBitmap = BitmapFactory.decodeResource(resources,R.drawable.before_cookie);
        imageView.setImageBitmap(myBitmap);

        RoundedBitmapDrawable circleBitmap = createCircleImage(myBitmap);
        imageView.setImageDrawable(circleBitmap);
    }

    private RoundedBitmapDrawable createCircleImage(Bitmap bitmap){

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int bitmapRadius = Math.min(bitmapWidth,bitmapHeight)/2;

        //so that the bitmap border will be 20
        int borderWidthHalf = 10;

        int bitmapSquareWidth = Math.min(bitmapWidth,bitmapHeight);
        int actualBitmapSquareWidth = bitmapSquareWidth + borderWidthHalf;

        /*/****
        passing actualBitmapSquareWidth as both width and height since a perfect circle
        has equal width and height (like a square)
         ****/
        Bitmap roundedBitmap = Bitmap.createBitmap(actualBitmapSquareWidth,actualBitmapSquareWidth, Bitmap.Config.ARGB_8888);

        //we're drawing the bitmap. so we need a canvas
        Canvas canvas = new Canvas(roundedBitmap);
        canvas.drawColor(Color.BLUE);

        int x = borderWidthHalf + bitmapSquareWidth - bitmapWidth;
        int y = borderWidthHalf + bitmapSquareWidth - bitmapHeight;
        //fill the canvas with bitmap.
        /* without this next step, It'll just draw a circle with a border
         but no Image*/
        canvas.drawBitmap(bitmap,x,y,null);

        Paint paint = new Paint();
        //we just need a line for the border
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidthHalf*2);
        //I chose a white border
        paint.setColor(Color.WHITE);
        //draw a circle which will be occupied by the bitmap
        canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,actualBitmapSquareWidth/2,paint);

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources,roundedBitmap);
        roundedBitmapDrawable.setCornerRadius(bitmapRadius);
        roundedBitmapDrawable.setAntiAlias(true);

        return roundedBitmapDrawable;

    }
}
