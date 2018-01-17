package irshad.sheikh.loginlibrary.users;

/**
 * Copyright (c) 2016 Codelight Studios
 * Created by Kalyan on 9/25/2015.
 */
public class SmartGoogleUser extends SmartUser {

    private String displayName;
    private String photoUrl;
    private String idToken;
    private String Email;
    private String Id;
    private String GivenName;
    private String FamilyName;

    public SmartGoogleUser() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    @Override
    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String getEmail() {
        return Email;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setGivenName(String givenName) {
        GivenName = givenName;
    }

    public String getGivenName() {
        return GivenName;
    }

    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }

    public String getFamilyName() {
        return FamilyName;
    }
}
