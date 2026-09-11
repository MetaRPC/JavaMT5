package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-connection.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ConnectionGrpc {

  private ConnectionGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.Connection";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.ConnectRequest,
      mt5_term_api.Mt5TermApiConnection.ConnectReply> getConnectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Connect",
      requestType = mt5_term_api.Mt5TermApiConnection.ConnectRequest.class,
      responseType = mt5_term_api.Mt5TermApiConnection.ConnectReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.ConnectRequest,
      mt5_term_api.Mt5TermApiConnection.ConnectReply> getConnectMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.ConnectRequest, mt5_term_api.Mt5TermApiConnection.ConnectReply> getConnectMethod;
    if ((getConnectMethod = ConnectionGrpc.getConnectMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getConnectMethod = ConnectionGrpc.getConnectMethod) == null) {
          ConnectionGrpc.getConnectMethod = getConnectMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiConnection.ConnectRequest, mt5_term_api.Mt5TermApiConnection.ConnectReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Connect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.ConnectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.ConnectReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("Connect"))
              .build();
        }
      }
    }
    return getConnectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest,
      mt5_term_api.Mt5TermApiConnection.ConnectProxyReply> getConnectProxyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ConnectProxy",
      requestType = mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest.class,
      responseType = mt5_term_api.Mt5TermApiConnection.ConnectProxyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest,
      mt5_term_api.Mt5TermApiConnection.ConnectProxyReply> getConnectProxyMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest, mt5_term_api.Mt5TermApiConnection.ConnectProxyReply> getConnectProxyMethod;
    if ((getConnectProxyMethod = ConnectionGrpc.getConnectProxyMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getConnectProxyMethod = ConnectionGrpc.getConnectProxyMethod) == null) {
          ConnectionGrpc.getConnectProxyMethod = getConnectProxyMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest, mt5_term_api.Mt5TermApiConnection.ConnectProxyReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ConnectProxy"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.ConnectProxyReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("ConnectProxy"))
              .build();
        }
      }
    }
    return getConnectProxyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.CheckConnectRequest,
      mt5_term_api.Mt5TermApiConnection.CheckConnectReply> getCheckConnectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckConnect",
      requestType = mt5_term_api.Mt5TermApiConnection.CheckConnectRequest.class,
      responseType = mt5_term_api.Mt5TermApiConnection.CheckConnectReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.CheckConnectRequest,
      mt5_term_api.Mt5TermApiConnection.CheckConnectReply> getCheckConnectMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.CheckConnectRequest, mt5_term_api.Mt5TermApiConnection.CheckConnectReply> getCheckConnectMethod;
    if ((getCheckConnectMethod = ConnectionGrpc.getCheckConnectMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getCheckConnectMethod = ConnectionGrpc.getCheckConnectMethod) == null) {
          ConnectionGrpc.getCheckConnectMethod = getCheckConnectMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiConnection.CheckConnectRequest, mt5_term_api.Mt5TermApiConnection.CheckConnectReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckConnect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.CheckConnectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.CheckConnectReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("CheckConnect"))
              .build();
        }
      }
    }
    return getCheckConnectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.DisconnectRequest,
      mt5_term_api.Mt5TermApiConnection.DisconnectReply> getDisconnectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Disconnect",
      requestType = mt5_term_api.Mt5TermApiConnection.DisconnectRequest.class,
      responseType = mt5_term_api.Mt5TermApiConnection.DisconnectReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.DisconnectRequest,
      mt5_term_api.Mt5TermApiConnection.DisconnectReply> getDisconnectMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.DisconnectRequest, mt5_term_api.Mt5TermApiConnection.DisconnectReply> getDisconnectMethod;
    if ((getDisconnectMethod = ConnectionGrpc.getDisconnectMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getDisconnectMethod = ConnectionGrpc.getDisconnectMethod) == null) {
          ConnectionGrpc.getDisconnectMethod = getDisconnectMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiConnection.DisconnectRequest, mt5_term_api.Mt5TermApiConnection.DisconnectReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Disconnect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.DisconnectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.DisconnectReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("Disconnect"))
              .build();
        }
      }
    }
    return getDisconnectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.ReconnectRequest,
      mt5_term_api.Mt5TermApiConnection.ReconnectReply> getReconnectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Reconnect",
      requestType = mt5_term_api.Mt5TermApiConnection.ReconnectRequest.class,
      responseType = mt5_term_api.Mt5TermApiConnection.ReconnectReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.ReconnectRequest,
      mt5_term_api.Mt5TermApiConnection.ReconnectReply> getReconnectMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.ReconnectRequest, mt5_term_api.Mt5TermApiConnection.ReconnectReply> getReconnectMethod;
    if ((getReconnectMethod = ConnectionGrpc.getReconnectMethod) == null) {
      synchronized (ConnectionGrpc.class) {
        if ((getReconnectMethod = ConnectionGrpc.getReconnectMethod) == null) {
          ConnectionGrpc.getReconnectMethod = getReconnectMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiConnection.ReconnectRequest, mt5_term_api.Mt5TermApiConnection.ReconnectReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Reconnect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.ReconnectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.ReconnectReply.getDefaultInstance()))
              .setSchemaDescriptor(new ConnectionMethodDescriptorSupplier("Reconnect"))
              .build();
        }
      }
    }
    return getReconnectMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ConnectionStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConnectionStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConnectionStub>() {
        @java.lang.Override
        public ConnectionStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConnectionStub(channel, callOptions);
        }
      };
    return ConnectionStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ConnectionBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConnectionBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConnectionBlockingStub>() {
        @java.lang.Override
        public ConnectionBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConnectionBlockingStub(channel, callOptions);
        }
      };
    return ConnectionBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ConnectionFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ConnectionFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ConnectionFutureStub>() {
        @java.lang.Override
        public ConnectionFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ConnectionFutureStub(channel, callOptions);
        }
      };
    return ConnectionFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ConnectionImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Creates terminal connection to the MT5 server and returns Guid of it 
     * [DefaultValues]
     * {
     *   "user": "21455",
     *   "password": "1nJeS+Ae",
     *   "host": "95.217.147.61",
     *   "port": "443",
     *   "baseChartSymbol": "EURUSD",  
     *   "waitForTerminalIsAlive": "true"
     * }  
     * </pre>
     */
    public void connect(mt5_term_api.Mt5TermApiConnection.ConnectRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.ConnectReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConnectMethod(), responseObserver);
    }

    /**
     * <pre>
     * Connect to account with user, password, host, port.
     * [DefaultValues] 
     * { 
     *  "user": "21455", 
     *  "password": "1nJeS+Ae", 
     *  "host": "95.217.147.61", 
     *  "port": "443", 
     *  "proxyUser": "ProxyUser123", 
     *  "proxyPassword": "qwerty123", 
     *  "proxyHost": "65.108.126.217", 
     *  "proxyPort": "1080", 
     *  "proxyType": "Socks5" 
     * }
     * </pre>
     */
    public void connectProxy(mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.ConnectProxyReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConnectProxyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Checks if terminal connection to MT5 server is alive
     * </pre>
     */
    public void checkConnect(mt5_term_api.Mt5TermApiConnection.CheckConnectRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.CheckConnectReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckConnectMethod(), responseObserver);
    }

    /**
     * <pre>
     * Close terminal connection to MT5 server
     * </pre>
     */
    public void disconnect(mt5_term_api.Mt5TermApiConnection.DisconnectRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.DisconnectReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDisconnectMethod(), responseObserver);
    }

    /**
     * <pre>
     * If you need to recreate terminal instance with the same id
     * </pre>
     */
    public void reconnect(mt5_term_api.Mt5TermApiConnection.ReconnectRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.ReconnectReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReconnectMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getConnectMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiConnection.ConnectRequest,
                mt5_term_api.Mt5TermApiConnection.ConnectReply>(
                  this, METHODID_CONNECT)))
          .addMethod(
            getConnectProxyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest,
                mt5_term_api.Mt5TermApiConnection.ConnectProxyReply>(
                  this, METHODID_CONNECT_PROXY)))
          .addMethod(
            getCheckConnectMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiConnection.CheckConnectRequest,
                mt5_term_api.Mt5TermApiConnection.CheckConnectReply>(
                  this, METHODID_CHECK_CONNECT)))
          .addMethod(
            getDisconnectMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiConnection.DisconnectRequest,
                mt5_term_api.Mt5TermApiConnection.DisconnectReply>(
                  this, METHODID_DISCONNECT)))
          .addMethod(
            getReconnectMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiConnection.ReconnectRequest,
                mt5_term_api.Mt5TermApiConnection.ReconnectReply>(
                  this, METHODID_RECONNECT)))
          .build();
    }
  }

  /**
   */
  public static final class ConnectionStub extends io.grpc.stub.AbstractAsyncStub<ConnectionStub> {
    private ConnectionStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConnectionStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConnectionStub(channel, callOptions);
    }

    /**
     * <pre>
     * Creates terminal connection to the MT5 server and returns Guid of it 
     * [DefaultValues]
     * {
     *   "user": "21455",
     *   "password": "1nJeS+Ae",
     *   "host": "95.217.147.61",
     *   "port": "443",
     *   "baseChartSymbol": "EURUSD",  
     *   "waitForTerminalIsAlive": "true"
     * }  
     * </pre>
     */
    public void connect(mt5_term_api.Mt5TermApiConnection.ConnectRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.ConnectReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConnectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Connect to account with user, password, host, port.
     * [DefaultValues] 
     * { 
     *  "user": "21455", 
     *  "password": "1nJeS+Ae", 
     *  "host": "95.217.147.61", 
     *  "port": "443", 
     *  "proxyUser": "ProxyUser123", 
     *  "proxyPassword": "qwerty123", 
     *  "proxyHost": "65.108.126.217", 
     *  "proxyPort": "1080", 
     *  "proxyType": "Socks5" 
     * }
     * </pre>
     */
    public void connectProxy(mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.ConnectProxyReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConnectProxyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Checks if terminal connection to MT5 server is alive
     * </pre>
     */
    public void checkConnect(mt5_term_api.Mt5TermApiConnection.CheckConnectRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.CheckConnectReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckConnectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Close terminal connection to MT5 server
     * </pre>
     */
    public void disconnect(mt5_term_api.Mt5TermApiConnection.DisconnectRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.DisconnectReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDisconnectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * If you need to recreate terminal instance with the same id
     * </pre>
     */
    public void reconnect(mt5_term_api.Mt5TermApiConnection.ReconnectRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.ReconnectReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReconnectMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ConnectionBlockingStub extends io.grpc.stub.AbstractBlockingStub<ConnectionBlockingStub> {
    private ConnectionBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConnectionBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConnectionBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Creates terminal connection to the MT5 server and returns Guid of it 
     * [DefaultValues]
     * {
     *   "user": "21455",
     *   "password": "1nJeS+Ae",
     *   "host": "95.217.147.61",
     *   "port": "443",
     *   "baseChartSymbol": "EURUSD",  
     *   "waitForTerminalIsAlive": "true"
     * }  
     * </pre>
     */
    public mt5_term_api.Mt5TermApiConnection.ConnectReply connect(mt5_term_api.Mt5TermApiConnection.ConnectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConnectMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Connect to account with user, password, host, port.
     * [DefaultValues] 
     * { 
     *  "user": "21455", 
     *  "password": "1nJeS+Ae", 
     *  "host": "95.217.147.61", 
     *  "port": "443", 
     *  "proxyUser": "ProxyUser123", 
     *  "proxyPassword": "qwerty123", 
     *  "proxyHost": "65.108.126.217", 
     *  "proxyPort": "1080", 
     *  "proxyType": "Socks5" 
     * }
     * </pre>
     */
    public mt5_term_api.Mt5TermApiConnection.ConnectProxyReply connectProxy(mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConnectProxyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Checks if terminal connection to MT5 server is alive
     * </pre>
     */
    public mt5_term_api.Mt5TermApiConnection.CheckConnectReply checkConnect(mt5_term_api.Mt5TermApiConnection.CheckConnectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckConnectMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Close terminal connection to MT5 server
     * </pre>
     */
    public mt5_term_api.Mt5TermApiConnection.DisconnectReply disconnect(mt5_term_api.Mt5TermApiConnection.DisconnectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDisconnectMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * If you need to recreate terminal instance with the same id
     * </pre>
     */
    public mt5_term_api.Mt5TermApiConnection.ReconnectReply reconnect(mt5_term_api.Mt5TermApiConnection.ReconnectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReconnectMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ConnectionFutureStub extends io.grpc.stub.AbstractFutureStub<ConnectionFutureStub> {
    private ConnectionFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ConnectionFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ConnectionFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Creates terminal connection to the MT5 server and returns Guid of it 
     * [DefaultValues]
     * {
     *   "user": "21455",
     *   "password": "1nJeS+Ae",
     *   "host": "95.217.147.61",
     *   "port": "443",
     *   "baseChartSymbol": "EURUSD",  
     *   "waitForTerminalIsAlive": "true"
     * }  
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiConnection.ConnectReply> connect(
        mt5_term_api.Mt5TermApiConnection.ConnectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConnectMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Connect to account with user, password, host, port.
     * [DefaultValues] 
     * { 
     *  "user": "21455", 
     *  "password": "1nJeS+Ae", 
     *  "host": "95.217.147.61", 
     *  "port": "443", 
     *  "proxyUser": "ProxyUser123", 
     *  "proxyPassword": "qwerty123", 
     *  "proxyHost": "65.108.126.217", 
     *  "proxyPort": "1080", 
     *  "proxyType": "Socks5" 
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiConnection.ConnectProxyReply> connectProxy(
        mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConnectProxyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Checks if terminal connection to MT5 server is alive
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiConnection.CheckConnectReply> checkConnect(
        mt5_term_api.Mt5TermApiConnection.CheckConnectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckConnectMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Close terminal connection to MT5 server
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiConnection.DisconnectReply> disconnect(
        mt5_term_api.Mt5TermApiConnection.DisconnectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDisconnectMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * If you need to recreate terminal instance with the same id
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiConnection.ReconnectReply> reconnect(
        mt5_term_api.Mt5TermApiConnection.ReconnectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReconnectMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CONNECT = 0;
  private static final int METHODID_CONNECT_PROXY = 1;
  private static final int METHODID_CHECK_CONNECT = 2;
  private static final int METHODID_DISCONNECT = 3;
  private static final int METHODID_RECONNECT = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ConnectionImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ConnectionImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CONNECT:
          serviceImpl.connect((mt5_term_api.Mt5TermApiConnection.ConnectRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.ConnectReply>) responseObserver);
          break;
        case METHODID_CONNECT_PROXY:
          serviceImpl.connectProxy((mt5_term_api.Mt5TermApiConnection.ConnectProxyRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.ConnectProxyReply>) responseObserver);
          break;
        case METHODID_CHECK_CONNECT:
          serviceImpl.checkConnect((mt5_term_api.Mt5TermApiConnection.CheckConnectRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.CheckConnectReply>) responseObserver);
          break;
        case METHODID_DISCONNECT:
          serviceImpl.disconnect((mt5_term_api.Mt5TermApiConnection.DisconnectRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.DisconnectReply>) responseObserver);
          break;
        case METHODID_RECONNECT:
          serviceImpl.reconnect((mt5_term_api.Mt5TermApiConnection.ReconnectRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.ReconnectReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ConnectionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ConnectionBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiConnection.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Connection");
    }
  }

  private static final class ConnectionFileDescriptorSupplier
      extends ConnectionBaseDescriptorSupplier {
    ConnectionFileDescriptorSupplier() {}
  }

  private static final class ConnectionMethodDescriptorSupplier
      extends ConnectionBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ConnectionMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ConnectionGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ConnectionFileDescriptorSupplier())
              .addMethod(getConnectMethod())
              .addMethod(getConnectProxyMethod())
              .addMethod(getCheckConnectMethod())
              .addMethod(getDisconnectMethod())
              .addMethod(getReconnectMethod())
              .build();
        }
      }
    }
    return result;
  }
}
