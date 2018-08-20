package com.aviator.kusca.helpers;

import android.os.Handler;
import android.os.Message;

import com.aviator.kusca.KuscaElection;

import java.lang.ref.WeakReference;

/**
 * Created by Aviator on 12/5/2017. Tran
 */

public class CloseActivity extends Handler{

    private WeakReference<KuscaElection> weakReference_1;

    public CloseActivity(WeakReference<KuscaElection> weakReference) {
       // weakReference_1=new WeakReference<KuscaElection>(weakReference);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        final KuscaElection kuscaElection=weakReference_1.get();
    }

    @Override
    public void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
    }
}
