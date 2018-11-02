package com.lixxy.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class PersonManagerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Person person = new Person();
        person.setName("小红");
        mPeople.add(person);
    }

    public static final String TAG = "=========>";

    //包含Person对象的list
    private List<Person> mPeople = new ArrayList<>();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "绑定成功");

        return mBinder;
    }

    private Binder mBinder = new IPersonAidl.Stub() {

        @Override
        public List<Person> getList() throws RemoteException {
            return mPeople;
        }

        @Override
        public void addPerson(Person person) throws RemoteException {
            mPeople.add(person);
        }
    };


}
