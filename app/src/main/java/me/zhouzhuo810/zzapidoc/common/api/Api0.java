package me.zhouzhuo810.zzapidoc.common.api;

import me.zhouzhuo810.zzapidoc.common.api.entity.AddDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceExampleResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddRequestHeaderResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddResponseArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteRequestHeaderResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetInterfaceDetailsResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetProjectDetailsResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetRequestArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetRequestHeaderResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetResponseArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.ImportProjectEntity;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateUserPasswordResult;
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
    @POST("/ZzApiDoc/v1/user/userLogin")
    Observable<UserLoginResult> userLogin(@Field("phone") String phone, @Field("password") String password);

    /*
     * 注册接口
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/user/userRegister")
    Observable<UserRegisterResult> userRegister(@Field("phone") String phone, @Field("password") String password, @Field("name") String name, @Field("email") String email, @Field("sex") String sex);

    /*修改登录密码*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/user/updateUserPassword")
    Observable<UpdateUserPasswordResult> updateUserPassword(@Field("userId") String userId, @Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);

    /*添加接口示例*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/interface/addInterfaceExample")
    Observable<AddInterfaceExampleResult> addInterfaceExample(@Field("userId") String userId, @Field("example") String example, @Field("interfaceId") String interfaceId);

    /*
     * 添加字典
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/dictionary/addDictionary")
    Observable<AddDictionaryResult> addDictionary(@Field("name") String name, @Field("type") String type, @Field("pid") String pid, @Field("position") String position, @Field("userId") String userId);

    /*
     * 查询字典
     */
    @GET("/ZzApiDoc/v1/dictionary/getDictionary")
    Observable<GetDictionaryResult> getDictionary(@Query("type") String type);

    /*
     * 添加项目
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/project/addProject")
    Observable<AddProjectResult> addProject(@Field("name") String name, @Field("property") String property, @Field("note") String note, @Field("userId") String userId);

    /*
     * 查询项目列表
     */
    @GET("/ZzApiDoc/v1/project/getAllProject")
    Observable<GetAllProjectResult> getAllProject(@Query("userId") String userId);

    /*
     * 查询接口列表
     */
    @GET("/ZzApiDoc/v1/interface/getAllInterface")
    Observable<GetAllInterfaceResult> getAllInterface(@Query("projectId") String projectId, @Query("userId") String userId);


    /*
     * 查询接口列表根据分组
     */
    @GET("/ZzApiDoc/v1/interface/getInterfaceByGroupId")
    Observable<GetAllInterfaceResult> getInterfaceByGroupId(@Query("projectId") String projectId, @Query("groupId") String groupId, @Query("userId") String userId);


    /*
     * 添加接口分组
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/interfaceGroup/addInterfaceGroup")
    Observable<AddInterfaceGroupResult> addInterfaceGroup(@Field("name") String name, @Field("projectId") String projectId, @Field("ip") String ip, @Field("userId") String userId);

    /*
     * 添加接口
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/interface/addInterface")
    Observable<AddInterfaceResult> addInterface(@Field("name") String name, @Field("groupId") String groupId, @Field("httpMethodId") String httpMethodId, @Field("userId") String userId, @Field("projectId") String projectId, @Field("path") String path, @Field("requestArgs") String requestArgs, @Field("responseArgs") String responseArgs);

    /*
     * 查询接口详情
     */
    @GET("/ZzApiDoc/v1/interface/getInterfaceDetails")
    Observable<GetInterfaceDetailsResult> getInterfaceDetails(@Query("interfaceId") String interfaceId, @Query("userId") String userId);

    /*
     * 查询项目详情
     */
    @GET("/ZzApiDoc/v1/project/getProjectDetails")
    Observable<GetProjectDetailsResult> getProjectDetails(@Query("projectId") String projectId, @Query("userId") String userId);

    /*
     * 更新接口
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/interface/updateInterface")
    Observable<UpdateInterfaceResult> updateInterface(@Field("interfaceId") String interfaceId, @Field("name") String name, @Field("path") String path, @Field("projectId") String projectId, @Field("groupId") String groupId, @Field("httpMethodId") String httpMethodId, @Field("note") String note, @Field("userId") String userId, @Field("requestArgs") String requestArgs, @Field("responseArgs") String responseArgs);

    /*
     * 更新接口分组
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/interfaceGroup/updateInterfaceGroup")
    Observable<UpdateInterfaceResult> updateInterfaceGroup(@Field("interfaceGroupId") String interfaceGroupId,
                                                           @Field("name") String name,
                                                           @Field("projectId") String projectId,
                                                           @Field("ip") String ip,
                                                           @Field("userId") String userId);

    /*
     * 删除接口
     */
    @GET("/ZzApiDoc/v1/interface/deleteInterface")
    Observable<DeleteInterfaceResult> deleteInterface(@Query("id") String id, @Query("userId") String userId);

    /*
     * 删除项目
     */
    @GET("/ZzApiDoc/v1/project/deleteProject")
    Observable<DeleteProjectResult> deleteProject(@Query("id") String id, @Query("userId") String userId);

    /*
     * 刪除接口分組
     */
    @GET("/ZzApiDoc/v1/interfaceGroup/deleteInterfaceGroup")
    Observable<DeleteInterfaceGroupResult> deleteInterfaceGroup(@Query("id") String id, @Query("userId") String userId);

    /*
     * 获取分组列表
     */
    @GET("/ZzApiDoc/v1/interfaceGroup/getAllInterfaceGroup")
    Observable<GetAllInterfaceGroupResult> getAllInterfaceGroup(@Query("projectId") String projectId, @Query("userId") String userId);


    /*获取返回参数根据接口id和pid*/
    @GET("/ZzApiDoc/v1/responseArg/getResponseArgByInterfaceIdAndPid")
    Observable<GetResponseArgResult> getResponseArgByInterfaceIdAndPid(@Query("interfaceId") String interfaceId, @Query("pid") String pid, @Query("userId") String userId);

    /*获取请求参数根据接口id和pid*/
    @GET("/ZzApiDoc/v1/requestArg/getRequestArgByInterfaceIdAndPid")
    Observable<GetRequestArgResult> getRequestArgByInterfaceIdAndPid(@Query("interfaceId") String interfaceId, @Query("pid") String pid, @Query("userId") String userId);


    /*添加返回参数*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/responseArg/addResponseArg")
    Observable<AddResponseArgResult> addResponseArg(
            @Field("pid") String pid,
            @Field("name") String name,
            @Field("type") int type,
            @Field("projectId") String projectId,
            @Field("interfaceId") String interfaceId,
            @Field("note") String note,
            @Field("userId") String userId,
            @Field("isGlobal") boolean isGlobal
    );

    /*添加请求参数*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/requestArg/addRequestArg")
    Observable<AddResponseArgResult> addRequestArg(
            @Field("pid") String pid,
            @Field("name") String name,
            @Field("type") int type,
            @Field("projectId") String projectId,
            @Field("interfaceId") String interfaceId,
            @Field("note") String note,
            @Field("userId") String userId,
            @Field("isRequire") boolean isRequire,
            @Field("isGlobal") boolean isGlobal
    );

    /*删除返回参数*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/responseArg/deleteResponseArg")
    Observable<DeleteArgResult> deleteResponseArg(
            @Field("id") String id,
            @Field("userId") String userId
    );

    /*删除请求参数*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/requestArg/deleteRequestArg")
    Observable<DeleteArgResult> deleteRequestArg(
            @Field("id") String id,
            @Field("userId") String userId
    );

    /*检查更新*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/update/checkUpdate")
    Observable<UpdateResult> checkUpdate(@Field("versionCode") int versionCode);

    /*添加请求头*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/requestHeader/addRequestHeader")
    Observable<AddRequestHeaderResult> addRequestHeader(
            @Field("name") String name,
            @Field("value") String value,
            @Field("note") String note,
            @Field("interfaceId") String interfaceId,
            @Field("projectId") String projectId,
            @Field("userId") String userId,
            @Field("isGlobal") boolean isGlobal
    );

    /*删除请求头*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/requestHeader/deleteRequestHeader")
    Observable<DeleteRequestHeaderResult> deleteRequestHeader(
            @Field("id") String id,
            @Field("userId") String userId
    );

    /*根据接口id获取请求头*/
    @GET("/ZzApiDoc/v1/requestHeader/getRequestHeaderByInterfaceId")
    Observable<GetRequestHeaderResult> getRequestHeaderByInterfaceId(
            @Query("interfaceId") String interfaceId,
            @Query("userId") String userId
    );

    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/project/importProject")
    Observable<ImportProjectEntity> importProject(
            @Field("json") String json,
            @Field("property") String property,
            @Field("userId") String userId
    );
}