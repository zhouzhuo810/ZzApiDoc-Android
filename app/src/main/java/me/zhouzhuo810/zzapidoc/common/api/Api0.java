package me.zhouzhuo810.zzapidoc.common.api;

import me.zhouzhuo810.zzapidoc.common.api.entity.AddDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetInterfaceDetailsResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetProjectDetailsResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UserLoginResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UserRegisterResult;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * 默认分类
 */
public interface Api0 {
    /*
     * 登录接口
     */
    @FormUrlEncoded
    @POST("/v1/user/userLogin")
    Observable<UserLoginResult> userLogin(@Field("phone") String phone, @Field("password") String password);

    /*
     * 注册接口
     */
    @FormUrlEncoded
    @POST("/v1/user/userRegister")
    Observable<UserRegisterResult> userRegister(@Field("phone") String phone, @Field("password") String password, @Field("name") String name, @Field("email") String email, @Field("sex") String sex);

    /*
     * 添加字典
     */
    @FormUrlEncoded
    @POST("/v1/dictionary/addDictionary")
    Observable<AddDictionaryResult> addDictionary(@Field("name") String name, @Field("type") String type, @Field("pid") String pid, @Field("position") String position, @Field("userId") String userId);

    /*
     * 查询字典
     */
    @GET("/v1/dictionary/getDictionary")
    Observable<GetDictionaryResult> getDictionary(@Query("type") String type);

    /*
     * 添加项目
     */
    @FormUrlEncoded
    @POST("/v1/project/addProject")
    Observable<AddProjectResult> addProject(@Field("name") String name, @Field("property") String property, @Field("note") String note, @Field("userId") String userId);

    /*
     * 查询项目列表
     */
    @GET("/v1/project/getAllProject")
    Observable<GetAllProjectResult> getAllProject(@Query("userId") String userId);

    /*
     * 查询接口列表
     */
    @GET("/v1/interface/getAllInterface")
    Observable<GetAllInterfaceResult> getAllInterface(@Query("projectId") String projectId, @Query("userId") String userId);

    /*
     * 添加接口分组
     */
    @GET("/v1/interfaceGroup/addInterfaceGroup")
    Observable<AddInterfaceGroupResult> addInterfaceGroup(@Query("name") String name, @Query("projectId") String projectId, @Query("userId") String userId);

    /*
     * 添加接口
     */
    @GET("/v1/interface/addInterface")
    Observable<AddInterfaceResult> addInterface(@Query("name") String name, @Query("groupId") String groupId, @Query("httpMethodId") String httpMethodId, @Query("userId") String userId, @Query("projectId") String projectId, @Query("path") String path, @Query("requestArgs") String requestArgs, @Query("responseArgs") String responseArgs);

    /*
     * 查询接口详情
     */
    @GET("/v1/interface/getInterfaceDetails")
    Observable<GetInterfaceDetailsResult> getInterfaceDetails(@Query("interfaceId") String interfaceId, @Query("userId") String userId);

    /*
     * 查询项目详情
     */
    @GET("/v1/project/getProjectDetails")
    Observable<GetProjectDetailsResult> getProjectDetails(@Query("projectId") String projectId, @Query("userId") String userId);

    /*
     * 更新接口
     */
    @GET("/v1/interface/updateInterface")
    Observable<UpdateInterfaceResult> updateInterface(@Query("interfaceId") String interfaceId, @Query("name") String name, @Query("path") String path, @Query("projectId") String projectId, @Query("groupId") String groupId, @Query("httpMethodId") String httpMethodId, @Query("note") String note, @Query("userId") String userId, @Query("requestArgs") String requestArgs, @Query("responseArgs") String responseArgs);

    /*
     * 删除接口
     */
    @GET("/v1/interface/deleteInterface")
    Observable<DeleteInterfaceResult> deleteInterface(@Query("id") String id, @Query("userId") String userId);

    /*
     * 删除项目
     */
    @GET("/v1/project/deleteProject")
    Observable<DeleteProjectResult> deleteProject(@Query("id") String id, @Query("userId") String userId);

    /*
     * 刪除接口分組
     */
    @GET("/v1/interfaceGroup/deleteInterfaceGroup")
    Observable<DeleteInterfaceGroupResult> deleteInterfaceGroup(@Query("id") String id, @Query("userId") String userId);
}