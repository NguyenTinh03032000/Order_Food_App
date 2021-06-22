package com.example.order_food_master;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.order_food_master.DAO.EmployeeDAO;
import com.example.order_food_master.DTO.EmployeeDTO;
import com.example.order_food_master.Fragment.DatePickerFragment;
public class DangKy extends AppCompatActivity implements View.OnClickListener,View.OnFocusChangeListener{
    EditText etUsername, etPassword, etBirthday, etPhoneNumber;
    RadioGroup rgGenre;
    TextView tv_title;
    RadioButton rdioMale,rdioFemale;
    Button btConfirm, btExit;
    String genre;
    EmployeeDAO employeeDAO;
    int idEmploy=0;
    int firstSignin = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        (DangKy.this).getSupportActionBar().setTitle("Đăng ký");
        employeeDAO = new EmployeeDAO(this);
        addControls();

        firstSignin = getIntent().getIntExtra("firstSignin",0);

        idEmploy = getIntent().getIntExtra("T_idEmploy",0);

        if(idEmploy != 0){
            tv_title.setText("Cập nhật thông tin");
            EmployeeDTO employeeDTO = employeeDAO.getEmployeeWithID(idEmploy);

            etUsername.setText(employeeDTO.getUsername());
            etUsername.setEnabled(false);
            etUsername.setTextColor(Color.GRAY);
            etPassword.setText(employeeDTO.getPassword());
            etBirthday.setText(employeeDTO.getBirthday());
            etPhoneNumber.setText(String.valueOf(employeeDTO.getPhone()));
            String genre = employeeDTO.getGenre();
            if(genre.equals("Nam")){
                rdioMale.setChecked(true);
            } else if(genre.equals("Nữ")){
                rdioFemale.setChecked(true);
            }
            btConfirm.setText("Xác nhận");
        }
    }
    private void addControls() {
        etUsername = (EditText) findViewById(R.id.et_userName);
        etPassword = (EditText) findViewById(R.id.et_password);
        etBirthday = (EditText) findViewById(R.id.et_birthday);
        etPhoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        rdioMale = (RadioButton) findViewById(R.id.rdio_Male);
        rdioFemale = (RadioButton) findViewById(R.id.rdio_Female);
        rgGenre = (RadioGroup) findViewById(R.id.rg_Genre);
        btConfirm = (Button) findViewById(R.id.bt_confirm);
        btExit = (Button) findViewById(R.id.bt_exit);
        tv_title=(TextView)findViewById(R.id.tv_login_title) ;
        btConfirm.setOnClickListener( this);
        btExit.setOnClickListener(this);
        etBirthday.setOnFocusChangeListener(this);
    }
    private void processInsert(){
        String sUsername = etUsername.getText().toString();
        String sPassword = etPassword.getText().toString();
        switch (rgGenre.getCheckedRadioButtonId()){
            case R.id.rdio_Male:
                genre = "Nam";
                break;
            case R.id.rdio_Female:
                genre = "Nữ";
                break;
        }
        String sBirthday = etBirthday.getText().toString();
        int iPhone = Integer.parseInt(etPhoneNumber.getText().toString());

        if (sUsername == null || sUsername.equals("")) {
            Toast.makeText(this,"Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
        } else if (sPassword == null || sPassword.equals("")){
            Toast.makeText(this,"Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        } else {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setUsername(sUsername);
            dto.setPassword(sPassword);
            dto.setGenre(genre);
            dto.setBirthday(sBirthday);
            dto.setPhone(iPhone);

            long rs = employeeDAO.insert(dto);
            if(rs != 0){
                Toast.makeText(this,"Đăng ký thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void processUpdate(int idEmploy){
        String sUsername = etUsername.getText().toString();
        String sPassword = etPassword.getText().toString();
        switch (rgGenre.getCheckedRadioButtonId()){
            case R.id.rdio_Male:
                genre = "Nam";
                break;
            case R.id.rdio_Female:
                genre = "Nữ";
                break;
        }
        String sBirthday = etBirthday.getText().toString();
        int iPhone = Integer.parseInt(etPhoneNumber.getText().toString());

        if (sPassword == null || sPassword.equals("")){
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        } else {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setId(idEmploy);
            dto.setUsername(sUsername);
            dto.setPassword(sPassword);
            dto.setGenre(genre);
            dto.setBirthday(sBirthday);
            dto.setPhone(iPhone);
            boolean rs = employeeDAO.update(dto);
            if(rs){
                Toast.makeText(this,"Cập nhật thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.bt_confirm:
                if(idEmploy != 0){
                    // update
                    processUpdate(idEmploy);
                } else{
                    // insert
                    processInsert();
                }
                break;
            case R.id.bt_exit:
                finish();
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        int id = view.getId();
        switch (id){
            case R.id.et_birthday:
                if (b){
                    // show popup calendar
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getFragmentManager(),"Tag_Birthday");
                }
                break;
        }
    }
}