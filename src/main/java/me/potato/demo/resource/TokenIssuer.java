package me.potato.demo.resource;

import io.smallrye.jwt.build.Jwt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.jwt.Claims;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@Path("token")
public class TokenIssuer {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String createToken(IssueForm form) {
    return Jwt.issuer("https://example.com/issuer")
              .upn(form.getEmail())
              .groups(new HashSet<>(Arrays.asList("User", "Admin")))
              .claim(Claims.birthdate.name(), form.getBirthDate())
              .sign();
  }


  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  static class IssueForm {
    private String email;
    private LocalDate birthDate;
  }
}
