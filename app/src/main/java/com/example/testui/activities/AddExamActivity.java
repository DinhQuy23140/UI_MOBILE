package com.example.testui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testui.R;
import com.example.testui.databinding.ActivityAddExamBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddExamActivity extends AppCompatActivity {

    ActivityAddExamBinding binding;
    String endcodeedImage, endcodeedImageAnswer;
    Bitmap bitmap, bitmapAnswer;
    private ImageView currentImageView;
    private ImageView currentDeleteButton;
    private ImageView currentImageViewAnswer;
    private ImageView currentDeleteButtonAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddExamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnAddQuestion.setOnClickListener(addQuestion -> {
            LayoutInflater layoutInflaterQuestion = LayoutInflater.from(AddExamActivity.this);
            View view = layoutInflaterQuestion.inflate(R.layout.add_question, null);

            Button btnAddAnswer = view.findViewById(R.id.btnAddAnswer);
            Button btnAddImage = view.findViewById(R.id.btnAddImage);
            LinearLayout lnAnswer = view.findViewById(R.id.lnAnswer);
            LayoutInflater layoutInflaterAnswer = LayoutInflater.from(AddExamActivity.this);

            // Lưu lại view cần set image
            ImageView ivQuestion = view.findViewById(R.id.imgQuestion);
            ImageView ivDeleteImage = view.findViewById(R.id.btnRemoveImage);

            btnAddImage.setOnClickListener(addImage -> {
                currentImageView = ivQuestion;
                currentDeleteButton = ivDeleteImage;

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImageQuestion.launch(intent);
            });

            btnAddAnswer.setOnClickListener(addAnswer -> {
                View viewAnswer = layoutInflaterAnswer.inflate(R.layout.add_answer, null);
                Button btnDeleteAnswer = viewAnswer.findViewById(R.id.btnDeleteAnswer);
                btnDeleteAnswer.setOnClickListener(deleteAnswer -> {
                    lnAnswer.removeView(viewAnswer);
                });
                Button btnAddImageAnswer = viewAnswer.findViewById(R.id.btnAddAnswerImage);
                btnAddImageAnswer.setOnClickListener(addImageAnswer -> {
                    currentImageViewAnswer = viewAnswer.findViewById(R.id.imgAnswer);
                    currentDeleteButtonAnswer = viewAnswer.findViewById(R.id.btnRemoveAnswerImage);
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    pickImageAnswer.launch(intent);
                });
                lnAnswer.addView(viewAnswer);
            });
            binding.questionListLayout.addView(view);
        });
    }

    //tao 1 intent chon anh tu thu vien
    private final ActivityResultLauncher<Intent> pickImageQuestion = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        endcodeedImage = enCodeImage(bitmap);

                        if (currentImageView != null && currentDeleteButton != null) {
                            currentImageView.setImageBitmap(bitmap);
                            currentImageView.setVisibility(View.VISIBLE);
                            currentDeleteButton.setVisibility(View.VISIBLE);

                            currentDeleteButton.setOnClickListener(deleteImage -> {
                                currentImageView.setVisibility(View.GONE);
                                currentDeleteButton.setVisibility(View.GONE);
                                bitmap = null;
                                endcodeedImage = null;
                            });
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    //tao 1 intent chon anh tu thu vien
    private final ActivityResultLauncher<Intent> pickImageAnswer = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        bitmapAnswer = BitmapFactory.decodeStream(inputStream);
                        endcodeedImageAnswer = enCodeImage(bitmapAnswer);

                        if (currentImageView != null && currentDeleteButton != null) {
                            currentImageViewAnswer.setImageBitmap(bitmapAnswer);
                            currentImageViewAnswer.setVisibility(View.VISIBLE);
                            currentDeleteButtonAnswer.setVisibility(View.VISIBLE);

                            currentDeleteButtonAnswer.setOnClickListener(deleteImage -> {
                                currentImageViewAnswer.setVisibility(View.GONE);
                                currentDeleteButtonAnswer.setVisibility(View.GONE);
                                bitmapAnswer = null;
                                endcodeedImageAnswer = null;
                            });
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    //set kich thuoc image
    private String enCodeImage(Bitmap bitmap){
        //set with
        int previewWith = 150;
        //set height
        int previewHeight = bitmap.getHeight() * previewWith / bitmap.getWidth();
        //scale image
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWith, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}