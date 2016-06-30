package org.wikipedia.feed.featured;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import org.wikipedia.R;
import org.wikipedia.feed.view.BigPictureCardView;
import org.wikipedia.feed.view.CardHeaderView;
import org.wikipedia.feed.view.FeaturedCardFooterView;
import org.wikipedia.history.HistoryEntry;

public class FeaturedArticleCardView extends BigPictureCardView {
    private FeaturedArticleCard card;

    public FeaturedArticleCardView(Context context) {
        super(context);
    }

    public void set(@NonNull FeaturedArticleCard card) {
        this.card = card;
        String articleTitle = card.articleTitle();
        String articleSubtitle = card.articleSubtitle();
        String extract = card.extract();
        Uri imageUri = card.image();

        articleTitle(articleTitle);
        articleSubtitle(articleSubtitle);
        extract(extract);
        image(imageUri);

        header(card);
        footer();

        onClickListener(new CardClickListener());
    }

    private void header(@NonNull FeaturedArticleCard card) {
        CardHeaderView header = new CardHeaderView(getContext())
                .setTitle(card.title())
                .setSubtitle(card.subtitle())
                .setImage(R.drawable.ic_star_black_24dp)
                .setImageCircleColor(R.color.feed_featured_icon_background)
                .setCard(card)
                .setCallback(getCallback());
        header(header);
    }

    private void footer() {
        footer(new FeaturedCardFooterView(getContext())
                .onSaveListener(new CardSaveListener())
                .onShareListener(new CardShareListener()));
    }

    private class CardClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (getCallback() != null) {
                getCallback().onSelectPage(card.historyEntry(HistoryEntry.SOURCE_FEED_FEATURED));
            }
        }
    }

    private class CardSaveListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (getCallback() != null) {
                getCallback().onAddPageToList(card.historyEntry(HistoryEntry.SOURCE_FEED_FEATURED));
            }
        }
    }

    private class CardShareListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (getCallback() != null) {
                getCallback().onSharePage(card.historyEntry(HistoryEntry.SOURCE_FEED_FEATURED));
            }
        }
    }
}
