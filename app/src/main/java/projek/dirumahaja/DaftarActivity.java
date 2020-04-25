package projek.dirumahaja;
import projek.dirumahaja.api.RegisterService;
import projek.dirumahaja.model.BaseResponse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class DaftarActivity extends AppCompatActivity {
    EditText etNama,etEmail,etPassword,etNomorMahasiswa,etInstansi;
    Button btnDaftar;

   final String urlDaftar ="http://rumahmakanku.000webhostapp.com/apiDirumahAja/register.php";

    private RegisterService registerService;

    public static void start(Context context) {
        Intent intent = new Intent(context, DaftarActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        etNama = (EditText) findViewById(R.id.et_daf_nama);
        etEmail = (EditText) findViewById(R.id.et_daf_email);
        etPassword = (EditText) findViewById(R.id.et_daf_password);
        etInstansi = (EditText) findViewById(R.id.et_daf_instansi);
        etNomorMahasiswa = (EditText) findViewById(R.id.et_daf_nomorMahasiswa);
        btnDaftar =(Button) findViewById(R.id.btn_daftar);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAct();

//                requestAsync(strEmail,strPassword,strNama,strNomorMahasiswa,strInstansi);
            }
        });
    }
    void registerAct() {
        String strNama = etNama.getText().toString();
        String strEmail = etEmail.getText().toString();
        String strPassword = etPassword.getText().toString();
        String strNomorMahasiswa = etNomorMahasiswa.getText().toString();
        String strInstansi = etInstansi.getText().toString();

        if(TextUtils.isEmpty(strNama)) {
            etNama.setError("Name cannot be empty !");
            return;
        }

        if(TextUtils.isEmpty(strEmail)) {
            etEmail.setError("Email cannot be empty !");
            return;
        }

        if(TextUtils.isEmpty(strPassword)) {
            etPassword.setError("Password cannot be empty !");
            return;
        }

//        if(TextUtils.isEmpty(strNomorMahasiswa)) {
//            etNomorMahasiswa.setError("Password cannot be empty !");
//            return;
//        }

        registerService = new RegisterService(this);
        registerService.doRegister(strNama, strEmail,strPassword, strNomorMahasiswa,strInstansi, new Callback() {
            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(DaftarActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(retrofit2.Call call, retrofit2.Response response) {
                BaseResponse baseResponse = (BaseResponse) response.body();

                if(baseResponse != null) {
                    if(!baseResponse.isError()) {
                        LoginActivity.start(DaftarActivity.this);
                        DaftarActivity.this.finish();
                    }

                    Toast.makeText(DaftarActivity.this, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void requestAsync(final String strEmail, final String strPassword,
//                              String strNama, String strNomorMahasiswa,String strInstansi) {
//        int SDK_INT = android.os.Build.VERSION.SDK_INT;
//        if (SDK_INT > 8) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                    .permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(5, TimeUnit.SECONDS);
//        builder.readTimeout(5, TimeUnit.SECONDS);
//        builder.writeTimeout(5, TimeUnit.SECONDS);
//        OkHttpClient client = builder.build();
//        RequestBody formBody = new FormBody.Builder()
//                .add("nama",strNama)
//                .add("email", strEmail)
//                .add("password", strPassword)
//                .add("nomorMahasiswa",strNomorMahasiswa)
//                .add("instansi", strInstansi)
//                .build();
//        Request request = new Request.Builder()
//                //.url("https://publicobject.com/helloworld.txt")
//                .url(urlDaftar)
//                .post(formBody)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("debuging", "Request Failed."+e.getMessage());
//                responseAsync("Request Failed."+e.getMessage());
//                e.printStackTrace();
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    if (response.isSuccessful()) {
//                        String responseString = response.body().string();
////                        Log.d("debuging", responseString);
////                        responseAsync("berhasil");
//                        Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(DaftarActivity.this,LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
////                        Log.d("debuging", "Error "+ response);
//                        responseAsync("Email atau Password Salah!");
//                    }
//                } catch (IOException e) {
////                    Log.d("debuging", "Exception caught : ", e);
//                    responseAsync("Error "+ e.getMessage());
//                }
//            }
//        });
////        Toast.makeText(this, "OkHTTP requestAsync", Toast.LENGTH_SHORT).show();
//    }
//    private void responseAsync(final String responseStr) {
//        runOnUiThread(new Runnable() {
//            public void run() {
//                Toast.makeText(getApplicationContext(), responseStr, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
