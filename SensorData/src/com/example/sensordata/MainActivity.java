package com.example.sensordata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    private String TAG = "SensorData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        buildView();
    }

    private void buildView() {
        Button accelButton = (Button) findViewById(R.id.accel_button);
        Button gyroButton = (Button) findViewById(R.id.gyro_button);
        Button stepCounterButton = (Button) findViewById(R.id.step_counter_button);
        Button uncalibratedGyroButton = (Button) findViewById(R.id.uncalibrated_gyro_button);

        Button wakeupAccelButton = (Button) findViewById(R.id.wakeup_accel_button);
        Button wakeupGyroButton = (Button) findViewById(R.id.wakeup_gyro_button);
        Button wakeupUncalibratedGyroButton = (Button) findViewById(R.id.wakeup_uncalibrated_gyro_button);
        Button wakeupStepCounterButton = (Button) findViewById(R.id.wakeup_step_counter_button);

        Button linearAccelButton = (Button) findViewById(R.id.linear_accel_button);
        Button lightButton = (Button) findViewById(R.id.light_button);
        Button gravityButton = (Button) findViewById(R.id.gravity_button);
        Button magnetButton = (Button) findViewById(R.id.magnet_button);
        Button motionButton = (Button) findViewById(R.id.motion_button);
        Button gameRotationButton = (Button) findViewById(R.id.game_rotation_button);

        accelButton.setOnClickListener(mOnClickListener);
        gyroButton.setOnClickListener(mOnClickListener);
        stepCounterButton.setOnClickListener(mOnClickListener);
        uncalibratedGyroButton.setOnClickListener(mOnClickListener);

        wakeupAccelButton.setOnClickListener(mOnClickListener);
        wakeupGyroButton.setOnClickListener(mOnClickListener);
        wakeupStepCounterButton.setOnClickListener(mOnClickListener);
        wakeupUncalibratedGyroButton.setOnClickListener(mOnClickListener);

        linearAccelButton.setOnClickListener(mOnClickListener);
        lightButton.setOnClickListener(mOnClickListener);
        gravityButton.setOnClickListener(mOnClickListener);
        magnetButton.setOnClickListener(mOnClickListener);
        motionButton.setOnClickListener(mOnClickListener);
        gameRotationButton.setOnClickListener(mOnClickListener);
    }

    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
            case R.id.accel_button:
                Log.i(TAG, "Button accel pushed");
                Intent intent0 = new Intent(MainActivity.this,
                        AccelActivity.class);
                startActivity(intent0);
                break;
            case R.id.gyro_button:
                Log.i(TAG, "Button gyro pushed");
                Intent intent1 = new Intent(MainActivity.this,
                        GyroActivity.class);
                startActivity(intent1);
                break;
            case R.id.uncalibrated_gyro_button:
                Log.i(TAG, "Button uncalibrated gyro pushed");
                Intent intent2 = new Intent(MainActivity.this,
                        UncalibratedGyroActivity.class);
                startActivity(intent2);
                break;
            case R.id.step_counter_button:
                Log.i(TAG, "Button step counter pushed");
                Intent intent3 = new Intent(MainActivity.this,
                        StepCounterActivity.class);
                startActivity(intent3);
                break;

            case R.id.wakeup_accel_button:
                Log.i(TAG, "Button wakeup accel pushed");
                Intent intent4 = new Intent(MainActivity.this,
                        WakeupAccelActivity.class);
                startActivity(intent4);
                break;
            case R.id.wakeup_gyro_button:
                Log.i(TAG, "Button wakeup gyro pushed");
                Intent intent5 = new Intent(MainActivity.this,
                        WakeupGyroActivity.class);
                startActivity(intent5);
                break;
            case R.id.wakeup_uncalibrated_gyro_button:
                Log.i(TAG, "Button wakeup uncalibrated gyro pushed");
                Intent intent6 = new Intent(MainActivity.this,
                        WakeupUncalibratedGyroActivity.class);
                startActivity(intent6);
                break;
            case R.id.wakeup_step_counter_button:
                Log.i(TAG, "Button wakeup step counter pushed");
                Intent intent7 = new Intent(MainActivity.this,
                        WakeupStepCounterActivity.class);
                startActivity(intent7);
                break;

            case R.id.linear_accel_button:
                Log.i(TAG, "Button linear accel pushed");
                Intent intent8 = new Intent(MainActivity.this,
                        LinearAccelActivity.class);
                startActivity(intent8);
                break;
            case R.id.light_button:
                Log.i(TAG, "Button light pushed");
                Intent intent9 = new Intent(MainActivity.this,
                        LightActivity.class);
                startActivity(intent9);
                break;
            case R.id.gravity_button:
                Log.i(TAG, "Button gravity pushed");
                Intent intent10 = new Intent(MainActivity.this,
                        GravityActivity.class);
                startActivity(intent10);
                break;
            case R.id.magnet_button:
                Log.i(TAG, "Button magnet pushed");
                Intent intent11 = new Intent(MainActivity.this,
                        MagnetActivity.class);
                startActivity(intent11);
                break;

            case R.id.motion_button:
                Log.i(TAG, "Button motion pushed");
                Intent intent12 = new Intent(MainActivity.this,
                        MotionActivity.class);
                startActivity(intent12);
                break;

            case R.id.game_rotation_button:
                Log.i(TAG, "Button game rotation pushed");
                Intent intent13 = new Intent(MainActivity.this,
                        GameRotationActivity.class);
                startActivity(intent13);
                break;

            default:
                break;
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
