package com.johnson.bid.bidding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FieldValue;
import com.johnson.bid.MainActivity;
import com.johnson.bid.R;
import com.johnson.bid.data.Product;
import com.johnson.bid.util.Firebase;
import com.johnson.bid.util.UserManager;

import java.util.ArrayList;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;

public class BiddingDetailFragment extends Fragment implements BiddingDetailContract.View {

    private BiddingDetailContract.Presenter mPresenter;
    private BiddingDetailAdapter mBiddingDetailAdapter;
    private String mAuctionType;
    private ArrayList<Long> myEyesOn;
    private Boolean isEyesOn = false;
    private Product mProduct;

    public BiddingDetailFragment() {
    }

    public static BiddingDetailFragment newInstance() {
        return new BiddingDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBiddingDetailAdapter = new BiddingDetailAdapter(mPresenter, (MainActivity) getActivity(), mAuctionType);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recycler_container, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mBiddingDetailAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.loadProductData();
    }

    @Override
    public void setPresenter(BiddingDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.showToolbarAndBottomNavigation();

        mPresenter.updateCenterData();

    }

    public void setAuctionType(String type) {
        mAuctionType = type;
    }

    @Override
    public void showBiddingUi(Product product) {
        if (mBiddingDetailAdapter == null) {
            mBiddingDetailAdapter = new BiddingDetailAdapter(mPresenter, (MainActivity) getActivity(), mAuctionType);
        }
        mProduct = product;
        mBiddingDetailAdapter.updateData(product);
    }
}
