package com.fanwe.live.appview.room;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;

import com.fanwe.library.activity.SDBaseActivity;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.fragment.LiveContLocalFragment;
import com.fanwe.live.fragment.LiveContTotalFragment;

/**
 * Created by shibx on 2017/2/6.
 *  直播间 贡献榜页面(代替fragment)
 */

public class RoomContributionView extends RoomView {

    private SDTabUnderline mTabToday;

    private SDTabUnderline mTabTotal;

    private SDSelectViewManager<SDTabUnderline> mSelectManager = new SDSelectViewManager<>();

    /** 用户id(String) */
    public static final String EXTRA_USER_ID = "extra_user_id";
    /** 房间id(int)*/
    public static final String EXTRA_ROOM_ID = "extra_room_id";

    private String mUserId;
    private int mRoomId;

    public RoomContributionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RoomContributionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoomContributionView(Context context) {
        super(context);
        init();
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.view_live_room_contribution;
    }

    @Override
    protected void init() {
        super.init();
        mTabToday = find(R.id.tab_con_today);
        mTabTotal = find(R.id.tab_con_total);
        getExtraDatas();
    }

    private void getExtraDatas() {
        if(getLiveInfo() == null) {
            return;
        }
        this.mUserId = getLiveInfo().getCreaterId();
        this.mRoomId = getLiveInfo().getRoomId();
        initConTab();
    }

    public void setExtraDatas(String userId, int roomId) {
        this.mUserId = userId;
        this.mRoomId = roomId;
        initConTab();
    }

    private void initConTab() {
        mTabToday.setTextTitle("当天排行");
        mTabToday.getViewConfig(mTabToday.mTvTitle).setTextColorNormalResId(R.color.text_black).setTextColorSelectedResId(R.color.main_color);
        mTabToday.getViewConfig(mTabToday.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelectedResId(R.color.main_color);

        mTabTotal.setTextTitle("累计排行");
        mTabTotal.getViewConfig(mTabTotal.mTvTitle).setTextColorNormalResId(R.color.text_black).setTextColorSelectedResId(R.color.main_color);
        mTabTotal.getViewConfig(mTabTotal.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelectedResId(R.color.main_color);

        mSelectManager.setListener(new SDSelectManager.SDSelectManagerListener<SDTabUnderline>() {

            @Override
            public void onNormal(int index, SDTabUnderline item) {
            }

            @Override
            public void onSelected(int index, SDTabUnderline item) {
                switch (index) {
                    case 0:
                        clickTodayConFrag();
                        break;
                    case 1:
                        clickTotalConFrag();
                        break;
                    default:
                        break;
                }
            }
        });

        mSelectManager.setItems(new SDTabUnderline[]{mTabToday, mTabTotal});
        mSelectManager.performClick(0);
    }

    private void clickTodayConFrag() {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_ROOM_ID, mRoomId);
        ((SDBaseActivity)getActivity()).getSDFragmentManager().toggle(R.id.ll_content, null, LiveContLocalFragment.class, bundle);
    }

    private void clickTotalConFrag() {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_USER_ID, mUserId);
        ((SDBaseActivity)getActivity()).getSDFragmentManager().toggle(R.id.ll_content, null, LiveContTotalFragment.class, bundle);
    }
}
