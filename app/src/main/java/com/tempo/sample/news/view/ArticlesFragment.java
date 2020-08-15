package com.tempo.sample.news.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tempo.sample.BaseFragment;
import com.tempo.sample.listener.OnItemClickListener;
import com.tempo.sample.listener.PaginationListener;
import com.tempo.sample.R;
import com.tempo.sample.di.Injectable;
import com.tempo.sample.news.data.SearchModel;
import com.tempo.sample.news.data.model.Article;
import com.tempo.sample.news.viewmodel.NewsViewModel;
import com.tempo.sample.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tempo.sample.utils.Constants.KEY_ARTICLE;
import static com.tempo.sample.utils.Constants.PAGE_SIZE;
import static com.tempo.sample.utils.Constants.PAGE_START;
import static com.tempo.sample.utils.Constants.SORT_BY_Popularity;
import static com.tempo.sample.utils.Constants.SORT_BY_PublishedAt;
import static com.tempo.sample.utils.Constants.SORT_BY_Relevancy;
import static com.tempo.sample.utils.DateUtil.DATE_YYYY_MM_DD_FORMAT;

public class ArticlesFragment extends BaseFragment implements Injectable, OnItemClickListener<Article> {
    @BindView(R.id.newsRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.emptyTextView)
    TextView emptyTextView;

    private List<Article> articles;
    private ArticlesAdapter articlesAdapter;
    private NewsViewModel viewModel;
    private SearchModel searchModel;

    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage;
    private boolean isLoading = false;
    int itemCount;

    public ArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);

        startSearch(view);

        //Date From
        String dateFrom = DateUtil.formatDate(DATE_YYYY_MM_DD_FORMAT, DateUtil.getNowDate());
        dateTextView.setText(dateFrom);

        //Sort By
        String sortBy = getSortByKey(0);

        searchModel = new SearchModel("a", dateFrom, sortBy, 1);

        articles = new ArrayList<>();
        articlesAdapter = new ArticlesAdapter(articles, this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(articlesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new PaginationListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                getArticles();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        swipeRefresh.setOnRefreshListener(() -> {
            getFirstPage();
        });
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String sortBy = getSortByKey(item.getOrder());
        if (!sortBy.equals(searchModel.getSortBy())) {
            item.setChecked(true);
            searchModel.setSortBy(sortBy);
            getFirstPage();
        }
        return true;
    }

    private void startSearch(View view) {
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 3) {
                    searchModel.setQuery(newText);
                    getFirstPage();
                    return true;
                }
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            searchModel.setQuery(null);
            getFirstPage();
            return true;
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getFirstPage();
    }

    void getFirstPage() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        articlesAdapter.clear();
        getArticles();
    }

    private void getArticles() {
        searchModel.setPage(currentPage);
        swipeRefresh.setRefreshing(true);
        viewModel.getNewsList(searchModel);
    }

    protected void createViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel.class);
    }

    protected void observeViewModel() {
        viewModel.getObservable().observe(this, model -> {
            swipeRefresh.setRefreshing(false);
            if (!hasError(model)) {
                if (currentPage == PAGE_START) {
                    itemCount = model.getModel().getTotalResults();
                    totalPage = itemCount / PAGE_SIZE;
                    int rest = itemCount % PAGE_SIZE;
                    if (rest > 0)
                        totalPage++;
                }
                if (itemCount == 0) {
                    emptyTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                bindData(model.getModel().getResponse());
            }
        });
    }

    private void bindData(List<Article> articles) {
        swipeRefresh.setRefreshing(false);
        articlesAdapter.addItems(articles);
        swipeRefresh.setRefreshing(false);
        if (currentPage >= totalPage) {
            isLastPage = true;
        }
        isLoading = false;
    }

    @OnClick(R.id.dateTextView)
    void onCLickDate() {
        Calendar calendar = DateUtil.getNowCalender();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            Calendar mCalendar = DateUtil.getNowCalender();
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String dateFrom = DateUtil.formatDate(DATE_YYYY_MM_DD_FORMAT, mCalendar.getTime());
            searchModel.setFromDate(dateFrom);
            dateTextView.setText(dateFrom);
            getFirstPage();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    String getSortByKey(int sortByOrder) {
        switch (sortByOrder) {
            case 2:
                return SORT_BY_Relevancy;
            case 3:
                return SORT_BY_Popularity;
            default:
                return SORT_BY_PublishedAt;
        }
    }

    @Override
    public void onItemClicked(Article article) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_ARTICLE, article);
        Navigation.findNavController(getView())
                .navigate(R.id.action_newsFragment_to_newsDetailsFragment, bundle);
    }
}