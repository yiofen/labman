package com.fjnu.labman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.fjnu.labman.R;
import com.fjnu.labman.utils.CommonUtil;
import com.fjnu.labman.utils.DBUtil;
import com.fjnu.labman.utils.StrUtil;

public class LoginActivity extends AbActivity {

    @AbIocView(id = R.id.login_account_et)private EditText loginAcc;
    @AbIocView(id = R.id.login_pwd_et)private EditText loginPwd;
    @AbIocView(id = R.id.login_btn, click = "loginClick")private Button loginBtn;
    @AbIocView(id = R.id.login_to_register, click = "toRegiste")private TextView toRegister;
    private String userID = null;
    private String pwd = null;

    private View[] loginInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //标题初始化,考虑一下

    }


    public void loginClick(View v) {
        userID = loginAcc.getText().toString();
        pwd = loginPwd.getText().toString();

        if(StrUtil.checkID(LoginActivity.this, loginAcc, userID)
            && StrUtil.checkPwd(LoginActivity.this, loginPwd, pwd))
            this.login(userID, pwd);
        else
            return;
    }



    public void login(String userID, String pwd) {
        loginInfo = new View[]{loginAcc, loginPwd};
        boolean flag = DBUtil.loginSuccess(LoginActivity.this, loginInfo, userID, pwd);
        if(!flag)
            return;
        Intent intent = new Intent(LoginActivity.this, MagtActivity.class);
        startActivity(intent);
        finish();//登录成功之后销毁登录活动
    }

    public void toRegiste(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, CommonUtil.TO_REGISTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case CommonUtil.TO_REGISTE:
                if(resultCode == RESULT_OK) {
                    String returnID = data.getStringExtra(CommonUtil.RETURN_ID);
                    loginAcc.setText(returnID);
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
