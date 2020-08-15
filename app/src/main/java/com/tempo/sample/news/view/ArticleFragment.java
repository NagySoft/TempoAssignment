package com.tempo.sample.news.view;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.tempo.sample.R;
import com.tempo.sample.news.data.model.Article;
import com.tempo.sample.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tempo.sample.utils.Constants.KEY_ARTICLE;
import static com.tempo.sample.utils.DateUtil.DATE_DD_MM_YYYY_FORMAT;

public class ArticleFragment extends Fragment {
    @BindView(R.id.articleImage)
    ImageView articleImage;
    @BindView(R.id.titleTextView)
    TextView titleTextView;
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.contentTextView)
    TextView contentTextView;
    @BindView(R.id.authorTextView)
    TextView authorTextView;
    @BindView(R.id.dateTextView)
    TextView dateTextView;
    @BindView(R.id.sourceTextView)
    TextView sourceTextView;

    private Article article;

    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ButterKnife.bind(this, view);
        article = getArguments().getParcelable(KEY_ARTICLE);
        bindData();
        return view;
    }

    private void bindData() {
        Glide.with(articleImage.getContext())
                .load(article.getUrlToImage())
                .placeholder(R.drawable.logo)
                .into(articleImage);
        titleTextView.setText(article.getTitle());
        sourceTextView.setText(article.getSource().getName());
        descriptionTextView.setText(article.getDescription());
        contentTextView.setText(article.getContent());
        dateTextView.setText(DateUtil.formatDate(DATE_DD_MM_YYYY_FORMAT, article.getPublishedAt()));
        authorTextView.setText(article.getAuthor());
        sourceTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
