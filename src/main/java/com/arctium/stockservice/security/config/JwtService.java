package com.arctium.stockservice.security.config;import com.arctium.stockservice.configuration.StockJwtConfiguration;import com.arctium.stockservice.security.entity.UserPrincipal;import io.jsonwebtoken.Claims;import io.jsonwebtoken.Jwts;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Component;import java.util.Date;import java.util.List;import java.util.function.Function;@Componentpublic class JwtService {    public static final String ROLES = "Roles";    @Autowired    private StockJwtConfiguration stockJwtConfiguration;    //retrieve username from jwt token    private String getUsernameFromClaim(final Claims claims) {        return assessClaims(Claims::getSubject, claims);    }    //retrieve expiration date from jwt token    private Date getExpirationDateFromClaims(final Claims claims) {        return assessClaims(Claims::getExpiration, claims);    }    private List<String> getRoles(final Claims claims) {        return assessClaims(cl -> (List<String>) cl.get(ROLES), claims);    }    private  <T> T assessClaims(final Function<Claims, T> claimsResolver, final Claims claims) {        return claimsResolver.apply(claims);    }    //for retrieving any information from token we will need the secret key    private Claims getAllClaimsFromToken(final String token) {        return Jwts.parser().setSigningKey(stockJwtConfiguration.getKey())                .parseClaimsJws(token).getBody();    }    //check if the token has expired    private Boolean hasTokenExpired(final Claims claims) {        final Date expiration = getExpirationDateFromClaims(claims);        return expiration.before(new Date());    }    /**     * Validate the token by assessing     * Signature, Expiry time and username     * @param token     * @return True if token is valid     */    //validate token    public Boolean validateToken(final String token) {        final Claims claims = getAllClaimsFromToken(token);        final String username = getUsernameFromClaim(claims);        return username != null && !hasTokenExpired(claims);    }    /**     * Extracts UserPrincipal from a jwt token     * @param aJwtToken     * @return UserPrincipal     */    public UserPrincipal getUserPrincipalfrom(final String aJwtToken) {        final Claims claims = getAllClaimsFromToken(aJwtToken);        return new UserPrincipal(getUsernameFromClaim(claims), getRoles(claims));    }}