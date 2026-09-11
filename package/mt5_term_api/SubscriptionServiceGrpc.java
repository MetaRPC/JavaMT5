package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-subscriptions.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SubscriptionServiceGrpc {

  private SubscriptionServiceGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.SubscriptionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickRequest,
      mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickReply> getOnSymbolTickMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnSymbolTick",
      requestType = mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickRequest.class,
      responseType = mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickRequest,
      mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickReply> getOnSymbolTickMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickRequest, mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickReply> getOnSymbolTickMethod;
    if ((getOnSymbolTickMethod = SubscriptionServiceGrpc.getOnSymbolTickMethod) == null) {
      synchronized (SubscriptionServiceGrpc.class) {
        if ((getOnSymbolTickMethod = SubscriptionServiceGrpc.getOnSymbolTickMethod) == null) {
          SubscriptionServiceGrpc.getOnSymbolTickMethod = getOnSymbolTickMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickRequest, mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnSymbolTick"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickReply.getDefaultInstance()))
              .setSchemaDescriptor(new SubscriptionServiceMethodDescriptorSupplier("OnSymbolTick"))
              .build();
        }
      }
    }
    return getOnSymbolTickMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnTradeRequest,
      mt5_term_api.Mt5TermApiSubscriptions.OnTradeReply> getOnTradeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnTrade",
      requestType = mt5_term_api.Mt5TermApiSubscriptions.OnTradeRequest.class,
      responseType = mt5_term_api.Mt5TermApiSubscriptions.OnTradeReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnTradeRequest,
      mt5_term_api.Mt5TermApiSubscriptions.OnTradeReply> getOnTradeMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnTradeRequest, mt5_term_api.Mt5TermApiSubscriptions.OnTradeReply> getOnTradeMethod;
    if ((getOnTradeMethod = SubscriptionServiceGrpc.getOnTradeMethod) == null) {
      synchronized (SubscriptionServiceGrpc.class) {
        if ((getOnTradeMethod = SubscriptionServiceGrpc.getOnTradeMethod) == null) {
          SubscriptionServiceGrpc.getOnTradeMethod = getOnTradeMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiSubscriptions.OnTradeRequest, mt5_term_api.Mt5TermApiSubscriptions.OnTradeReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnTrade"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiSubscriptions.OnTradeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiSubscriptions.OnTradeReply.getDefaultInstance()))
              .setSchemaDescriptor(new SubscriptionServiceMethodDescriptorSupplier("OnTrade"))
              .build();
        }
      }
    }
    return getOnTradeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitRequest,
      mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitReply> getOnPositionProfitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnPositionProfit",
      requestType = mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitRequest.class,
      responseType = mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitRequest,
      mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitReply> getOnPositionProfitMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitRequest, mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitReply> getOnPositionProfitMethod;
    if ((getOnPositionProfitMethod = SubscriptionServiceGrpc.getOnPositionProfitMethod) == null) {
      synchronized (SubscriptionServiceGrpc.class) {
        if ((getOnPositionProfitMethod = SubscriptionServiceGrpc.getOnPositionProfitMethod) == null) {
          SubscriptionServiceGrpc.getOnPositionProfitMethod = getOnPositionProfitMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitRequest, mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnPositionProfit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitReply.getDefaultInstance()))
              .setSchemaDescriptor(new SubscriptionServiceMethodDescriptorSupplier("OnPositionProfit"))
              .build();
        }
      }
    }
    return getOnPositionProfitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest,
      mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> getOnPositionsAndPendingOrdersTicketsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnPositionsAndPendingOrdersTickets",
      requestType = mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest.class,
      responseType = mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest,
      mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> getOnPositionsAndPendingOrdersTicketsMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest, mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> getOnPositionsAndPendingOrdersTicketsMethod;
    if ((getOnPositionsAndPendingOrdersTicketsMethod = SubscriptionServiceGrpc.getOnPositionsAndPendingOrdersTicketsMethod) == null) {
      synchronized (SubscriptionServiceGrpc.class) {
        if ((getOnPositionsAndPendingOrdersTicketsMethod = SubscriptionServiceGrpc.getOnPositionsAndPendingOrdersTicketsMethod) == null) {
          SubscriptionServiceGrpc.getOnPositionsAndPendingOrdersTicketsMethod = getOnPositionsAndPendingOrdersTicketsMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest, mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnPositionsAndPendingOrdersTickets"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply.getDefaultInstance()))
              .setSchemaDescriptor(new SubscriptionServiceMethodDescriptorSupplier("OnPositionsAndPendingOrdersTickets"))
              .build();
        }
      }
    }
    return getOnPositionsAndPendingOrdersTicketsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionRequest,
      mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionReply> getOnTradeTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnTradeTransaction",
      requestType = mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionRequest.class,
      responseType = mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionRequest,
      mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionReply> getOnTradeTransactionMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionRequest, mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionReply> getOnTradeTransactionMethod;
    if ((getOnTradeTransactionMethod = SubscriptionServiceGrpc.getOnTradeTransactionMethod) == null) {
      synchronized (SubscriptionServiceGrpc.class) {
        if ((getOnTradeTransactionMethod = SubscriptionServiceGrpc.getOnTradeTransactionMethod) == null) {
          SubscriptionServiceGrpc.getOnTradeTransactionMethod = getOnTradeTransactionMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionRequest, mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnTradeTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionReply.getDefaultInstance()))
              .setSchemaDescriptor(new SubscriptionServiceMethodDescriptorSupplier("OnTradeTransaction"))
              .build();
        }
      }
    }
    return getOnTradeTransactionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SubscriptionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceStub>() {
        @java.lang.Override
        public SubscriptionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SubscriptionServiceStub(channel, callOptions);
        }
      };
    return SubscriptionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SubscriptionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceBlockingStub>() {
        @java.lang.Override
        public SubscriptionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SubscriptionServiceBlockingStub(channel, callOptions);
        }
      };
    return SubscriptionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SubscriptionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SubscriptionServiceFutureStub>() {
        @java.lang.Override
        public SubscriptionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SubscriptionServiceFutureStub(channel, callOptions);
        }
      };
    return SubscriptionServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SubscriptionServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Streams real-time symbol tick data for the specified symbols.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * [DefaultValues]
     * {
     *   "symbolNames": "EURUSD,BTCUSD"
     * }
     * </pre>
     */
    public void onSymbolTick(mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnSymbolTickMethod(), responseObserver);
    }

    /**
     * <pre>
     * Streams real-time trade events (orders, positions, deals changes).
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onTrade(mt5_term_api.Mt5TermApiSubscriptions.OnTradeRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnTradeReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnTradeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Streams real-time position profit updates at the specified timer interval.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onPositionProfit(mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnPositionProfitMethod(), responseObserver);
    }

    /**
     * <pre>
     * Streams real-time position and pending order ticket changes.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onPositionsAndPendingOrdersTickets(mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnPositionsAndPendingOrdersTicketsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Streams real-time trade transaction events (order add/update/delete, deal add, position changes).
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onTradeTransaction(mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnTradeTransactionMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getOnSymbolTickMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickRequest,
                mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickReply>(
                  this, METHODID_ON_SYMBOL_TICK)))
          .addMethod(
            getOnTradeMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiSubscriptions.OnTradeRequest,
                mt5_term_api.Mt5TermApiSubscriptions.OnTradeReply>(
                  this, METHODID_ON_TRADE)))
          .addMethod(
            getOnPositionProfitMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitRequest,
                mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitReply>(
                  this, METHODID_ON_POSITION_PROFIT)))
          .addMethod(
            getOnPositionsAndPendingOrdersTicketsMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest,
                mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply>(
                  this, METHODID_ON_POSITIONS_AND_PENDING_ORDERS_TICKETS)))
          .addMethod(
            getOnTradeTransactionMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionRequest,
                mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionReply>(
                  this, METHODID_ON_TRADE_TRANSACTION)))
          .build();
    }
  }

  /**
   */
  public static final class SubscriptionServiceStub extends io.grpc.stub.AbstractAsyncStub<SubscriptionServiceStub> {
    private SubscriptionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscriptionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SubscriptionServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Streams real-time symbol tick data for the specified symbols.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * [DefaultValues]
     * {
     *   "symbolNames": "EURUSD,BTCUSD"
     * }
     * </pre>
     */
    public void onSymbolTick(mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnSymbolTickMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Streams real-time trade events (orders, positions, deals changes).
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onTrade(mt5_term_api.Mt5TermApiSubscriptions.OnTradeRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnTradeReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnTradeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Streams real-time position profit updates at the specified timer interval.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onPositionProfit(mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnPositionProfitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Streams real-time position and pending order ticket changes.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onPositionsAndPendingOrdersTickets(mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnPositionsAndPendingOrdersTicketsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Streams real-time trade transaction events (order add/update/delete, deal add, position changes).
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public void onTradeTransaction(mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnTradeTransactionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SubscriptionServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SubscriptionServiceBlockingStub> {
    private SubscriptionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscriptionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SubscriptionServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Streams real-time symbol tick data for the specified symbols.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * [DefaultValues]
     * {
     *   "symbolNames": "EURUSD,BTCUSD"
     * }
     * </pre>
     */
    public java.util.Iterator<mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickReply> onSymbolTick(
        mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnSymbolTickMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Streams real-time trade events (orders, positions, deals changes).
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public java.util.Iterator<mt5_term_api.Mt5TermApiSubscriptions.OnTradeReply> onTrade(
        mt5_term_api.Mt5TermApiSubscriptions.OnTradeRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnTradeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Streams real-time position profit updates at the specified timer interval.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public java.util.Iterator<mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitReply> onPositionProfit(
        mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnPositionProfitMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Streams real-time position and pending order ticket changes.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public java.util.Iterator<mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply> onPositionsAndPendingOrdersTickets(
        mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnPositionsAndPendingOrdersTicketsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Streams real-time trade transaction events (order add/update/delete, deal add, position changes).
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * Swagger does not support streaming — use /subscription-stream interactive viewer.
     * </pre>
     */
    public java.util.Iterator<mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionReply> onTradeTransaction(
        mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnTradeTransactionMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SubscriptionServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SubscriptionServiceFutureStub> {
    private SubscriptionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SubscriptionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SubscriptionServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_ON_SYMBOL_TICK = 0;
  private static final int METHODID_ON_TRADE = 1;
  private static final int METHODID_ON_POSITION_PROFIT = 2;
  private static final int METHODID_ON_POSITIONS_AND_PENDING_ORDERS_TICKETS = 3;
  private static final int METHODID_ON_TRADE_TRANSACTION = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SubscriptionServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SubscriptionServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ON_SYMBOL_TICK:
          serviceImpl.onSymbolTick((mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnSymbolTickReply>) responseObserver);
          break;
        case METHODID_ON_TRADE:
          serviceImpl.onTrade((mt5_term_api.Mt5TermApiSubscriptions.OnTradeRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnTradeReply>) responseObserver);
          break;
        case METHODID_ON_POSITION_PROFIT:
          serviceImpl.onPositionProfit((mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnPositionProfitReply>) responseObserver);
          break;
        case METHODID_ON_POSITIONS_AND_PENDING_ORDERS_TICKETS:
          serviceImpl.onPositionsAndPendingOrdersTickets((mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnPositionsAndPendingOrdersTicketsReply>) responseObserver);
          break;
        case METHODID_ON_TRADE_TRANSACTION:
          serviceImpl.onTradeTransaction((mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiSubscriptions.OnTradeTransactionReply>) responseObserver);
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

  private static abstract class SubscriptionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SubscriptionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiSubscriptions.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SubscriptionService");
    }
  }

  private static final class SubscriptionServiceFileDescriptorSupplier
      extends SubscriptionServiceBaseDescriptorSupplier {
    SubscriptionServiceFileDescriptorSupplier() {}
  }

  private static final class SubscriptionServiceMethodDescriptorSupplier
      extends SubscriptionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SubscriptionServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (SubscriptionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SubscriptionServiceFileDescriptorSupplier())
              .addMethod(getOnSymbolTickMethod())
              .addMethod(getOnTradeMethod())
              .addMethod(getOnPositionProfitMethod())
              .addMethod(getOnPositionsAndPendingOrdersTicketsMethod())
              .addMethod(getOnTradeTransactionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
