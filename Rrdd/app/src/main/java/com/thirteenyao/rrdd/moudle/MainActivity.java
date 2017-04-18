package com.thirteenyao.rrdd.moudle;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thirteenyao.rrdd.R;
import com.thirteenyao.rrdd.base.activity.RootActivity;
import com.thirteenyao.rrdd.base.mvp.activity.BaseActivity;
import com.thirteenyao.rrdd.base.net.bean.RootResponseModel;
import com.thirteenyao.rrdd.moudle.news.bean.WeatherBean;
import com.thirteenyao.rrdd.moudle.news.fragment.NewsMainFragment;
import com.thirteenyao.rrdd.moudle.news.model.impl.TestModelImpl;
import com.thirteenyao.rrdd.moudle.news.presenter.WeatherPresenter;
import com.thirteenyao.rrdd.moudle.news.presenter.impl.WeatherPresenterImpl;
import com.thirteenyao.rrdd.moudle.news.view.IWeatherView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<IWeatherView,WeatherPresenterImpl> implements NavigationView.OnNavigationItemSelectedListener,IWeatherView {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

//    @BindView(R.id.fl_container)
//    FrameLayout mFrameLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.tv_test)
    TextView testTv;

    private int mItemId = -1;


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case R.id.nav_news:
                    Toast.makeText(MainActivity.this,"建设中！",Toast.LENGTH_LONG).show();
//                    replaceFragment(R.id.fl_container, new NewsMainFragment(), mSparseTags.get(R.id.nav_news));
                    break;
                case R.id.nav_photos:
                    Toast.makeText(MainActivity.this,"建设中！",Toast.LENGTH_LONG).show();

//                    replaceFragment(R.id.fl_container, new PhotoMainFragment(), mSparseTags.get(R.id.nav_photos));
                    break;
                case R.id.nav_videos:
                    Toast.makeText(MainActivity.this,"建设中！",Toast.LENGTH_LONG).show();

//                    replaceFragment(R.id.fl_container, new VideoMainFragment(), mSparseTags.get(R.id.nav_videos));
                    break;
                case R.id.nav_setting:
                    Toast.makeText(MainActivity.this,"建设中！",Toast.LENGTH_LONG).show();

//                    SettingsActivity.launch(HomeActivity.this);
                    break;
            }
            mItemId = -1;
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void createPresenter() {
        mPresenter=new WeatherPresenterImpl();

    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initView() {
        initDrawerLayout(mDrawerLayout,mNavigationView);

        mNavigationView.setCheckedItem(R.id.nav_news);
//        addFragment(R.id.fl_container,new NewsMainFragment(),"News");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if (item.isChecked()) {
            return true;
        }
        mItemId = item.getItemId();
        return true;
    }

    /**
     * 初始化 DrawerLayout
     *
     * @param drawerLayout DrawerLayout
     * @param navView      NavigationView
     */
    private void initDrawerLayout(DrawerLayout drawerLayout, NavigationView navView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            //将侧边栏顶部延伸至status bar
            drawerLayout.setFitsSystemWindows(true);
            //将主页面顶部延伸至status bar
            drawerLayout.setClipToPadding(false);
        }
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                mHandler.sendEmptyMessage(mItemId);
            }
        });
        navView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void toast(WeatherBean bean) {
        testTv.setText(bean.toString());
    }
    @OnClick(R.id.tv_test)
    public void onClick(){
        mPresenter.getWeather("北京");
    }
}
