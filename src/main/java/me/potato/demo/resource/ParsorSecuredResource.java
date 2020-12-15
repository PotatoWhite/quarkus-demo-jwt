package me.potato.demo.resource;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/parsor/secured")
public class ParsorSecuredResource {

  @Inject
  JWTParser parser;
  private String secret="AyM1SysPpbyDfgZld3umj1qzKObwVMko";

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Response getUserName(@CookieParam("jwt") String jwtCookie) throws ParseException {
    if(jwtCookie == null) {
      String newJwtCookie=Jwt.upn("Alice")
                             .signWithSecret(secret);
      return Response.ok("Alice")
                     .cookie(new NewCookie("jwt", newJwtCookie))
                     .build();
    } else {
      JsonWebToken jwt=parser.verify(jwtCookie, secret);
      return Response.ok(jwt.getName())
                     .build();
    }
  }
}
