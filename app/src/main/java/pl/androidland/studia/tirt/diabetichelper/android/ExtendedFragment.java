package pl.androidland.studia.tirt.diabetichelper.android;

import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;

public abstract class ExtendedFragment extends Fragment {

    public <T extends View> T getViewById(int resId, Class<T> type) {
        return type.cast(getView().findViewById(resId));
    }

    public Button getButtonById(int resId) {
        return getViewById(resId, Button.class);
    }

    public TextView getTextViewById(int resId) {
        return getViewById(resId, TextView.class);
    }

    public WebView getWebViewById(int resId) {
        return getViewById(resId, WebView.class);
    }

    public ImageView getImageViewById(int resId) {
        return getViewById(resId, ImageView.class);
    }

    public ListView getListViewById(int resId) {
        return getViewById(resId,ListView.class);
    }

    public ExpandableListView getExpandableListViewById(int resId) {
        return getViewById(resId, ExpandableListView.class);
    }

    public LinearLayout getLinearLayoutById(int resId) {
        return getViewById(resId, LinearLayout.class);
    }

}
