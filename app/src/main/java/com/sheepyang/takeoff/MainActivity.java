package com.sheepyang.takeoff;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Integer size = 50;
    private static final String TAG = "takeoff";
    private ImageView ivPre;
    int startX;//声明startX变量
    int startY;//声明startY变量
    private Canvas canvas;
    private Paint paint;
    private Path path;
    private Bitmap srcBitmap;
    private Bitmap copyPreBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void restart(View v) {
        srcBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.pre19);
        copyPreBitmap =  Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
        canvas =  new Canvas(copyPreBitmap);
        path = new Path();//实例化画图类

        paint =  new Paint();        //抗锯齿
        paint.setColor(Color.RED);//设置画笔颜色
        paint.setStrokeWidth(size);//设置描边宽度
        BlurMaskFilter bmf = new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL);//指定了一个模糊的样式和半径来处理Paint的边缘。
        paint. setMaskFilter(bmf);//为Paint分配边缘效果。
        paint.setStyle(Paint.Style.STROKE);//让画出的图形是空心的
        paint.setStrokeJoin(Paint.Join.ROUND);//设置结合处的样子 Miter:结合处为锐角， Round:结合处为圆弧：BEVEL：结合处为直线。
        paint.setStrokeCap(Paint.Cap.SQUARE);//画笔笔刷类型   方形形状

        canvas.drawBitmap(srcBitmap, new Matrix(), paint);
        ivPre.setImageBitmap(copyPreBitmap);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));//它的作用是用此画笔后，画笔划过的痕迹就变成透明色了。画笔设置好了后，就可以调用该画笔进行橡皮痕迹的绘制了
    }

    private void initView() {
        ivPre = (ImageView) findViewById(R.id.ivPre);
        srcBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.pre19);
        copyPreBitmap =  Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), srcBitmap.getConfig());
        canvas =  new Canvas(copyPreBitmap);
        path = new Path();//实例化画图类

        paint =  new Paint();        //抗锯齿
        paint.setColor(Color.RED);//设置画笔颜色
        paint.setStrokeWidth(size);//设置描边宽度
        BlurMaskFilter bmf = new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL);//指定了一个模糊的样式和半径来处理Paint的边缘。
        paint. setMaskFilter(bmf);//为Paint分配边缘效果。
        paint.setStyle(Paint.Style.STROKE);//让画出的图形是空心的
        paint.setStrokeJoin(Paint.Join.ROUND);//设置结合处的样子 Miter:结合处为锐角， Round:结合处为圆弧：BEVEL：结合处为直线。
        paint.setStrokeCap(Paint.Cap.SQUARE);//画笔笔刷类型   方形形状

        canvas.drawBitmap(srcBitmap, new Matrix(), paint);
        ivPre.setImageBitmap(copyPreBitmap);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));//它的作用是用此画笔后，画笔划过的痕迹就变成透明色了。画笔设置好了后，就可以调用该画笔进行橡皮痕迹的绘制了
        ivPre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                switch(event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        for (int i = -size; i < size; i++) {
//                            for (int j = -size; j < size; j++) {
//                                if (Math.sqrt(i*i + j*j) < size) {
//                                    if ((int)event.getX() + i > 0 && (int)event.getY() + j > 0) {
////                                        Log.i(TAG, "ACTION_MOVE:" + (int)event.getX() + i + "," + (int)event.getY() + j);
//                                        copyPreBitmap.setPixel((int)event.getX() + i, (int)event.getY() + j, Color.TRANSPARENT);
//                                    }
//                                }
//                            }
//                        }
//                        ivPre.setImageBitmap(copyPreBitmap);
//                        break;
//                    default:
//                        break;
//                }
                /////////////////////////
                //方法二
                int x = (int) event.getX();//获得触摸的X轴位置
                int y = (int) event.getY();//获得触摸的Y轴位置
                int position = event.getAction();//获得的返回值 获取触控动作比如ACTION_DOWN

                int endX = 0;//声明变量endX
                int endY = 0;//声明变量endY

                switch (position){
                    case MotionEvent.ACTION_DOWN://当触摸时按下时
                        startX = x;
                        startY = y;
                        break;
                    case MotionEvent.ACTION_MOVE://当触摸移动时
                        endX = x;
                        endY = y;
                        path.moveTo(startX, startY);//起始点
                        path.lineTo(endX, endY);//终点
                        canvas.drawPath(path, paint);//绘制图像
                        ivPre.postInvalidate();//刷新界面

                        startX = endX;//将 endX值 也就是停止触摸时X轴的位置 付给 startX当中
                        startY = endY;//将 endY值 也就是停止触摸时X轴的位置 付给 startY当中
                        break;
                    case MotionEvent.ACTION_UP://当触摸结束时
                        break;
                }
                return true;
            }
        });
    }
}
