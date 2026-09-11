package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-account-helper.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AccountHelperGrpc {

  private AccountHelperGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.AccountHelper";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest,
      mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply> getAccountSummaryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AccountSummary",
      requestType = mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest.class,
      responseType = mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest,
      mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply> getAccountSummaryMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest, mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply> getAccountSummaryMethod;
    if ((getAccountSummaryMethod = AccountHelperGrpc.getAccountSummaryMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getAccountSummaryMethod = AccountHelperGrpc.getAccountSummaryMethod) == null) {
          AccountHelperGrpc.getAccountSummaryMethod = getAccountSummaryMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest, mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AccountSummary"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("AccountSummary"))
              .build();
        }
      }
    }
    return getAccountSummaryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest,
      mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply> getOrderHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OrderHistory",
      requestType = mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest.class,
      responseType = mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest,
      mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply> getOrderHistoryMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest, mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply> getOrderHistoryMethod;
    if ((getOrderHistoryMethod = AccountHelperGrpc.getOrderHistoryMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getOrderHistoryMethod = AccountHelperGrpc.getOrderHistoryMethod) == null) {
          AccountHelperGrpc.getOrderHistoryMethod = getOrderHistoryMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest, mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OrderHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("OrderHistory"))
              .build();
        }
      }
    }
    return getOrderHistoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest,
      mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply> getOpenedOrdersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OpenedOrders",
      requestType = mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest.class,
      responseType = mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest,
      mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply> getOpenedOrdersMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest, mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply> getOpenedOrdersMethod;
    if ((getOpenedOrdersMethod = AccountHelperGrpc.getOpenedOrdersMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getOpenedOrdersMethod = AccountHelperGrpc.getOpenedOrdersMethod) == null) {
          AccountHelperGrpc.getOpenedOrdersMethod = getOpenedOrdersMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest, mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OpenedOrders"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("OpenedOrders"))
              .build();
        }
      }
    }
    return getOpenedOrdersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest,
      mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply> getOpenedOrdersTicketsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OpenedOrdersTickets",
      requestType = mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest.class,
      responseType = mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest,
      mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply> getOpenedOrdersTicketsMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest, mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply> getOpenedOrdersTicketsMethod;
    if ((getOpenedOrdersTicketsMethod = AccountHelperGrpc.getOpenedOrdersTicketsMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getOpenedOrdersTicketsMethod = AccountHelperGrpc.getOpenedOrdersTicketsMethod) == null) {
          AccountHelperGrpc.getOpenedOrdersTicketsMethod = getOpenedOrdersTicketsMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest, mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OpenedOrdersTickets"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("OpenedOrdersTickets"))
              .build();
        }
      }
    }
    return getOpenedOrdersTicketsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest,
      mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply> getSymbolParamsManyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolParamsMany",
      requestType = mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest.class,
      responseType = mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest,
      mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply> getSymbolParamsManyMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest, mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply> getSymbolParamsManyMethod;
    if ((getSymbolParamsManyMethod = AccountHelperGrpc.getSymbolParamsManyMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getSymbolParamsManyMethod = AccountHelperGrpc.getSymbolParamsManyMethod) == null) {
          AccountHelperGrpc.getSymbolParamsManyMethod = getSymbolParamsManyMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest, mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolParamsMany"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("SymbolParamsMany"))
              .build();
        }
      }
    }
    return getSymbolParamsManyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest,
      mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply> getTickValueWithSizeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TickValueWithSize",
      requestType = mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest.class,
      responseType = mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest,
      mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply> getTickValueWithSizeMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest, mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply> getTickValueWithSizeMethod;
    if ((getTickValueWithSizeMethod = AccountHelperGrpc.getTickValueWithSizeMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getTickValueWithSizeMethod = AccountHelperGrpc.getTickValueWithSizeMethod) == null) {
          AccountHelperGrpc.getTickValueWithSizeMethod = getTickValueWithSizeMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest, mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TickValueWithSize"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("TickValueWithSize"))
              .build();
        }
      }
    }
    return getTickValueWithSizeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest,
      mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply> getPositionsHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PositionsHistory",
      requestType = mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest.class,
      responseType = mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest,
      mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply> getPositionsHistoryMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest, mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply> getPositionsHistoryMethod;
    if ((getPositionsHistoryMethod = AccountHelperGrpc.getPositionsHistoryMethod) == null) {
      synchronized (AccountHelperGrpc.class) {
        if ((getPositionsHistoryMethod = AccountHelperGrpc.getPositionsHistoryMethod) == null) {
          AccountHelperGrpc.getPositionsHistoryMethod = getPositionsHistoryMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest, mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PositionsHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountHelperMethodDescriptorSupplier("PositionsHistory"))
              .build();
        }
      }
    }
    return getPositionsHistoryMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AccountHelperStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountHelperStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountHelperStub>() {
        @java.lang.Override
        public AccountHelperStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountHelperStub(channel, callOptions);
        }
      };
    return AccountHelperStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AccountHelperBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountHelperBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountHelperBlockingStub>() {
        @java.lang.Override
        public AccountHelperBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountHelperBlockingStub(channel, callOptions);
        }
      };
    return AccountHelperBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AccountHelperFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountHelperFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountHelperFutureStub>() {
        @java.lang.Override
        public AccountHelperFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountHelperFutureStub(channel, callOptions);
        }
      };
    return AccountHelperFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AccountHelperImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Some information about account
     * </pre>
     */
    public void accountSummary(mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAccountSummaryMethod(), responseObserver);
    }

    /**
     * <pre>
     * Orders History
     * [DefaultValues]
     * {
     *   "pageNumber": "0",
     *   "itemsPerPage": "0",
     * }  
     * </pre>
     */
    public void orderHistory(mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOrderHistoryMethod(), responseObserver);
    }

    /**
     * <pre>
     * Pending orders and opened positions
     * </pre>
     */
    public void openedOrders(mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOpenedOrdersMethod(), responseObserver);
    }

    /**
     * <pre>
     * Just opened order tickets
     * </pre>
     */
    public void openedOrdersTickets(mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOpenedOrdersTicketsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Full information about symbol and his group for several symbols
     * </pre>
     */
    public void symbolParamsMany(mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolParamsManyMethod(), responseObserver);
    }

    /**
     * <pre>
     * Tick value as array
     * </pre>
     */
    public void tickValueWithSize(mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTickValueWithSizeMethod(), responseObserver);
    }

    /**
     * <pre>
     * History positions
     * </pre>
     */
    public void positionsHistory(mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPositionsHistoryMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAccountSummaryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest,
                mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply>(
                  this, METHODID_ACCOUNT_SUMMARY)))
          .addMethod(
            getOrderHistoryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest,
                mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply>(
                  this, METHODID_ORDER_HISTORY)))
          .addMethod(
            getOpenedOrdersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest,
                mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply>(
                  this, METHODID_OPENED_ORDERS)))
          .addMethod(
            getOpenedOrdersTicketsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest,
                mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply>(
                  this, METHODID_OPENED_ORDERS_TICKETS)))
          .addMethod(
            getSymbolParamsManyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest,
                mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply>(
                  this, METHODID_SYMBOL_PARAMS_MANY)))
          .addMethod(
            getTickValueWithSizeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest,
                mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply>(
                  this, METHODID_TICK_VALUE_WITH_SIZE)))
          .addMethod(
            getPositionsHistoryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest,
                mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply>(
                  this, METHODID_POSITIONS_HISTORY)))
          .build();
    }
  }

  /**
   */
  public static final class AccountHelperStub extends io.grpc.stub.AbstractAsyncStub<AccountHelperStub> {
    private AccountHelperStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountHelperStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountHelperStub(channel, callOptions);
    }

    /**
     * <pre>
     * Some information about account
     * </pre>
     */
    public void accountSummary(mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAccountSummaryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Orders History
     * [DefaultValues]
     * {
     *   "pageNumber": "0",
     *   "itemsPerPage": "0",
     * }  
     * </pre>
     */
    public void orderHistory(mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOrderHistoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Pending orders and opened positions
     * </pre>
     */
    public void openedOrders(mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOpenedOrdersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Just opened order tickets
     * </pre>
     */
    public void openedOrdersTickets(mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOpenedOrdersTicketsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Full information about symbol and his group for several symbols
     * </pre>
     */
    public void symbolParamsMany(mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolParamsManyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Tick value as array
     * </pre>
     */
    public void tickValueWithSize(mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTickValueWithSizeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * History positions
     * </pre>
     */
    public void positionsHistory(mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPositionsHistoryMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AccountHelperBlockingStub extends io.grpc.stub.AbstractBlockingStub<AccountHelperBlockingStub> {
    private AccountHelperBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountHelperBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountHelperBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Some information about account
     * </pre>
     */
    public mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply accountSummary(mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAccountSummaryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Orders History
     * [DefaultValues]
     * {
     *   "pageNumber": "0",
     *   "itemsPerPage": "0",
     * }  
     * </pre>
     */
    public mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply orderHistory(mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOrderHistoryMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Pending orders and opened positions
     * </pre>
     */
    public mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply openedOrders(mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOpenedOrdersMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Just opened order tickets
     * </pre>
     */
    public mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply openedOrdersTickets(mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOpenedOrdersTicketsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Full information about symbol and his group for several symbols
     * </pre>
     */
    public mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply symbolParamsMany(mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolParamsManyMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Tick value as array
     * </pre>
     */
    public mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply tickValueWithSize(mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTickValueWithSizeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * History positions
     * </pre>
     */
    public mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply positionsHistory(mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPositionsHistoryMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AccountHelperFutureStub extends io.grpc.stub.AbstractFutureStub<AccountHelperFutureStub> {
    private AccountHelperFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountHelperFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountHelperFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Some information about account
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply> accountSummary(
        mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAccountSummaryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Orders History
     * [DefaultValues]
     * {
     *   "pageNumber": "0",
     *   "itemsPerPage": "0",
     * }  
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply> orderHistory(
        mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOrderHistoryMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Pending orders and opened positions
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply> openedOrders(
        mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOpenedOrdersMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Just opened order tickets
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply> openedOrdersTickets(
        mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOpenedOrdersTicketsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Full information about symbol and his group for several symbols
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply> symbolParamsMany(
        mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolParamsManyMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Tick value as array
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply> tickValueWithSize(
        mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTickValueWithSizeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * History positions
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply> positionsHistory(
        mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPositionsHistoryMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ACCOUNT_SUMMARY = 0;
  private static final int METHODID_ORDER_HISTORY = 1;
  private static final int METHODID_OPENED_ORDERS = 2;
  private static final int METHODID_OPENED_ORDERS_TICKETS = 3;
  private static final int METHODID_SYMBOL_PARAMS_MANY = 4;
  private static final int METHODID_TICK_VALUE_WITH_SIZE = 5;
  private static final int METHODID_POSITIONS_HISTORY = 6;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AccountHelperImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AccountHelperImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ACCOUNT_SUMMARY:
          serviceImpl.accountSummary((mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.AccountSummaryReply>) responseObserver);
          break;
        case METHODID_ORDER_HISTORY:
          serviceImpl.orderHistory((mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.OrderHistoryReply>) responseObserver);
          break;
        case METHODID_OPENED_ORDERS:
          serviceImpl.openedOrders((mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersReply>) responseObserver);
          break;
        case METHODID_OPENED_ORDERS_TICKETS:
          serviceImpl.openedOrdersTickets((mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.OpenedOrdersTicketsReply>) responseObserver);
          break;
        case METHODID_SYMBOL_PARAMS_MANY:
          serviceImpl.symbolParamsMany((mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.SymbolParamsManyReply>) responseObserver);
          break;
        case METHODID_TICK_VALUE_WITH_SIZE:
          serviceImpl.tickValueWithSize((mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.TickValueWithSizeReply>) responseObserver);
          break;
        case METHODID_POSITIONS_HISTORY:
          serviceImpl.positionsHistory((mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountHelper.PositionsHistoryReply>) responseObserver);
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

  private static abstract class AccountHelperBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AccountHelperBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiAccountHelper.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AccountHelper");
    }
  }

  private static final class AccountHelperFileDescriptorSupplier
      extends AccountHelperBaseDescriptorSupplier {
    AccountHelperFileDescriptorSupplier() {}
  }

  private static final class AccountHelperMethodDescriptorSupplier
      extends AccountHelperBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AccountHelperMethodDescriptorSupplier(String methodName) {
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
      synchronized (AccountHelperGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AccountHelperFileDescriptorSupplier())
              .addMethod(getAccountSummaryMethod())
              .addMethod(getOrderHistoryMethod())
              .addMethod(getOpenedOrdersMethod())
              .addMethod(getOpenedOrdersTicketsMethod())
              .addMethod(getSymbolParamsManyMethod())
              .addMethod(getTickValueWithSizeMethod())
              .addMethod(getPositionsHistoryMethod())
              .build();
        }
      }
    }
    return result;
  }
}
