package com.example.wpy.teamattention;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpy.teamattention.database.OkhttpAction;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private TextView register;
    private TextView change_account;
    private AutoCompleteTextView account;
    private EditText password;
    private CheckBox checkBox;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    boolean rememberPass;
    private TextView show;

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        login = (Button) findViewById(R.id.login);
        checkBox = (CheckBox)findViewById(R.id.isRemember);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
        account = (AutoCompleteTextView)findViewById(R.id.login_account);
        password = (EditText)findViewById(R.id.login_password);
        show = (TextView)findViewById(R.id.login_show);
        final TextView show2 = findViewById(R.id.login_show2);

        rememberPass = pref.getBoolean("remember_password", false);
        if (rememberPass){
            String account1 = pref.getString("account", "");
            String password1 = pref.getString("password", "");
            account.setText(account1);
            password.setText(password1);
            checkBox.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String account1 = account.getText().toString();
                final String password1 = password.getText().toString();
                editor = pref.edit();
                if(checkBox.isChecked()){
                    editor.putBoolean("remember_password", true);
                    editor.putString("account", account1);
                    editor.putString("password", password1);
                }else{
                    editor.clear();
                }
                editor.apply();
                if (account1.equals("")){
                    Toast.makeText(LoginActivity.this, "用户名出错",Toast.LENGTH_SHORT).show();
                }else if (password1.equals("")){
                    Toast.makeText(LoginActivity.this, "密码出错",Toast.LENGTH_SHORT).show();
                }else {
                    show.setText("");
                    OkhttpAction okhttpAction = new OkhttpAction(LoginActivity.this);
                    try {
                        okhttpAction.getAuthorization(account1, password1, show);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < 10; i++){
                        if (show.getText().toString().equals("")){
                            SystemClock.sleep(20);
                        }
                        else break;;
                    }
                    String token = show.getText().toString();
                    okhttpAction.setToken(token);
                    if (token.equals("")){
                        Toast.makeText(LoginActivity.this, "账户或密码错误", Toast.LENGTH_SHORT).show();
                    }else {
                        okhttpAction.get_user(token, show2);
                        for (int i = 0; i < 10; i++){
                            if (show2.getText().toString().equals("")){
                                SystemClock.sleep(20);
                            }
                            else break;
                        }
                        String con = show2.getText().toString();
                        try{
                            JSONObject jsonObject = new JSONObject(con);
                            int user_id = jsonObject.getInt("id");
                            String user_email = jsonObject.getString("email");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("token", token);
                            intent.putExtra("user_id", user_id);
                            intent.putExtra("account", account1);
                            intent.putExtra("user_email", user_email);
                            startActivity(intent);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(LoginActivity.this, "token = " + okhttpAction.token, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        register = (TextView) findViewById(R.id.login_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()){
                Toast.makeText(context, "网络已连接", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "无网络连接", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
