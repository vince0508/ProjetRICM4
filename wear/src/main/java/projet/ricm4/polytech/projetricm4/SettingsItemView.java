package projet.ricm4.polytech.projetricm4;

import android.support.wearable.view.WearableListView;
import android.content.Context;
import java.util.List;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.FrameLayout;
import android.view.View;
public final class SettingsItemView extends FrameLayout implements WearableListView.OnCenterProximityListener {

    final ImageView image;
    final TextView text;

    public SettingsItemView(Context context) {
        super(context);
        View.inflate(context, R.layout.wearablelistview_item, this);
        image = (ImageView) findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);

    }


    @Override
    public void onCenterPosition(boolean b) {

        //Animation example to be ran when the view becomes the centered one
        image.animate().scaleX(1f).scaleY(1f).alpha(1);
        text.animate().scaleX(1f).scaleY(1f).alpha(1);

    }

    @Override
    public void onNonCenterPosition(boolean b) {
    
        //Animation example to be ran when the view is not the centered one anymore
        image.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);
        text.animate().scaleX(0.8f).scaleY(0.8f).alpha(0.6f);

    }
}