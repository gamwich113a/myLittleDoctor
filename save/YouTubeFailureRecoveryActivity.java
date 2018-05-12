package client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical;

import android.content.Intent;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import client.mylittledoctor.formation.arlebois.com.mylittledoctor.MainActivity;
import client.mylittledoctor.formation.arlebois.com.mylittledoctor.R;

/**
 * An abstract activity which deals with recovering from errors which may occur
 * during API initialization, but can be corrected through user action.
 */
public abstract class YouTubeFailureRecoveryActivity extends SherlockFragmentActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            //String errorMessage = String.format(getString(R.string.error_player),
            //        errorReason.toString());
            String errorMessage = "errorNIicolas";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(MainActivity.DEVELOPER_KEY, this);
        }
    }

    protected abstract YouTubePlayer.Provider getYouTubePlayerProvider();

}