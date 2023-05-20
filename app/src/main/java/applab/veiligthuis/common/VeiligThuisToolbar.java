package applab.veiligthuis.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.SignInUp.LogInActivity;

public class VeiligThuisToolbar extends Toolbar {

    private ImageView homeImageView;
    private ImageView profileImageView;

    public VeiligThuisToolbar(Context context) {
        super(context);
        init(context);
    }

    public VeiligThuisToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VeiligThuisToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        inflate(context, R.layout.toolbar, this);

        homeImageView = findViewById(R.id.home_image_view);
        profileImageView = findViewById(R.id.profile_image_view);

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        LogInActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
