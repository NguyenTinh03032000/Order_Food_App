package com.example.order_food_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.order_food_master.DAO.EmployeeDAO;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etPassword;
    Button btLogIn, btSignIn;
    EmployeeDAO employeeDAO;
    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
    }
    private void addControls() {
        etUsername = (EditText) findViewById(R.id.et_usernameLogin);
        etPassword = (EditText) findViewById(R.id.et_passwordLogin);
        btLogIn = (Button) findViewById(R.id.bt_loginLogin);
        btSignIn = (Button) findViewById(R.id.bt_signIn);
        tvTitle = (TextView) findViewById(R.id.tv_login_title);

        btLogIn.setOnClickListener(this);
        btSignIn.setOnClickListener(this);

        employeeDAO = new EmployeeDAO(this);
    }
    protected void onResume() {
        // update GUI, data,.... when SignInActivity close
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.bt_loginLogin:
                checkLogIn();
                break;
            case R.id.bt_signIn:
                Intent intent = new Intent(this,DangKy.class);
                intent.putExtra("firstSignin",1);
                startActivity(intent);
                break;
        }
    }
    private void checkLogIn(){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        int idEmploy  = employeeDAO.checkEmployees(username,password);
        if(idEmploy > 0){

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("T_Username",username);
            intent.putExtra("T_idEmploy",idEmploy);
            startActivity(intent);
        } else{
            Toast.makeText(this, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}