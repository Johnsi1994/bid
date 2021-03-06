package com.johnson.bid.chat.chatcontent;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.firestore.FieldValue;
import com.johnson.bid.Bid;
import com.johnson.bid.R;
import com.johnson.bid.data.ChatContent;
import com.johnson.bid.data.ChatRoom;
import com.johnson.bid.util.Constants;
import com.johnson.bid.util.Firebase;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChatContentPresenter implements ChatContentContract.Presenter {

    private ChatContentContract.View mChatContentView;
    private ArrayList<ChatContent> mChatContentArrayList;
    private ChatRoom mChatRoom;

    public ChatContentPresenter(@NonNull ChatContentContract.View chatContentView) {
        mChatContentView = checkNotNull(chatContentView, "chatContentView cannot be null!");
    }

    @Override
    public void start() {}

    @Override
    public void showBottomNavigation() {}

    @Override
    public void sendMessage(ChatContent chatContent) {

        Firebase.getInstance().getFirestore().collection(Bid.getAppContext().getString(R.string.firebase_chatrooms))
                .document(String.valueOf(mChatRoom.getChatRoomId()))
                .update(Bid.getAppContext().getString(R.string.firebase_field_chat_content_array_list), FieldValue.arrayUnion(chatContent))
                .addOnSuccessListener(aVoid -> Log.d(Constants.TAG, "chatContent successfully added!"))
                .addOnFailureListener(e -> Log.w(Constants.TAG, "chatContent Error updating document", e));
    }

    @Override
    public void setChatRoomData(ChatRoom chatRoom) {

        mChatRoom = chatRoom;
        mChatContentArrayList = new ArrayList<>();
        mChatContentArrayList = chatRoom.getChatContentArrayList();
    }

    @Override
    public void loadChatContentData() {

        mChatContentView.showChatContentUi(mChatContentArrayList);
    }

    @Override
    public void setChatListener() {

        Firebase.getInstance().getFirestore().collection(Bid.getAppContext().getString(R.string.firebase_chatrooms))
                .document(String.valueOf(mChatRoom.getChatRoomId()))
                .addSnapshotListener((queryDocumentSnapshots, e) -> {

                    if (e != null) {
                        return;
                    }

                    if (queryDocumentSnapshots != null && queryDocumentSnapshots.exists()) {

                        mChatContentArrayList = new ArrayList<>();
                        mChatContentArrayList = queryDocumentSnapshots.toObject(ChatRoom.class).getChatContentArrayList();
                        loadChatContentData();
                    }
                });
    }

    @Override
    public void updateToolbar(String title) {}

    @Override
    public void hideToolbar() {}

}
