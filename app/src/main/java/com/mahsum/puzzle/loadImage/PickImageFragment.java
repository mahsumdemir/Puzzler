package com.mahsum.puzzle.loadImage;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.RawContacts.Data;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.mahsum.puzzle.LocalStorage;
import com.mahsum.puzzle.R;
import com.mahsum.puzzle.core.GameBoard;
import com.yalantis.ucrop.UCrop;

import database.DatabaseHelper;
import database.DatabaseInterface;
import database.DatabaseInterface.DataListener;
import java.io.File;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;

public class PickImageFragment extends Fragment implements Contract.View{

  private static final String TAG = "PickImageFragment";
  private Contract.Presenter presenter;
  private SavedPuzzlesAdapter adapter;

  public PickImageFragment() {
    // Required empty public constructor
  }

  public static PickImageFragment newInstance() {
    PickImageFragment pickImageFragment = new PickImageFragment();
    pickImageFragment.presenter = new MainActivityPresenter(pickImageFragment);
    return pickImageFragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_pick_image, container, false);

    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.savedPuzzles);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    adapter = new SavedPuzzlesAdapter(getActivity(), DatabaseInterface.getGameBoards());
    recyclerView.setAdapter(adapter);
    DatabaseInterface.addDataListener(new DataListener() {
      @Override
      public void onDataChanges(ArrayList<GameBoard> newDataSet) {
        adapter.setSavedGames(newDataSet);
        adapter.notifyDataSetChanged();
      }
    });

    FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.addPuzzleFab);
    fab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        presenter.startImageChoosing();
      }
    });
    return view;
  }

  @Override
  public void onResume() {
    Log.d(TAG, "PickImageFragment.onResume");
    super.onResume();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data == null) return;
    final Observable<Intent> imageUri = Observable.just(data);

    imageUri.subscribe(new Consumer<Intent>() {
      @Override
      public void accept(@NonNull Intent intent) throws Exception {
        UCrop.of(intent.getData(), Uri.fromFile(new File(getActivity().getCacheDir(), "image")))
            .withAspectRatio(1, 1)
            .start(getActivity());  //handle result at PickImageActivity
      }
    });
  }

  @Override
  public void showImage(Bitmap image) {
  }

  @Override
  public ContentResolver getContentResolver() {
    return getActivity().getContentResolver();
  }
}
