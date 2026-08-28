package com.example.kolokvijum2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private CheckBox checkboxFetchRoles, checkboxShowDescription;
    private Button buttonTakePicture;
    private ImageView imageViewPicture;
    private TextView textViewProximity, textViewRoleDescription;

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private static final float PROXIMITY_THRESHOLD = 5.0f;

    private RoleDatabase roleDatabase;
    private RoleApiService roleApiService;
    private ActivityResultLauncher<Void> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeDatabase();
        initializeRetrofit();
        initializeSensor();
        setupCameraLauncher();
        setupCheckBoxListeners();
        setupButtonListener();
        requestPermissions();
    }

    private void initializeViews() {
        checkboxFetchRoles = findViewById(R.id.checkboxFetchRoles);
        checkboxShowDescription = findViewById(R.id.checkboxShowDescription);
        buttonTakePicture = findViewById(R.id.buttonTakePicture);
        imageViewPicture = findViewById(R.id.imageViewPicture);
        textViewProximity = findViewById(R.id.textViewProximity);
        textViewRoleDescription = findViewById(R.id.textViewRoleDescription);
    }

    private void initializeDatabase() {
        roleDatabase = RoleDatabase.getInstance(this);
    }

    private void initializeRetrofit() {
        roleApiService = RetrofitClient.getRoleApiService();
    }

    private void initializeSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }
    }

    private void setupCameraLauncher() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicturePreview(),
                bitmap -> {
                    if (bitmap != null) {
                        saveBitmapToFile(bitmap);
                        imageViewPicture.setImageBitmap(bitmap);
                    }
                }
        );
    }

    private void setupCheckBoxListeners() {
        checkboxFetchRoles.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                fetchAndSaveRolesWithEvenIds();
            }
        });

        checkboxShowDescription.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showRoleWithMaxId();
            }
        });
    }

    private void setupButtonListener() {
        buttonTakePicture.setOnClickListener(v -> openCamera());
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.INTERNET
                        },
                        100);
            }
        }
    }

    private void openCamera() {
        cameraLauncher.launch(null);
    }

    private void saveBitmapToFile(Bitmap bitmap) {
        try {
            File filesDir = getFilesDir();
            File imageFile = new File(filesDir, "photo_" + System.currentTimeMillis() + ".jpg");

            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            Toast.makeText(this, "Fotografija sačuvana: " + imageFile.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Greška pri čuvanju: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAndSaveRolesWithEvenIds() {
        roleApiService.getAllRoles().enqueue(new Callback<RoleResponse>() {
            @Override
            public void onResponse(Call<RoleResponse> call, Response<RoleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Role> allRoles = response.body().getRoles();

                    new Thread(() -> {
                        roleDatabase.roleDao().deleteAllRoles();

                        for (Role role : allRoles) {
                            if (role.id % 2 == 0) {
                                roleDatabase.roleDao().insertRole(role);
                            }
                        }

                        runOnUiThread(() ->
                                Toast.makeText(MainActivity.this,
                                        "Uloge sa parnim ID-om sačuvane",
                                        Toast.LENGTH_SHORT).show()
                        );
                    }).start();
                } else {
                    Toast.makeText(MainActivity.this, "Greška pri dobijanju uloga",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RoleResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Greška: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showRoleWithMaxId() {
        new Thread(() -> {
            Role roleWithMaxId = roleDatabase.roleDao().getRoleWithMaxId();

            runOnUiThread(() -> {
                if (roleWithMaxId != null) {
                    String description = "ID: " + roleWithMaxId.id + "\n" +
                            "Naslov: " + roleWithMaxId.title + "\n" +
                            "Opis: " + roleWithMaxId.description;
                    textViewRoleDescription.setText(description);
                } else {
                    textViewRoleDescription.setText("Nema uloga u bazi");
                }
            });
        }).start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float proximityValue = event.values[0];
            textViewProximity.setText("Proximity senzor: " + proximityValue + " cm");

            if (proximityValue > PROXIMITY_THRESHOLD) {
                Toast.makeText(this, "Daleko!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                         int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Dozvole su odobrene", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
