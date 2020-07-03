package com.icandothisallday2020.ex86firebasecloudmessagepush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Script;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getToken(View view) {
        //앱을 FCM 서버에 등록하면 앱을 식별할 수 있는 고유 토큰값(문자열-)을 줌
        //FCM-Firebase Cloud Message
        //Token 값(instance ID)를 통해 디바이스들 식별->메세지 전달

        FirebaseInstanceId instanceId=FirebaseInstanceId.getInstance();
        instanceId.getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String token=task.getResult().getToken();
                //Logcat 창에 token 값 출력
                Log.i("token",token);
                String real_token="cYPl8cQ4Qh-By8fASy5qxn:APA91bHQFDXfHnERS-Sd2fof_8vLG5Abkh9BcOSAtdoGBV_FGXDFC4laWEOweXFeH0AJmHCxuuBwCOuyH78UPu0gyqBjPwhko3X1YjaE4X1E1Vfshb_PfasTi3VsSM3rKZMD5b5bqyzT";

                //실무에서는 이 token 값을 본인의 웹서버(soon0.dothome)에 전송하여 웹 DB에 token 값 저장
            }
        });
    }
}
