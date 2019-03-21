package com.daf.cloudshare.ui.home;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;

import com.daf.cloudshare.R;
import com.daf.cloudshare.base.BaseFragment;
import com.daf.cloudshare.model.FavoriteBean;

import com.daf.cloudshare.model.TipBean;
import com.daf.cloudshare.model.TypeBean;
import com.daf.cloudshare.net.AppUrl;
import com.daf.cloudshare.net.HttpUtil;
import com.daf.cloudshare.ui.product.DetailFragment;
import com.daf.cloudshare.ui.favorite.FavoriteFragment;
import com.daf.cloudshare.ui.web.WebFragment;
import com.daf.cloudshare.model.BannerBean;
import com.daf.cloudshare.ui.product.ProductFragment;
import com.daf.cloudshare.utils.Const;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

/**
 * Created by PP on 2019/3/15.
 */
public class Home2Fragment extends BaseFragment {
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.tv_sub)
    TextView mTvSub;
    @BindView(R.id.root)
    RelativeLayout mRoot;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(R.id.tv2)
    TextView mTv2;
    @BindView(R.id.tv_sub2)
    TextView mTvSub2;
    @BindView(R.id.root2)
    RelativeLayout mRoot2;
    @BindView(R.id.iv3)
    ImageView mIv3;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.tv_sub3)
    TextView mTvSub3;
    @BindView(R.id.root3)
    RelativeLayout mRoot3;
    @BindView(R.id.iv4)
    ImageView mIv4;
    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.tv_sub4)
    TextView mTvSub4;
    @BindView(R.id.root4)
    RelativeLayout mRoot4;
    @BindView(R.id.tvMore)
    TextView mTvMore;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.flipper)
    ViewFlipper mFlipper;


    private List<FavoriteBean.DataBean> mData=new ArrayList<>();
    private TypeBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home2;
    }

    @Override
    protected void init() {
        initBanner();
        initAd();
        initType();
        initRecycler();

    }

    private void initRecycler() {


        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(),4));
        FavoriteHomeAdapter adapter=new FavoriteHomeAdapter(mData);
        mRecycler.setAdapter(adapter);

        View headView=LayoutInflater.from(getActivity()).inflate(R.layout.head_favorite,null);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(ProductFragment.getInstance("产品列表","", AppUrl.prjList));
            }
        });



        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.getTopFavorite, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        FavoriteBean bean=JSON.parseObject(response,FavoriteBean.class);
                        if (bean.data.size()==0){

                            mTvMore.setVisibility(View.GONE);
                            adapter.addHeaderView(headView);
                        }else{
                            mTvMore.setVisibility(View.VISIBLE);
                        }
                        adapter.setNewData(bean.data);


                    }
                });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                String id=((List<FavoriteBean.DataBean>)adapter.getData()).get(position).f_value;
                String name=((List<FavoriteBean.DataBean>)adapter.getData()).get(position).f_name;
                startFragment(DetailFragment.getInstance(id,name));

            }
        });

    }

    private void initType() {
        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.getProType, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) throws IOException {
                        mBean = JSON.parseObject(response, TypeBean.class);
                        mTv.setText(mBean.data.get(0).t_name);
                        mTvSub.setText(mBean.data.get(0).t_subtitle);
                        mTv2.setText(mBean.data.get(1).t_name);
                        mTvSub2.setText(mBean.data.get(1).t_subtitle);
                        mTv3.setText(mBean.data.get(2).t_name);
                        mTvSub3.setText(mBean.data.get(2).t_subtitle);
                        mTv4.setText(mBean.data.get(3).t_name);
                        mTvSub4.setText(mBean.data.get(3).t_subtitle);
                        Glide.with(getActivity()).load(mBean.data.get(0).t_logo).into(mIv);
                        Glide.with(getActivity()).load(mBean.data.get(1).t_logo).into(mIv2);
                        Glide.with(getActivity()).load(mBean.data.get(2).t_logo).into(mIv3);
                        Glide.with(getActivity()).load(mBean.data.get(3).t_logo).into(mIv4);

                    }
                });


    }

    private void initAd() {



        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.getIndexTip, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String s) throws IOException {
                        try {

                            JSONObject jsonObject=new JSONObject(s);
                            if (jsonObject.getString("code").equals(Const.body_success)){

                                TipBean tipBean=JSON.parseObject(s,TipBean.class);

                                for (int i=0;i<tipBean.data.size();i++){
                                    View flipper_item = LayoutInflater.from(getActivity()).inflate(R.layout.flipper_item,null);
                                    TextView index= flipper_item.findViewById(R.id.index);
                                    TextView index2= flipper_item.findViewById(R.id.index2);
                                    index.setText(tipBean.data.get(i).p_name+"产品最新上架");

                                    if (TextUtils.isEmpty(tipBean.data.get(i).p_resume)){
                                        index2.setVisibility(View.GONE);
                                    }else{
                                        index2.setVisibility(View.VISIBLE);
                                    }

                                    index2.setText(tipBean.data.get(i).p_resume);
                                    mFlipper.addView(flipper_item);
                                }



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


        mFlipper.setAutoStart(true);
        mFlipper.startFlipping();


    }

    @Override
    public void onStop() {
        super.onStop();
        mFlipper.stopFlipping();
    }

    @OnClick({R.id.root, R.id.root2, R.id.root3, R.id.root4, R.id.tvMore})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.root:
                startFragment(ProductFragment.getInstance(mBean.data.get(0).t_name,mBean.data.get(0).t_type,AppUrl.prjList));
                break;
            case R.id.root2:
                startFragment(ProductFragment.getInstance(mBean.data.get(1).t_name,mBean.data.get(1).t_type,AppUrl.prjList));
                break;
            case R.id.root3:
                startFragment(ProductFragment.getInstance(mBean.data.get(2).t_name,mBean.data.get(2).t_type,AppUrl.prjList));
                break;
            case R.id.root4:
                startFragment(ProductFragment.getInstance(mBean.data.get(3).t_name,mBean.data.get(3).t_type,AppUrl.prjList));
                break;
            case R.id.tvMore:
                startFragment(new FavoriteFragment());
                break;
        }
    }

    private void initBanner() {

        HttpUtil.getInstance(getActivity())
                .postForm(AppUrl.banner, null, new HttpUtil.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String s) throws IOException {
                        try {

                            JSONObject json=new JSONObject(s);
                            if (json.getString("code").equals(Const.body_success)){
                                BannerBean bannerBean= JSON.parseObject(s,BannerBean.class);
                                List<BannerBean.DataBean> mDatas= bannerBean.getData();
                                List<String> imgs=new ArrayList<>();
                                for (int i=0;i<mDatas.size();i++){
                                    imgs.add(mDatas.get(i).getIcon());
                                }
                                //
                                mBanner.setImages(imgs);
                                mBanner.setImageLoader(new ImageLoader() {
                                    @Override
                                    public void displayImage(Context context, Object path, ImageView imageView) {
                                        Glide.with(getActivity()).load(path).into(imageView);
                                    }
                                });
                                mBanner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {
                                        //点击
                                        String url=mDatas.get(position).getUrl();
                                        if (!TextUtils.isEmpty(url)){
                                            startFragment(WebFragment.getInstance(url));
                                        }


                                    }
                                }).start();



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });



    }
}
