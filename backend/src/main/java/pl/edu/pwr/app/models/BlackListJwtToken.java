package pl.edu.pwr.app.models;
import javax.persistence.*;

@Entity
@Table(name = "token_blacklist")
public class BlackListJwtToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;


    @Column(name = "token", columnDefinition = "LONGTEXT")
    private String token;


    @Column(name = "expiry_at")
    private long expiryAt;


    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryAt(long expiryAt) {
        this.expiryAt = expiryAt;
    }
}