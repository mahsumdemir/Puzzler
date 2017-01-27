package com.mahsum.puzzle.makepuzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mahsum.puzzle.FileCouldNotCreated;
import com.mahsum.puzzle.FileCouldNotSaved;
import com.mahsum.puzzle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mahsum.puzzle.Saving.saveBitmap;

public class AnActivity extends AppCompatActivity {

    @BindView(R.id.image1) ImageView imageView1;
    @BindView(R.id.image2) ImageView imageView2;
    @BindView(R.id.image3) ImageView imageView3;
    @BindView(R.id.image4) ImageView imageView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_an);
            ButterKnife.bind(this);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.harput);
            bitmap = Bitmap.createScaledBitmap(bitmap, 1200, 1200, false);
            int maskHeight = 300, maskWeight = 300;

            BitmapMask topLeft = BitmapMask.topLeftMask(getApplicationContext()).resizeMask(maskWeight, maskHeight);
            BitmapMask left = BitmapMask.leftMask(getApplicationContext()).resizeMask(maskWeight, maskHeight);
            BitmapMask bottomLeft = BitmapMask.bottomLeftMask(getApplicationContext()).resizeMask(maskWeight, maskHeight);

            BitmapMask top = BitmapMask.topMask(getApplicationContext()).resizeMask(maskWeight, maskHeight);
            BitmapMask center = BitmapMask.centerMask(getApplicationContext()).resizeMask(maskWeight, maskHeight);
            BitmapMask bottom = BitmapMask.bottomMask(getApplicationContext()).resizeMask(maskWeight, maskHeight);

            BitmapMask topRight = BitmapMask.topRightMask(getApplicationContext()).resizeMask(maskWeight, maskHeight);
            BitmapMask right = BitmapMask.rightMask(getApplicationContext()).resizeMask(maskWeight, maskHeight);
            BitmapMask bottomRight = BitmapMask.bottomRightMask(getApplicationContext()).resizeMask(maskWeight, maskHeight);


            String storage = Environment.getExternalStorageState();
            saveBitmap(topLeft.maskBitmap(bitmap, 0, 0), storage + "/puzzle_images" + "topLeft");
            saveBitmap(top.maskBitmap(bitmap, 300, 0), storage + "/puzzle_images" + "top1");
            saveBitmap(top.maskBitmap(bitmap, 600, 0), storage + "/puzzle_images" + "top2");
            saveBitmap(topRight.maskBitmap(bitmap, 900, 0), storage + "/puzzle_images" + "top3");

            saveBitmap(left.maskBitmap(bitmap, 0, 300), storage + "/puzzle_images" + "left1");
            saveBitmap(center.maskBitmap(bitmap, 300, 300), storage + "/puzzle_images" + "center1");
            saveBitmap(center.maskBitmap(bitmap, 600, 300), storage + "/puzzle_images" + "center2");
            saveBitmap(right.maskBitmap(bitmap, 900, 300), storage + "/puzzle_images" + "right1");

            saveBitmap(left.maskBitmap(bitmap, 0, 600), storage + "/puzzle_images" + "left2");
            saveBitmap(center.maskBitmap(bitmap, 300, 600), storage + "/puzzle_images" + "center3");
            saveBitmap(center.maskBitmap(bitmap, 600, 600), storage + "/puzzle_images" + "center4");
            saveBitmap(right.maskBitmap(bitmap, 900, 600), storage + "/puzzle_images" + "right2");

            saveBitmap(bottomLeft.maskBitmap(bitmap, 0, 900), storage + "/puzzle_images" + "bottomLeft");
            saveBitmap(bottom.maskBitmap(bitmap, 300, 900), storage + "/puzzle_images" + "bottom1");
            saveBitmap(bottom.maskBitmap(bitmap, 600, 900), storage + "/puzzle_images" + "bottom2");
            saveBitmap(bottomRight.maskBitmap(bitmap, 900, 900), storage + "/puzzle_images" + "bottomRight");
        } catch (FileCouldNotCreated fileCouldNotCreated) {
            fileCouldNotCreated.printStackTrace();
        } catch (FileCouldNotSaved fileCouldNotSaved) {
            fileCouldNotSaved.printStackTrace();
        }
    }

}

