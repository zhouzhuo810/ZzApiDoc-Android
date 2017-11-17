package me.zhouzhuo810.zzapidoc.common.api;

import java.util.List;

import me.zhouzhuo810.zzapidoc.common.api.entity.AddActionResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddApplicationResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceExampleResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddQrCodeResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddRequestHeaderResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddResponseArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddVersionProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddVersionRecordResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.AddVersionResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteActionResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteActivityResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteApplicationResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteQrCodeResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteRequestHeaderResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteVersionProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteVersionRecordResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.DeleteVersionResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GenerateEmptyExampleResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllActionsResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllApplicationResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceGroupResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllMyActivityResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllMyFragmentResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllMyWidgetResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllQrCodeResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllVersionProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllVersionRecordResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetAllVersionResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetDictionaryResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetInterfaceDetailsResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetProjectDetailsResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetRequestArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetRequestHeaderResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.GetResponseArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.ImportProjectEntity;
import me.zhouzhuo810.zzapidoc.common.api.entity.PreviewUIResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.PublishVersionEntity;
import me.zhouzhuo810.zzapidoc.common.api.entity.SetTestFinishResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateActionResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateInterfaceResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateQrCodeResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateResponseArgResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateUserPasswordResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateVersionProjectResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateVersionRecordResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UpdateVersionResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UserLoginResult;
import me.zhouzhuo810.zzapidoc.common.api.entity.UserRegisterResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Observable<AddProjectResult> addProject(@Field("name") String name, @Field("packageName") String packageName, @Field("property") String property, @Field("note") String note, @Field("userId") String userId);

    /*
     * 修改包名
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/project/updateProject")
    Observable<AddProjectResult> updateProject(@Field("projectId") String id, @Field("name") String name, @Field("packageName") String packageName, @Field("property") String property, @Field("note") String note, @Field("userId") String userId);

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
    Observable<AddInterfaceResult> addInterface(@Field("name") String name, @Field("groupId") String groupId,
                                                @Field("httpMethodId") String httpMethodId, @Field("userId") String userId,
                                                @Field("note") String note, @Field("projectId") String projectId,
                                                @Field("path") String path, @Field("requestArgs") String requestArgs, @Field("responseArgs") String responseArgs);

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
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/interface/deleteInterface")
    Observable<DeleteInterfaceResult> deleteInterface(@Field("id") String id, @Field("userId") String userId);

    /*
     * 删除项目
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/project/deleteProject")
    Observable<DeleteProjectResult> deleteProject(@Field("id") String id, @Field("userId") String userId);

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
            @Field("defValue") String defValue,
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
            @Field("defValue") String defValue,
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

    /*导入返回参数*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/responseArg/importResponseArg")
    Observable<ImportProjectEntity> importResponseArg(
            @Field("json") String json,
            @Field("interfaceId") String interfaceId,
            @Field("pid") String pid,
            @Field("userId") String userId
    );


    /*导入项目*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/project/importProject")
    Observable<ImportProjectEntity> importProject(
            @Field("json") String json,
            @Field("property") String property,
            @Field("userId") String userId
    );


    /*发布新版本*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/update/publishVersion")
    Observable<PublishVersionEntity> publishVersion(
            @Field("versionCode") String versionCode,
            @Field("versionName") String versionName,
            @Field("updateInfo") String updateInfo,
            @Field("releaseDate") String releaseDate,
            @Field("userId") String userId
    );

    /*生成空json示例*/
    @GET("/ZzApiDoc/v1/interface/generateEmptyExample")
    Observable<GenerateEmptyExampleResult> generateEmptyExample(
            @Query("userId") String userId,
            @Query("interfaceId") String interfaceId
    );

    /*获取application列表*/
    @GET("/ZzApiDoc/v1/application/getAllMyApplication")
    Observable<GetAllApplicationResult> getAllMyApplication(
            @Query("userId") String userId
    );

    /*删除应用*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/application/deleteApplication")
    Observable<DeleteApplicationResult> deleteApplication(
            @Field("userId") String userId,
            @Field("id") String id
    );

    /*获取Activity列表*/
    @GET("/ZzApiDoc/v1/activity/getAllMyActivity")
    Observable<GetAllMyActivityResult> getAllMyActivity(
            @Query("appId") String appId,
            @Query("userId") String userId
    );

    /*获取控件列表*/
    @GET("/ZzApiDoc/v1/widget/getAllMyWidget")
    Observable<GetAllMyWidgetResult> getAllMyWidget(
            @Query("relativeId") String relativeId,
            @Query("pid") String pid,
            @Query("userId") String userId
    );

    /*获取fragment列表*/
    @GET("/ZzApiDoc/v1/fragment/getAllMyFragment")
    Observable<GetAllMyFragmentResult> getAllMyFragment(
            @Query("activityId") String activityId,
            @Query("userId") String userId
    );


    /*删除activity*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/activity/deleteActivity")
    Observable<DeleteActivityResult> deleteActivity(
            @Field("id") String id,
            @Field("userId") String userId
    );

    /*删除widget*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/widget/deleteWidget")
    Observable<DeleteActivityResult> deleteWidget(
            @Field("id") String id,
            @Field("userId") String userId
    );


    /*删除fragment*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/fragment/deleteFragment")
    Observable<DeleteActivityResult> deleteFragment(
            @Field("id") String id,
            @Field("userId") String userId
    );

    /*修改返回参数*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/responseArg/updateResponseArg")
    Observable<UpdateResponseArgResult> updateResponseArg(
            @Field("responseArgId") String responseArgId,
            @Field("pid") String pid,
            @Field("name") String name,
            @Field("defValue") String defValue,
            @Field("type") int type,
            @Field("interfaceId") String interfaceId,
            @Field("note") String note,
            @Field("userId") String userId,
            @Field("isGlobal") boolean isGlobal
    );

    /*修改请求参数*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/requestArg/updateRequestArg")
    Observable<UpdateResponseArgResult> updateRequestArg(
            @Field("requestArgId") String requestArgId,
            @Field("pid") String pid,
            @Field("name") String name,
            @Field("defValue") String defValue,
            @Field("type") int type,
            @Field("interfaceId") String interfaceId,
            @Field("note") String note,
            @Field("userId") String userId,
            @Field("isRequire") boolean isRequire,
            @Field("isGlobal") boolean isGlobal
    );

    /*界面预览*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/activity/previewUI")
    Observable<PreviewUIResult> previewUI(
            @Field("id") String id,
            @Field("userId") String userId
    );


    /*设置接口测试完成*/
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/interface/setTestFinish")
    Observable<SetTestFinishResult> setTestFinish(
            @Field("interfaceId") String interfaceId,
            @Field("userId") String userId
    );

    /*
     * 添加二维码()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/qrcode/addQrCode")
    Observable<AddQrCodeResult> addQrCode(@Field("userId") String userId, @Field("isPrivate") boolean isPrivate, @Field("title") String title, @Field("content") String content);

    /*
     * 删除二维码()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/qrcode/deleteQrCode")
    Observable<DeleteQrCodeResult> deleteQrCode(@Field("userId") String userId, @Field("id") String id);

    /*
     * 更新二维码()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/qrcode/updateQrCode")
    Observable<UpdateQrCodeResult> updateQrCode(@Field("userId") String userId, @Field("id") String id, @Field("isPrivate") boolean isPrivate, @Field("title") String title, @Field("content") String content);

    /*
     * 获取所有二维码()
     */
    @GET("/ZzApiDoc/v1/qrcode/getAllQrCode")
    Observable<GetAllQrCodeResult> getAllQrCode(@Query("userId") String userId);


    /*
     * 添加版本()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/version/addVersion")
    Observable<AddVersionResult> addVersion(@Field("userId") String userId, @Field("projectId") String projectId, @Field("versionName") String versionName, @Field("versionCode") int versionCode, @Field("versionDesc") String versionDesc);

    /*
     * 更新版本()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/version/updateVersion")
    Observable<UpdateVersionResult> updateVersion(@Field("userId") String userId, @Field("versionId") String versionId, @Field("projectId") String projectId, @Field("versionName") String versionName, @Field("versionCode") String versionCode, @Field("versionDesc") String versionDesc);

    /*
     * 删除版本()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/version/deleteVersion")
    Observable<DeleteVersionResult> deleteVersion(@Field("userId") String userId, @Field("id") String id);

    /*
     * 获取所有版本()
     */
    @GET("/ZzApiDoc/v1/version/getAllVersion")
    Observable<GetAllVersionResult> getAllVersion(@Query("userId") String userId, @Query("projectId") String projectId);

    /*
     * 添加版本项目()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/versionProject/addVersionProject")
    Observable<AddVersionProjectResult> addVersionProject(@Field("userId") String userId, @Field("name") String name, @Field("note") String note);

    /*
     * 更新版本项目()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/versionProject/updateVersionProject")
    Observable<UpdateVersionProjectResult> updateVersionProject(@Field("userId") String userId);

    /*
     * 删除版本项目()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/versionProject/deleteVersionProject")
    Observable<DeleteVersionProjectResult> deleteVersionProject(@Field("userId") String userId, @Field("id") String id);

    /*
     * 获取所有版本项目()
     */
    @GET("/ZzApiDoc/v1/versionProject/getAllVersionProject")
    Observable<GetAllVersionProjectResult> getAllVersionProject(@Query("userId") String userId);

    /*
     * 添加版本记录()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/versionRecord/addVersionRecord")
    Observable<AddVersionRecordResult> addVersionRecord(@Field("userId") String userId, @Field("versionId") String versionId, @Field("projectId") String projectId, @Field("note") String note);

    /*
     * 更新版本记录()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/versionRecord/updateVersionRecord")
    Observable<UpdateVersionRecordResult> updateVersionRecord(@Field("userId") String userId, @Field("recordId") String recordId, @Field("versionId") String versionId, @Field("projectId") String projectId, @Field("note") String note);

    /*
     * 删除版本记录()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/versionRecord/deleteVersionRecord")
    Observable<DeleteVersionRecordResult> deleteVersionRecord(@Field("userId") String userId, @Field("id") String id);

    /*
     * 过去所有版本记录()
     */
    @GET("/ZzApiDoc/v1/versionRecord/getAllVersionRecord")
    Observable<GetAllVersionRecordResult> getAllVersionRecord(@Query("userId") String userId, @Query("versionId") String versionId);

    /*
     * 添加动作()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/action/addAction")
    Observable<AddActionResult> addAction(@Field("userId") String userId, @Field("type") int type,
                                          @Field("name") String name, @Field("widgetId") String widgetId,
                                          @Field("title") String title, @Field("msg") String msg,
                                          @Field("okText") String okText, @Field("cancelText") String cancelText,
                                          @Field("hintText") String hintText, @Field("defText") String defText,
                                          @Field("showOrHide") boolean showOrHide, @Field("okApiId") String okApiId,
                                          @Field("okActId") String okActId);

    /*
     * 更新动作()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/action/updateAction")
    Observable<UpdateActionResult> updateAction(@Field("userId") String userId, @Field("actionId") String actionId,
                                                @Field("type") int type, @Field("name") String name,
                                                @Field("widgetId") String widgetId, @Field("title") String title,
                                                @Field("msg") String msg, @Field("okText") String okText,
                                                @Field("cancelText") String cancelText, @Field("hintText") String hintText,
                                                @Field("defText") String defText, @Field("showOrHide") boolean showOrHide,
                                                @Field("okApiId") String okApiId, @Field("okActId") String okActId);

    /*
     * 删除动作()
     */
    @FormUrlEncoded
    @POST("/ZzApiDoc/v1/action/deleteAction")
    Observable<DeleteActionResult> deleteAction(@Field("userId") String userId, @Field("id") String id);

    /*
     * 获取所有动作()
     */
    @GET("/ZzApiDoc/v1/action/getAllActions")
    Observable<GetAllActionsResult> getAllActions(@Query("userId") String userId, @Query("widgetId") String widgetId);




}