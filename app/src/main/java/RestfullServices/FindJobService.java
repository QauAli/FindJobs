package RestfullServices;


import java.util.List;

import ModelClasses.Advertisement;
import ModelClasses.Advertiser;
import ModelClasses.Applicant;
import ModelClasses.UserImage;
import ModelClasses.Conversation;
import ModelClasses.Message;
import ModelClasses.Skill;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface FindJobService {

    @POST("/forgetpassword")
    Call<String> getPassword(@Body Advertiser advertiser);

    @POST("/skill/{id}")
    Call<Skill> createSkill(@Body Skill skill,
                            @Path("id") int id);
    @DELETE("/skill/{id}")
    Call<Skill> deleteSkill(@Path("id") int id);

    @PUT("/create_applicant")
    Call<Applicant> updateApplicant(@Body Applicant applicant);

    @PUT("/create_advertiser")
    Call<Advertiser> updateAdvertiser(@Body Advertiser advertiser);

    @POST("/wishList/{applicantId}/{advertisementId}")
    Call<String> addToWishlist(@Path("applicantId") int applicantId,
                               @Path("advertisementId") int advertisementId,
                               @Body String name);
    @GET("/wishList/{applicantId}/{advertisementId}")
    Call<List<Advertisement>> getAds(@Path("applicantId") int applicantId,
                               @Path("advertisementId") int advertisementId);

    @GET("/getApplications/{advertiserId}/{ad}")
    Call<List<Applicant>> getApplications(@Path("advertiserId") int id, @Path("ad") String ad);

    @GET("/getAdNames/{advertiserId}")
    Call<List<String>> getAdvertiserAds(@Path("advertiserId") int id);

    @GET("/sendApplication/{adId}/{applicantId}")
    Call<String> sendApplication(@Path("adId") int adId, @Path("applicantId") int applicantId);

    @DELETE("/deleteCon/{conId}/{deleter}")
    Call<Conversation> deleteCon(@Path("conId") int conId,
                                 @Path("deleter") String deleter);

    @POST("/con/{applicantId}/{advertiserId}")
    Call<Conversation> createCon(@Path("applicantId") int applicantId,
                                 @Path("advertiserId") int advertiserId,
                                 @Body Conversation conversation);

    @POST("/jobcon/{applicantId}/{jobId}")
    Call<Conversation> createJobCon(@Path("applicantId") int applicantId,
                                    @Path("jobId") int jobId,
                                    @Body Conversation conversation);

    @GET("/getApplicantCons/{getterId}")
    Call<List<Conversation>> getApplicantCons(@Path("getterId") int getterId);

    @GET("/getAdvertiserCons/{getterId}")
    Call<List<Conversation>> getAdvertiserCons(@Path("getterId") int getterId);

    @Multipart
    @POST("/send_image")
    Call<UserImage> createApplicantImage(@Part("image\"; filename=\"pp.png\" ") RequestBody file,
                                         @Part("id") RequestBody aid);
    @Multipart
    @POST("/saveAdvertiserImage")
    Call<UserImage> createAdvertiserImage(@Part("image\"; filename=\"pp.png\" ") RequestBody file,
                                          @Part("id") RequestBody aid);
    @POST("/create_advertiser")
    Call<Advertiser> createAdvertiser(@Body Advertiser advertiser);

    @POST("/create_applicant")
    Call<Applicant> createApplicant(@Body Applicant applicant);

    @GET("Skills")
    Call<List<Skill>> getAllSkills();

    @POST("/authenticateApplicant")
    Call<Applicant> authenticateApplicant(@Body Applicant applicant);

    @POST("/authenticateAdvertiser")
    Call<Advertiser> authenticateAdvertiser(@Body Advertiser advertiser);

    @POST("/msgs/{conId}")
    Call<Message> sendMsg(@Path("conId") int conId, @Body Message message);

    @GET("/msgs/{conId}")
    Call<List<Message>> getAllMsgss(@Path("conId") int conId);

    @GET("/get_advertisements/{province}/{district}")
    Call<List<Advertisement>> getAllAds(@Path("province") String province, @Path("district") String district);

    @GET("/get_profiles/{province}/{district}")
    Call<List<Applicant>> getAllProfiles(@Path("province") String province, @Path("district") String district);

    @POST("/create_advertisement")
    Call<Advertisement> createAdvertisement(@Body Advertisement advertisement);
}
