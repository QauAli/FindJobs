package Api;


import java.util.List;

import Models.Advertisement;
import Models.Advertiser;
import Models.Applicant;
import Models.UserImage;
import Models.Conversation;
import Models.Message;
import Models.Skill;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DataService {

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
