package com.majipeng.wechat;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.majipeng.wechat.service.MessengerServiceImpl;

import majipeng.model.User;


public class MainActivity extends MessengerActivity implements View.OnClickListener, TextWatcher {
    static final String TAG = "MainActivity";

    @Override
    public void onMessengerServiceCreated() {
        Toast.makeText(this, "Service created", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMessengerServiceDestroy() {

    }

    @Override
    public void handleServiceReply(Message message) {

    }


    private EditText mEdittext_UserName, mEdittext_Password;
    private Button mBtn_Logon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEdittext_UserName = (EditText) findViewById(R.id.login_username);
        mEdittext_Password = (EditText) findViewById(R.id.login_password);
        mBtn_Logon = (Button) findViewById(R.id.login_button);


        mEdittext_UserName.addTextChangedListener(this);
        mBtn_Logon.setOnClickListener(this);


    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        String username = mEdittext_UserName.getText().toString();
        String password = mEdittext_Password.getText().toString();
        if (TextUtils.isEmpty(username)) {
            mEdittext_UserName.setError(getString(R.string.error_username_short));
        } else if (TextUtils.isEmpty(password)) {
            mEdittext_Password.setError(getString(R.string.error_password_short));
        } else {
            User user=new User();
            user.setName(username);
            user.setPassword(password);
            Message message = Message.obtain();
            message.what= MessengerServiceImpl.EVENT_LOGON;
            message.obj = user;
            sendToService(message);
        }
    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * are about to be replaced by new text with length <code>after</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d(TAG, "beforeTextChanged:" + s + " start:" + start + " count:" + count);
    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * have just replaced old text that had length <code>before</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d(TAG, "onTextChanged:" + s + " start:" + start + " before:" + before + " count:" + count);
    }

    /**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "afterTextChanged:" + s);
    }
}
