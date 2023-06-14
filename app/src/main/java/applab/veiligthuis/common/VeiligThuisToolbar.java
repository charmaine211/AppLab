package applab.veiligthuis.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toolbar;

import androidx.annotation.Nullable;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.SignInUp.LogInActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import applab.veiligthuis.activity.profile.Profile;

public class VeiligThuisToolbar extends Toolbar {

    private ImageView homeImageView;
    private ImageView profileImageView;
    private FirebaseAuth mAuth;
    private BaseActivity returnToMainListener;

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

    public void setReturnToMainListener(BaseActivity listener) {
        this.returnToMainListener = listener;
    }

    private void init(Context context) {
        mAuth = FirebaseAuth.getInstance();

        inflate(context, R.layout.toolbar, this);

        homeImageView = findViewById(R.id.home_image_view);
        profileImageView = findViewById(R.id.profile_image_view);

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finishAndLogoutIfTaskRoot(context);
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                if( FirebaseAuth.getInstance().getCurrentUser() == null || FirebaseAuth.getInstance().getCurrentUser().isAnonymous() ){
                    popupMenu.getMenuInflater().inflate(R.menu.menu_profile_not_logged_in, popupMenu.getMenu());
                }
                else {
                    popupMenu.getMenuInflater().inflate(R.menu.menu_profile_logged_in, popupMenu.getMenu());
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.edit_profile:
                                context.startActivity(new Intent(context, Profile.class));
                                return true;
                            case R.id.sign_out:
                                mAuth.signOut();
                                returnToMainListener.returnToMain();
                                return true;
                            case R.id.sign_in:
                                context.startActivity(new Intent(context, LogInActivity.class));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }

    public void finishAndLogoutIfTaskRoot(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();

        if (tasks.size() > 0) {
            ActivityManager.RecentTaskInfo taskInfo = tasks.get(0).getTaskInfo();
            ComponentName rootActivity = taskInfo.topActivity;
            ComponentName currentActivity = taskInfo.baseActivity;

            if (!rootActivity.equals(currentActivity)) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }

            else{
                FirebaseAuth.getInstance().signOut();
            }
        }
    }

}
