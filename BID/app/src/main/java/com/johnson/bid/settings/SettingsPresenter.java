package com.johnson.bid.settings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.johnson.bid.util.Constants;
import com.johnson.bid.util.Firebase;
import com.johnson.bid.util.UserManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;

public class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View mSettingsView;

    public SettingsPresenter(@NonNull SettingsContract.View settingsView) {
        mSettingsView = checkNotNull(settingsView, "settingsView cannot be null!");
    }

    @Override
    public void start() {}

    @Override
    public void setUserName(String userName) {
        UserManager.getInstance().setUserName(userName);
        UserManager.getInstance().setHasUserDataChange(true);
    }

    @Override
    public void openGalleryDialog(String from) {}

    @Override
    public void setProfile(Bitmap bitmap) {

        StorageReference ref = Firebase.getInstance().getStorage().child(bitmap.toString());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = ref.putBytes(data);
        uploadTask
                .addOnSuccessListener(taskSnapshot -> {
                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();

                    UserManager.getInstance().setUserProfile(downloadUrl.toString());
                    UserManager.getInstance().setHasUserDataChange(true);
                })
                .addOnFailureListener(exception -> Log.d(Constants.TAG, exception.getMessage()));

        mSettingsView.showProfile(bitmap);
    }
}
