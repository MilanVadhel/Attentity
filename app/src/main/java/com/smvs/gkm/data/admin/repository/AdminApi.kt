package com.smvs.gkm.data.admin.repository

import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.data.auth.entity.UserDetails
import com.smvs.gkm.data.base.ApiResponse
import retrofit2.http.*

interface AdminApi {
    companion object {
        const val MEMBER_ID = "member_id"
        const val END_POINT_ADD_MEMBER = "/add-member"
        const val END_POINT_EDIT_MEMBER = "/edit-member/{member_id}"
        const val END_POINT_DELETE_MEMBER = "/delete-member/{member_id}"
        const val END_POINT_ASSIGN_USER = "/assign-user"
        const val END_POINT_REMOVE_USER = ""
        const val END_POINT_USERS = "/users"
        const val END_POINT_MEMBERS = "/members"
    }

    @GET(END_POINT_USERS)
    suspend fun getUsers() : ApiResponse<ArrayList<User>>

    @GET(END_POINT_MEMBERS)
    suspend fun getMembers() : ApiResponse<ArrayList<UserDetails>>

    @POST(END_POINT_ADD_MEMBER)
    suspend fun addMember(
        @Body hashMap: HashMap<String, Any>
    ): ApiResponse<Unit>

    @PUT(END_POINT_EDIT_MEMBER)
    suspend fun editMember(
        @Path(MEMBER_ID) memberId: Int,
        @Body hashMap: HashMap<String, Any>
    ): ApiResponse<Unit>

    @DELETE(END_POINT_DELETE_MEMBER)
    suspend fun deleteMember(
        @Path(MEMBER_ID) memberId: Int,
    ): ApiResponse<Unit>
}