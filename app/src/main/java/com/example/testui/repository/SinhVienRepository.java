package com.example.testui.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.example.testui.client.Client;
import com.example.testui.model.Assignment;
import com.example.testui.model.LoginResponse;
import com.example.testui.model.Student;
import com.example.testui.model.Supervisor;
import com.example.testui.model.Teacher;
import com.example.testui.model.User;
import com.example.testui.service.ApiService;
import com.example.testui.service.AssignmentService;
import com.example.testui.service.AuthService;
import com.example.testui.service.ResetPasswordService;
import com.example.testui.service.StudentService;
import com.example.testui.service.TeacherService;
import com.example.testui.service.UserService;
import com.example.testui.sharepreference.SharePreferenceManage;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinhVienRepository {
    ApiService apiService;
    AuthService authService;
    AssignmentService assignmentService;
    UserService userService;
    TeacherService teacherService;
    ResetPasswordService resetPasswordService;
    StudentService studentService;
    private static volatile SinhVienRepository instance;
    MutableLiveData<Boolean> loginResult = new MutableLiveData<>(false);
    MutableLiveData<Student> student = new MutableLiveData<>();
    MutableLiveData<List<Supervisor>> listSupervisor = new MutableLiveData<>();
    MutableLiveData<Assignment> assignmentByIdStudent = new MutableLiveData<>();
    MutableLiveData<Assignment> recentAssignment = new MutableLiveData<>();
    MutableLiveData<Boolean> registerResult = new MutableLiveData<Boolean>();
    MutableLiveData<List<Teacher>> listTeacher = new MutableLiveData<>();
    MutableLiveData<Boolean> isSendSuccess = new MutableLiveData<>();
    MutableLiveData<Boolean> isResetPasswordSuccess = new MutableLiveData<>();
    MutableLiveData<Boolean> isChangePasswordSuccess = new MutableLiveData<>();
    MutableLiveData<Boolean> isUpdateSuccess = new MutableLiveData<>();
    SharePreferenceManage sharePreferenceManage;
    // private constructor : singleton access
    public SinhVienRepository(Context context) {
        apiService = Client.getInstance().create(ApiService.class);
        authService = Client.getInstance().create(AuthService.class);
        assignmentService = Client.getInstance().create(AssignmentService.class);
        userService = Client.getInstance().create(UserService.class);
        teacherService = Client.getInstance().create(TeacherService.class);
        resetPasswordService = Client.getInstance().create(ResetPasswordService.class);
        studentService = Client.getInstance().create(StudentService.class);
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
                    Log.d("Student", new Gson().toJson(result));
                } else {
                    Log.e("API_ERROR", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.e("API_ERROR", "Failed to get student", throwable);
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

    public MutableLiveData<Assignment> getRecentAssignment(String studentId) {
        Call<Assignment> call = assignmentService.getRecentAssignmentByStudentId(studentId);
        call.enqueue(new Callback<Assignment>() {
            @Override
            public void onResponse(Call<Assignment> call, Response<Assignment> response) {
                if (response.isSuccessful()) {
                    Assignment assignment = response.body();
                    recentAssignment.setValue(assignment);
                } else {
                    Log.e("API_ERROR", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Assignment> call, Throwable throwable) {
                Log.e("API_ERROR", "Failed to get assignment", throwable);
            }
        });
        return recentAssignment;
    }

    public MutableLiveData<Assignment> getRecentAssignment() {
        return recentAssignment;
    }

    public void logout() {
        sharePreferenceManage.saveIsLogin(false);
    }

    public void register(User user) {
        Call<User> call = userService.register(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    registerResult.setValue(true);
                } else {
                    registerResult.setValue(false);
                    Log.e("API_ERROR", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                registerResult.setValue(false);
                Log.e("API_ERROR", "Failed to register", throwable);
            }
        });
    }

    public MutableLiveData<Boolean> getRegisterResult() {
        return registerResult;
    }

    public void setRegisterResult(MutableLiveData<Boolean> registerResult) {
        this.registerResult = registerResult;
    }

    public void loadAllTeacher() {
        Call<List<Teacher>> call = teacherService.getAllTeacher();
        call.enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listTeacher.setValue(response.body());
                } else {
                    Log.e("API_ERROR", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Teacher>> call, Throwable throwable) {
                Log.e("API_ERROR", "Failed to get teacher", throwable);
            }
        });
    }

    public MutableLiveData<List<Teacher>> getListTeacher() {
        return listTeacher;
    }

    public void sendResetPassword(Map<String, String> body) {
        Call<ResponseBody> call = resetPasswordService.sendResetPassword(body);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    isSendSuccess.setValue(true);
                    Log.d("Send-success", response.body().toString());
                } else {
                    isSendSuccess.setValue(false);
                    Log.e("Send-fail", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call call, Throwable throwable) {
                Log.e("Send-error", "Failed " + throwable);
            }
        });
    }

    public MutableLiveData<Boolean> getIsSendSuccess() {
        return isSendSuccess;
    }

    public void resetPassword(Map<String, String> body) {
        Call<ResponseBody> call = resetPasswordService.resetPassword(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    isResetPasswordSuccess.setValue(true);
                    Log.d("Reset Success", response.toString());
                } else {
                    isResetPasswordSuccess.setValue(false);
                    Log.e("Reset Failured", "Response failure" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.e("Reset Failure", "Throwable " + throwable);
            }
        });
    }

    public MutableLiveData<Boolean> getIsResetPasswordSuccess() {
        return isResetPasswordSuccess;
    }

    public void changePassword(String token, Map<String, String> body) {
        Call<ResponseBody> call = resetPasswordService.changePassword(token, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    isChangePasswordSuccess.setValue(true);
                    Log.d("Change Password Success", response.toString());
                } else {
                    isChangePasswordSuccess.setValue(false);
                    Log.e("Change Password Failure", "Response failure" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.e("Change Password Failure", "Throwable " + throwable);
            }
        });
    }

    public MutableLiveData<Boolean> getIsChangePasswordSuccess() {
        return isChangePasswordSuccess;
    }

    public String getAccessToken() {
        return sharePreferenceManage.getAccessToken();
    }

    public void updateInfStudent(String studentId, Map<String, String> body) {
        Call<ResponseBody> call = studentService.updateStudent(studentId, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    isUpdateSuccess.setValue(true);
                    Log.d("Update Success", response.toString());
                } else {
                    isUpdateSuccess.setValue(false);
                    Log.e("Update Failured", "Response failure" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.e("Update Failure", "Throwable " + throwable);
            }
        });
    }

    public MutableLiveData<Boolean> getIsUpdateSuccess() {
        return isUpdateSuccess;
    }
}