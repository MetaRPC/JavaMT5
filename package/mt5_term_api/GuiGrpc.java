package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-gui.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class GuiGrpc {

  private GuiGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.Gui";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiLoginRequest,
      mt5_term_api.Mt5TermApiGui.GuiLoginReply> getLoginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Login",
      requestType = mt5_term_api.Mt5TermApiGui.GuiLoginRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiLoginReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiLoginRequest,
      mt5_term_api.Mt5TermApiGui.GuiLoginReply> getLoginMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiLoginRequest, mt5_term_api.Mt5TermApiGui.GuiLoginReply> getLoginMethod;
    if ((getLoginMethod = GuiGrpc.getLoginMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getLoginMethod = GuiGrpc.getLoginMethod) == null) {
          GuiGrpc.getLoginMethod = getLoginMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiLoginRequest, mt5_term_api.Mt5TermApiGui.GuiLoginReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Login"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiLoginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiLoginReply.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("Login"))
              .build();
        }
      }
    }
    return getLoginMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest,
      mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply> getBrokerSearchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "BrokerSearch",
      requestType = mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest,
      mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply> getBrokerSearchMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest, mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply> getBrokerSearchMethod;
    if ((getBrokerSearchMethod = GuiGrpc.getBrokerSearchMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getBrokerSearchMethod = GuiGrpc.getBrokerSearchMethod) == null) {
          GuiGrpc.getBrokerSearchMethod = getBrokerSearchMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest, mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "BrokerSearch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("BrokerSearch"))
              .build();
        }
      }
    }
    return getBrokerSearchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiLoginExRequest,
      mt5_term_api.Mt5TermApiGui.GuiLoginExReply> getLoginExMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LoginEx",
      requestType = mt5_term_api.Mt5TermApiGui.GuiLoginExRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiLoginExReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiLoginExRequest,
      mt5_term_api.Mt5TermApiGui.GuiLoginExReply> getLoginExMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiLoginExRequest, mt5_term_api.Mt5TermApiGui.GuiLoginExReply> getLoginExMethod;
    if ((getLoginExMethod = GuiGrpc.getLoginExMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getLoginExMethod = GuiGrpc.getLoginExMethod) == null) {
          GuiGrpc.getLoginExMethod = getLoginExMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiLoginExRequest, mt5_term_api.Mt5TermApiGui.GuiLoginExReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LoginEx"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiLoginExRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiLoginExReply.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("LoginEx"))
              .build();
        }
      }
    }
    return getLoginExMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest,
      mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply> getCloseDialogsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CloseDialogs",
      requestType = mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest,
      mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply> getCloseDialogsMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest, mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply> getCloseDialogsMethod;
    if ((getCloseDialogsMethod = GuiGrpc.getCloseDialogsMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getCloseDialogsMethod = GuiGrpc.getCloseDialogsMethod) == null) {
          GuiGrpc.getCloseDialogsMethod = getCloseDialogsMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest, mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CloseDialogs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("CloseDialogs"))
              .build();
        }
      }
    }
    return getCloseDialogsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> getDemoOpenAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DemoOpenAccount",
      requestType = mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> getDemoOpenAccountMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest, mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> getDemoOpenAccountMethod;
    if ((getDemoOpenAccountMethod = GuiGrpc.getDemoOpenAccountMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getDemoOpenAccountMethod = GuiGrpc.getDemoOpenAccountMethod) == null) {
          GuiGrpc.getDemoOpenAccountMethod = getDemoOpenAccountMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest, mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DemoOpenAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("DemoOpenAccount"))
              .build();
        }
      }
    }
    return getDemoOpenAccountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage,
      mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage> getDemoOpenAccountInteractiveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DemoOpenAccountInteractive",
      requestType = mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage,
      mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage> getDemoOpenAccountInteractiveMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage, mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage> getDemoOpenAccountInteractiveMethod;
    if ((getDemoOpenAccountInteractiveMethod = GuiGrpc.getDemoOpenAccountInteractiveMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getDemoOpenAccountInteractiveMethod = GuiGrpc.getDemoOpenAccountInteractiveMethod) == null) {
          GuiGrpc.getDemoOpenAccountInteractiveMethod = getDemoOpenAccountInteractiveMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage, mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DemoOpenAccountInteractive"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("DemoOpenAccountInteractive"))
              .build();
        }
      }
    }
    return getDemoOpenAccountInteractiveMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GuiStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GuiStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GuiStub>() {
        @java.lang.Override
        public GuiStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GuiStub(channel, callOptions);
        }
      };
    return GuiStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GuiBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GuiBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GuiBlockingStub>() {
        @java.lang.Override
        public GuiBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GuiBlockingStub(channel, callOptions);
        }
      };
    return GuiBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GuiFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<GuiFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<GuiFutureStub>() {
        @java.lang.Override
        public GuiFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new GuiFutureStub(channel, callOptions);
        }
      };
    return GuiFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class GuiImplBase implements io.grpc.BindableService {

    /**
     */
    public void login(mt5_term_api.Mt5TermApiGui.GuiLoginRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiLoginReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoginMethod(), responseObserver);
    }

    /**
     */
    public void brokerSearch(mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getBrokerSearchMethod(), responseObserver);
    }

    /**
     */
    public void loginEx(mt5_term_api.Mt5TermApiGui.GuiLoginExRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiLoginExReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoginExMethod(), responseObserver);
    }

    /**
     */
    public void closeDialogs(mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCloseDialogsMethod(), responseObserver);
    }

    /**
     */
    public void demoOpenAccount(mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDemoOpenAccountMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage> demoOpenAccountInteractive(
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getDemoOpenAccountInteractiveMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLoginMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiLoginRequest,
                mt5_term_api.Mt5TermApiGui.GuiLoginReply>(
                  this, METHODID_LOGIN)))
          .addMethod(
            getBrokerSearchMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest,
                mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply>(
                  this, METHODID_BROKER_SEARCH)))
          .addMethod(
            getLoginExMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiLoginExRequest,
                mt5_term_api.Mt5TermApiGui.GuiLoginExReply>(
                  this, METHODID_LOGIN_EX)))
          .addMethod(
            getCloseDialogsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest,
                mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply>(
                  this, METHODID_CLOSE_DIALOGS)))
          .addMethod(
            getDemoOpenAccountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest,
                mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply>(
                  this, METHODID_DEMO_OPEN_ACCOUNT)))
          .addMethod(
            getDemoOpenAccountInteractiveMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage,
                mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage>(
                  this, METHODID_DEMO_OPEN_ACCOUNT_INTERACTIVE)))
          .build();
    }
  }

  /**
   */
  public static final class GuiStub extends io.grpc.stub.AbstractAsyncStub<GuiStub> {
    private GuiStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GuiStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GuiStub(channel, callOptions);
    }

    /**
     */
    public void login(mt5_term_api.Mt5TermApiGui.GuiLoginRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiLoginReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void brokerSearch(mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getBrokerSearchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void loginEx(mt5_term_api.Mt5TermApiGui.GuiLoginExRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiLoginExReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoginExMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void closeDialogs(mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCloseDialogsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void demoOpenAccount(mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDemoOpenAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage> demoOpenAccountInteractive(
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getDemoOpenAccountInteractiveMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class GuiBlockingStub extends io.grpc.stub.AbstractBlockingStub<GuiBlockingStub> {
    private GuiBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GuiBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GuiBlockingStub(channel, callOptions);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiGui.GuiLoginReply login(mt5_term_api.Mt5TermApiGui.GuiLoginRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply brokerSearch(mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getBrokerSearchMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiGui.GuiLoginExReply loginEx(mt5_term_api.Mt5TermApiGui.GuiLoginExRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginExMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply closeDialogs(mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCloseDialogsMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply demoOpenAccount(mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDemoOpenAccountMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class GuiFutureStub extends io.grpc.stub.AbstractFutureStub<GuiFutureStub> {
    private GuiFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected GuiFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new GuiFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiLoginReply> login(
        mt5_term_api.Mt5TermApiGui.GuiLoginRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoginMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply> brokerSearch(
        mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getBrokerSearchMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiLoginExReply> loginEx(
        mt5_term_api.Mt5TermApiGui.GuiLoginExRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoginExMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply> closeDialogs(
        mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCloseDialogsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> demoOpenAccount(
        mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDemoOpenAccountMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOGIN = 0;
  private static final int METHODID_BROKER_SEARCH = 1;
  private static final int METHODID_LOGIN_EX = 2;
  private static final int METHODID_CLOSE_DIALOGS = 3;
  private static final int METHODID_DEMO_OPEN_ACCOUNT = 4;
  private static final int METHODID_DEMO_OPEN_ACCOUNT_INTERACTIVE = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GuiImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GuiImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOGIN:
          serviceImpl.login((mt5_term_api.Mt5TermApiGui.GuiLoginRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiLoginReply>) responseObserver);
          break;
        case METHODID_BROKER_SEARCH:
          serviceImpl.brokerSearch((mt5_term_api.Mt5TermApiGui.GuiBrokerSearchRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiBrokerSearchReply>) responseObserver);
          break;
        case METHODID_LOGIN_EX:
          serviceImpl.loginEx((mt5_term_api.Mt5TermApiGui.GuiLoginExRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiLoginExReply>) responseObserver);
          break;
        case METHODID_CLOSE_DIALOGS:
          serviceImpl.closeDialogs((mt5_term_api.Mt5TermApiGui.GuiCloseDialogsRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiCloseDialogsReply>) responseObserver);
          break;
        case METHODID_DEMO_OPEN_ACCOUNT:
          serviceImpl.demoOpenAccount((mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply>) responseObserver);
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
        case METHODID_DEMO_OPEN_ACCOUNT_INTERACTIVE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.demoOpenAccountInteractive(
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GuiBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GuiBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiGui.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Gui");
    }
  }

  private static final class GuiFileDescriptorSupplier
      extends GuiBaseDescriptorSupplier {
    GuiFileDescriptorSupplier() {}
  }

  private static final class GuiMethodDescriptorSupplier
      extends GuiBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GuiMethodDescriptorSupplier(String methodName) {
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
      synchronized (GuiGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GuiFileDescriptorSupplier())
              .addMethod(getLoginMethod())
              .addMethod(getBrokerSearchMethod())
              .addMethod(getLoginExMethod())
              .addMethod(getCloseDialogsMethod())
              .addMethod(getDemoOpenAccountMethod())
              .addMethod(getDemoOpenAccountInteractiveMethod())
              .build();
        }
      }
    }
    return result;
  }
}
