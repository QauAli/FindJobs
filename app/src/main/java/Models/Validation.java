package Models;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class Validation {
    public static boolean isEmail(EditText text){
        CharSequence email = text.getText().toString();
        return (!Patterns.EMAIL_ADDRESS.matcher(email).matches() );
    }
    public static boolean isEmpty(EditText text){
        CharSequence txt = text.getText().toString();
        return TextUtils.isEmpty(txt);
    }
}
