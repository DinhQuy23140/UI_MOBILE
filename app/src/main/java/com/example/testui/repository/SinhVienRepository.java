package com.example.testui.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.example.testui.client.Client;
import com.example.testui.model.Assignment;
import com.example.testui.model.LoginResponse;
import com.example.testui.model.Student;
import com.example.testui.model.Supervisor;
import com.example.testui.service.ApiService;
import com.example.testui.service.AuthService;
import com.example.testui.sharepreference.SharePreferenceManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class SinhVienRepository {
    ApiService apiService;
    AuthService authService;
    private static volatile SinhVienRepository instance;
    MutableLiveData<Boolean> loginResult = new MutableLiveData<>(false);
    MutableLiveData<Student> student = new MutableLiveData<>();
    MutableLiveData<List<Supervisor>> listSupervisor = new MutableLiveData<>();
    MutableLiveData<Assignment> assignmentByIdStudent = new MutableLiveData<>();
    SharePreferenceManage sharePreferenceManage;
    // private constructor : singleton access
    public SinhVienRepository(Context context) {
        apiService = Client.getInstance().create(ApiService.class);
        authService = Client.getInstance().create(AuthService.class);
        sharePreferenceManage = new SharePreferenceManage(context);
    }

    public MutableLiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(MutableLiveData<Boolean> loginResult) {
        this.loginResult = loginResult;
    }

    public void login(String username, String password, boolean isSave) {
        Log.d("Login", "Bắt đầu login với username: " + username);

        Map<String, String> body = new HashMap<>();
        body.put("email", username);
        body.put("password", password);

        Log.d("Login", "Request body: " + body.toString());

        Call<LoginResponse> call = apiService.login(body);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("Login", "onResponse được gọi");
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Login", "Response thành công: " + response.body().toString());
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()) {
                        Log.d("Login", "Đăng nhập thành công. Lưu thông tin user...");

                        sharePreferenceManage.saveTokenType(loginResponse.getToken_type());
                        sharePreferenceManage.saveAccessToken(loginResponse.getAccess_token());
                        sharePreferenceManage.saveUserId(loginResponse.getUserLogin().getId());
                        sharePreferenceManage.saveEmail(loginResponse.getUserLogin().getEmail());
                        sharePreferenceManage.saveFullName(loginResponse.getUserLogin().getFullname());
                        sharePreferenceManage.saveRole(loginResponse.getUserLogin().getRole());
                        sharePreferenceManage.savePassword(password);
                        sharePreferenceManage.saveIsLogin(true);
                        sharePreferenceManage.saveInfoUser(isSave);
                        sharePreferenceManage.saveIdStudent(loginResponse.getUserLogin().getStudent().getId());

                        Log.d("Login", "Thông tin user đã lưu thành công");
                        loginResult.setValue(true);
                    } else {
                        Log.e("Login", "Đăng nhập thất bại: loginResponse.isSuccess() == false");
                        loginResult.setValue(false);
                    }
                } else {
                    Log.e("Login", "Response thất bại. Code: " + response.code() + ", message: " + response.message());
                    loginResult.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                Log.e("Login", "onFailure: Lỗi khi gọi API", throwable);
                loginResult.setValue(false);
            }
        });
    }

    public String getStudentId(){
        return sharePreferenceManage.getIdStudent();
    }

    public boolean getSaveInf(){
        return sharePreferenceManage.getInfoUser();
    }

    public boolean getLogin() {
        return sharePreferenceManage.getIsLogin();
    }

    public String getEmail() {
        return sharePreferenceManage.getEmail();
    }

    public String getPassword() {
        return sharePreferenceManage.getPassword();
    }

    public String getUserId() {
        return sharePreferenceManage.getUserId();
    }

    public MutableLiveData<Student> getStudent() {
        return student;
    }

    public MutableLiveData<List<Supervisor>> getListSupervisor() {
        return listSupervisor;
    }

    public void getStudentById() {
        Call call = apiService.getStudentById(sharePreferenceManage.getIdStudent());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Student result = (Student) response.body();
                    student.setValue(result);
                }
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {

            }
        });
    }

    public void getGVHD() {
        Call<List<Supervisor>> call = apiService.getAllSuperVisor();
        call.enqueue(new Callback<List<Supervisor>>() {
            @Override
            public void onResponse(Call<List<Supervisor>> call, Response<List<Supervisor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listSupervisor.setValue(response.body());
                } else {
                    // Xử lý khi API trả về lỗi (ví dụ code 400, 500)
                    Log.e("API_ERROR", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Supervisor>> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc lỗi khác
                Log.e("API_ERROR", "Failed to fetch supervisors", t);
            }
        });
    }

    public void getAssignmentByStudentId(String studentId) {
        Call<Assignment> call = apiService.getAssignmentByStudentId(studentId);
        call.enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                if (response.isSuccessful()) {
                    Assignment assignment = response.body();
                    assignmentByIdStudent.setValue(assignment);
                } else {
                    Log.e("API_ERROR", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable throwable) {
                Log.e("API_ERROR", "Failed to get assignment", throwable);
            }
        });
    }

    public MutableLiveData<Assignment> getAssignmentByIdStudent() {
        return assignmentByIdStudent;
    }
}