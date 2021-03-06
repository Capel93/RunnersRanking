/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-03-26 20:30:19 UTC)
 * on 2015-05-27 at 18:55:42 UTC 
 * Modify at your own risk.
 */

package com.example.joanmarc.myapplication.backend.routeApi;

/**
 * Service definition for RouteApi (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link RouteApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class RouteApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the routeApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "routeApi/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public RouteApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  RouteApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getRoute".
   *
   * This request holds the parameters needed by the routeApi server.  After setting any optional
   * parameters, call the {@link GetRoute#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetRoute getRoute(java.lang.Long id) throws java.io.IOException {
    GetRoute result = new GetRoute(id);
    initialize(result);
    return result;
  }

  public class GetRoute extends RouteApiRequest<com.example.joanmarc.myapplication.backend.routeApi.model.Route> {

    private static final String REST_PATH = "route/{id}";

    /**
     * Create a request for the method "getRoute".
     *
     * This request holds the parameters needed by the the routeApi server.  After setting any
     * optional parameters, call the {@link GetRoute#execute()} method to invoke the remote operation.
     * <p> {@link
     * GetRoute#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetRoute(java.lang.Long id) {
      super(RouteApi.this, "GET", REST_PATH, null, com.example.joanmarc.myapplication.backend.routeApi.model.Route.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetRoute setAlt(java.lang.String alt) {
      return (GetRoute) super.setAlt(alt);
    }

    @Override
    public GetRoute setFields(java.lang.String fields) {
      return (GetRoute) super.setFields(fields);
    }

    @Override
    public GetRoute setKey(java.lang.String key) {
      return (GetRoute) super.setKey(key);
    }

    @Override
    public GetRoute setOauthToken(java.lang.String oauthToken) {
      return (GetRoute) super.setOauthToken(oauthToken);
    }

    @Override
    public GetRoute setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetRoute) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetRoute setQuotaUser(java.lang.String quotaUser) {
      return (GetRoute) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetRoute setUserIp(java.lang.String userIp) {
      return (GetRoute) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetRoute setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetRoute set(String parameterName, Object value) {
      return (GetRoute) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertRoute".
   *
   * This request holds the parameters needed by the routeApi server.  After setting any optional
   * parameters, call the {@link InsertRoute#execute()} method to invoke the remote operation.
   *
   * @param content the {@link com.example.joanmarc.myapplication.backend.routeApi.model.Route}
   * @return the request
   */
  public InsertRoute insertRoute(com.example.joanmarc.myapplication.backend.routeApi.model.Route content) throws java.io.IOException {
    InsertRoute result = new InsertRoute(content);
    initialize(result);
    return result;
  }

  public class InsertRoute extends RouteApiRequest<com.example.joanmarc.myapplication.backend.routeApi.model.Route> {

    private static final String REST_PATH = "route";

    /**
     * Create a request for the method "insertRoute".
     *
     * This request holds the parameters needed by the the routeApi server.  After setting any
     * optional parameters, call the {@link InsertRoute#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertRoute#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.example.joanmarc.myapplication.backend.routeApi.model.Route}
     * @since 1.13
     */
    protected InsertRoute(com.example.joanmarc.myapplication.backend.routeApi.model.Route content) {
      super(RouteApi.this, "POST", REST_PATH, content, com.example.joanmarc.myapplication.backend.routeApi.model.Route.class);
    }

    @Override
    public InsertRoute setAlt(java.lang.String alt) {
      return (InsertRoute) super.setAlt(alt);
    }

    @Override
    public InsertRoute setFields(java.lang.String fields) {
      return (InsertRoute) super.setFields(fields);
    }

    @Override
    public InsertRoute setKey(java.lang.String key) {
      return (InsertRoute) super.setKey(key);
    }

    @Override
    public InsertRoute setOauthToken(java.lang.String oauthToken) {
      return (InsertRoute) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertRoute setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertRoute) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertRoute setQuotaUser(java.lang.String quotaUser) {
      return (InsertRoute) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertRoute setUserIp(java.lang.String userIp) {
      return (InsertRoute) super.setUserIp(userIp);
    }

    @Override
    public InsertRoute set(String parameterName, Object value) {
      return (InsertRoute) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listRouteByUser".
   *
   * This request holds the parameters needed by the routeApi server.  After setting any optional
   * parameters, call the {@link ListRouteByUser#execute()} method to invoke the remote operation.
   *
   * @param count
   * @param userName
   * @return the request
   */
  public ListRouteByUser listRouteByUser(java.lang.Integer count, java.lang.String userName) throws java.io.IOException {
    ListRouteByUser result = new ListRouteByUser(count, userName);
    initialize(result);
    return result;
  }

  public class ListRouteByUser extends RouteApiRequest<com.example.joanmarc.myapplication.backend.routeApi.model.CollectionResponseRoute> {

    private static final String REST_PATH = "listRoutesByUser";

    /**
     * Create a request for the method "listRouteByUser".
     *
     * This request holds the parameters needed by the the routeApi server.  After setting any
     * optional parameters, call the {@link ListRouteByUser#execute()} method to invoke the remote
     * operation. <p> {@link ListRouteByUser#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param count
     * @param userName
     * @since 1.13
     */
    protected ListRouteByUser(java.lang.Integer count, java.lang.String userName) {
      super(RouteApi.this, "GET", REST_PATH, null, com.example.joanmarc.myapplication.backend.routeApi.model.CollectionResponseRoute.class);
      this.count = com.google.api.client.util.Preconditions.checkNotNull(count, "Required parameter count must be specified.");
      this.userName = com.google.api.client.util.Preconditions.checkNotNull(userName, "Required parameter userName must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListRouteByUser setAlt(java.lang.String alt) {
      return (ListRouteByUser) super.setAlt(alt);
    }

    @Override
    public ListRouteByUser setFields(java.lang.String fields) {
      return (ListRouteByUser) super.setFields(fields);
    }

    @Override
    public ListRouteByUser setKey(java.lang.String key) {
      return (ListRouteByUser) super.setKey(key);
    }

    @Override
    public ListRouteByUser setOauthToken(java.lang.String oauthToken) {
      return (ListRouteByUser) super.setOauthToken(oauthToken);
    }

    @Override
    public ListRouteByUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListRouteByUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListRouteByUser setQuotaUser(java.lang.String quotaUser) {
      return (ListRouteByUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListRouteByUser setUserIp(java.lang.String userIp) {
      return (ListRouteByUser) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer count;

    /**

     */
    public java.lang.Integer getCount() {
      return count;
    }

    public ListRouteByUser setCount(java.lang.Integer count) {
      this.count = count;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String userName;

    /**

     */
    public java.lang.String getUserName() {
      return userName;
    }

    public ListRouteByUser setUserName(java.lang.String userName) {
      this.userName = userName;
      return this;
    }

    @Override
    public ListRouteByUser set(String parameterName, Object value) {
      return (ListRouteByUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listRoutes".
   *
   * This request holds the parameters needed by the routeApi server.  After setting any optional
   * parameters, call the {@link ListRoutes#execute()} method to invoke the remote operation.
   *
   * @param count
   * @param userName
   * @return the request
   */
  public ListRoutes listRoutes(java.lang.Integer count, java.lang.String userName) throws java.io.IOException {
    ListRoutes result = new ListRoutes(count, userName);
    initialize(result);
    return result;
  }

  public class ListRoutes extends RouteApiRequest<com.example.joanmarc.myapplication.backend.routeApi.model.RouteCollection> {

    private static final String REST_PATH = "listRoutes";

    /**
     * Create a request for the method "listRoutes".
     *
     * This request holds the parameters needed by the the routeApi server.  After setting any
     * optional parameters, call the {@link ListRoutes#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListRoutes#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param count
     * @param userName
     * @since 1.13
     */
    protected ListRoutes(java.lang.Integer count, java.lang.String userName) {
      super(RouteApi.this, "GET", REST_PATH, null, com.example.joanmarc.myapplication.backend.routeApi.model.RouteCollection.class);
      this.count = com.google.api.client.util.Preconditions.checkNotNull(count, "Required parameter count must be specified.");
      this.userName = com.google.api.client.util.Preconditions.checkNotNull(userName, "Required parameter userName must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListRoutes setAlt(java.lang.String alt) {
      return (ListRoutes) super.setAlt(alt);
    }

    @Override
    public ListRoutes setFields(java.lang.String fields) {
      return (ListRoutes) super.setFields(fields);
    }

    @Override
    public ListRoutes setKey(java.lang.String key) {
      return (ListRoutes) super.setKey(key);
    }

    @Override
    public ListRoutes setOauthToken(java.lang.String oauthToken) {
      return (ListRoutes) super.setOauthToken(oauthToken);
    }

    @Override
    public ListRoutes setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListRoutes) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListRoutes setQuotaUser(java.lang.String quotaUser) {
      return (ListRoutes) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListRoutes setUserIp(java.lang.String userIp) {
      return (ListRoutes) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer count;

    /**

     */
    public java.lang.Integer getCount() {
      return count;
    }

    public ListRoutes setCount(java.lang.Integer count) {
      this.count = count;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String userName;

    /**

     */
    public java.lang.String getUserName() {
      return userName;
    }

    public ListRoutes setUserName(java.lang.String userName) {
      this.userName = userName;
      return this;
    }

    @Override
    public ListRoutes set(String parameterName, Object value) {
      return (ListRoutes) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link RouteApi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link RouteApi}. */
    @Override
    public RouteApi build() {
      return new RouteApi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link RouteApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setRouteApiRequestInitializer(
        RouteApiRequestInitializer routeapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(routeapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
