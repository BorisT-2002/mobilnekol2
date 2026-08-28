package com.example.kolokvijum2;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RoleApiService {
    @GET("dummy-json")
    Call<RoleResponse> getAllRoles();
}
