package com.majipeng.wechat.service;

import android.os.Handler;

class CallbackHandler extends Handler {
        private DispatchMessageListener listener;
        private String name;
        CallbackHandler(DispatchMessageListener listener, String name){
            this.listener=listener;
            this.name=name;
            this.sendEmptyMessage(MessengerService.EVENT_HANDLER_INIT);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            listener.dispatchMessage(name,msg);
        }
    }