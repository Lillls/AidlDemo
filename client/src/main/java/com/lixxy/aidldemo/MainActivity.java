package com.lixxy.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.lixxy.service.Person;
import com.lixxy.service.IPersonAidl;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "===>";
    private IPersonAidl mIBookAidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.lixxy.service", "com.lixxy.service.PersonManagerService"));

        Button button = findViewById(R.id.mBtnBind);
        Button get = findViewById(R.id.mBtnGet);
        Button add = findViewById(R.id.mBtnAdd);
        final TextView name = findViewById(R.id.mTvName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Person> list = mIBookAidl.getList();
                    StringBuilder builder = new StringBuilder();
                    for (Person person : list) {
                        builder.append(person.getName());
                    }
                    name.setText(builder.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }


            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                person.setName("小明");
                try {
                    mIBookAidl.addPerson(person);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIBookAidl = IPersonAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG,"连接失败");
        }
    };
}
