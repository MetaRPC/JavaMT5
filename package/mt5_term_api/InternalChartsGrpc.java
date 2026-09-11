package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-internal-charts.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class InternalChartsGrpc {

  private InternalChartsGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.InternalCharts";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest,
      mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply> getOpenChartForSymbolMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OpenChartForSymbol",
      requestType = mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest.class,
      responseType = mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest,
      mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply> getOpenChartForSymbolMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest, mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply> getOpenChartForSymbolMethod;
    if ((getOpenChartForSymbolMethod = InternalChartsGrpc.getOpenChartForSymbolMethod) == null) {
      synchronized (InternalChartsGrpc.class) {
        if ((getOpenChartForSymbolMethod = InternalChartsGrpc.getOpenChartForSymbolMethod) == null) {
          InternalChartsGrpc.getOpenChartForSymbolMethod = getOpenChartForSymbolMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest, mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OpenChartForSymbol"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply.getDefaultInstance()))
              .setSchemaDescriptor(new InternalChartsMethodDescriptorSupplier("OpenChartForSymbol"))
              .build();
        }
      }
    }
    return getOpenChartForSymbolMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest,
      mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply> getCloseChartForSymbolMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CloseChartForSymbol",
      requestType = mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest.class,
      responseType = mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest,
      mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply> getCloseChartForSymbolMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest, mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply> getCloseChartForSymbolMethod;
    if ((getCloseChartForSymbolMethod = InternalChartsGrpc.getCloseChartForSymbolMethod) == null) {
      synchronized (InternalChartsGrpc.class) {
        if ((getCloseChartForSymbolMethod = InternalChartsGrpc.getCloseChartForSymbolMethod) == null) {
          InternalChartsGrpc.getCloseChartForSymbolMethod = getCloseChartForSymbolMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest, mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CloseChartForSymbol"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply.getDefaultInstance()))
              .setSchemaDescriptor(new InternalChartsMethodDescriptorSupplier("CloseChartForSymbol"))
              .build();
        }
      }
    }
    return getCloseChartForSymbolMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest,
      mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply> getOpenChartWithEaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OpenChartWithEa",
      requestType = mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest.class,
      responseType = mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest,
      mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply> getOpenChartWithEaMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest, mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply> getOpenChartWithEaMethod;
    if ((getOpenChartWithEaMethod = InternalChartsGrpc.getOpenChartWithEaMethod) == null) {
      synchronized (InternalChartsGrpc.class) {
        if ((getOpenChartWithEaMethod = InternalChartsGrpc.getOpenChartWithEaMethod) == null) {
          InternalChartsGrpc.getOpenChartWithEaMethod = getOpenChartWithEaMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest, mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OpenChartWithEa"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply.getDefaultInstance()))
              .setSchemaDescriptor(new InternalChartsMethodDescriptorSupplier("OpenChartWithEa"))
              .build();
        }
      }
    }
    return getOpenChartWithEaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest,
      mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply> getSaveChartTemplateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SaveChartTemplate",
      requestType = mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest.class,
      responseType = mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest,
      mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply> getSaveChartTemplateMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest, mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply> getSaveChartTemplateMethod;
    if ((getSaveChartTemplateMethod = InternalChartsGrpc.getSaveChartTemplateMethod) == null) {
      synchronized (InternalChartsGrpc.class) {
        if ((getSaveChartTemplateMethod = InternalChartsGrpc.getSaveChartTemplateMethod) == null) {
          InternalChartsGrpc.getSaveChartTemplateMethod = getSaveChartTemplateMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest, mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SaveChartTemplate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply.getDefaultInstance()))
              .setSchemaDescriptor(new InternalChartsMethodDescriptorSupplier("SaveChartTemplate"))
              .build();
        }
      }
    }
    return getSaveChartTemplateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static InternalChartsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InternalChartsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InternalChartsStub>() {
        @java.lang.Override
        public InternalChartsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InternalChartsStub(channel, callOptions);
        }
      };
    return InternalChartsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static InternalChartsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InternalChartsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InternalChartsBlockingStub>() {
        @java.lang.Override
        public InternalChartsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InternalChartsBlockingStub(channel, callOptions);
        }
      };
    return InternalChartsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static InternalChartsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<InternalChartsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<InternalChartsFutureStub>() {
        @java.lang.Override
        public InternalChartsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new InternalChartsFutureStub(channel, callOptions);
        }
      };
    return InternalChartsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class InternalChartsImplBase implements io.grpc.BindableService {

    /**
     */
    public void openChartForSymbol(mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOpenChartForSymbolMethod(), responseObserver);
    }

    /**
     */
    public void closeChartForSymbol(mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCloseChartForSymbolMethod(), responseObserver);
    }

    /**
     */
    public void openChartWithEa(mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOpenChartWithEaMethod(), responseObserver);
    }

    /**
     */
    public void saveChartTemplate(mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSaveChartTemplateMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getOpenChartForSymbolMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest,
                mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply>(
                  this, METHODID_OPEN_CHART_FOR_SYMBOL)))
          .addMethod(
            getCloseChartForSymbolMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest,
                mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply>(
                  this, METHODID_CLOSE_CHART_FOR_SYMBOL)))
          .addMethod(
            getOpenChartWithEaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest,
                mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply>(
                  this, METHODID_OPEN_CHART_WITH_EA)))
          .addMethod(
            getSaveChartTemplateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest,
                mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply>(
                  this, METHODID_SAVE_CHART_TEMPLATE)))
          .build();
    }
  }

  /**
   */
  public static final class InternalChartsStub extends io.grpc.stub.AbstractAsyncStub<InternalChartsStub> {
    private InternalChartsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InternalChartsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InternalChartsStub(channel, callOptions);
    }

    /**
     */
    public void openChartForSymbol(mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOpenChartForSymbolMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void closeChartForSymbol(mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCloseChartForSymbolMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void openChartWithEa(mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOpenChartWithEaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void saveChartTemplate(mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSaveChartTemplateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class InternalChartsBlockingStub extends io.grpc.stub.AbstractBlockingStub<InternalChartsBlockingStub> {
    private InternalChartsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InternalChartsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InternalChartsBlockingStub(channel, callOptions);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply openChartForSymbol(mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOpenChartForSymbolMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply closeChartForSymbol(mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCloseChartForSymbolMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply openChartWithEa(mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOpenChartWithEaMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply saveChartTemplate(mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSaveChartTemplateMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class InternalChartsFutureStub extends io.grpc.stub.AbstractFutureStub<InternalChartsFutureStub> {
    private InternalChartsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected InternalChartsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new InternalChartsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply> openChartForSymbol(
        mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOpenChartForSymbolMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply> closeChartForSymbol(
        mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCloseChartForSymbolMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply> openChartWithEa(
        mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOpenChartWithEaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply> saveChartTemplate(
        mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSaveChartTemplateMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_OPEN_CHART_FOR_SYMBOL = 0;
  private static final int METHODID_CLOSE_CHART_FOR_SYMBOL = 1;
  private static final int METHODID_OPEN_CHART_WITH_EA = 2;
  private static final int METHODID_SAVE_CHART_TEMPLATE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final InternalChartsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(InternalChartsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_OPEN_CHART_FOR_SYMBOL:
          serviceImpl.openChartForSymbol((mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.OpenChartForSymbolReply>) responseObserver);
          break;
        case METHODID_CLOSE_CHART_FOR_SYMBOL:
          serviceImpl.closeChartForSymbol((mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.CloseChartForSymbolReply>) responseObserver);
          break;
        case METHODID_OPEN_CHART_WITH_EA:
          serviceImpl.openChartWithEa((mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.OpenChartWithEaReply>) responseObserver);
          break;
        case METHODID_SAVE_CHART_TEMPLATE:
          serviceImpl.saveChartTemplate((mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiInternalCharts.SaveChartTemplateReply>) responseObserver);
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

  private static abstract class InternalChartsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    InternalChartsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiInternalCharts.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("InternalCharts");
    }
  }

  private static final class InternalChartsFileDescriptorSupplier
      extends InternalChartsBaseDescriptorSupplier {
    InternalChartsFileDescriptorSupplier() {}
  }

  private static final class InternalChartsMethodDescriptorSupplier
      extends InternalChartsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    InternalChartsMethodDescriptorSupplier(String methodName) {
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
      synchronized (InternalChartsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new InternalChartsFileDescriptorSupplier())
              .addMethod(getOpenChartForSymbolMethod())
              .addMethod(getCloseChartForSymbolMethod())
              .addMethod(getOpenChartWithEaMethod())
              .addMethod(getSaveChartTemplateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
