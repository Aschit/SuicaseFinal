package com.example.suicasedesign;

import android.app.Activity;
import android.view.View;

import com.github.dhaval2404.imagepicker.ImagePicker;

public class ImagePickerUtility {
    public static void pickImage(View view, Activity activity) {
        ImagePicker.with(activity)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }
}

// pick image method is used to pick the image from the device storage
//View view: A view that is used to pick the Image,
// such as a button or image view.
//Activity activity: The current activity in which the image selection is triggered.
//Inside the method, the ImagePicker library is used to configure and launch the image selection process.
//ImagePicker.with(activity): Initializes the ImagePicker with the given activity.
//.crop(): Optionally enables cropping of the selected image.
//.compress(1024): Specifies that the final image size should be less than 1 MB.
//.maxResultSize(1080, 1080): Specifies that the final image resolution should be less than 1080 x 1080 pixels.
//.start(): Starts the image selection process.