package com.liompei.youquanhelper.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Liompei
 * time : 2017/10/10 9:39
 * 1137694912@qq.com
 * remark:解决嵌套ScrollView显示不正常问题
 */

public class MyGridView extends GridView {

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


    //1.android:numColumns="auto_fit" //GridView的列数设置为自动
    //2.android:columnWidth="90dp " //每列的宽度，也就是Item的宽度
    //3.android:stretchMode="columnWidth"//缩放与列宽大小同步
    //4.android:verticalSpacing="10dp" //两行之间的边距
    //5.android:horizontalSpacing="10dp" //两列之间的边距
    //6.android:cacheColorHint="#00000000" //去除拖动时默认的黑色背景
    //7.android:listSelector="#00000000" //去除选中时的黄色底色
    //8.android:scrollbars="none" //隐藏GridView的滚动条
    //9.android:fadeScrollbars="true" //设置为true就可以实现滚动条的自动隐藏和显示
    //10.android:fastScrollEnabled="true" //GridView出现快速滚动的按钮(至少滚动4页才会显示)
    //11.android:fadingEdge="none" //GridView衰落(褪去)边缘颜色为空，缺省值是vertical。(可以理解为上下边缘的提示色)
    //12.android:fadingEdgeLength="10dip" //定义的衰落(褪去)边缘的长度
    //13.android:stackFromBottom="true" //设置为true时，你做好的列表就会显示你列表的最下面
    //14.android:transcriptMode="alwaysScroll" //当你动态添加数据时，列表将自动往下滚动最新的条目可以自动滚动到可视范围内
    //15.android:drawSelectorOnTop="false" //点击某条记录不放，颜色会在记录的后面成为背景色,内容的文字可见(缺省为false)
}
