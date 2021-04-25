package pl.edu.pwr.app.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TokenBlackList {
    @Id
    @GeneratedValue
    private long tokenId;

    private String token;

    public TokenBlackList() {
    }

    public TokenBlackList(long tokenId, String token) {
        this.tokenId = tokenId;
        this.token = token;
    }

    public TokenBlackList(String token) {
        this.token = token;
    }

    public long getUuid() {
        return tokenId;
    }

    public void setUuid(long tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
