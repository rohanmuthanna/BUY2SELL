package buy2sell.rohanmuthanna;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class MsgFragment extends Fragment  {

    private EditText editTextMessage;
    private Button buttonSend;
    private String sName;
    private String sEmail;
    private String pName;
    private String bName;
    private String bEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_msg, container, false);

        return v;
    }



}
