package com.mycomet.cc916647.mycomet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by cmcoffee91 on 3/31/18.
 */

public class CometAnimation extends View {


    private float cx, cy, x, y, w, h, radius;
    Context c;
    int angle;

    public CometAnimation(Context context){
        super(context);
        c = context;
        angle = 0;
        radius = 50;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint ellipsePaint = new Paint();
        ellipsePaint.setStyle(Paint.Style.STROKE);
        ellipsePaint.setColor(Color.RED);

        Paint circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.BLUE);

        /*
        try{
            Thread.sleep(10);
        }
        catch (InterruptedException e){
            Toast.makeText(c, "Fatal error during the sleep interval", Toast.LENGTH_SHORT).show();
        }

        */



        //get width and height of the view
        h = getHeight();
        w = getWidth();

        cx = w/2;
        cy = h/2;


        Path path = new Path();
        path.addOval(new RectF(100,100,w-100,h-100),Path.Direction.CW);

        //draw ellipse path
        canvas.drawPath(path,ellipsePaint);

        //draw the circle
        canvas.drawCircle(x, y,radius,circlePaint);


        angle = ( (angle + 1) % 360 );

        //Log.e("CometAnimation", "angle:" + angle);

        updateEllipseCoordinates();


        // invalidate whole view and invoke the callback onDraw()
        invalidate();
    }



    private void updateEllipseCoordinates(){

       /*
       https://www.mathopenref.com/coordparamellipse.html
        */


        float radians = (float) Math.toRadians(angle);
        float a = (cx - 100);
        float b = (cy - 100);

        x = cx + a*(float)Math.cos(radians);
        y = cy + b*(float)Math.sin(radians);

       // Log.e("CometAnimation","x ellipse coord:" + Float.toString(x));
      //  Log.e("CometAnimation","y ellipse coord:" + Float.toString(y));


    }
}
