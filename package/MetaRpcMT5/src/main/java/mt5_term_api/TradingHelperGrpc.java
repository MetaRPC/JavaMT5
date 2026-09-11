package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-trading-helper.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TradingHelperGrpc {

  private TradingHelperGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.TradingHelper";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest,
      mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply> getOrderSendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrderSend",
      requestType = mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest.class,
      responseType = mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest,
      mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply> getOrderSendMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest, mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply> getOrderSendMethod;
    if ((getOrderSendMethod = TradingHelperGrpc.getOrderSendMethod) == null) {
      synchronized (TradingHelperGrpc.class) {
        if ((getOrderSendMethod = TradingHelperGrpc.getOrderSendMethod) == null) {
          TradingHelperGrpc.getOrderSendMethod = getOrderSendMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest, mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrderSend"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradingHelperMethodDescriptorSupplier("OrderSend"))
              .build();
        }
      }
    }
    return getOrderSendMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest,
      mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply> getOrderModifyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrderModify",
      requestType = mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest.class,
      responseType = mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest,
      mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply> getOrderModifyMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest, mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply> getOrderModifyMethod;
    if ((getOrderModifyMethod = TradingHelperGrpc.getOrderModifyMethod) == null) {
      synchronized (TradingHelperGrpc.class) {
        if ((getOrderModifyMethod = TradingHelperGrpc.getOrderModifyMethod) == null) {
          TradingHelperGrpc.getOrderModifyMethod = getOrderModifyMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest, mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrderModify"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradingHelperMethodDescriptorSupplier("OrderModify"))
              .build();
        }
      }
    }
    return getOrderModifyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest,
      mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply> getOrderCloseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrderClose",
      requestType = mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest.class,
      responseType = mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest,
      mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply> getOrderCloseMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest, mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply> getOrderCloseMethod;
    if ((getOrderCloseMethod = TradingHelperGrpc.getOrderCloseMethod) == null) {
      synchronized (TradingHelperGrpc.class) {
        if ((getOrderCloseMethod = TradingHelperGrpc.getOrderCloseMethod) == null) {
          TradingHelperGrpc.getOrderCloseMethod = getOrderCloseMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest, mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrderClose"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradingHelperMethodDescriptorSupplier("OrderClose"))
              .build();
        }
      }
    }
    return getOrderCloseMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TradingHelperStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradingHelperStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradingHelperStub>() {
        @java.lang.Override
        public TradingHelperStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradingHelperStub(channel, callOptions);
        }
      };
    return TradingHelperStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TradingHelperBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradingHelperBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradingHelperBlockingStub>() {
        @java.lang.Override
        public TradingHelperBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradingHelperBlockingStub(channel, callOptions);
        }
      };
    return TradingHelperBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TradingHelperFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradingHelperFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradingHelperFutureStub>() {
        @java.lang.Override
        public TradingHelperFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradingHelperFutureStub(channel, callOptions);
        }
      };
    return TradingHelperFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TradingHelperImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Send market or pending order
     * </pre>
     */
    public void orderSend(mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrderSendMethod(), responseObserver);
    }

    /**
     * <pre>
     * Modify market or pending order
     * </pre>
     */
    public void orderModify(mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrderModifyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Close market or pending order
     * </pre>
     */
    public void orderClose(mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrderCloseMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getOrderSendMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest,
                mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply>(
                  this, METHODID_ORDER_SEND)))
          .addMethod(
            getOrderModifyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest,
                mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply>(
                  this, METHODID_ORDER_MODIFY)))
          .addMethod(
            getOrderCloseMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest,
                mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply>(
                  this, METHODID_ORDER_CLOSE)))
          .build();
    }
  }

  /**
   */
  public static final class TradingHelperStub extends io.grpc.stub.AbstractAsyncStub<TradingHelperStub> {
    private TradingHelperStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradingHelperStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradingHelperStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send market or pending order
     * </pre>
     */
    public void orderSend(mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrderSendMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Modify market or pending order
     * </pre>
     */
    public void orderModify(mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrderModifyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Close market or pending order
     * </pre>
     */
    public void orderClose(mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrderCloseMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TradingHelperBlockingStub extends io.grpc.stub.AbstractBlockingStub<TradingHelperBlockingStub> {
    private TradingHelperBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradingHelperBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradingHelperBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send market or pending order
     * </pre>
     */
    public mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply orderSend(mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrderSendMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Modify market or pending order
     * </pre>
     */
    public mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply orderModify(mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrderModifyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Close market or pending order
     * </pre>
     */
    public mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply orderClose(mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrderCloseMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TradingHelperFutureStub extends io.grpc.stub.AbstractFutureStub<TradingHelperFutureStub> {
    private TradingHelperFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradingHelperFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradingHelperFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Send market or pending order
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply> orderSend(
        mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrderSendMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Modify market or pending order
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply> orderModify(
        mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrderModifyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Close market or pending order
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply> orderClose(
        mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrderCloseMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ORDER_SEND = 0;
  private static final int METHODID_ORDER_MODIFY = 1;
  private static final int METHODID_ORDER_CLOSE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TradingHelperImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TradingHelperImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ORDER_SEND:
          serviceImpl.orderSend((mt5_term_api.Mt5TermApiTradingHelper.OrderSendRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradingHelper.OrderSendReply>) responseObserver);
          break;
        case METHODID_ORDER_MODIFY:
          serviceImpl.orderModify((mt5_term_api.Mt5TermApiTradingHelper.OrderModifyRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradingHelper.OrderModifyReply>) responseObserver);
          break;
        case METHODID_ORDER_CLOSE:
          serviceImpl.orderClose((mt5_term_api.Mt5TermApiTradingHelper.OrderCloseRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradingHelper.OrderCloseReply>) responseObserver);
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

  private static abstract class TradingHelperBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TradingHelperBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiTradingHelper.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TradingHelper");
    }
  }

  private static final class TradingHelperFileDescriptorSupplier
      extends TradingHelperBaseDescriptorSupplier {
    TradingHelperFileDescriptorSupplier() {}
  }

  private static final class TradingHelperMethodDescriptorSupplier
      extends TradingHelperBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TradingHelperMethodDescriptorSupplier(String methodName) {
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
      synchronized (TradingHelperGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TradingHelperFileDescriptorSupplier())
              .addMethod(getOrderSendMethod())
              .addMethod(getOrderModifyMethod())
              .addMethod(getOrderCloseMethod())
              .build();
        }
      }
    }
    return result;
  }
}
