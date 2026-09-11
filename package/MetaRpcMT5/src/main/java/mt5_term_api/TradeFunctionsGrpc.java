package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-trade-functions.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TradeFunctionsGrpc {

  private TradeFunctionsGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.TradeFunctions";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest,
      mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply> getOrderCalcMarginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrderCalcMargin",
      requestType = mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest.class,
      responseType = mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest,
      mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply> getOrderCalcMarginMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest, mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply> getOrderCalcMarginMethod;
    if ((getOrderCalcMarginMethod = TradeFunctionsGrpc.getOrderCalcMarginMethod) == null) {
      synchronized (TradeFunctionsGrpc.class) {
        if ((getOrderCalcMarginMethod = TradeFunctionsGrpc.getOrderCalcMarginMethod) == null) {
          TradeFunctionsGrpc.getOrderCalcMarginMethod = getOrderCalcMarginMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest, mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrderCalcMargin"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradeFunctionsMethodDescriptorSupplier("OrderCalcMargin"))
              .build();
        }
      }
    }
    return getOrderCalcMarginMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest,
      mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply> getOrderCalcProfitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrderCalcProfit",
      requestType = mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest.class,
      responseType = mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest,
      mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply> getOrderCalcProfitMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest, mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply> getOrderCalcProfitMethod;
    if ((getOrderCalcProfitMethod = TradeFunctionsGrpc.getOrderCalcProfitMethod) == null) {
      synchronized (TradeFunctionsGrpc.class) {
        if ((getOrderCalcProfitMethod = TradeFunctionsGrpc.getOrderCalcProfitMethod) == null) {
          TradeFunctionsGrpc.getOrderCalcProfitMethod = getOrderCalcProfitMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest, mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrderCalcProfit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradeFunctionsMethodDescriptorSupplier("OrderCalcProfit"))
              .build();
        }
      }
    }
    return getOrderCalcProfitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest,
      mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply> getOrderCheckMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrderCheck",
      requestType = mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest.class,
      responseType = mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest,
      mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply> getOrderCheckMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest, mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply> getOrderCheckMethod;
    if ((getOrderCheckMethod = TradeFunctionsGrpc.getOrderCheckMethod) == null) {
      synchronized (TradeFunctionsGrpc.class) {
        if ((getOrderCheckMethod = TradeFunctionsGrpc.getOrderCheckMethod) == null) {
          TradeFunctionsGrpc.getOrderCheckMethod = getOrderCheckMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest, mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrderCheck"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradeFunctionsMethodDescriptorSupplier("OrderCheck"))
              .build();
        }
      }
    }
    return getOrderCheckMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply> getPositionsTotalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PositionsTotal",
      requestType = com.google.protobuf.Empty.class,
      responseType = mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply> getPositionsTotalMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply> getPositionsTotalMethod;
    if ((getPositionsTotalMethod = TradeFunctionsGrpc.getPositionsTotalMethod) == null) {
      synchronized (TradeFunctionsGrpc.class) {
        if ((getPositionsTotalMethod = TradeFunctionsGrpc.getPositionsTotalMethod) == null) {
          TradeFunctionsGrpc.getPositionsTotalMethod = getPositionsTotalMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PositionsTotal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply.getDefaultInstance()))
              .setSchemaDescriptor(new TradeFunctionsMethodDescriptorSupplier("PositionsTotal"))
              .build();
        }
      }
    }
    return getPositionsTotalMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TradeFunctionsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradeFunctionsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradeFunctionsStub>() {
        @java.lang.Override
        public TradeFunctionsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradeFunctionsStub(channel, callOptions);
        }
      };
    return TradeFunctionsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TradeFunctionsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradeFunctionsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradeFunctionsBlockingStub>() {
        @java.lang.Override
        public TradeFunctionsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradeFunctionsBlockingStub(channel, callOptions);
        }
      };
    return TradeFunctionsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TradeFunctionsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TradeFunctionsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TradeFunctionsFutureStub>() {
        @java.lang.Override
        public TradeFunctionsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TradeFunctionsFutureStub(channel, callOptions);
        }
      };
    return TradeFunctionsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TradeFunctionsImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * The function calculates the margin required for the specified order type, on the current account, in the current market environment not taking into account current pending orders and open positions. It allows the evaluation of margin for the trade operation planned. The value is returned in the account currency +
     * https://www.mql5.com/en/docs/trading/ordercalcmargin
     * </pre>
     */
    public void orderCalcMargin(mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrderCalcMarginMethod(), responseObserver);
    }

    /**
     * <pre>
     * The function calculates the profit for the current account, in the current market conditions, based on the parameters passed. The function is used for pre-evaluation of the result of a trade operation. The value is returned in the account currency. + 
     * https://www.mql5.com/en/docs/trading/ordercalcprofit
     * </pre>
     */
    public void orderCalcProfit(mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrderCalcProfitMethod(), responseObserver);
    }

    /**
     * <pre>
     * Checks if there are enough money to execute a required Trade Operation Type +
     * https://www.mql5.com/en/docs/trading/ordercheck
     * </pre>
     */
    public void orderCheck(mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrderCheckMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns the number of open positions +
     * https://www.mql5.com/en/docs/trading/positionstotal
     * </pre>
     */
    public void positionsTotal(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPositionsTotalMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getOrderCalcMarginMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest,
                mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply>(
                  this, METHODID_ORDER_CALC_MARGIN)))
          .addMethod(
            getOrderCalcProfitMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest,
                mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply>(
                  this, METHODID_ORDER_CALC_PROFIT)))
          .addMethod(
            getOrderCheckMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest,
                mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply>(
                  this, METHODID_ORDER_CHECK)))
          .addMethod(
            getPositionsTotalMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply>(
                  this, METHODID_POSITIONS_TOTAL)))
          .build();
    }
  }

  /**
   */
  public static final class TradeFunctionsStub extends io.grpc.stub.AbstractAsyncStub<TradeFunctionsStub> {
    private TradeFunctionsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradeFunctionsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradeFunctionsStub(channel, callOptions);
    }

    /**
     * <pre>
     * The function calculates the margin required for the specified order type, on the current account, in the current market environment not taking into account current pending orders and open positions. It allows the evaluation of margin for the trade operation planned. The value is returned in the account currency +
     * https://www.mql5.com/en/docs/trading/ordercalcmargin
     * </pre>
     */
    public void orderCalcMargin(mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrderCalcMarginMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * The function calculates the profit for the current account, in the current market conditions, based on the parameters passed. The function is used for pre-evaluation of the result of a trade operation. The value is returned in the account currency. + 
     * https://www.mql5.com/en/docs/trading/ordercalcprofit
     * </pre>
     */
    public void orderCalcProfit(mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrderCalcProfitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Checks if there are enough money to execute a required Trade Operation Type +
     * https://www.mql5.com/en/docs/trading/ordercheck
     * </pre>
     */
    public void orderCheck(mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrderCheckMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns the number of open positions +
     * https://www.mql5.com/en/docs/trading/positionstotal
     * </pre>
     */
    public void positionsTotal(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPositionsTotalMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TradeFunctionsBlockingStub extends io.grpc.stub.AbstractBlockingStub<TradeFunctionsBlockingStub> {
    private TradeFunctionsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradeFunctionsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradeFunctionsBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * The function calculates the margin required for the specified order type, on the current account, in the current market environment not taking into account current pending orders and open positions. It allows the evaluation of margin for the trade operation planned. The value is returned in the account currency +
     * https://www.mql5.com/en/docs/trading/ordercalcmargin
     * </pre>
     */
    public mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply orderCalcMargin(mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrderCalcMarginMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * The function calculates the profit for the current account, in the current market conditions, based on the parameters passed. The function is used for pre-evaluation of the result of a trade operation. The value is returned in the account currency. + 
     * https://www.mql5.com/en/docs/trading/ordercalcprofit
     * </pre>
     */
    public mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply orderCalcProfit(mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrderCalcProfitMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Checks if there are enough money to execute a required Trade Operation Type +
     * https://www.mql5.com/en/docs/trading/ordercheck
     * </pre>
     */
    public mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply orderCheck(mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrderCheckMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns the number of open positions +
     * https://www.mql5.com/en/docs/trading/positionstotal
     * </pre>
     */
    public mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply positionsTotal(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPositionsTotalMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TradeFunctionsFutureStub extends io.grpc.stub.AbstractFutureStub<TradeFunctionsFutureStub> {
    private TradeFunctionsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TradeFunctionsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TradeFunctionsFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * The function calculates the margin required for the specified order type, on the current account, in the current market environment not taking into account current pending orders and open positions. It allows the evaluation of margin for the trade operation planned. The value is returned in the account currency +
     * https://www.mql5.com/en/docs/trading/ordercalcmargin
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply> orderCalcMargin(
        mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrderCalcMarginMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * The function calculates the profit for the current account, in the current market conditions, based on the parameters passed. The function is used for pre-evaluation of the result of a trade operation. The value is returned in the account currency. + 
     * https://www.mql5.com/en/docs/trading/ordercalcprofit
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply> orderCalcProfit(
        mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrderCalcProfitMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Checks if there are enough money to execute a required Trade Operation Type +
     * https://www.mql5.com/en/docs/trading/ordercheck
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply> orderCheck(
        mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrderCheckMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns the number of open positions +
     * https://www.mql5.com/en/docs/trading/positionstotal
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply> positionsTotal(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPositionsTotalMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ORDER_CALC_MARGIN = 0;
  private static final int METHODID_ORDER_CALC_PROFIT = 1;
  private static final int METHODID_ORDER_CHECK = 2;
  private static final int METHODID_POSITIONS_TOTAL = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TradeFunctionsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TradeFunctionsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ORDER_CALC_MARGIN:
          serviceImpl.orderCalcMargin((mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcMarginReply>) responseObserver);
          break;
        case METHODID_ORDER_CALC_PROFIT:
          serviceImpl.orderCalcProfit((mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.OrderCalcProfitReply>) responseObserver);
          break;
        case METHODID_ORDER_CHECK:
          serviceImpl.orderCheck((mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.OrderCheckReply>) responseObserver);
          break;
        case METHODID_POSITIONS_TOTAL:
          serviceImpl.positionsTotal((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiTradeFunctions.PositionsTotalReply>) responseObserver);
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

  private static abstract class TradeFunctionsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TradeFunctionsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiTradeFunctions.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TradeFunctions");
    }
  }

  private static final class TradeFunctionsFileDescriptorSupplier
      extends TradeFunctionsBaseDescriptorSupplier {
    TradeFunctionsFileDescriptorSupplier() {}
  }

  private static final class TradeFunctionsMethodDescriptorSupplier
      extends TradeFunctionsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TradeFunctionsMethodDescriptorSupplier(String methodName) {
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
      synchronized (TradeFunctionsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TradeFunctionsFileDescriptorSupplier())
              .addMethod(getOrderCalcMarginMethod())
              .addMethod(getOrderCalcProfitMethod())
              .addMethod(getOrderCheckMethod())
              .addMethod(getPositionsTotalMethod())
              .build();
        }
      }
    }
    return result;
  }
}
