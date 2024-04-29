package com.example.contacts;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AddDisplayContactActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int GALLERY_PERMISSION_REQUEST_CODE = 101;

    ImageView imageViewProfile;
    EditText editTextFirstName, editTextLastName, editTextAddress, editTextEmail, editTextPhone;
    Button buttonSave, buttonCancel, buttonDelete;
    int contactId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_display_contact);

        // Check and request permission to access external storage
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_PERMISSION_REQUEST_CODE);
        }

        imageViewProfile = findViewById(R.id.imageViewProfile);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonClose);
        buttonDelete = findViewById(R.id.buttonDelete);

        buttonSave.setOnClickListener(v -> saveContact());
        buttonCancel.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        if (intent.hasExtra("contactId")) {
            contactId = intent.getIntExtra("contactId", -1);
            displayContactDetails(contactId);
            buttonDelete.setVisibility(View.VISIBLE);
            buttonDelete.setOnClickListener(v -> deleteContact(contactId));
        } else {
            buttonDelete.setVisibility(View.GONE);
        }

        imageViewProfile.setOnClickListener(v -> openGallery());
    }

    private void saveContact() {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String address = editTextAddress.getText().toString();
        String email = editTextEmail.getText().toString();
        String phone = editTextPhone.getText().toString();

        // Pass the selected image URI to the MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("firstName", firstName);
        resultIntent.putExtra("lastName", lastName);
        resultIntent.putExtra("address", address);
        resultIntent.putExtra("email", email);
        resultIntent.putExtra("phone", phone);
        setResult(RESULT_OK, resultIntent);

        finish();
    }

    private void deleteContact(int contactId) {
        finish();
    }

    private void displayContactDetails(int contactId) {
        editTextFirstName.setText("John");
        editTextLastName.setText("Doe");
        editTextAddress.setText("123 Main St");
        editTextEmail.setText("john.doe@example.com");
        editTextPhone.setText("123-456-7890");
        imageViewProfile.setImageResource(R.drawable.icon16);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageViewProfile.setImageURI(selectedImageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GALLERY_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }
}