package buy2sell.rohanmuthanna;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContactUsFragment extends Fragment {

    TextView feedback;
    ImageView instagram,twitter,website;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contactus_fragment, container, false);

        feedback = (TextView) v.findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:" + Uri.encode("buy2sellapp@gmail.com") + "?subject=" +
                        Uri.encode("Contact Buy2Sell ") + "&body=" + Uri.encode("");

                Uri uri = Uri.parse(uriText);
                intent.setData(uri);
                startActivity(Intent.createChooser(intent, "Send Email"));

            }
        });


        instagram = v.findViewById(R.id.instagram);
        instagram.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.instagram.com/buy2sellapp/");
            }


            private void gotoUrl(String s) {
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });


        twitter = v.findViewById(R.id.twitter);
        twitter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoUrl("https://twitter.com/buy2sellapp");
            }

            private void gotoUrl(String s) {
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

        website = v.findViewById(R.id.website);
        website.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gotoUrl("https://buy2sellapp.wixsite.com/download");
            }

            private void gotoUrl(String s) {
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });


        return v;
    }


}
