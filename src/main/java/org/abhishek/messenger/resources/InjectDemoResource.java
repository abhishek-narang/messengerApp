package org.abhishek.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	@GET
	@Path("/annotations")
	public String getParamUsingAnnotations(
			@MatrixParam("matrixParam") String matrixParam,
			@HeaderParam("access_token") String access_token,
			@CookieParam("myCookie") String myCookie) {

		return "Matrix Param: " + matrixParam + " \nHeader Params: "
				+ access_token + "\nCookie: " + myCookie;
	}

	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo,
			@Context HttpHeaders headers) {

		String path = uriInfo.getAbsolutePath().toString();
		String baseURI = uriInfo.getBaseUri().toString();
		String requestPath = uriInfo.getPath();
		String requestURI = uriInfo.getRequestUri().toString();

		String cookies = headers.getCookies().toString();
		String languages = headers.getAcceptableLanguages().toString();

		return "UriInfo methods\n" + "Absolute Path: " + path + "\n"
				+ "Base URI: " + baseURI + "\n" + "Request Path: "
				+ requestPath + "\n" + "Request URI: " + requestURI + "\n\n"
				+ "HttpHeaders methods\n" + "Cookies: " + cookies + "\n"
				+ "Languages: " + languages;
	}
}
