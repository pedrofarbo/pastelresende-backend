package br.com.pasteldoresende.api.seguranca.service;

import java.io.Serializable;
import java.util.Date;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String name;
    private final String token;
    private final Date expirationToken;

    public JwtAuthenticationResponse(String name, String token, Date expirationToken) {
        this.name = name;
        this.token = token;
        this.expirationToken = expirationToken;
    }

    public String getName() {
        return this.name;
    }

    public String getToken() {
        return this.token;
    }

	public Date getExpirationToken() {
		return expirationToken;
	}


}
