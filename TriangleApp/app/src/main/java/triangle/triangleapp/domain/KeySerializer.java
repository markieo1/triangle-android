package triangle.triangleapp.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kevin Ly on 6/21/2017.
 */

public class KeySerializer {
    @SerializedName("publicKey")
    private String publicKey;

    @SerializedName("streamerName")
    private String streamerName;

    public KeySerializer(String publicKey, String streamerName){
        this.publicKey = publicKey;
        this.streamerName = streamerName;
    }
}