package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-account-information.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AccountInformationGrpc {

  private AccountInformationGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.AccountInformation";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest,
      mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply> getAccountInfoDoubleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AccountInfoDouble",
      requestType = mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest.class,
      responseType = mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest,
      mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply> getAccountInfoDoubleMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest, mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply> getAccountInfoDoubleMethod;
    if ((getAccountInfoDoubleMethod = AccountInformationGrpc.getAccountInfoDoubleMethod) == null) {
      synchronized (AccountInformationGrpc.class) {
        if ((getAccountInfoDoubleMethod = AccountInformationGrpc.getAccountInfoDoubleMethod) == null) {
          AccountInformationGrpc.getAccountInfoDoubleMethod = getAccountInfoDoubleMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest, mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AccountInfoDouble"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountInformationMethodDescriptorSupplier("AccountInfoDouble"))
              .build();
        }
      }
    }
    return getAccountInfoDoubleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest,
      mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply> getAccountInfoIntegerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AccountInfoInteger",
      requestType = mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest.class,
      responseType = mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest,
      mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply> getAccountInfoIntegerMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest, mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply> getAccountInfoIntegerMethod;
    if ((getAccountInfoIntegerMethod = AccountInformationGrpc.getAccountInfoIntegerMethod) == null) {
      synchronized (AccountInformationGrpc.class) {
        if ((getAccountInfoIntegerMethod = AccountInformationGrpc.getAccountInfoIntegerMethod) == null) {
          AccountInformationGrpc.getAccountInfoIntegerMethod = getAccountInfoIntegerMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest, mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AccountInfoInteger"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountInformationMethodDescriptorSupplier("AccountInfoInteger"))
              .build();
        }
      }
    }
    return getAccountInfoIntegerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest,
      mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply> getAccountInfoStringMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AccountInfoString",
      requestType = mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest.class,
      responseType = mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest,
      mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply> getAccountInfoStringMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest, mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply> getAccountInfoStringMethod;
    if ((getAccountInfoStringMethod = AccountInformationGrpc.getAccountInfoStringMethod) == null) {
      synchronized (AccountInformationGrpc.class) {
        if ((getAccountInfoStringMethod = AccountInformationGrpc.getAccountInfoStringMethod) == null) {
          AccountInformationGrpc.getAccountInfoStringMethod = getAccountInfoStringMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest, mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AccountInfoString"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply.getDefaultInstance()))
              .setSchemaDescriptor(new AccountInformationMethodDescriptorSupplier("AccountInfoString"))
              .build();
        }
      }
    }
    return getAccountInfoStringMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AccountInformationStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountInformationStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountInformationStub>() {
        @java.lang.Override
        public AccountInformationStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountInformationStub(channel, callOptions);
        }
      };
    return AccountInformationStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AccountInformationBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountInformationBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountInformationBlockingStub>() {
        @java.lang.Override
        public AccountInformationBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountInformationBlockingStub(channel, callOptions);
        }
      };
    return AccountInformationBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AccountInformationFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccountInformationFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccountInformationFutureStub>() {
        @java.lang.Override
        public AccountInformationFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccountInformationFutureStub(channel, callOptions);
        }
      };
    return AccountInformationFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class AccountInformationImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Returns the double value of the corresponding account property
     * https://www.mql5.com/en/docs/account/accountinfodouble
     * </pre>
     */
    public void accountInfoDouble(mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAccountInfoDoubleMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns the value of the properties of the account
     * https://www.mql5.com/en/docs/account/accountinfointeger
     * </pre>
     */
    public void accountInfoInteger(mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAccountInfoIntegerMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns the value of the appropriate account property
     * https://www.mql5.com/en/docs/account/accountinfostring
     * </pre>
     */
    public void accountInfoString(mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAccountInfoStringMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAccountInfoDoubleMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest,
                mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply>(
                  this, METHODID_ACCOUNT_INFO_DOUBLE)))
          .addMethod(
            getAccountInfoIntegerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest,
                mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply>(
                  this, METHODID_ACCOUNT_INFO_INTEGER)))
          .addMethod(
            getAccountInfoStringMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest,
                mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply>(
                  this, METHODID_ACCOUNT_INFO_STRING)))
          .build();
    }
  }

  /**
   */
  public static final class AccountInformationStub extends io.grpc.stub.AbstractAsyncStub<AccountInformationStub> {
    private AccountInformationStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountInformationStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountInformationStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns the double value of the corresponding account property
     * https://www.mql5.com/en/docs/account/accountinfodouble
     * </pre>
     */
    public void accountInfoDouble(mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAccountInfoDoubleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns the value of the properties of the account
     * https://www.mql5.com/en/docs/account/accountinfointeger
     * </pre>
     */
    public void accountInfoInteger(mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAccountInfoIntegerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns the value of the appropriate account property
     * https://www.mql5.com/en/docs/account/accountinfostring
     * </pre>
     */
    public void accountInfoString(mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAccountInfoStringMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class AccountInformationBlockingStub extends io.grpc.stub.AbstractBlockingStub<AccountInformationBlockingStub> {
    private AccountInformationBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountInformationBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountInformationBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns the double value of the corresponding account property
     * https://www.mql5.com/en/docs/account/accountinfodouble
     * </pre>
     */
    public mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply accountInfoDouble(mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAccountInfoDoubleMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns the value of the properties of the account
     * https://www.mql5.com/en/docs/account/accountinfointeger
     * </pre>
     */
    public mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply accountInfoInteger(mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAccountInfoIntegerMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns the value of the appropriate account property
     * https://www.mql5.com/en/docs/account/accountinfostring
     * </pre>
     */
    public mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply accountInfoString(mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAccountInfoStringMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class AccountInformationFutureStub extends io.grpc.stub.AbstractFutureStub<AccountInformationFutureStub> {
    private AccountInformationFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccountInformationFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccountInformationFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns the double value of the corresponding account property
     * https://www.mql5.com/en/docs/account/accountinfodouble
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply> accountInfoDouble(
        mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAccountInfoDoubleMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns the value of the properties of the account
     * https://www.mql5.com/en/docs/account/accountinfointeger
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply> accountInfoInteger(
        mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAccountInfoIntegerMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns the value of the appropriate account property
     * https://www.mql5.com/en/docs/account/accountinfostring
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply> accountInfoString(
        mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAccountInfoStringMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ACCOUNT_INFO_DOUBLE = 0;
  private static final int METHODID_ACCOUNT_INFO_INTEGER = 1;
  private static final int METHODID_ACCOUNT_INFO_STRING = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AccountInformationImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(AccountInformationImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ACCOUNT_INFO_DOUBLE:
          serviceImpl.accountInfoDouble((mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoDoubleReply>) responseObserver);
          break;
        case METHODID_ACCOUNT_INFO_INTEGER:
          serviceImpl.accountInfoInteger((mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoIntegerReply>) responseObserver);
          break;
        case METHODID_ACCOUNT_INFO_STRING:
          serviceImpl.accountInfoString((mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiAccountInformation.AccountInfoStringReply>) responseObserver);
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

  private static abstract class AccountInformationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AccountInformationBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiAccountInformation.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AccountInformation");
    }
  }

  private static final class AccountInformationFileDescriptorSupplier
      extends AccountInformationBaseDescriptorSupplier {
    AccountInformationFileDescriptorSupplier() {}
  }

  private static final class AccountInformationMethodDescriptorSupplier
      extends AccountInformationBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    AccountInformationMethodDescriptorSupplier(String methodName) {
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
      synchronized (AccountInformationGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AccountInformationFileDescriptorSupplier())
              .addMethod(getAccountInfoDoubleMethod())
              .addMethod(getAccountInfoIntegerMethod())
              .addMethod(getAccountInfoStringMethod())
              .build();
        }
      }
    }
    return result;
  }
}
