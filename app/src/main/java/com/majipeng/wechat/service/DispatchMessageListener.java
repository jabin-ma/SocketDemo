package com.majipeng.wechat.service;


import android.os.Handler;

interface DispatchMessageListener {
        void dispatchMessage(String from,android.os.Message message);
    }