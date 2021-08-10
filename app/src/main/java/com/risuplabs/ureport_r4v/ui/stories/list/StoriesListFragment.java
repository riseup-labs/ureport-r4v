package com.risuplabs.ureport_r4v.ui.stories.list;

import android.animation.ObjectAnimator;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.risuplabs.ureport_r4v.R;
import com.risuplabs.ureport_r4v.adapter.StoryListAdapter;
import com.risuplabs.ureport_r4v.base.BaseFragment;
import com.risuplabs.ureport_r4v.databinding.FragmentStoriesListBinding;
import com.risuplabs.ureport_r4v.model.story.ModelProgress;
import com.risuplabs.ureport_r4v.model.story.ModelStory;
import com.risuplabs.ureport_r4v.model.story.ModelStoryImage;
import com.risuplabs.ureport_r4v.network.data.ApiConstants;
import com.risuplabs.ureport_r4v.ui.stories.StoryViewModel;
import com.risuplabs.ureport_r4v.utils.ConnectivityCheck;
import com.risuplabs.ureport_r4v.utils.ProgressData;
import com.risuplabs.ureport_r4v.utils.ImageDownloader;
import com.risuplabs.ureport_r4v.utils.StaticMethods;
import com.risuplabs.ureport_r4v.utils.pref_manager.PrefKeys;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.risuplabs.ureport_r4v.utils.StorageUtils.saveToFile;


public class StoriesListFragment extends BaseFragment<FragmentStoriesListBinding> {

    private StoryListAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private String next_url = "";
    List<ModelStory> list = new ArrayList();
    List<ModelStoryImage> imageList = new ArrayList();
    ModelStory story;
    int org_id;
    int count = 0;
    int progressValue = 0;
    ImageDownloader downloader;
    boolean dataLock = true;
    boolean refreshLock = true;

    @Inject
    StoryViewModel storyViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_stories_list;
    }

    @Override
    public void onViewReady() {

        org_id = prefManager.getInt(PrefKeys.ORG_ID);
        downloader = new ImageDownloader();

        initRecyclerView();
        saveData();

        storyViewModel.getAllStoriesFromLocal(org_id).observe(getViewLifecycleOwner(), response -> {
            mAdapter.addItems(response);
        });

        binding.swipeToRefresh.setOnRefreshListener(() -> {
            binding.swipeToRefresh.setRefreshing(false);
            refresh();
        });

        showProgress();

        binding.btnRetry.setOnClickListener(v->{
            refresh();
        });

        binding.btnHide.setOnClickListener(v->{
            gone(binding.noInternetLayout);
        });

    }

    public void initRecyclerView() {
        binding.recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new StoryListAdapter(getViewLifecycleOwner(), getContext(), org_id);
        binding.recyclerView.setAdapter(mAdapter);
    }

    private void getRemoteData(String url) {
        storyViewModel.getStories(url);
    }

    @Override
    public void onStart() {
        super.onStart();
        storyViewModel.getStoriesCount(org_id).observe(getViewLifecycleOwner(), count -> {
            int storyCount = count;
            if (storyCount == 0) {
                visible(binding.emptyListWarning);
                if(ConnectivityCheck.isConnected(getContext())) {
                    binding.progressBar.setIndeterminate(true);
                    binding.loadingTvTitle.setText(R.string.fetching_stories);
                    visible(binding.loadingLayout);
                    gone(binding.noInternetLayout);
                    ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
                    ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1500).start();
                    refreshLock = false;
                    getRemoteData(ApiConstants.STORIES + org_id);
                }else{
                    ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
                    ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1500).start();
                    visible(binding.noInternetLayout);
                    gone(binding.loadingLayout);
                }
            }else{
                gone(binding.emptyListWarning);
            }
        });

    }

    public void refresh() {
        if (refreshLock) {
            if(ConnectivityCheck.isConnected(getContext())) {
                refreshLock = false;
                ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
                ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1000).start();
                visible(binding.loadingLayout);
                gone(binding.noInternetLayout);
                binding.loadingTvTitle.setText(R.string.updating_stories);
                binding.progressBar.setIndeterminate(true);
                getRemoteData(ApiConstants.STORIES + org_id);
            }else{
                ObjectAnimator.ofFloat(binding.layoutFooter, "alpha",  0, 1f).setDuration(2000).start();
                ObjectAnimator.ofFloat(binding.layoutFooter, "translationY", 300, 0).setDuration(1500).start();
                visible(binding.noInternetLayout);
                gone(binding.loadingLayout);
            }
        }
    }

    public void saveData() {
        storyViewModel.response.observe(getViewLifecycleOwner(), response -> {
            list.addAll(response.data.stories);
            next_url = response.data.next;
            count = response.data.count;
            progressValue += 10;
            binding.progressBar.setIndeterminate(false);
            binding.progressBar.setProgress(progressValue);
            binding.progressBar.setMax(count);
            binding.loadingTvProgress.setText("("+progressValue+"/"+count+")");
            Log.d(TAG, "onViewReady: " + count);
            if (next_url != null) {
                getRemoteData(next_url);
            } else {
                progressValue = 0;
                gone(binding.loadingLayout);
                binding.loadingTvTitle.setText("");
                binding.loadingTvProgress.setText("");
                for (int i = list.size() - 1; i >= 0; i--) {
                    story = list.get(i);
                    String content = story.content;
                    story.org_id = org_id;
                    //Unique content filename
                    String contentFileName = org_id + "_" + story.id;
                    saveToFile(getContext(), content, contentFileName);
                    //Changing the META off content
                    story.content = contentFileName;
                    //Insert story to the database
                    storyViewModel.insertStory(story);
                    //Save the content.json into internal storage
                    Log.d(TAG, "saveData: "+content);


                    //Setting up Image download list
                    if (story.images.size() > 0) {
                        ModelStoryImage modelStoryImage = new ModelStoryImage();
                        modelStoryImage.url = story.images.get(0);
                        modelStoryImage.filename = "org_" + org_id + "_content_image_" + story.id + ".jpg";
                        if (!downloader.isFileExist(getContext(), modelStoryImage.filename)) {
                            imageList.add(modelStoryImage);
                        }
                    }
                }
                //Saving last update
                String tag = PrefKeys.LAST_LOCAL_STORY_UPDATE_TIME + org_id;
                StaticMethods.setLocalUpdateDate(prefManager, tag);
                //Download Images
                downloader.downloadImage(imageList, getContext());
                imageList = new ArrayList<>();
            }

        });
    }

    public void showProgress() {
        ProgressData.progressData.observe(getViewLifecycleOwner(), value -> {
            visible(binding.loadingLayout);
            Log.d(TAG, "showProgress: " + value.progress + " " + value.max+ " " + value.status);
            binding.loadingTvTitle.setText(R.string.updating_photos);
            binding.loadingTvProgress.setText("("+value.progress+"/"+value.max+")");
            binding.progressBar.setProgress(value.progress);
            binding.progressBar.setMax(value.max);
            mAdapter.notifyDataSetChanged();
            if (value.progress == value.max) {
                gone(binding.loadingLayout);
                if (dataLock) {
                    ProgressData.progressData.setValue(new ModelProgress(0, 0,""));
                    dataLock = false;
                    refreshLock = true;
                }
            }
        });
    }



}
