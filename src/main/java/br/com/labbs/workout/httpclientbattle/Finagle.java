package br.com.labbs.workout.httpclientbattle;

import br.com.labbs.workout.httpclientbattle.shared.Env;
import br.com.labbs.workout.httpclientbattle.shared.HttpClient;
import com.twitter.finagle.Http;
import com.twitter.finagle.Service;
import com.twitter.finagle.http.Request;
import com.twitter.finagle.http.RequestBuilder;
import com.twitter.finagle.http.Response;

public class Finagle implements HttpClient<Request,Response,Service<Request, Response>> {

    private final Service<Request, Response> client;
    private final RequestBuilder requestBuilder;
    private final static String FINAGLE = "Finagle";

    @SuppressWarnings("unused")
    public Finagle() {
        client = Http.client().withLabel("http-client-battle-finagle").newService(String.format("%s:%d", Env.URL_SERVER_DOMAIN.get(), Env.URL_SERVER_PORT.getInt()));
        requestBuilder = RequestBuilder.create().url(Env.URL_SERVER.get());
    }

    public String getClientName() {
        return FINAGLE;
    }

    public Request newRequest(String url) {
        return RequestBuilder.safeBuildGet(requestBuilder);
    }

    public void addHeaderToRequest(Request request, String key, String value) {
        request.headerMap().put(key, value);
    }

    public Response execRequest(Request request, int i) throws Exception {
        return client.apply(request).toJavaFuture().get();
    }

    public int getResponseStatusCode(Response response) {
        return response.statusCode();
    }



}
