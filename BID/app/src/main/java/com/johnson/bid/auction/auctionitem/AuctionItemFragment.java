package com.johnson.bid.auction.auctionitem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnson.bid.MainMvpController;
import com.johnson.bid.R;
import com.johnson.bid.data.Product;
import com.johnson.bid.util.UserManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.johnson.bid.MainMvpController.ENGLISH;
import static com.johnson.bid.MainMvpController.SEALED;

public class AuctionItemFragment extends Fragment implements AuctionItemContract.View {

    private AuctionItemContract.Presenter mPresenter;
    private AuctionItemAdapter mAuctionItemAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String mAuctionType;

    public AuctionItemFragment() {
    }

    public static AuctionItemFragment newInstance() {
        return new AuctionItemFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuctionItemAdapter = new AuctionItemAdapter(mPresenter, mAuctionType);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recycler_swipe_container, container, false);

        mSwipeRefreshLayout = root.findViewById(R.id.swipe_layout);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_container_swipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAuctionItemAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadData();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(false);
            loadData();
        });

    }

    private void loadData() {
        switch (mAuctionType) {
            case ENGLISH:
                mPresenter.loadEnglishData();
                break;
            case SEALED:
                mPresenter.loadSealedData();
                break;
            default:
        }
    }

    @Override
    public void setPresenter(AuctionItemContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void setAuctionType(@MainMvpController.AuctionType String auctionType) {
        mAuctionType = auctionType;
    }

    @Override
    public void showAuctionUi(ArrayList<Product> productList) {
        mAuctionItemAdapter.updateData(productList);
    }

    @Override
    public void showSellingFailUi(Product product, String from) {

        mPresenter.removeSellingProductId(product.getProductId(), from);
        mPresenter.addNobodyBidProductId(product.getProductId(), from);
        mPresenter.increaseUnreadNobodyBid(from);

        mPresenter.loadMySellingData();
        mPresenter.loadNobodyBidData();
        mPresenter.loadNobodyBidBadgeData();
    }

    @Override
    public void showSoldSuccessUi(Product product, String from) {

        mPresenter.removeSellingProductId(product.getProductId(), from);
        mPresenter.addSoldProductId(product.getProductId(), from);
        mPresenter.increaseUnreadSold(from);

        mPresenter.loadMySellingData();
        mPresenter.loadMySoldData();
        mPresenter.loadSoldBadgeData();

        if (!UserManager.getInstance().hasChatRoom(product.getHighestUserId())) {
            mPresenter.createChatRoom(product, from);
        }
    }

    @Override
    public void showBoughtSuccessUi(Product product, String from) {
        mPresenter.removeBiddingProductId(product.getProductId(), from);
        mPresenter.addBoughtProductId(product.getProductId(), from);
        mPresenter.increaseUnreadBought(from);

        mPresenter.loadMyBiddingData();
        mPresenter.loadMyBoughtData();
        mPresenter.loadBoughtBadgeData();

        if (!UserManager.getInstance().hasChatRoom(product.getSellerId())) {
            mPresenter.createChatRoom(product, from);
        }
    }

    @Override
    public void showMyBiddingData() {
        mPresenter.loadMyBiddingData();
    }

    @Override
    public void showTradeBadge() {
        mPresenter.updateTradeBadge();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAuctionItemAdapter.cancelAllTimers();
    }
}
