package com.hongbin.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * @author WangHongBin ^_^
 * @date 2020/9/30 13:55
 **/
public class DraftView extends View {

    /**
     * 线条转角角度
     */
    private float radius;

    /**
     * 线条宽度
     */
    private float lineWidth;

    /**
     * 线条颜色
     */
    private int lineColor;

    /**
     * 背景色
     */
    private int backgroundColor;

    /**
     * 上一个元素放这里
     */
    private LinkedList<Path> previousList;

    /**
     * 下一个元素放这里
     */
    private LinkedList<Path> nextList;

    private Context context;

    public DraftView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        // 获取我们定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DraftView);
        radius = a.getFloat(R.styleable.DraftView_line_corner, 15.0f);
        lineWidth = a.getFloat(R.styleable.DraftView_line_width, 15.0f);
        lineColor = a.getColor(R.styleable.DraftView_paint_color, Color.BLACK);
        backgroundColor = a.getColor(R.styleable.DraftView_background_color, Color.parseColor("#52696969"));
        setBackgroundColor(backgroundColor);
        a.recycle();
        //初始化
        init();
    }


    private Paint paint;

    /**
     * 现在显示的path
     */
    private Path currPath;

    public void init() {
        previousList = new LinkedList<>();
        nextList = new LinkedList<>();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(lineColor);
        paint.setStyle(Paint.Style.STROKE);
        //设置画笔的线冒样式：
        paint.setStrokeCap(Paint.Cap.ROUND);
        //设置多次调用 Path.lineTo 这种线段之间连接处的样式。
        paint.setStrokeJoin(Paint.Join.ROUND);
        //setPathEffect
        //系统给我们提供了六种效果，分别是 CornerPathEffect、DashPathEffect、DiscretePathEffect、PathDashPathEffect、ComposePathEffect、SumPathEffect
        paint.setPathEffect(new CornerPathEffect(radius));
        paint.setStrokeWidth(lineWidth);

        currPath = new Path();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (nextList.size() > 0 && nextList.peekLast() != currPath) {
            Toast.makeText(context, "该页面已暂存，无法修改或删除了哦！", Toast.LENGTH_SHORT).show();
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float startX = event.getX();
                float startY = event.getY();
                currPath.moveTo(startX, startY);
                currPath.lineTo(startX + 0.1f, startY + 0.1f);
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                currPath.lineTo(moveX, moveY);
                break;
        }
        invalidate();
        return true;
    }

    private Canvas canvas;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        canvas.drawPath(currPath, paint);
    }

    /**
     * 清空画布
     *
     * @author WangHongBin ^_^
     * @date 2020/9/30 14:54
     * @modify-date
     **/
    public void clearCanvas() {
        if (nextList.size() > 0 && nextList.peekLast() != currPath) {
            Toast.makeText(context, "该页面已暂存，无法修改或删除了哦！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (canvas != null) {
            //保存数据
            previousList.add(currPath);
            //清除path路径
            currPath = new Path();
            //重新绘制
            invalidate();
        }
    }

    /**
     * 上一步结果
     */
    public void preCanvas() {
        if (previousList.size() > 0) {
            nextList.push(currPath);
            //取最后一个
            currPath = previousList.pollLast();
            invalidate();
        }
    }

    /**
     * 下一步结果
     */
    public void nextCanvas() {
        if (nextList.size() > 0) {
            previousList.add(currPath);
            currPath = nextList.pollFirst();
            invalidate();
        }
    }

    /**
     * 显示控件
     */
    public void show() {
        this.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏控件
     */
    public void hide() {
        this.setVisibility(View.GONE);
    }

    /**
     * 清空数据
     */
    public void clearData() {
        previousList.clear();
        nextList.clear();
        currPath = new Path();
    }

    /**
     * 释放资源
     */
    public void release() {
        previousList.clear();
        nextList.clear();
        //清除path路径
        currPath = null;
        //释放
        paint = null;
    }

    /**
     * 设置线条拐角角度
     */
    public void setRadius(float radius) {
        this.radius = radius;
        paint.setPathEffect(new CornerPathEffect(radius));
    }

    /**
     * 设置线条宽度
     */
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        paint.setStrokeWidth(lineWidth);
    }

    /**
     * 设置线条颜色
     */
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        paint.setColor(lineColor);
    }

    /**
     * 设置背景色
     */
    public void setBackground_Color(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBackgroundColor(backgroundColor);
    }

    /**
     * 是否有下一页
     */
    public boolean haveNext() {
        return nextList.size() != 0;
    }

    /**
     * 是否有上一页
     */
    public boolean havePrev() {
        return previousList.size() != 0;
    }
}
