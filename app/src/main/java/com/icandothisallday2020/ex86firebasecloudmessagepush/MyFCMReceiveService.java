package com.icandothisallday2020.ex86firebasecloudmessagepush;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFCMReceiveService extends FirebaseMessagingService {



    //PUSH server 에서 보낸 메세지가 수신되었을 때 자동으로 발동하는 메소드
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        //remoteMessage : 받은 원격 메세지
        super.onMessageReceived(remoteMessage);
        //이 안에서는 Notification 만 만들 수 있음(Toast 도 불가)
        //우선, receive 확인용으로 Logcat 에 출력
        Log.i("TAG","message received");

        //메세지를 보낸 기기명 [ Firebase 서버에서 자동 지정된 이름 ]
        String fromWho=remoteMessage.getFrom();

        //알림에대한 데이터
        String notiTitle="title"; //제목이 안왔을 때 기본 값
        String notiTBody="body text"; //내용이 안왔을 때 기본 값
        if(remoteMessage.getNotification()!=null){
            notiTitle=remoteMessage.getNotification().getTitle();
            notiTBody=remoteMessage.getNotification().getBody();
//            Uri notiImg=remoteMessage.getNotification().getImageUrl(); 유료버전 이미지까지 보냈을 때 가져오기
        }

        //firebase push message 에 추가 데이터(  [key - value] 송신  )가 있을 경우
        Map<String, String> data = remoteMessage.getData();
        String name="",msg="";
        if(data!=null){
            name=data.get("name");
            msg=data.get("msg");
        }


        Log.i("TAG",fromWho+":"+notiTitle+"-"+notiTBody+">>"+name+"|"+msg);//알림 수신 확인

        //받은 값을 Notification 객체를 만들어 공지
        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("ch01","channel01",NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);

            builder=new NotificationCompat.Builder(this,"ch01");
        }else builder=new NotificationCompat.Builder(this,null);//ver.O 이전에는 channel X

        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentTitle(notiTitle);
        builder.setContentText(notiTBody);

        //알림을 선택했을떄 실행될 액티비티를 실행하는 Intent 생성
        Intent intent=new Intent( this,MessageActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("msg",msg);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//액티비티가 없을때 생성하고 보여줌
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//액티비티가 중복될때 없애고 최근걸로
        //인텐트 보류
        PendingIntent pendingIntent=PendingIntent.getActivity(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        Notification notification=builder.build();
        manager.notify(111,notification);
    }
}
