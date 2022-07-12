package com.example.again;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    Connection connect;
    String ConnectionResult = "";

    private Button btn_login;
    private EditText et_login_email;
    private EditText et_login_pass;
    private String inputid, inputpasswd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_login_email = (EditText) findViewById(R.id.et_login_email);
        et_login_pass = (EditText) findViewById(R.id.et_login_pass);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputid = et_login_email.getText().toString(); //입력한 id
                inputpasswd = et_login_pass.getText().toString(); // 입력한 비번

                try {
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect = connectionHelper.connectionclass();
                    if (connect != null) {
                        String query = "EXEC dbo.SP_PDA_NLOGIN_INQUERY_EXECUTE_LOGIN '" + inputid + "', '" + inputpasswd + "', 'ko'";
                        Statement st = connect.createStatement();
                        ResultSet rs = st.executeQuery(query);

                        while (rs.next()){
                            if( rs.getString(1).isEmpty()){ //로그인 성공
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("id", inputid); //key값 id에 입력한 id를 value로
                                intent.putExtra("passwd", inputpasswd); //key값 passwd에 입력한 passwd를 value로
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "로그인 실패 " , Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
                catch (Exception ex){
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}











