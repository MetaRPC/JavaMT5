package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-charts.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChartsGrpc {

  private ChartsGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.Charts";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest,
      mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply> getOpenTerminalChartWithEaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OpenTerminalChartWithEa",
      requestType = mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest.class,
      responseType = mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest,
      mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply> getOpenTerminalChartWithEaMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest, mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply> getOpenTerminalChartWithEaMethod;
    if ((getOpenTerminalChartWithEaMethod = ChartsGrpc.getOpenTerminalChartWithEaMethod) == null) {
      synchronized (ChartsGrpc.class) {
        if ((getOpenTerminalChartWithEaMethod = ChartsGrpc.getOpenTerminalChartWithEaMethod) == null) {
          ChartsGrpc.getOpenTerminalChartWithEaMethod = getOpenTerminalChartWithEaMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest, mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OpenTerminalChartWithEa"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply.getDefaultInstance()))
              .setSchemaDescriptor(new ChartsMethodDescriptorSupplier("OpenTerminalChartWithEa"))
              .build();
        }
      }
    }
    return getOpenTerminalChartWithEaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest,
      mt5_term_api.Mt5TermApiCharts.GetEaParamsReply> getGetEaParamsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetEaParams",
      requestType = mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest.class,
      responseType = mt5_term_api.Mt5TermApiCharts.GetEaParamsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest,
      mt5_term_api.Mt5TermApiCharts.GetEaParamsReply> getGetEaParamsMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest, mt5_term_api.Mt5TermApiCharts.GetEaParamsReply> getGetEaParamsMethod;
    if ((getGetEaParamsMethod = ChartsGrpc.getGetEaParamsMethod) == null) {
      synchronized (ChartsGrpc.class) {
        if ((getGetEaParamsMethod = ChartsGrpc.getGetEaParamsMethod) == null) {
          ChartsGrpc.getGetEaParamsMethod = getGetEaParamsMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest, mt5_term_api.Mt5TermApiCharts.GetEaParamsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetEaParams"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.GetEaParamsReply.getDefaultInstance()))
              .setSchemaDescriptor(new ChartsMethodDescriptorSupplier("GetEaParams"))
              .build();
        }
      }
    }
    return getGetEaParamsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.AttachEaRequest,
      mt5_term_api.Mt5TermApiCharts.AttachEaReply> getAttachEaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AttachEa",
      requestType = mt5_term_api.Mt5TermApiCharts.AttachEaRequest.class,
      responseType = mt5_term_api.Mt5TermApiCharts.AttachEaReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.AttachEaRequest,
      mt5_term_api.Mt5TermApiCharts.AttachEaReply> getAttachEaMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.AttachEaRequest, mt5_term_api.Mt5TermApiCharts.AttachEaReply> getAttachEaMethod;
    if ((getAttachEaMethod = ChartsGrpc.getAttachEaMethod) == null) {
      synchronized (ChartsGrpc.class) {
        if ((getAttachEaMethod = ChartsGrpc.getAttachEaMethod) == null) {
          ChartsGrpc.getAttachEaMethod = getAttachEaMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiCharts.AttachEaRequest, mt5_term_api.Mt5TermApiCharts.AttachEaReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AttachEa"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.AttachEaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.AttachEaReply.getDefaultInstance()))
              .setSchemaDescriptor(new ChartsMethodDescriptorSupplier("AttachEa"))
              .build();
        }
      }
    }
    return getAttachEaMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest,
      mt5_term_api.Mt5TermApiCharts.GetRunningEasReply> getGetRunningEasMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetRunningEas",
      requestType = mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest.class,
      responseType = mt5_term_api.Mt5TermApiCharts.GetRunningEasReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest,
      mt5_term_api.Mt5TermApiCharts.GetRunningEasReply> getGetRunningEasMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest, mt5_term_api.Mt5TermApiCharts.GetRunningEasReply> getGetRunningEasMethod;
    if ((getGetRunningEasMethod = ChartsGrpc.getGetRunningEasMethod) == null) {
      synchronized (ChartsGrpc.class) {
        if ((getGetRunningEasMethod = ChartsGrpc.getGetRunningEasMethod) == null) {
          ChartsGrpc.getGetRunningEasMethod = getGetRunningEasMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest, mt5_term_api.Mt5TermApiCharts.GetRunningEasReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetRunningEas"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.GetRunningEasReply.getDefaultInstance()))
              .setSchemaDescriptor(new ChartsMethodDescriptorSupplier("GetRunningEas"))
              .build();
        }
      }
    }
    return getGetRunningEasMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest,
      mt5_term_api.Mt5TermApiCharts.GetEaLogsReply> getGetEaLogsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetEaLogs",
      requestType = mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest.class,
      responseType = mt5_term_api.Mt5TermApiCharts.GetEaLogsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest,
      mt5_term_api.Mt5TermApiCharts.GetEaLogsReply> getGetEaLogsMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest, mt5_term_api.Mt5TermApiCharts.GetEaLogsReply> getGetEaLogsMethod;
    if ((getGetEaLogsMethod = ChartsGrpc.getGetEaLogsMethod) == null) {
      synchronized (ChartsGrpc.class) {
        if ((getGetEaLogsMethod = ChartsGrpc.getGetEaLogsMethod) == null) {
          ChartsGrpc.getGetEaLogsMethod = getGetEaLogsMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest, mt5_term_api.Mt5TermApiCharts.GetEaLogsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetEaLogs"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.GetEaLogsReply.getDefaultInstance()))
              .setSchemaDescriptor(new ChartsMethodDescriptorSupplier("GetEaLogs"))
              .build();
        }
      }
    }
    return getGetEaLogsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.StopEaRequest,
      mt5_term_api.Mt5TermApiCharts.StopEaReply> getStopEaMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StopEa",
      requestType = mt5_term_api.Mt5TermApiCharts.StopEaRequest.class,
      responseType = mt5_term_api.Mt5TermApiCharts.StopEaReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.StopEaRequest,
      mt5_term_api.Mt5TermApiCharts.StopEaReply> getStopEaMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiCharts.StopEaRequest, mt5_term_api.Mt5TermApiCharts.StopEaReply> getStopEaMethod;
    if ((getStopEaMethod = ChartsGrpc.getStopEaMethod) == null) {
      synchronized (ChartsGrpc.class) {
        if ((getStopEaMethod = ChartsGrpc.getStopEaMethod) == null) {
          ChartsGrpc.getStopEaMethod = getStopEaMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiCharts.StopEaRequest, mt5_term_api.Mt5TermApiCharts.StopEaReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StopEa"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.StopEaRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiCharts.StopEaReply.getDefaultInstance()))
              .setSchemaDescriptor(new ChartsMethodDescriptorSupplier("StopEa"))
              .build();
        }
      }
    }
    return getStopEaMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChartsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChartsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChartsStub>() {
        @java.lang.Override
        public ChartsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChartsStub(channel, callOptions);
        }
      };
    return ChartsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChartsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChartsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChartsBlockingStub>() {
        @java.lang.Override
        public ChartsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChartsBlockingStub(channel, callOptions);
        }
      };
    return ChartsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChartsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChartsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChartsFutureStub>() {
        @java.lang.Override
        public ChartsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChartsFutureStub(channel, callOptions);
        }
      };
    return ChartsFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ChartsImplBase implements io.grpc.BindableService {

    /**
     */
    public void openTerminalChartWithEa(mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOpenTerminalChartWithEaMethod(), responseObserver);
    }

    /**
     */
    public void getEaParams(mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.GetEaParamsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEaParamsMethod(), responseObserver);
    }

    /**
     */
    public void attachEa(mt5_term_api.Mt5TermApiCharts.AttachEaRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.AttachEaReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAttachEaMethod(), responseObserver);
    }

    /**
     */
    public void getRunningEas(mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.GetRunningEasReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetRunningEasMethod(), responseObserver);
    }

    /**
     */
    public void getEaLogs(mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.GetEaLogsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetEaLogsMethod(), responseObserver);
    }

    /**
     */
    public void stopEa(mt5_term_api.Mt5TermApiCharts.StopEaRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.StopEaReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStopEaMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getOpenTerminalChartWithEaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest,
                mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply>(
                  this, METHODID_OPEN_TERMINAL_CHART_WITH_EA)))
          .addMethod(
            getGetEaParamsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest,
                mt5_term_api.Mt5TermApiCharts.GetEaParamsReply>(
                  this, METHODID_GET_EA_PARAMS)))
          .addMethod(
            getAttachEaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiCharts.AttachEaRequest,
                mt5_term_api.Mt5TermApiCharts.AttachEaReply>(
                  this, METHODID_ATTACH_EA)))
          .addMethod(
            getGetRunningEasMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest,
                mt5_term_api.Mt5TermApiCharts.GetRunningEasReply>(
                  this, METHODID_GET_RUNNING_EAS)))
          .addMethod(
            getGetEaLogsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest,
                mt5_term_api.Mt5TermApiCharts.GetEaLogsReply>(
                  this, METHODID_GET_EA_LOGS)))
          .addMethod(
            getStopEaMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiCharts.StopEaRequest,
                mt5_term_api.Mt5TermApiCharts.StopEaReply>(
                  this, METHODID_STOP_EA)))
          .build();
    }
  }

  /**
   */
  public static final class ChartsStub extends io.grpc.stub.AbstractAsyncStub<ChartsStub> {
    private ChartsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChartsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChartsStub(channel, callOptions);
    }

    /**
     */
    public void openTerminalChartWithEa(mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOpenTerminalChartWithEaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getEaParams(mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.GetEaParamsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetEaParamsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void attachEa(mt5_term_api.Mt5TermApiCharts.AttachEaRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.AttachEaReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAttachEaMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getRunningEas(mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.GetRunningEasReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetRunningEasMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getEaLogs(mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.GetEaLogsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetEaLogsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void stopEa(mt5_term_api.Mt5TermApiCharts.StopEaRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.StopEaReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getStopEaMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ChartsBlockingStub extends io.grpc.stub.AbstractBlockingStub<ChartsBlockingStub> {
    private ChartsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChartsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChartsBlockingStub(channel, callOptions);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply openTerminalChartWithEa(mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOpenTerminalChartWithEaMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiCharts.GetEaParamsReply getEaParams(mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetEaParamsMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiCharts.AttachEaReply attachEa(mt5_term_api.Mt5TermApiCharts.AttachEaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAttachEaMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiCharts.GetRunningEasReply getRunningEas(mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetRunningEasMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiCharts.GetEaLogsReply getEaLogs(mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetEaLogsMethod(), getCallOptions(), request);
    }

    /**
     */
    public mt5_term_api.Mt5TermApiCharts.StopEaReply stopEa(mt5_term_api.Mt5TermApiCharts.StopEaRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getStopEaMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ChartsFutureStub extends io.grpc.stub.AbstractFutureStub<ChartsFutureStub> {
    private ChartsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChartsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChartsFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply> openTerminalChartWithEa(
        mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOpenTerminalChartWithEaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiCharts.GetEaParamsReply> getEaParams(
        mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetEaParamsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiCharts.AttachEaReply> attachEa(
        mt5_term_api.Mt5TermApiCharts.AttachEaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAttachEaMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiCharts.GetRunningEasReply> getRunningEas(
        mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetRunningEasMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiCharts.GetEaLogsReply> getEaLogs(
        mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetEaLogsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiCharts.StopEaReply> stopEa(
        mt5_term_api.Mt5TermApiCharts.StopEaRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getStopEaMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_OPEN_TERMINAL_CHART_WITH_EA = 0;
  private static final int METHODID_GET_EA_PARAMS = 1;
  private static final int METHODID_ATTACH_EA = 2;
  private static final int METHODID_GET_RUNNING_EAS = 3;
  private static final int METHODID_GET_EA_LOGS = 4;
  private static final int METHODID_STOP_EA = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChartsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChartsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_OPEN_TERMINAL_CHART_WITH_EA:
          serviceImpl.openTerminalChartWithEa((mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.OpenTerminalChartWithEaReply>) responseObserver);
          break;
        case METHODID_GET_EA_PARAMS:
          serviceImpl.getEaParams((mt5_term_api.Mt5TermApiCharts.GetEaParamsRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.GetEaParamsReply>) responseObserver);
          break;
        case METHODID_ATTACH_EA:
          serviceImpl.attachEa((mt5_term_api.Mt5TermApiCharts.AttachEaRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.AttachEaReply>) responseObserver);
          break;
        case METHODID_GET_RUNNING_EAS:
          serviceImpl.getRunningEas((mt5_term_api.Mt5TermApiCharts.GetRunningEasRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.GetRunningEasReply>) responseObserver);
          break;
        case METHODID_GET_EA_LOGS:
          serviceImpl.getEaLogs((mt5_term_api.Mt5TermApiCharts.GetEaLogsRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.GetEaLogsReply>) responseObserver);
          break;
        case METHODID_STOP_EA:
          serviceImpl.stopEa((mt5_term_api.Mt5TermApiCharts.StopEaRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiCharts.StopEaReply>) responseObserver);
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

  private static abstract class ChartsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChartsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiCharts.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Charts");
    }
  }

  private static final class ChartsFileDescriptorSupplier
      extends ChartsBaseDescriptorSupplier {
    ChartsFileDescriptorSupplier() {}
  }

  private static final class ChartsMethodDescriptorSupplier
      extends ChartsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ChartsMethodDescriptorSupplier(String methodName) {
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
      synchronized (ChartsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChartsFileDescriptorSupplier())
              .addMethod(getOpenTerminalChartWithEaMethod())
              .addMethod(getGetEaParamsMethod())
              .addMethod(getAttachEaMethod())
              .addMethod(getGetRunningEasMethod())
              .addMethod(getGetEaLogsMethod())
              .addMethod(getStopEaMethod())
              .build();
        }
      }
    }
    return result;
  }
}
