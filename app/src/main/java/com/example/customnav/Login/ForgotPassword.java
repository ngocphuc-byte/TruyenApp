package com.example.customnav.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customnav.R;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class ForgotPassword extends AppCompatActivity {
    EditText edtEmail, edtCode;
    Button btnSend, btnXacNhan;
    TextView txtRandom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        AnhXa();
        Send();
    }



   private void Send(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                if(email.isEmpty() || !email.contains("@")){
                    Toast.makeText(ForgotPassword.this, "Vui lòng nhập đúng Gmail", Toast.LENGTH_SHORT).show();
                } else {

                    final String username = "ngocphuc00002@gmail.com";
//                    final String password = "ngocphuc05082001";
                    final String password = "xjfocsyqslbzemqf";
                    String messageToSend = txtRandom.getText().toString().trim();
                    Properties props = new Properties();
                    props.put("mail.smtp.auth","true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host","smtp.gmail.com");
                    props.put("mail.smtp.port","587");
                    Session session = Session.getInstance(props,
                            new javax.mail.Authenticator(){
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication(){
                                    return new PasswordAuthentication(username, password);
                                }
                            });
                    try {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(username));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                        message.setSubject("Mã kích hoạt lấy lại mật khẩu");
                        message.setText(messageToSend);
                        Transport.send(message);
                        Toast.makeText(ForgotPassword.this, "Đã gửi Email", Toast.LENGTH_SHORT).show();
                    } catch (MessagingException e){
                        throw new RuntimeException(e);
                    }
                    edtEmail.setEnabled(false);
                    btnSend.setEnabled(false);
                    btnXacNhan.setEnabled(true);
                }
                }

        });
       StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       StrictMode.setThreadPolicy(policy);
   }

    public void XacNhan(View view) {
        String code = txtRandom.getText().toString();
        String CODE = edtCode.getText().toString();
        if(code.equals(CODE)){
            finish();
            Intent intent = new Intent(ForgotPassword.this, ChangePassword.class);
            intent.putExtra("email", edtEmail.getText().toString().trim());
            startActivity(intent);
        } else {
            Toast.makeText(ForgotPassword.this, "Sai mã CODE", Toast.LENGTH_SHORT).show();
        }
    }

    private void AnhXa() {
        edtEmail = findViewById(R.id.edtEmail);
        edtCode = findViewById(R.id.edtCode);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnSend = findViewById(R.id.btnSend);
        txtRandom = findViewById(R.id.random);
        int code = (int) Math.floor((Math.random()*899999)+100000);
        txtRandom.setText(String.valueOf(code));
    }
}