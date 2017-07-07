package trevornelson.bingo_watcher;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class bingoGame extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextView valueTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bingo_game);

        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.bingo_add_content);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                dispatchTakePictureIntent();
                // TODO: Do something with yout menu items, or return false if you don't want to show them
                return false;
            }
        });

        LinearLayout linearLayout =  (LinearLayout) findViewById(R.id.bingo_cards);
        valueTV = new TextView(this);
        valueTV.setText("hallo hallo");
        valueTV.setId(2);
        valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        linearLayout.addView(valueTV);
    }

    public void dispatchTakePictureIntent() {
        Log.d("TEST", "You hit the button");
        Intent takePictureIntent = new Intent(this, OcrCaptureActivity.class);
        takePictureIntent.putExtra(OcrCaptureActivity.AutoFocus, true);

//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null) {
                String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
//                valueTV.setText(R.string.ocr_success);
                valueTV.setText(text);
//                Log.d(TAG, "Text read: " + text);
            } else {
                valueTV.setText("failed ocr");
//                Log.d(TAG, "No Text captured, intent data is null");
            }
        }
    }
}
