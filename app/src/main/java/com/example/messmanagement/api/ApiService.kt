package com.example.messmanagement.api

import com.example.messmanagement.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/orders/create/{username}")
    suspend fun createOrder(
        @Path("username") username: String,
        @Body order: Order,
        @Header("Authorization") token: String
    ): Response<Order>

    @GET("api/orders/user/{userId}")
    suspend fun getOrdersByUser(
        @Path("userId") userId: Long,
        @Header("Authorization") token: String
    ): Response<List<Order>>

    @GET("api/orders/all")
    suspend fun getAllOrders(
        @Header("Authorization") token: String
    ): Response<List<Order>>

    @PUT("api/orders/update/{orderId}")
    suspend fun updateOrderStatus(
        @Path("orderId") orderId: Long,
        @Query("status") status: String,
        @Header("Authorization") token: String
    ): Response<Order>

    @GET("api/menu/items")
    suspend fun getMenuItems(
        @Header("Authorization") token: String
    ): Response<List<MenuItem>>

    // Permission-related endpoints
    @POST("api/permissions/create")
    suspend fun createPermission(
        @Header("Authorization") token: String,
        @Body permission: Permission
    ): Response<PermissionResponse>

    @GET("api/permissions")
    suspend fun getPermissions(
        @Header("Authorization") token: String
    ): Response<List<Permission>>

    @GET("api/permissions/{id}")
    suspend fun getPermissionById(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Permission>

    @PATCH("api/permissions/{id}/approve")
    suspend fun approvePermission(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Permission>

    @PATCH("api/permissions/{id}/disapprove")
    suspend fun disapprovePermission(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Permission>
}