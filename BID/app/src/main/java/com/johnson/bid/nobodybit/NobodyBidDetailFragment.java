package com.johnson.bid.nobodybit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnson.bid.MainActivity;
import com.johnson.bid.R;
import com.johnson.bid.data.Product;

import static com.google.common.base.Preconditions.checkNotNull;

public class NobodyBidDetailFragment extends Fragment implements NobodyBidDetailContract.View {

    private NobodyBidDetailContract.Presenter mPresenter;
    private NobodyBidDetailAdapter mNobodyBidDetailAdapter;

    public NobodyBidDetailFragment() {}

    public static NobodyBidDetailFragment newInstance() {
        return new NobodyBidDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNobodyBidDetailAdapter = new NobodyBidDetailAdapter(mPresenter, (MainActivity) getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recycler_normal_container, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_container_normal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mNobodyBidDetailAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.loadNobodyBidDetailData();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.showToolbarAndBottomNavigation();
    }

    @Override
    public void setPresenter(NobodyBidDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showNobodyBidDetailUi(Product product) {
        mNobodyBidDetailAdapter.updateData(product);
    }
}
