package com.sqq.app.util;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shuaiqiangqiang
 * @version 1.0 2018/4/9
 * @since JDK 1.7
 */
public class ObserveRecyclerViewActivity extends Activity implements ObservableScrollViewCallbacks {

    private List<String> mList;
    private ViewGroup linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_observe_recyclerview);
        mList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mList.add(String.valueOf(i + 1));
        }
        ObservableRecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter());

        linearLayout = findViewById(R.id.linear_layout);
        recyclerView.setScrollViewCallbacks(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {  // 拖拽结果回调
        System.out.println("-------------scrollState:" + scrollState);
        if (scrollState == ScrollState.UP) {
            moveLayout(-linearLayout.getHeight());
        } else if (scrollState == ScrollState.DOWN) {
            moveLayout(0);
        }
    }

    // 布局移动动画效果
    private void moveLayout(int toTranslationY) {
        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        if (toTranslationY == params.topMargin) {
            System.out.println("-------------not move------------");
            return;
        }
        ValueAnimator animator = ValueAnimator.ofInt(params.topMargin, toTranslationY).setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.topMargin = (int) animation.getAnimatedValue();
                System.out.println("#####" + params.topMargin);
                linearLayout.setLayoutParams(params);
            }
        });
        animator.start();
    }

    private class Adapter extends RecyclerView.Adapter<MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(ObserveRecyclerViewActivity.this).inflate(R.layout.recycler_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.textView.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder {

        TextView textView;

        MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}
