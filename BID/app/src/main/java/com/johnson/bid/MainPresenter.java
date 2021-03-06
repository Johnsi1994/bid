package com.johnson.bid;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.johnson.bid.bidding.BiddingDetailContract;
import com.johnson.bid.bidding.BiddingDetailPresenter;
import com.johnson.bid.bought.BoughtDetailContract;
import com.johnson.bid.bought.BoughtDetailPresenter;
import com.johnson.bid.auction.auctionitem.AuctionItemContract;
import com.johnson.bid.auction.auctionitem.AuctionItemPresenter;
import com.johnson.bid.chat.ChatContract;
import com.johnson.bid.chat.ChatPresenter;
import com.johnson.bid.auction.AuctionContract;
import com.johnson.bid.auction.AuctionPresenter;
import com.johnson.bid.auction.auctionitem.AuctionItemFragment;
import com.johnson.bid.chat.chatcontent.ChatContentContract;
import com.johnson.bid.chat.chatcontent.ChatContentPresenter;
import com.johnson.bid.data.ChatContent;
import com.johnson.bid.data.ChatRoom;
import com.johnson.bid.data.Product;
import com.johnson.bid.dialog.MessageDialog;
import com.johnson.bid.eyeson.EyesOnContract;
import com.johnson.bid.eyeson.EyesOnPresenter;
import com.johnson.bid.login.LoginContract;
import com.johnson.bid.login.LoginPresenter;
import com.johnson.bid.nobodybit.NobodyBidDetailContract;
import com.johnson.bid.nobodybit.NobodyBidDetailPresenter;
import com.johnson.bid.post.PostContract;
import com.johnson.bid.post.PostPresenter;
import com.johnson.bid.search.SearchContract;
import com.johnson.bid.search.SearchPresenter;
import com.johnson.bid.selling.SellingDetailContract;
import com.johnson.bid.selling.SellingDetailPresenter;
import com.johnson.bid.settings.SettingsContract;
import com.johnson.bid.settings.SettingsPresenter;
import com.johnson.bid.sold.SoldDetailContract;
import com.johnson.bid.sold.SoldDetailPresenter;
import com.johnson.bid.trade.TradeContract;
import com.johnson.bid.trade.TradeItem.TradeItemContract;
import com.johnson.bid.trade.TradeItem.TradeItemFragment;
import com.johnson.bid.trade.TradeItem.TradeItemPresenter;
import com.johnson.bid.trade.TradePresenter;
import com.johnson.bid.util.Constants;
import com.johnson.bid.util.Firebase;
import com.johnson.bid.util.UserManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.johnson.bid.MainMvpController.ENGLISH;
import static com.johnson.bid.dialog.MessageDialog.BID_SUCCESS;

public class MainPresenter implements MainContract.Presenter, AuctionContract.Presenter, TradeContract.Presenter,
        ChatContract.Presenter, SettingsContract.Presenter, AuctionItemContract.Presenter, LoginContract.Presenter,
        PostContract.Presenter, TradeItemContract.Presenter, BiddingDetailContract.Presenter, SellingDetailContract.Presenter,
        BoughtDetailContract.Presenter, SoldDetailContract.Presenter, NobodyBidDetailContract.Presenter, EyesOnContract.Presenter,
        SearchContract.Presenter, ChatContentContract.Presenter {

    private MainContract.View mMainView;

    private LoginPresenter mLoginPresenter;
    private AuctionPresenter mAuctionPresenter;
    private TradePresenter mTradePresenter;
    private ChatPresenter mChatPresenter;
    private SettingsPresenter mSettingsPresenter;
    private PostPresenter mPostPresenter;
    private BiddingDetailPresenter mBiddingDetailPresenter;
    private SellingDetailPresenter mSellingDetailPresenter;
    private BoughtDetailPresenter mBoughtDetailPresenter;
    private SoldDetailPresenter mSoldDetailPresenter;
    private NobodyBidDetailPresenter mNobodyBidDetailPresenter;
    private EyesOnPresenter mEyesOnPresenter;
    private SearchPresenter mSearchPresenter;
    private ChatContentPresenter mChatContentPresenter;

    private AuctionItemPresenter mEnglishAuctionItemPresenter;
    private AuctionItemPresenter mSealedAuctionItemPresenter;

    private TradeItemPresenter mMyBiddingPresenter;
    private TradeItemPresenter mMySellingPresenter;
    private TradeItemPresenter mMyBoughtPresenter;
    private TradeItemPresenter mMySoldPresenter;
    private TradeItemPresenter mNobodyBidPresenter;

    public MainPresenter(MainContract.View mainView) {
        mMainView = checkNotNull(mainView, "mainView cannot be null!");
        mMainView.setPresenter(this);
    }

    void setLoginPresenter(LoginPresenter loginPresenter) {
        mLoginPresenter = checkNotNull(loginPresenter);
    }

    void setPostPresenter(PostPresenter postPresenter) {
        mPostPresenter = checkNotNull(postPresenter);
    }

    void setSearchPresenter(SearchPresenter searchPresenter) {
        mSearchPresenter = checkNotNull(searchPresenter);
    }

    void setAuctionPresenter(AuctionPresenter auctionPresenter) {
        mAuctionPresenter = checkNotNull(auctionPresenter);
    }

    void setTradePresenter(TradePresenter tradePresenter) {
        mTradePresenter = checkNotNull(tradePresenter);
    }

    void setChatPresenter(ChatPresenter chatPresenter) {
        mChatPresenter = checkNotNull(chatPresenter);
    }

    void setSettingsPresenter(SettingsPresenter settingsPresenter) {
        mSettingsPresenter = checkNotNull(settingsPresenter);
    }

    void setEyesOnPresenter(EyesOnPresenter eyesOnPresenter) {
        mEyesOnPresenter = checkNotNull(eyesOnPresenter);
    }

    void setChatContentPresenter(ChatContentPresenter chatContentPresenter) {
        mChatContentPresenter = checkNotNull(chatContentPresenter);
    }

    void setEnglishAuctionItemPresenter(AuctionItemPresenter englishAuctionItemPresenter) {
        mEnglishAuctionItemPresenter = checkNotNull(englishAuctionItemPresenter);
    }

    void setSealedAuctionItemPresenter(AuctionItemPresenter sealedAuctionItemPresenter) {
        mSealedAuctionItemPresenter = checkNotNull(sealedAuctionItemPresenter);
    }

    void setMyBiddingPresenter(TradeItemPresenter myBiddingPresenter) {
        mMyBiddingPresenter = checkNotNull(myBiddingPresenter);
    }

    void setMySellingPresenter(TradeItemPresenter mySellingPresenter) {
        mMySellingPresenter = checkNotNull(mySellingPresenter);
    }

    void setMyBoughtPresenter(TradeItemPresenter myBoughtPresenter) {
        mMyBoughtPresenter = checkNotNull(myBoughtPresenter);
    }

    void setMySoldPresenter(TradeItemPresenter mySoldPresenter) {
        mMySoldPresenter = checkNotNull(mySoldPresenter);
    }

    void setNobodyBitPresenter(TradeItemPresenter nobodyBitPresenter) {
        mNobodyBidPresenter = checkNotNull(nobodyBitPresenter);
    }

    void setBiddingDetailPresenter(BiddingDetailPresenter biddingDetailPresenter) {
        mBiddingDetailPresenter = checkNotNull(biddingDetailPresenter);
    }

    void setSellingDetailPresenter(SellingDetailPresenter sellingDetailPresenter) {
        mSellingDetailPresenter = checkNotNull(sellingDetailPresenter);
    }

    void setBoughtDetailPresenter(BoughtDetailPresenter boughtDetailPresenter) {
        mBoughtDetailPresenter = checkNotNull(boughtDetailPresenter);
    }

    void setSoldDetailPresenter(SoldDetailPresenter soldDetailPresenter) {
        mSoldDetailPresenter = checkNotNull(soldDetailPresenter);
    }

    void setNobodyBidDetailPresenter(NobodyBidDetailPresenter nobodyBidDetailPresenter) {
        mNobodyBidDetailPresenter = checkNotNull(nobodyBidDetailPresenter);
    }

    @Override
    public void openLogin() {
        mMainView.openLoginUi();
    }

    @Override
    public void openCenter() {
        mMainView.openCenterUi();
    }

    @Override
    public void updateAuctionData() {
        mEnglishAuctionItemPresenter.loadEnglishData();
        mSealedAuctionItemPresenter.loadSealedData();
    }

    @Override
    public void loadBiddingFreshData() {
        mBiddingDetailPresenter.loadBiddingFreshData();
    }

    @Override
    public void setSellerHasRead(boolean isRead) {
        mPostPresenter.setSellerHasRead(isRead);
    }

    @Override
    public void setBuyerHasRead(boolean isRead) {
        mPostPresenter.setBuyerHasRead(isRead);
    }

    @Override
    public void setSellerName(String name) {
        mPostPresenter.setSellerName(name);
    }

    @Override
    public void uploadImages(int i, UserManager.LoadCallback loadCallback) {
        mPostPresenter.uploadImages(i, loadCallback);
    }

    @Override
    public void uploadProduct(long id) {
        mPostPresenter.uploadProduct(id);
    }

    @Override
    public void openTrade() {
        mMainView.openTradeUi();
    }

    @Override
    public void openChat() {
        mMainView.openChatUi();
    }

    @Override
    public void openSettings() {
        mMainView.openSettingsUi();
    }

    @Override
    public void updateToolbar(String title) {
        mMainView.setToolbarTitleUi(title);
    }

    @Override
    public void chatWithSeller() {
        mBoughtDetailPresenter.chatWithSeller();
    }

    @Override
    public void hideToolbar() {
        mMainView.hideToolbarUi();
    }

    @Override
    public void showToolbar() {
        mMainView.showToolbarUi();
    }

    @Override
    public void chatWithBuyer() {
        mSoldDetailPresenter.chatWithBuyer();
    }

    @Override
    public void setPostPics(ArrayList<Bitmap> imageBitmap) {
        mPostPresenter.setPostPics(imageBitmap);
    }

    @Override
    public void loadPostPics() {
        mPostPresenter.loadPostPics();
    }

    @Override
    public void hideToolbarAndBottomNavigation() {
        mMainView.hideToolbarUi();
        mMainView.hideBottomNavigationUi();
    }

    @Override
    public void loadSearchData() {
        mSearchPresenter.loadSearchData();
    }

    @Override
    public void setKeyword(String keyword) {
        mSearchPresenter.setKeyword(keyword);
    }

    @Override
    public void loadEyesOnData() {
        mEyesOnPresenter.loadEyesOnData();
    }

    @Override
    public void setMyEyesOnData(ArrayList<Product> products) {
        mEyesOnPresenter.setMyEyesOnData(products);
    }

    @Override
    public void loadEnglishData() {
        mEnglishAuctionItemPresenter.loadEnglishData();
    }

    @Override
    public void setEnglishData(ArrayList<Product> productList) {
        mEnglishAuctionItemPresenter.setEnglishData(productList);
    }

    @Override
    public void loadSealedData() {
        mSealedAuctionItemPresenter.loadSealedData();
    }

    @Override
    public void setSealedData(ArrayList<Product> productList) {
        mSealedAuctionItemPresenter.setSealedData(productList);
    }

    @Override
    public void showToolbarAndBottomNavigation() {
        mMainView.showToolbarUi();
        mMainView.showBottomNavigationUi();
    }

    @Override
    public void setNobodyBitDetailData(Product product) {
        mNobodyBidDetailPresenter.setNobodyBitDetailData(product);
    }

    @Override
    public void loadNobodyBidDetailData() {
        mNobodyBidDetailPresenter.loadNobodyBidDetailData();
    }

    @Override
    public void loadBoughtDetailData() {
        mBoughtDetailPresenter.loadBoughtDetailData();
    }

    @Override
    public void setBoughtDetailData(Product product) {
        mBoughtDetailPresenter.setBoughtDetailData(product);
    }

    @Override
    public void setSellingDetailData(Product product) {
        mSellingDetailPresenter.setSellingDetailData(product);
    }

    @Override
    public void loadSellingDetailData() {
        mSellingDetailPresenter.loadSellingDetailData();
    }

    @Override
    public void openDeleteProductDialog(Product product) {
        mMainView.openDeleteProductDialog(product);
    }

    @Override
    public void loadSellingFreshData() {
        mSellingDetailPresenter.loadSellingFreshData();
    }

    @Override
    public void setProductData(Product product) {
        mBiddingDetailPresenter.setProductData(product);
    }

    @Override
    public void loadProductData() {
        mBiddingDetailPresenter.loadProductData();
    }

    @Override
    public void openBidDialog(String from, Product product) {
        mMainView.openBidDialog(from, product);
    }

    @Override
    public void hideBottomNavigation() {
        mMainView.hideBottomNavigationUi();
    }

    @Override
    public void loadChatData() {
        mChatPresenter.loadChatData();
    }

    @Override
    public void setChatData(ArrayList<ChatRoom> chatRoomArrayList) {
        mChatPresenter.setChatData(chatRoomArrayList);
    }

    @Override
    public void showBottomNavigation() {
        mMainView.showBottomNavigationUi();
    }

    @Override
    public void sendMessage(ChatContent chatContent) {
        mChatContentPresenter.sendMessage(chatContent);
    }

    @Override
    public void setChatRoomData(ChatRoom chatRoom) {
        mChatContentPresenter.setChatRoomData(chatRoom);
    }

    @Override
    public void loadChatContentData() {
        mChatContentPresenter.loadChatContentData();
    }

    @Override
    public void setChatListener() {
        mChatContentPresenter.setChatListener();
    }

    @Override
    public void updateTradeBadge() {

        int unreadCount = UserManager.getInstance().getUser().getUnreadBought() +
                UserManager.getInstance().getUser().getUnreadSold() +
                UserManager.getInstance().getUser().getUnreadNobodyBid();

        mMainView.updateTradeBadgeUi(unreadCount);
    }

    @Override
    public void openSearch(String toolbarTitle, String keyWord) {
        mMainView.openSearchUi(keyWord);
        updateToolbar(toolbarTitle);
    }

    @Override
    public void placeBid(Product product, String from, String price) {
        Firebase.getInstance().getFirestore().collection(Bid.getAppContext().getString(R.string.firebase_products))
                .document(String.valueOf(product.getProductId()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        Product latestProduct;
                        latestProduct = document.toObject(Product.class);
                        int placeBidTimes = latestProduct.getPlaceBidTimes();

                        if (from.equals(ENGLISH)) {
                            if ("".equals(price)) {

                                mMainView.showWarningMsgNull();
                            } else if (Integer.parseInt(price) < latestProduct.getCurrentPrice()) {

                                mMainView.showWarningMsgPriceToLow();
                            } else if ((Integer.parseInt(price) - latestProduct.getCurrentPrice()) < latestProduct.getIncrease()) {

                                mMainView.showWarningMsgPriceUnderIncrease();
                            } else {

                                if (Integer.parseInt(price) < latestProduct.getReservePrice()) {
                                    Toast.makeText(Bid.getAppContext(),
                                            Bid.getAppContext().getString(R.string.warning_msg_under_reserve_price),
                                            Toast.LENGTH_LONG).show();
                                }

                                mMainView.hideWarningMsg();

                                latestProduct.setCurrentPrice(Integer.parseInt(price));
                                latestProduct.setHighestUserId(UserManager.getInstance().getUser().getId());
                                latestProduct.setBuyerName(UserManager.getInstance().getUser().getName());
                                latestProduct.setPlaceBidTimes(placeBidTimes + 1);

                                mMainView.setAfterBidData(latestProduct);

                                Firebase.getInstance().getFirestore().collection(Bid.getAppContext().getString(R.string.firebase_products))
                                        .document(String.valueOf(latestProduct.getProductId()))
                                        .update(Bid.getAppContext().getString(R.string.firebase_filed_current_price), Integer.parseInt(price),
                                                Bid.getAppContext().getString(R.string.firebase_field_highest_user_id), UserManager.getInstance().getUser().getId(),
                                                Bid.getAppContext().getString(R.string.firebase_field_buyer_name), UserManager.getInstance().getUser().getName(),
                                                Bid.getAppContext().getString(R.string.firebase_field_place_bid_times), placeBidTimes + 1)
                                        .addOnSuccessListener(aVoid -> Log.d(Constants.TAG, "BID DocumentSnapshot successfully updated!"))
                                        .addOnFailureListener(e -> Log.w(Constants.TAG, "BID Error updating document", e));

                                ArrayList<Long> myProductsId = UserManager.getInstance().getUser().getMyBiddingProductsId();
                                boolean hasProduct = false;

                                for (int i = 0; i < myProductsId.size(); i++) {
                                    if (myProductsId.get(i).equals(latestProduct.getProductId())) {
                                        hasProduct = true;
                                    }
                                }

                                if (!hasProduct) {
                                    UserManager.getInstance().addBiddingProductId(latestProduct.getProductId());
                                    UserManager.getInstance().updateUser2Firebase();
                                }

                                mMainView.dismissDialog();
                                mMainView.showMessageDialogUi(BID_SUCCESS);
                            }

                        } else {
                            if ("".equals(price)) {

                                mMainView.showWarningMsgNull();
                            } else {
                                mMainView.hideWarningMsg();

                                latestProduct.setPlaceBidTimes(placeBidTimes + 1);

                                if (Integer.parseInt(price) > latestProduct.getCurrentPrice()) {

                                    Firebase.getInstance().getFirestore().collection(Bid.getAppContext().getString(R.string.firebase_products))
                                            .document(String.valueOf(latestProduct.getProductId()))
                                            .update(Bid.getAppContext().getString(R.string.firebase_filed_current_price), Integer.parseInt(price),
                                                    Bid.getAppContext().getString(R.string.firebase_field_highest_user_id), UserManager.getInstance().getUser().getId(),
                                                    Bid.getAppContext().getString(R.string.firebase_field_buyer_name), UserManager.getInstance().getUser().getName(),
                                                    Bid.getAppContext().getString(R.string.firebase_field_place_bid_times), placeBidTimes + 1)
                                            .addOnSuccessListener(aVoid -> Log.d(Constants.TAG, "BID DocumentSnapshot successfully updated!"))
                                            .addOnFailureListener(e -> Log.w(Constants.TAG, "BID Error updating document", e));
                                } else {
                                    Firebase.getInstance().getFirestore().collection(Bid.getAppContext().getString(R.string.firebase_products))
                                            .document(String.valueOf(latestProduct.getProductId()))
                                            .update(Bid.getAppContext().getString(R.string.firebase_field_place_bid_times), placeBidTimes + 1)
                                            .addOnSuccessListener(aVoid -> Log.d(Constants.TAG, "BID DocumentSnapshot successfully updated!"))
                                            .addOnFailureListener(e -> Log.w(Constants.TAG, "BID Error updating document", e));
                                }

                                mMainView.setAfterBidData(latestProduct);

                                UserManager.getInstance().addBiddingProductId(latestProduct.getProductId());
                                UserManager.getInstance().updateUser2Firebase();

                                mMainView.dismissDialog();
                                mMainView.showMessageDialogUi(BID_SUCCESS);
                            }
                        }
                    } else {
                        Log.d(Constants.TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    @Override
    public void decreaseUnreadBought() {
        mMyBoughtPresenter.decreaseUnreadBought();
    }

    @Override
    public void decreaseUnreadSold() {
        mMySoldPresenter.decreaseUnreadSold();
    }

    @Override
    public void decreaseUnreadNobodyBid() {
        mNobodyBidPresenter.decreaseUnreadNobodyBid();
    }

    @Override
    public void start() {
    }

    @Override
    public AuctionItemFragment findEnglishAuction() {
        return mMainView.findEnglishAuctionView();
    }

    @Override
    public AuctionItemFragment findSealedAuction() {
        return mMainView.findSealedAuctionView();
    }

    @Override
    public void openPost(String title, ArrayList<Bitmap> imageBitmap) {
        mMainView.openPostUi(imageBitmap);
        updateToolbar(title);
        hideBottomNavigation();
    }

    @Override
    public void openGalleryDialog(String from) {
        mMainView.openGalleryDialog(from);
    }

    @Override
    public void setProfile(Bitmap imageBitmap) {
        mSettingsPresenter.setProfile(imageBitmap);
    }

    @Override
    public void openEyesOn(String toolbarTitle) {
        mMainView.openEyesOnUi();
        updateToolbar(toolbarTitle);
    }

    @Override
    public void openSearchDialog() {
        mMainView.openSearchDialog();
    }

    @Override
    public void showPostSuccessDialog() {
        mMainView.showMessageDialogUi(MessageDialog.POST_SUCCESS);
    }

    @Override
    public void onLoginSuccess() {
        mMainView.setBottomNavigation();
        showToolbarAndBottomNavigation();
        mMainView.openCenterUi();
    }

    @Override
    public void setProductTitle(String productTitle) {
        mPostPresenter.setProductTitle(productTitle);
    }

    @Override
    public void setAuctionCondition(String auctionCondition) {
        mPostPresenter.setAuctionCondition(auctionCondition);
    }

    @Override
    public void setProductIntro(String productIntro) {
        mPostPresenter.setProductIntro(productIntro);
    }

    @Override
    public void setStartingPrice(int startingPrice) {
        mPostPresenter.setStartingPrice(startingPrice);
    }

    @Override
    public void setReservePrice(int reservePrice) {
        mPostPresenter.setReservePrice(reservePrice);
    }

    @Override
    public void setProductCondition(String condition) {
        mPostPresenter.setProductCondition(condition);
    }

    @Override
    public void setAuctionType(String auctionType) {
        mPostPresenter.setAuctionType(auctionType);
    }

    @Override
    public void setIncrease(int increase) {
        mPostPresenter.setIncrease(increase);
    }

    @Override
    public void setExpireTime(long expireTime) {
        mPostPresenter.setExpireTime(expireTime);
    }

    @Override
    public void setProductId(long productId) {
        mPostPresenter.setProductId(productId);
    }

    @Override
    public void setPostProductId2User(long productId) {
        mPostPresenter.setPostProductId2User(productId);
    }

    @Override
    public void setStartingTime(long startingTime) {
        mPostPresenter.setStartingTime(startingTime);
    }

    @Override
    public void setImages() {
        mPostPresenter.setImages();
    }

    @Override
    public void setSellerId(long sellerId) {
        mPostPresenter.setSellerId(sellerId);
    }

    @Override
    public void setPlaceBidTimes(int participantsNumber) {
        mPostPresenter.setPlaceBidTimes(participantsNumber);
    }

    @Override
    public void setCurrentPrice(int currentPrice) {
        mPostPresenter.setCurrentPrice(currentPrice);
    }

    @Override
    public TradeItemFragment findMyBidding() {
        return mMainView.findMyBiddingView();
    }

    @Override
    public TradeItemFragment findMySelling() {
        return mMainView.findMySellingView();
    }

    @Override
    public TradeItemFragment findMyBought() {
        return mMainView.findMyBoughtView();
    }

    @Override
    public TradeItemFragment findMySold() {
        return mMainView.findMySoldView();
    }

    @Override
    public TradeItemFragment findNobodyBid() {
        return mMainView.findNobodyBidView();
    }

    @Override
    public void loadBoughtBadgeData() {

        if (mTradePresenter != null) {
            mTradePresenter.loadBoughtBadgeData();
        }
    }

    @Override
    public void setBoughtBadgeData(int unreadBought) {
        mTradePresenter.setBoughtBadgeData(unreadBought);
    }

    @Override
    public void loadSoldBadgeData() {

        if (mTradePresenter != null) {
            mTradePresenter.loadSoldBadgeData();
        }
    }

    @Override
    public void setSoldBadgeData(int unreadSold) {
        mTradePresenter.setSoldBadgeData(unreadSold);
    }

    @Override
    public void loadNobodyBidBadgeData() {

        if (mTradePresenter != null) {
            mTradePresenter.loadNobodyBidBadgeData();
        }
    }

    @Override
    public void setNobodyBidBadgeData(int unreadNobodyBid) {
        mTradePresenter.setNobodyBidBadgeData(unreadNobodyBid);
    }


    @Override
    public void openBidding(String auctionType, Product product) {
        mMainView.findBiddingView(auctionType, product);
    }

    @Override
    public void openSelling(String auctionType, Product product) {
        mMainView.findSellingView(auctionType, product);
    }

    @Override
    public void removeSellingProductId(long productId, String from) {

        if (from.equals(ENGLISH)) {
            mEnglishAuctionItemPresenter.removeSellingProductId(productId, from);
        } else {
            mSealedAuctionItemPresenter.removeSellingProductId(productId, from);
        }
    }

    @Override
    public void addNobodyBidProductId(long productId, String from) {

        if (from.equals(ENGLISH)) {
            mEnglishAuctionItemPresenter.addNobodyBidProductId(productId, from);
        } else {
            mSealedAuctionItemPresenter.addNobodyBidProductId(productId, from);
        }
    }

    @Override
    public void addSoldProductId(long productId, String from) {

        if (from.equals(ENGLISH)) {
            mEnglishAuctionItemPresenter.addSoldProductId(productId, from);
        } else {
            mSealedAuctionItemPresenter.addSoldProductId(productId, from);
        }
    }

    @Override
    public void removeBiddingProductId(long productId, String from) {

        if (from.equals(ENGLISH)) {
            mEnglishAuctionItemPresenter.removeBiddingProductId(productId, from);
        } else {
            mSealedAuctionItemPresenter.removeBiddingProductId(productId, from);
        }
    }

    @Override
    public void addBoughtProductId(long productId, String from) {

        if (from.equals(ENGLISH)) {
            mEnglishAuctionItemPresenter.addBoughtProductId(productId, from);
        } else {
            mSealedAuctionItemPresenter.addBoughtProductId(productId, from);
        }
    }

    @Override
    public void increaseUnreadNobodyBid(String from) {

        if (from.equals(ENGLISH)) {
            mEnglishAuctionItemPresenter.increaseUnreadNobodyBid(from);
        } else {
            mSealedAuctionItemPresenter.increaseUnreadNobodyBid(from);
        }
    }

    @Override
    public void increaseUnreadSold(String from) {

        if (from.equals(ENGLISH)) {
            mEnglishAuctionItemPresenter.increaseUnreadSold(from);
        } else {
            mSealedAuctionItemPresenter.increaseUnreadSold(from);
        }
    }

    @Override
    public void increaseUnreadBought(String from) {

        if (from.equals(ENGLISH)) {
            mEnglishAuctionItemPresenter.increaseUnreadBought(from);
        } else {
            mSealedAuctionItemPresenter.increaseUnreadBought(from);
        }
    }

    @Override
    public void productResult(Product product, String from) {

        if (from.equals(ENGLISH)) {
            mEnglishAuctionItemPresenter.productResult(product, from);
        } else {
            mSealedAuctionItemPresenter.productResult(product, from);
        }
    }

    @Override
    public void createChatRoom(Product product, String from) {

        if (from.equals(ENGLISH)) {
            mEnglishAuctionItemPresenter.createChatRoom(product, from);
        } else {
            mSealedAuctionItemPresenter.createChatRoom(product, from);
        }
    }

    @Override
    public void openBoughtDetail(Product product) {
        mMainView.findBoughtDetailView(product);
    }

    @Override
    public void openSoldDetail(Product product) {
        mMainView.findSoldDetailView(product);
    }

    @Override
    public void openNobodyBidDetail(Product product) {
        mMainView.findNobodyBidDetailView(product);
    }

    @Override
    public void setBuyerHasRead(boolean hasRead, Product product) {

        mMyBoughtPresenter.setBuyerHasRead(hasRead, product);
    }

    @Override
    public void setSellerHasRead(boolean hasRead, Product product, int from) {

        if (from == 1) {
            mMySoldPresenter.setSellerHasRead(hasRead, product, from);
        } else {
            mNobodyBidPresenter.setSellerHasRead(hasRead, product, from);
        }
    }

    @Override
    public void minusNobodyBidBadgeCount(TradeItemPresenter.LoadCallback loadCallback) {
        mNobodyBidPresenter.minusNobodyBidBadgeCount(loadCallback);
    }

    @Override
    public void minusSoldBadgeCount(TradeItemPresenter.LoadCallback loadCallback) {
        mMySoldPresenter.minusSoldBadgeCount(loadCallback);
    }

    @Override
    public void minusBoughtBadgeCount(TradeItemPresenter.LoadCallback loadCallback) {
        mMyBoughtPresenter.minusBoughtBadgeCount(loadCallback);
    }

    @Override
    public void loadMyBiddingData() {

        if (mMyBiddingPresenter != null) {
            mMyBiddingPresenter.loadMyBiddingData();
        }
    }

    @Override
    public void setMyBiddingData(ArrayList<Product> productsList) {
        mMyBiddingPresenter.setMyBiddingData(productsList);
    }

    @Override
    public void loadMySellingData() {

        if (mMySellingPresenter != null) {
            mMySellingPresenter.loadMySellingData();
        }
    }

    @Override
    public void setMySellingData(ArrayList<Product> productsList) {
        mMySellingPresenter.setMySellingData(productsList);
    }

    @Override
    public void loadMyBoughtData() {

        if (mMyBoughtPresenter != null) {
            mMyBoughtPresenter.loadMyBoughtData();
        }
    }

    @Override
    public void setMyBoughtData(ArrayList<Product> productsList) {
        mMyBoughtPresenter.setMyBoughtData(productsList);
    }

    @Override
    public void loadMySoldData() {

        if (mMySoldPresenter != null) {
            mMySoldPresenter.loadMySoldData();
        }
    }

    @Override
    public void setMySoldData(ArrayList<Product> productsList) {
        mMySoldPresenter.setMySoldData(productsList);
    }

    @Override
    public void loadNobodyBidData() {

        if (mNobodyBidPresenter != null) {
            mNobodyBidPresenter.loadNobodyBidData();
        }
    }

    @Override
    public void setNobodyBidData(ArrayList<Product> productsList) {
        mNobodyBidPresenter.setNobodyBidData(productsList);
    }

    @Override
    public void setSoldDetailData(Product product) {
        mSoldDetailPresenter.setSoldDetailData(product);
    }

    @Override
    public void loadSoldDetailData() {
        mSoldDetailPresenter.loadSoldDetailData();
    }

    @Override
    public void setUserName(String userName) {
        mSettingsPresenter.setUserName(userName);
    }

    @Override
    public void openChatContent(ChatRoom chatRoom, String from) {
        mMainView.openChatContentUi(chatRoom, from);
    }
}
