package com.johnson.bid.util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.johnson.bid.Bid;
import com.johnson.bid.data.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserManager {

    private User mUser;
    private CallbackManager mFbCallbackManager;

    private static class UserManagerHolder {
        private static final UserManager INSTANCE = new UserManager();
    }

    private UserManager() {
    }

    public static UserManager getInstance() {
        return UserManagerHolder.INSTANCE;
    }

    public void loginBidByFacebook(Context context, final LoadCallback loadCallback) {

        mFbCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mFbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserInfoFromFacebook(loginResult, new LoadCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Johnsi", "TOKEN : " + loginResult.getAccessToken().getToken());
                        loadCallback.onSuccess();
                    }

                    @Override
                    public void onFail(String errorMessage) {
                        loadCallback.onFail("Get User Info Failed");
                    }
                });
                Log.d("Johnsi", "FB Login Success");
            }

            @Override
            public void onCancel() {

                Log.d("Johnsi", "FB Login Cancel");
                loadCallback.onFail("FB Login Cancel");
            }

            @Override
            public void onError(FacebookException exception) {

                Log.d("Johnsi", "FB Login Error");
                loadCallback.onFail("FB Login Error: " + exception.getMessage());
            }
        });

        loginFacebook(context);
    }

    private void getUserInfoFromFacebook(LoginResult loginResult, LoadCallback loadCallback) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
            try {
                if (response.getConnection().getResponseCode() == 200) {
                    long id = object.getLong("id");
//                    Log.d("Johnsi", "ID : " + id);
                    String name = object.getString("name");
                    String userPhoto = "https://graph.facebook.com/" + id + "/picture?type=small";

//                    大頭照處理
//                    public static Bitmap getFacebookProfilePicture(String userID){
//                        URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
//                        Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
//
//                        return bitmap;
//                    }
//
//                    Bitmap bitmap = getFacebookProfilePicture(userId);

                    Firebase.getFirestore().collection("users")
                            .whereEqualTo("id", id)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {

                                    if (task.getResult().size() == 0) {

                                        User user = new User();
                                        user.setId(id);
                                        user.setName(name);
                                        user.setImage(userPhoto);
                                        setUser(user);

                                        Firebase.getFirestore().collection("users")
                                                .document(String.valueOf(id))
                                                .set(user)
                                                .addOnSuccessListener(documentReference -> Log.d("Johnsi", "DocumentSnapshot added"))
                                                .addOnFailureListener(e -> Log.w("Johnsi", "Error adding document", e));

                                    } else {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            setUser(document.toObject(User.class));
                                        }
                                    }

                                } else {
                                    Log.d("Johnsi", "Error getting documents: ", task.getException());
                                }
                            });

                    loadCallback.onSuccess();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        graphRequest.executeAsync();
    }

    public void getUserProfile() {

        Firebase.getFirestore().collection("users")
                .document(AccessToken.getCurrentAccessToken().getUserId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        DocumentSnapshot document = task.getResult();
                        setUser(document.toObject(User.class));

                    } else {
                        Log.d("Johnsi", "Error getting documents: ", task.getException());
                    }
                });

    }

    private void loginFacebook(Context context) {

        LoginManager.getInstance().logInWithReadPermissions(
                (Activity) context, Arrays.asList("email"));

    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public CallbackManager getFbCallbackManager() {
        return mFbCallbackManager;
    }

    public interface LoadCallback {

        void onSuccess();

        void onFail(String errorMessage);
    }
}
