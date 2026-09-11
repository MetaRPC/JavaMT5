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

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> getDemoFindCompaniesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DemoFindCompanies",
      requestType = mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> getDemoFindCompaniesMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest, mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> getDemoFindCompaniesMethod;
    if ((getDemoFindCompaniesMethod = GuiGrpc.getDemoFindCompaniesMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getDemoFindCompaniesMethod = GuiGrpc.getDemoFindCompaniesMethod) == null) {
          GuiGrpc.getDemoFindCompaniesMethod = getDemoFindCompaniesMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest, mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DemoFindCompanies"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("DemoFindCompanies"))
              .build();
        }
      }
    }
    return getDemoFindCompaniesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> getDemoServersAndTypesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DemoServersAndTypes",
      requestType = mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> getDemoServersAndTypesMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest, mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> getDemoServersAndTypesMethod;
    if ((getDemoServersAndTypesMethod = GuiGrpc.getDemoServersAndTypesMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getDemoServersAndTypesMethod = GuiGrpc.getDemoServersAndTypesMethod) == null) {
          GuiGrpc.getDemoServersAndTypesMethod = getDemoServersAndTypesMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest, mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DemoServersAndTypes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("DemoServersAndTypes"))
              .build();
        }
      }
    }
    return getDemoServersAndTypesMethod;
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

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> getDemoEnumControlsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DemoEnumControls",
      requestType = mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> getDemoEnumControlsMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest, mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> getDemoEnumControlsMethod;
    if ((getDemoEnumControlsMethod = GuiGrpc.getDemoEnumControlsMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getDemoEnumControlsMethod = GuiGrpc.getDemoEnumControlsMethod) == null) {
          GuiGrpc.getDemoEnumControlsMethod = getDemoEnumControlsMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest, mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DemoEnumControls"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("DemoEnumControls"))
              .build();
        }
      }
    }
    return getDemoEnumControlsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoProgressEvent> getDemoOpenAccountWithProgressMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DemoOpenAccountWithProgress",
      requestType = mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiDemoProgressEvent.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoProgressEvent> getDemoOpenAccountWithProgressMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest, mt5_term_api.Mt5TermApiGui.GuiDemoProgressEvent> getDemoOpenAccountWithProgressMethod;
    if ((getDemoOpenAccountWithProgressMethod = GuiGrpc.getDemoOpenAccountWithProgressMethod) == null) {
      synchronized (GuiGrpc.class) {
        if ((getDemoOpenAccountWithProgressMethod = GuiGrpc.getDemoOpenAccountWithProgressMethod) == null) {
          GuiGrpc.getDemoOpenAccountWithProgressMethod = getDemoOpenAccountWithProgressMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest, mt5_term_api.Mt5TermApiGui.GuiDemoProgressEvent>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DemoOpenAccountWithProgress"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoProgressEvent.getDefaultInstance()))
              .setSchemaDescriptor(new GuiMethodDescriptorSupplier("DemoOpenAccountWithProgress"))
              .build();
        }
      }
    }
    return getDemoOpenAccountWithProgressMethod;
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
    public void demoFindCompanies(mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDemoFindCompaniesMethod(), responseObserver);
    }

    /**
     */
    public void demoServersAndTypes(mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDemoServersAndTypesMethod(), responseObserver);
    }

    /**
     */
    public void demoOpenAccount(mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDemoOpenAccountMethod(), responseObserver);
    }

    /**
     */
    public void demoEnumControls(mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDemoEnumControlsMethod(), responseObserver);
    }

    /**
     */
    public void demoOpenAccountWithProgress(mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoProgressEvent> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDemoOpenAccountWithProgressMethod(), responseObserver);
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
            getDemoFindCompaniesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest,
                mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply>(
                  this, METHODID_DEMO_FIND_COMPANIES)))
          .addMethod(
            getDemoServersAndTypesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest,
                mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply>(
                  this, METHODID_DEMO_SERVERS_AND_TYPES)))
          .addMethod(
            getDemoOpenAccountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest,
                mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply>(
                  this, METHODID_DEMO_OPEN_ACCOUNT)))
          .addMethod(
            getDemoEnumControlsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest,
                mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply>(
                  this, METHODID_DEMO_ENUM_CONTROLS)))
          .addMethod(
            getDemoOpenAccountWithProgressMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest,
                mt5_term_api.Mt5TermApiGui.GuiDemoProgressEvent>(
                  this, METHODID_DEMO_OPEN_ACCOUNT_WITH_PROGRESS)))
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
    public void demoFindCompanies(mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDemoFindCompaniesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void demoServersAndTypes(mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDemoServersAndTypesMethod(), getCallOptions()), request, responseObserver);
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
    public void demoEnumControls(mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDemoEnumControlsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void demoOpenAccountWithProgress(mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoProgressEvent> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getDemoOpenAccountWithProgressMethod(), getCallOptions()), request, responseObserver);
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
    public mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply demoFindCompanies(mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDemoFindCompaniesMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply demoServersAndTypes(mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDemoServersAndTypesMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply demoOpenAccount(mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDemoOpenAccountMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply demoEnumControls(mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDemoEnumControlsMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<mt5_term_api.Mt5TermApiGui.GuiDemoProgressEvent> demoOpenAccountWithProgress(
        mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getDemoOpenAccountWithProgressMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> demoFindCompanies(
        mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDemoFindCompaniesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> demoServersAndTypes(
        mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDemoServersAndTypesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> demoOpenAccount(
        mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDemoOpenAccountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> demoEnumControls(
        mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDemoEnumControlsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LOGIN = 0;
  private static final int METHODID_BROKER_SEARCH = 1;
  private static final int METHODID_LOGIN_EX = 2;
  private static final int METHODID_CLOSE_DIALOGS = 3;
  private static final int METHODID_DEMO_FIND_COMPANIES = 4;
  private static final int METHODID_DEMO_SERVERS_AND_TYPES = 5;
  private static final int METHODID_DEMO_OPEN_ACCOUNT = 6;
  private static final int METHODID_DEMO_ENUM_CONTROLS = 7;
  private static final int METHODID_DEMO_OPEN_ACCOUNT_WITH_PROGRESS = 8;
  private static final int METHODID_DEMO_OPEN_ACCOUNT_INTERACTIVE = 9;

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
        case METHODID_DEMO_FIND_COMPANIES:
          serviceImpl.demoFindCompanies((mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply>) responseObserver);
          break;
        case METHODID_DEMO_SERVERS_AND_TYPES:
          serviceImpl.demoServersAndTypes((mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply>) responseObserver);
          break;
        case METHODID_DEMO_OPEN_ACCOUNT:
          serviceImpl.demoOpenAccount((mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply>) responseObserver);
          break;
        case METHODID_DEMO_ENUM_CONTROLS:
          serviceImpl.demoEnumControls((mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply>) responseObserver);
          break;
        case METHODID_DEMO_OPEN_ACCOUNT_WITH_PROGRESS:
          serviceImpl.demoOpenAccountWithProgress((mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoProgressEvent>) responseObserver);
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
              .addMethod(getDemoFindCompaniesMethod())
              .addMethod(getDemoServersAndTypesMethod())
              .addMethod(getDemoOpenAccountMethod())
              .addMethod(getDemoEnumControlsMethod())
              .addMethod(getDemoOpenAccountWithProgressMethod())
              .addMethod(getDemoOpenAccountInteractiveMethod())
              .build();
        }
      }
    }
    return result;
  }
}
