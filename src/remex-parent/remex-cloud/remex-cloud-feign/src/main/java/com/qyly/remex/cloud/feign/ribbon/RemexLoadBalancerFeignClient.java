package com.qyly.remex.cloud.feign.ribbon;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.springframework.cloud.netflix.ribbon.ServerIntrospector;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.util.ConcurrentReferenceHashMap;

import com.netflix.client.ClientException;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;

import feign.Client;
import feign.Request;
import feign.Response;

/**
 * 重写org.springframework.cloud.netflix.feign.ribbon.LoadBalancerFeignClient
 * 
 * @see org.springframework.cloud.netflix.feign.ribbon.LoadBalancerFeignClient
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexLoadBalancerFeignClient implements Client {

	public static final Request.Options DEFAULT_OPTIONS = new Request.Options();

	private final Client delegate;
	private SpringClientFactory clientFactory;
	
	private volatile Map<String, RemexFeignLoadBalancer> cache = new ConcurrentReferenceHashMap<>();

	public RemexLoadBalancerFeignClient(Client delegate, SpringClientFactory clientFactory) {
		this.delegate = delegate;
		this.clientFactory = clientFactory;
	}

	@Override
	public Response execute(Request request, Request.Options options) throws IOException {
		try {
			URI asUri = URI.create(request.url());
			String clientName = asUri.getHost();
			URI uriWithoutHost = cleanUrl(request.url(), clientName);
			RemexFeignLoadBalancer.RibbonRequest ribbonRequest = new RemexFeignLoadBalancer.RibbonRequest(
					this.delegate, request, uriWithoutHost);

			IClientConfig requestConfig = getClientConfig(options, clientName);
			return lbClient(clientName).executeWithLoadBalancer(ribbonRequest,
					requestConfig).toResponse();
		}
		catch (ClientException e) {
			IOException io = findIOException(e);
			if (io != null) {
				throw io;
			}
			throw new RuntimeException(e);
		}
	}

	IClientConfig getClientConfig(Request.Options options, String clientName) {
		IClientConfig requestConfig;
		if (options == DEFAULT_OPTIONS) {
			requestConfig = this.clientFactory.getClientConfig(clientName);
		} else {
			requestConfig = new FeignOptionsClientConfig(options);
		}
		return requestConfig;
	}

	protected IOException findIOException(Throwable t) {
		if (t == null) {
			return null;
		}
		if (t instanceof IOException) {
			return (IOException) t;
		}
		return findIOException(t.getCause());
	}

	public Client getDelegate() {
		return this.delegate;
	}

	static URI cleanUrl(String originalUrl, String host) {
		String newUrl = originalUrl.replaceFirst(host, "");
		StringBuffer buffer = new StringBuffer(newUrl);
		if((newUrl.startsWith("https://") && newUrl.length() == 8) ||
				(newUrl.startsWith("http://") && newUrl.length() == 7)) {
			buffer.append("/");
		}
		return URI.create(buffer.toString());
	}

	private RemexFeignLoadBalancer lbClient(String clientName) {
		if (this.cache.containsKey(clientName)) {
			return this.cache.get(clientName);
		}
		IClientConfig config = this.clientFactory.getClientConfig(clientName);
		ILoadBalancer lb = this.clientFactory.getLoadBalancer(clientName);
		ServerIntrospector serverIntrospector = this.clientFactory.getInstance(clientName, ServerIntrospector.class);
		RemexFeignLoadBalancer client = new RemexFeignLoadBalancer(lb, config, serverIntrospector);
		this.cache.put(clientName, client);
		return client;
	}

	static class FeignOptionsClientConfig extends DefaultClientConfigImpl {

		public FeignOptionsClientConfig(Request.Options options) {
			setProperty(CommonClientConfigKey.ConnectTimeout,
					options.connectTimeoutMillis());
			setProperty(CommonClientConfigKey.ReadTimeout, options.readTimeoutMillis());
		}

		@Override
		public void loadProperties(String clientName) {

		}

		@Override
		public void loadDefaultValues() {

		}

	}
}
