package net.ultrasist.v30.api.demo;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import net.ultrasist.api.model.User;
import net.ultrasist.api.model.VucemResResponseError;
import net.ultrasist.api.model.VucemRestResponse;
import net.ultrasist.api.utils.RestHttpsRequest;

class DemoApplicationTests {
	private RestHttpsRequest invoker = RestHttpsRequest.getInstance();

	@Test
	void testLogin() { // not a unit test. instead, integration test
		String jwt = "";
		String endpoint = "http://localhost:9999/api/login";
		String bodyAsJson = "{\"user\":\"user10@example.com\",\"password\":\"clave10\"}";
		
		VucemRestResponse vr = VucemRestResponse.createVucemRestResponse(invoker.sendPostRequest(endpoint, bodyAsJson, jwt));
		
		// main block
		HttpStatus status = vr.getHttpStatus();
		String response = vr.getJsonResponse();
		boolean errors = vr.hasErrors();
		
		// aqui haz el unmarshal
		if(!errors) {
			User user = VucemRestResponse.convertStringToObject(response, User.class);
			System.out.println(user.getNick());
			boolean test = user.getNick().contains("nick10");
			assertEquals(true, test);
		} else {
			System.out.println(status);
			VucemResResponseError err = VucemRestResponse.getError(response);
			String longDesc = err.getLongDesc();
			assertEquals(1, longDesc.length());
		}
	}
}
