package com.johnson.bid.bidding;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.johnson.bid.Bid;
import com.johnson.bid.MainActivity;
import com.johnson.bid.R;
import com.johnson.bid.data.Product;
import com.johnson.bid.util.ImageManager;

import static com.johnson.bid.MainMvpController.ENGLISH;

public class BiddingAdapter extends RecyclerView.Adapter {

    private BiddingContract.Presenter mPresenter;
    private LinearSnapHelper mLinearSnapHelper;
    private MainActivity mMainActivity;
    private String mAuctionType;
    private Product mProduct;

    public BiddingAdapter(BiddingContract.Presenter presenter, MainActivity mainActivity, String auctionType) {
        mPresenter = presenter;
        mMainActivity = mainActivity;
        mAuctionType = auctionType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (mAuctionType.equals(ENGLISH)) {
            return new EnglishViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_bid_page_e, viewGroup, false));
        } else {
            return new SealedViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_bid_page_s, viewGroup, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                Bid.getAppContext(), LinearLayoutManager.HORIZONTAL, false);
        BiddingGalleryAdapter biddingGalleryAdapter = new BiddingGalleryAdapter(mPresenter, mProduct.getImages());


        if (mAuctionType.equals(ENGLISH)) {
            if (mLinearSnapHelper == null) {
                mLinearSnapHelper = new LinearSnapHelper();
                mLinearSnapHelper.attachToRecyclerView(((EnglishViewHolder) viewHolder).getGalleryRecycler());
            }
            ((EnglishViewHolder) viewHolder).getGalleryRecycler().setAdapter(biddingGalleryAdapter);
            ((EnglishViewHolder) viewHolder).getGalleryRecycler().setLayoutManager(layoutManager);

            bindEnglishViewHolder((EnglishViewHolder) viewHolder, mProduct);
        } else {
            if (mLinearSnapHelper == null) {
                mLinearSnapHelper = new LinearSnapHelper();
                mLinearSnapHelper.attachToRecyclerView(((SealedViewHolder) viewHolder).getGalleryRecycler());
            }
            ((SealedViewHolder) viewHolder).getGalleryRecycler().setAdapter(biddingGalleryAdapter);
            ((SealedViewHolder) viewHolder).getGalleryRecycler().setLayoutManager(layoutManager);

            bindSealedViewHolder((SealedViewHolder) viewHolder, mProduct);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    private class EnglishViewHolder extends RecyclerView.ViewHolder {

        private Button mBackBtn;
        private RecyclerView mGalleryRecycler;
        private TextView mTitleText;
        private Button mEyesOnBtn;
        private TextView mIntroText;
        private TextView mConditionText;
        private TextView mLastTimeText;
        private TextView mPriceText;
        private TextView mIncreaseText;
        private TextView mBuyerText;
        private TextView mPeopleText;
        private TextView mSellerText;
        private Button mBidBtn;

        public EnglishViewHolder(@NonNull View itemView) {
            super(itemView);

            mBackBtn = itemView.findViewById(R.id.button_bid_back_e);
            mGalleryRecycler = itemView.findViewById(R.id.recycler_bid_gallery_e);
            mTitleText = itemView.findViewById(R.id.text_bid_title_e);
            mEyesOnBtn = itemView.findViewById(R.id.button_bid_eyes_on_e);
            mIntroText = itemView.findViewById(R.id.text_bid_intro_e);
            mConditionText = itemView.findViewById(R.id.text_bid_condition_e);
            mLastTimeText = itemView.findViewById(R.id.text_bid_last_time_e);
            mPriceText = itemView.findViewById(R.id.text_bid_price_e);
            mIncreaseText = itemView.findViewById(R.id.text_bid_increase_e);
            mBuyerText = itemView.findViewById(R.id.text_bid_buyer_e);
            mPeopleText = itemView.findViewById(R.id.text_bid_people_num_e);
            mSellerText = itemView.findViewById(R.id.text_bid_seller_e);
            mBidBtn = itemView.findViewById(R.id.button_bid_e);

            mBackBtn.setOnClickListener(v ->
                    mMainActivity.onBackPressed()
            );

            mBidBtn.setOnClickListener(v -> {
                mPresenter.openBidDialog(mProduct);
            });
        }

        private RecyclerView getGalleryRecycler() {
            return mGalleryRecycler;
        }

        private TextView getTitleText() {
            return mTitleText;
        }

        private TextView getIntroText() {
            return mIntroText;
        }

        private TextView getConditionText() {
            return mConditionText;
        }

        private TextView getLastTimeText() {
            return mLastTimeText;
        }

        private TextView getPriceText() {
            return mPriceText;
        }

        private TextView getIncreaseText() {
            return mIncreaseText;
        }

        private TextView getBuyerText() {
            return mBuyerText;
        }

        private TextView getPeopleText() {
            return mPeopleText;
        }

        private TextView getSellerText() {
            return mSellerText;
        }
    }

    private void bindEnglishViewHolder(EnglishViewHolder holder, Product product) {

        holder.getTitleText().setText(product.getTitle());
        holder.getIntroText().setText(product.getIntroduction());
        holder.getConditionText().setText(product.getCondition());
        holder.getLastTimeText().setText(String.valueOf(product.getExpired()));
        holder.getPriceText().setText(String.valueOf(product.getCurrentPrice()));
        holder.getIncreaseText().setText(String.valueOf(product.getIncrease()));
        holder.getBuyerText().setText(String.valueOf(product.getHighestUserId()));
        holder.getPeopleText().setText(String.valueOf(product.getParticipantsNumber()));
        holder.getSellerText().setText(String.valueOf(product.getSellerId()));

    }

    private class SealedViewHolder extends RecyclerView.ViewHolder {

        private Button mBackBtn;
        private RecyclerView mGalleryRecycler;
        private TextView mTitleText;
        private Button mEyesOnBtn;
        private TextView mIntroText;
        private TextView mConditionText;
        private TextView mLastTimeText;
        private TextView mSellerText;
        private Button mBidBtn;

        public SealedViewHolder(@NonNull View itemView) {
            super(itemView);

            mBackBtn = itemView.findViewById(R.id.button_bid_back_s);
            mGalleryRecycler = itemView.findViewById(R.id.recycler_bid_gallery_s);
            mTitleText = itemView.findViewById(R.id.text_bid_title_s);
            mEyesOnBtn = itemView.findViewById(R.id.button_bid_eyes_on_s);
            mIntroText = itemView.findViewById(R.id.text_bid_intro_s);
            mConditionText = itemView.findViewById(R.id.text_bid_condition_s);
            mLastTimeText = itemView.findViewById(R.id.text_bid_last_time_s);
            mSellerText = itemView.findViewById(R.id.text_bid_seller_s);
            mBidBtn = itemView.findViewById(R.id.button_bid_s);

            mBackBtn.setOnClickListener(v ->
                    mMainActivity.onBackPressed()
            );
        }

        private RecyclerView getGalleryRecycler() {
            return mGalleryRecycler;
        }

        private TextView getTitleText() {
            return mTitleText;
        }

        private TextView getIntroText() {
            return mIntroText;
        }

        private TextView getConditionText() {
            return mConditionText;
        }

        private TextView getLastTimeText() {
            return mLastTimeText;
        }

        private TextView getSellerText() {
            return mSellerText;
        }
    }

    private void bindSealedViewHolder(SealedViewHolder holder, Product product) {

        holder.getTitleText().setText(product.getTitle());
        holder.getIntroText().setText(product.getIntroduction());
        holder.getConditionText().setText(product.getCondition());
        holder.getLastTimeText().setText(String.valueOf(product.getExpired()));
        holder.getSellerText().setText(String.valueOf(product.getSellerId()));

    }

    public void updateData(Product product) {
        mProduct = product;
        notifyDataSetChanged();
    }
}