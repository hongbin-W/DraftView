package com.hongbin.library;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author WangHongBin ^_^
 * @date 2020/10/9 12:56
 **/
public class DraftControllerView extends LinearLayout {

    private DraftView draftView;

    private ImageView iv_next;

    private ImageView iv_prev;


    public DraftControllerView(Context context) {
        super(context);
        init(context);
    }

    public DraftControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DraftControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setOrientation(VERTICAL);
        this.setVisibility(GONE);
        setBackgroundColor(Color.parseColor("#00000000"));
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_custom_draft_control_view, null);
        addView(view);

        //获取view
        ImageView iv_cancel = view.findViewById(R.id.iv_cancel);
        ImageView iv_clear = view.findViewById(R.id.iv_clear);
        iv_next = view.findViewById(R.id.iv_next);
        iv_prev = view.findViewById(R.id.iv_prev);
        draftView = view.findViewById(R.id.draftView);

        //设置点击事件
        iv_cancel.setOnClickListener(onClickListener);
        iv_next.setOnClickListener(onClickListener);
        iv_clear.setOnClickListener(onClickListener);
        iv_prev.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.iv_cancel) {
                DraftControllerView.this.setVisibility(View.GONE);
//                    draftView.hide();
            } else if (id == R.id.iv_next) {
                draftView.nextCanvas();
            } else if (id == R.id.iv_clear) {
                draftView.clearCanvas();
            } else if (id == R.id.iv_prev) {
                draftView.preCanvas();
            }

            boolean haveNext = draftView.haveNext();

            boolean havePrev = draftView.havePrev();

            if (haveNext) {
                iv_next.setImageResource(R.mipmap.icon_draft_next_enable);
            } else {
                iv_next.setImageResource(R.mipmap.icon_draft_next_unable);
            }
            if (havePrev) {
                iv_prev.setImageResource(R.mipmap.icon_draft_prev_enable);
            } else {
                iv_prev.setImageResource(R.mipmap.icon_draft_prev_unable);
            }
        }
    };

    /**
     * 控件展示
     */
    public void show() {
        this.setVisibility(View.VISIBLE);
    }

    /**
     * 获取view对象
     */
    public DraftView getDraftView() {
        return draftView;
    }
}
