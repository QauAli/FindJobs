package ModelClasses;

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
    public static boolean iscnic(EditText text){
        String temp = text.getText().toString();
        if(temp.length()<13)
            return false;
        if(temp.length()>13)
            return false;
        return true;
    }
    public static boolean isPhone(EditText text){
        String temp = text.getText().toString();
        if(temp.length()<11)
            return false;
        if(temp.length()>11)
            return false;
        return true;
    }
}
