package com.example.ecodrive;

import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.example.ecodrive.Constants;
import com.example.ecodrive.Utils;
import java.util.ArrayList;

public class MainActivity extends FlutterActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

  private Context mContext;

  /**
    * The entry point for interacting with activity recognition.
    */
  private ActivityRecognitionClient mActivityRecognitionClient;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    mContext = this;

    Toast.makeText(mContext,
                      "1updateiiii",
                      Toast.LENGTH_SHORT)
                      .show();

    mActivityRecognitionClient = new ActivityRecognitionClient(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this);
    updateDetectedActivitiesList();
  }

  @Override
  protected void onPause() {
    PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this);
    super.onPause();
  }

  /**
    * Registers for activity recognition updates using
    * {@link ActivityRecognitionClient#requestActivityUpdates(long, PendingIntent)}.
    * Registers success and failure callbacks.
    */
  public void requestActivityUpdatesButtonHandler(View view) {
    Task<Void> task = mActivityRecognitionClient.requestActivityUpdates(
            Constants.DETECTION_INTERVAL_IN_MILLISECONDS,
            getActivityDetectionPendingIntent());

    task.addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void result) {
            Toast.makeText(mContext,
                    "1updateiiii",
                    Toast.LENGTH_SHORT)
                    .show();
            setUpdatesRequestedState(true);
            updateDetectedActivitiesList();
        }
    });

    task.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.w(">>>>", "activity_updates_not_enabled");
            Toast.makeText(mContext,
                    "activity_updates_not_enabled",
                    Toast.LENGTH_SHORT)
                    .show();
            setUpdatesRequestedState(false);
        }
    });
  }

  /**
    * Removes activity recognition updates using
    * {@link ActivityRecognitionClient#removeActivityUpdates(PendingIntent)}. Registers success and
    * failure callbacks.
    */
  public void removeActivityUpdatesButtonHandler(View view) {
      Task<Void> task = mActivityRecognitionClient.removeActivityUpdates(
              getActivityDetectionPendingIntent());
      task.addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void result) {
              Toast.makeText(mContext,
                      "activity_updates_removed",
                      Toast.LENGTH_SHORT)
                      .show();
              setUpdatesRequestedState(false);
          }
      });

      task.addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Log.w(">>>>", "Failed to enable activity recognition.");
              Toast.makeText(mContext, "activity_updates_not_removed",
                      Toast.LENGTH_SHORT).show();
              setUpdatesRequestedState(true);
          }
      });
  }

  /**
    * Gets a PendingIntent to be sent for each activity detection.
    */
  private PendingIntent getActivityDetectionPendingIntent() {
      Intent intent = new Intent(this, DetectedActivitiesIntentService.class);

      // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
      // requestActivityUpdates() and removeActivityUpdates().
      return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
  }

  /**
    * Retrieves the boolean from SharedPreferences that tracks whether we are requesting activity
    * updates.
    */
  private boolean getUpdatesRequestedState() {
      return PreferenceManager.getDefaultSharedPreferences(this)
              .getBoolean(Constants.KEY_ACTIVITY_UPDATES_REQUESTED, false);
  }

  /**
    * Processes the list of freshly detected activities. Asks the adapter to update its list of
    * DetectedActivities with new {@code DetectedActivity} objects reflecting the latest detected
    * activities.
    */
  protected void updateDetectedActivitiesList() {
      ArrayList<DetectedActivity> detectedActivities = Utils.detectedActivitiesFromJson(
              PreferenceManager.getDefaultSharedPreferences(mContext)
                      .getString(Constants.KEY_DETECTED_ACTIVITIES, ""));
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
      if (s.equals(Constants.KEY_DETECTED_ACTIVITIES)) {
          updateDetectedActivitiesList();
      }
  }

      /**
     * Sets the boolean in SharedPreferences that tracks whether we are requesting activity
     * updates.
     */
    private void setUpdatesRequestedState(boolean requesting) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(Constants.KEY_ACTIVITY_UPDATES_REQUESTED, requesting)
                .apply();
    }
}
