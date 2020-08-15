package com.tempo.sample;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tempo.sample.di.Injectable;
import com.tempo.sample.network.NetworkStatus;
import com.tempo.sample.network.model.BaseModel;
import com.tempo.sample.utils.ToastUtil;

import javax.inject.Inject;

public abstract class BaseFragment extends Fragment implements Injectable {

    ProgressDialog mProgressDialog;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initProgressDialog();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createViewModel();
        observeViewModel();
    }

    protected void createViewModel() {

    }

    protected void observeViewModel() {

    }

    protected void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
    }

    protected void showProgressDialog() {
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        mProgressDialog.hide();
    }

    protected boolean hasError(BaseModel data) {
        hideProgressDialog();
        NetworkStatus status = data.getStatus();
        if (status == NetworkStatus.HTTP_FAILURE || status == NetworkStatus.IO_FAILURE) {
            if (data.getMessage() != null)
                ToastUtil.showToast(data.getMessage());
            return true;
        }
        if (status == NetworkStatus.UNAUTHORIZED) {
            ToastUtil.showToast(R.string.unauthorized_user);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProgressDialog.dismiss();
    }
}