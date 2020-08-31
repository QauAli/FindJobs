package Api;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoader {
    public static void LoadImage(ImageView imageView, String name, Context context){
        Glide.with(context)
                .load("http://192.168.8.103:5000/getImage/"+name)
                //.load("http://10.0.2.2:5000/getImage/"+name)
                .into(imageView);
    }
}
