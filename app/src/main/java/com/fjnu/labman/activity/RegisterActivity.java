package com.fjnu.labman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ab.activity.AbActivity;
import com.ab.view.ioc.AbIocView;
import com.fjnu.labman.R;
import com.fjnu.labman.model.User;
import com.fjnu.labman.utils.CommonUtil;
import com.fjnu.labman.utils.DBUtil;
import com.fjnu.labman.utils.StrUtil;

/**
 * 注册
 */
public class RegisterActivity extends AbActivity {

    private String userID = null;
    private String pwd = null;
    private String pwdConf = null;
    private String userName = null;
    private String userTel = null;
    private String userSex = null;
    private String userClassName = null;
    private Integer userAge = 0;
    private View[] infoArray;

    @AbIocView(id = R.id.regt_account_et)private EditText regtAcc;
    @AbIocView(id = R.id.regt_pwd_et)private EditText regtPwd;
    @AbIocView(id = R.id.regt_conf_pwd)private EditText regtPwdConf;
    @AbIocView(id = R.id.name_et)private EditText uName;
    @AbIocView(id = R.id.tel_et)private EditText uTel;
    @AbIocView(id = R.id.class_name_et)private EditText uClassName;
    @AbIocView(id = R.id.age_spin)private Spinner uAge;
    @AbIocView(id = R.id.sex_spin)private Spinner uSex;
    @AbIocView(id = R.id.regt_btn, click = "registerClick")private Button regtBtn;
    @AbIocView(id = R.id.cancel_btn, click = "cancelClick")private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);



    }

    public void registerClick(View v) {

        this.init();

        //输入规范检测
        if(!StrUtil.checkID(RegisterActivity.this, regtAcc, userID))
            return;
        if(!StrUtil.checkPwd(RegisterActivity.this, regtPwd, pwd))
            return;
        if(!StrUtil.checkPwdConf(RegisterActivity.this, regtPwdConf, pwd, pwdConf))
            return;
        if(!StrUtil.checkInfo(RegisterActivity.this, infoArray, userName, userTel, userSex, userClassName, String.valueOf(userAge)))
            return;

        this.register(userID, pwd, userName, userTel, userSex, userClassName, userAge);
    }

    public void cancelClick(View v) {
        finish();
    }

    private void init() {
        userID = regtAcc.getText().toString();
        pwd = regtPwd.getText().toString();
        pwdConf = regtPwdConf.getText().toString();
        userName = uName.getText().toString();
        userTel = uTel.getText().toString();
        userSex = uSex.getSelectedItem().toString();
        userClassName = uClassName.getText().toString();
        userAge = Integer.parseInt(uAge.getSelectedItem().toString());
        infoArray = new View[]{uName, uTel, uSex, uClassName, uAge};
    }

    public void register(String userID, String pwd, String userName, String userTel,
                         String userSex, String userClassName, Integer userAge) {

        User user = new User(userID, userName, pwd, userTel, userSex, userClassName, userAge);
        //数据库匹配查看是否已存在当前帐号
        boolean flag = DBUtil.registerSuccess(RegisterActivity.this, regtAcc, user, userID);
        if(!flag)
            return;

        //返回账号到登录界面
        Intent intent = new Intent();
        intent.putExtra(CommonUtil.RETURN_ID, userID);
        setResult(RESULT_OK, intent);
        finish();
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
