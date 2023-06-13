package applab.veiligthuis.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toolbar;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.SignInUp.LogInActivity;
import applab.veiligthuis.activity.home.MainActivity;
import applab.veiligthuis.activity.profile.Profile;

public class VeiligThuisToolbar extends Toolbar {

    private ImageView homeImageView;
    private ImageView profileImageView;
    private FirebaseAuth mAuth;


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
                                returnToMain();
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

    private void returnToMain() {
        Context context = getContext();
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainIntent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
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
