package com.qyly.remex.cloud.feign.ribbon;

import static org.springframework.cloud.netflix.ribbon.RibbonUtils.updateToHttpsIfNeeded;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.ribbon.ServerIntrospector;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

import com.netflix.client.AbstractLoadBalancerAwareClient;
import com.netflix.client.ClientException;
import com.netflix.client.ClientRequest;
import com.netflix.client.IResponse;
import com.netflix.client.RequestSpecificRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.reactive.ServerOperation;

import feign.Client;
import feign.Request;
import feign.Response;
import rx.Observable;

/**
 * 重写org.springframework.cloud.netflix.feign.ribbon.FeignLoadBalancer
 * 
 * @see org.springframework.cloud.netflix.feign.ribbon.FeignLoadBalancer
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexFeignLoadBalancer extends AbstractLoadBalancerAwareClient<RemexFeignLoadBalancer.RibbonRequest, RemexFeignLoadBalancer.RibbonResponse> {

	protected int connectTimeout;
	protected int readTimeout;
	protected IClientConfig clientConfig;
	protected ServerIntrospector serverIntrospector;

	public RemexFeignLoadBalancer(ILoadBalancer lb, IClientConfig clientConfig,
			ServerIntrospector serverIntrospector) {
		super(lb, clientConfig);
		this.setRetryHandler(RetryHandler.DEFAULT);
		this.clientConfig = clientConfig;
		this.connectTimeout = clientConfig.get(CommonClientConfigKey.ConnectTimeout);
		this.readTimeout = clientConfig.get(CommonClientConfigKey.ReadTimeout);
		this.serverIntrospector = serverIntrospector;
	}
	
	public RibbonResponse executeWithLoadBalancer(RibbonRequest request, IClientConfig requestConfig) throws ClientException {
		RequestSpecificRetryHandler handler = getRequestSpecificRetryHandler(request, requestConfig);
		RemexLoadBalancerCommand<RibbonResponse> command = RemexLoadBalancerCommand.<RibbonResponse>builder()
                .withLoadBalancerContext(this)
                .withRetryHandler(handler)
                .withLoadBalancerURI(request.getUri())
                .build();

        try {
            return command.submit(
                new ServerOperation<RibbonResponse>() {
                    @Override
                    public Observable<RibbonResponse> call(Server server) {
                        URI finalUri = reconstructURIWithServer(server, request.getUri());
                        RibbonRequest requestForServer = (RibbonRequest) request.replaceUri(finalUri);
                        try {
                            return Observable.just(execute(requestForServer, requestConfig));
                        } 
                        catch (Exception e) {
                            return Observable.error(e);
                        }
                    }
                })
                .toBlocking()
                .single();
        } catch (Exception e) {
            Throwable t = e.getCause();
            if (t instanceof ClientException) {
                throw (ClientException) t;
            } else {
                throw new ClientException(e);
            }
        }
	}

	@Override
	public RibbonResponse execute(RibbonRequest request, IClientConfig configOverride)
			throws IOException {
		Request.Options options;
		if (configOverride != null) {
			options = new Request.Options(
					configOverride.get(CommonClientConfigKey.ConnectTimeout,
							this.connectTimeout),
					(configOverride.get(CommonClientConfigKey.ReadTimeout,
							this.readTimeout)));
		}
		else {
			options = new Request.Options(this.connectTimeout, this.readTimeout);
		}
		Response response = request.client().execute(request.toRequest(), options);
		return new RibbonResponse(request.getUri(), response);
	}

	@Override
	public RequestSpecificRetryHandler getRequestSpecificRetryHandler(
			RibbonRequest request, IClientConfig requestConfig) {
		if (this.clientConfig.get(CommonClientConfigKey.OkToRetryOnAllOperations,
				false)) {
			return new RequestSpecificRetryHandler(true, true, this.getRetryHandler(),
					requestConfig);
		}
		if (!request.toRequest().method().equals("GET")) {
			return new RequestSpecificRetryHandler(true, false, this.getRetryHandler(),
					requestConfig);
		}
		else {
			return new RequestSpecificRetryHandler(true, true, this.getRetryHandler(),
					requestConfig);
		}
	}

	@Override
	public URI reconstructURIWithServer(Server server, URI original) {
		URI uri = updateToHttpsIfNeeded(original, this.clientConfig, this.serverIntrospector, server);
		return super.reconstructURIWithServer(server, uri);
	}

	static class RibbonRequest extends ClientRequest implements Cloneable {

		private final Request request;
		private final Client client;

		RibbonRequest(Client client, Request request, URI uri) {
			this.client = client;
			setUri(uri);
			this.request = toRequest(request);
		}

		private Request toRequest(Request request) {
			Map<String, Collection<String>> headers = new LinkedHashMap<>(
					request.headers());
			return Request.create(request.method(),getUri().toASCIIString(),headers,request.body(),request.charset());
		}

		Request toRequest() {
			return toRequest(this.request);
		}

		Client client() {
			return this.client;
		}

		HttpRequest toHttpRequest() {
			return new HttpRequest() {
				@Override
				public HttpMethod getMethod() {
					return HttpMethod.resolve(RibbonRequest.this.toRequest().method());
				}

				@Override
				public URI getURI() {
					return RibbonRequest.this.getUri();
				}

				@Override
				public HttpHeaders getHeaders() {
					Map<String, List<String>> headers = new HashMap<String, List<String>>();
					Map<String, Collection<String>> feignHeaders = RibbonRequest.this.toRequest().headers();
					for(String key : feignHeaders.keySet()) {
						headers.put(key, new ArrayList<String>(feignHeaders.get(key)));
					}
					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.putAll(headers);
					return httpHeaders;

				}
			};
		}


		@Override
		public Object clone() {
			return new RibbonRequest(this.client, this.request, getUri());
		}
	}

	static class RibbonResponse implements IResponse {

		private final URI uri;
		private final Response response;

		RibbonResponse(URI uri, Response response) {
			this.uri = uri;
			this.response = response;
		}

		@Override
		public Object getPayload() throws ClientException {
			return this.response.body();
		}

		@Override
		public boolean hasPayload() {
			return this.response.body() != null;
		}

		@Override
		public boolean isSuccess() {
			return this.response.status() == 200;
		}

		@Override
		public URI getRequestedURI() {
			return this.uri;
		}

		@Override
		public Map<String, Collection<String>> getHeaders() {
			return this.response.headers();
		}

		Response toResponse() {
			return this.response;
		}

		@Override
		public void close() throws IOException {
			if (this.response != null && this.response.body() != null) {
				this.response.body().close();
			}
		}

	}

}