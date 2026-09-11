package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Provides access to terminal log content (Journal and Experts tabs).
 * Reads log entries directly from the MT5 terminal GUI.
 * Requires 'id' header with the terminal connection GUID returned by Connect.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-connection.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LogsGrpc {

  private LogsGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.Logs";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.JournalRequest,
      mt5_term_api.Mt5TermApiConnection.JournalReply> getJournalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Journal",
      requestType = mt5_term_api.Mt5TermApiConnection.JournalRequest.class,
      responseType = mt5_term_api.Mt5TermApiConnection.JournalReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.JournalRequest,
      mt5_term_api.Mt5TermApiConnection.JournalReply> getJournalMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.JournalRequest, mt5_term_api.Mt5TermApiConnection.JournalReply> getJournalMethod;
    if ((getJournalMethod = LogsGrpc.getJournalMethod) == null) {
      synchronized (LogsGrpc.class) {
        if ((getJournalMethod = LogsGrpc.getJournalMethod) == null) {
          LogsGrpc.getJournalMethod = getJournalMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiConnection.JournalRequest, mt5_term_api.Mt5TermApiConnection.JournalReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Journal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.JournalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.JournalReply.getDefaultInstance()))
              .setSchemaDescriptor(new LogsMethodDescriptorSupplier("Journal"))
              .build();
        }
      }
    }
    return getJournalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.OnJournalRequest,
      mt5_term_api.Mt5TermApiConnection.OnJournalReply> getOnJournalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnJournal",
      requestType = mt5_term_api.Mt5TermApiConnection.OnJournalRequest.class,
      responseType = mt5_term_api.Mt5TermApiConnection.OnJournalReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.OnJournalRequest,
      mt5_term_api.Mt5TermApiConnection.OnJournalReply> getOnJournalMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.OnJournalRequest, mt5_term_api.Mt5TermApiConnection.OnJournalReply> getOnJournalMethod;
    if ((getOnJournalMethod = LogsGrpc.getOnJournalMethod) == null) {
      synchronized (LogsGrpc.class) {
        if ((getOnJournalMethod = LogsGrpc.getOnJournalMethod) == null) {
          LogsGrpc.getOnJournalMethod = getOnJournalMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiConnection.OnJournalRequest, mt5_term_api.Mt5TermApiConnection.OnJournalReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnJournal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.OnJournalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.OnJournalReply.getDefaultInstance()))
              .setSchemaDescriptor(new LogsMethodDescriptorSupplier("OnJournal"))
              .build();
        }
      }
    }
    return getOnJournalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.JournalRequest,
      mt5_term_api.Mt5TermApiConnection.JournalReply> getExpertsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Experts",
      requestType = mt5_term_api.Mt5TermApiConnection.JournalRequest.class,
      responseType = mt5_term_api.Mt5TermApiConnection.JournalReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.JournalRequest,
      mt5_term_api.Mt5TermApiConnection.JournalReply> getExpertsMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.JournalRequest, mt5_term_api.Mt5TermApiConnection.JournalReply> getExpertsMethod;
    if ((getExpertsMethod = LogsGrpc.getExpertsMethod) == null) {
      synchronized (LogsGrpc.class) {
        if ((getExpertsMethod = LogsGrpc.getExpertsMethod) == null) {
          LogsGrpc.getExpertsMethod = getExpertsMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiConnection.JournalRequest, mt5_term_api.Mt5TermApiConnection.JournalReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Experts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.JournalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.JournalReply.getDefaultInstance()))
              .setSchemaDescriptor(new LogsMethodDescriptorSupplier("Experts"))
              .build();
        }
      }
    }
    return getExpertsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.OnJournalRequest,
      mt5_term_api.Mt5TermApiConnection.OnJournalReply> getOnExpertsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OnExperts",
      requestType = mt5_term_api.Mt5TermApiConnection.OnJournalRequest.class,
      responseType = mt5_term_api.Mt5TermApiConnection.OnJournalReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.OnJournalRequest,
      mt5_term_api.Mt5TermApiConnection.OnJournalReply> getOnExpertsMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiConnection.OnJournalRequest, mt5_term_api.Mt5TermApiConnection.OnJournalReply> getOnExpertsMethod;
    if ((getOnExpertsMethod = LogsGrpc.getOnExpertsMethod) == null) {
      synchronized (LogsGrpc.class) {
        if ((getOnExpertsMethod = LogsGrpc.getOnExpertsMethod) == null) {
          LogsGrpc.getOnExpertsMethod = getOnExpertsMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiConnection.OnJournalRequest, mt5_term_api.Mt5TermApiConnection.OnJournalReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OnExperts"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.OnJournalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiConnection.OnJournalReply.getDefaultInstance()))
              .setSchemaDescriptor(new LogsMethodDescriptorSupplier("OnExperts"))
              .build();
        }
      }
    }
    return getOnExpertsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LogsStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogsStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogsStub>() {
        @java.lang.Override
        public LogsStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogsStub(channel, callOptions);
        }
      };
    return LogsStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LogsBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogsBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogsBlockingStub>() {
        @java.lang.Override
        public LogsBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogsBlockingStub(channel, callOptions);
        }
      };
    return LogsBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LogsFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogsFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogsFutureStub>() {
        @java.lang.Override
        public LogsFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogsFutureStub(channel, callOptions);
        }
      };
    return LogsFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Provides access to terminal log content (Journal and Experts tabs).
   * Reads log entries directly from the MT5 terminal GUI.
   * Requires 'id' header with the terminal connection GUID returned by Connect.
   * </pre>
   */
  public static abstract class LogsImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Returns log entries from the terminal Journal tab.
     * The Journal tab contains system messages about terminal connection status, 
     * network activity, server synchronization and other internal events.
     * Works regardless of which tab is currently active in the terminal UI.
     * </pre>
     */
    public void journal(mt5_term_api.Mt5TermApiConnection.JournalRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.JournalReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getJournalMethod(), responseObserver);
    }

    /**
     * <pre>
     * Streams log entries from the terminal Journal tab in real-time.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * </pre>
     */
    public void onJournal(mt5_term_api.Mt5TermApiConnection.OnJournalRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.OnJournalReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnJournalMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns log entries from the terminal Experts tab.
     * The Experts tab contains messages from Expert Advisors (EAs), scripts and indicators
     * including Print() output, initialization/deinitialization events and runtime errors.
     * Works regardless of which tab is currently active in the terminal UI.
     * </pre>
     */
    public void experts(mt5_term_api.Mt5TermApiConnection.JournalRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.JournalReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExpertsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Streams log entries from the terminal Experts tab in real-time.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * </pre>
     */
    public void onExperts(mt5_term_api.Mt5TermApiConnection.OnJournalRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.OnJournalReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOnExpertsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getJournalMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiConnection.JournalRequest,
                mt5_term_api.Mt5TermApiConnection.JournalReply>(
                  this, METHODID_JOURNAL)))
          .addMethod(
            getOnJournalMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiConnection.OnJournalRequest,
                mt5_term_api.Mt5TermApiConnection.OnJournalReply>(
                  this, METHODID_ON_JOURNAL)))
          .addMethod(
            getExpertsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiConnection.JournalRequest,
                mt5_term_api.Mt5TermApiConnection.JournalReply>(
                  this, METHODID_EXPERTS)))
          .addMethod(
            getOnExpertsMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiConnection.OnJournalRequest,
                mt5_term_api.Mt5TermApiConnection.OnJournalReply>(
                  this, METHODID_ON_EXPERTS)))
          .build();
    }
  }

  /**
   * <pre>
   * Provides access to terminal log content (Journal and Experts tabs).
   * Reads log entries directly from the MT5 terminal GUI.
   * Requires 'id' header with the terminal connection GUID returned by Connect.
   * </pre>
   */
  public static final class LogsStub extends io.grpc.stub.AbstractAsyncStub<LogsStub> {
    private LogsStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogsStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogsStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns log entries from the terminal Journal tab.
     * The Journal tab contains system messages about terminal connection status, 
     * network activity, server synchronization and other internal events.
     * Works regardless of which tab is currently active in the terminal UI.
     * </pre>
     */
    public void journal(mt5_term_api.Mt5TermApiConnection.JournalRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.JournalReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getJournalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Streams log entries from the terminal Journal tab in real-time.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * </pre>
     */
    public void onJournal(mt5_term_api.Mt5TermApiConnection.OnJournalRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.OnJournalReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnJournalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns log entries from the terminal Experts tab.
     * The Experts tab contains messages from Expert Advisors (EAs), scripts and indicators
     * including Print() output, initialization/deinitialization events and runtime errors.
     * Works regardless of which tab is currently active in the terminal UI.
     * </pre>
     */
    public void experts(mt5_term_api.Mt5TermApiConnection.JournalRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.JournalReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExpertsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Streams log entries from the terminal Experts tab in real-time.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * </pre>
     */
    public void onExperts(mt5_term_api.Mt5TermApiConnection.OnJournalRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.OnJournalReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getOnExpertsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Provides access to terminal log content (Journal and Experts tabs).
   * Reads log entries directly from the MT5 terminal GUI.
   * Requires 'id' header with the terminal connection GUID returned by Connect.
   * </pre>
   */
  public static final class LogsBlockingStub extends io.grpc.stub.AbstractBlockingStub<LogsBlockingStub> {
    private LogsBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogsBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogsBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns log entries from the terminal Journal tab.
     * The Journal tab contains system messages about terminal connection status, 
     * network activity, server synchronization and other internal events.
     * Works regardless of which tab is currently active in the terminal UI.
     * </pre>
     */
    public mt5_term_api.Mt5TermApiConnection.JournalReply journal(mt5_term_api.Mt5TermApiConnection.JournalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getJournalMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Streams log entries from the terminal Journal tab in real-time.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * </pre>
     */
    public java.util.Iterator<mt5_term_api.Mt5TermApiConnection.OnJournalReply> onJournal(
        mt5_term_api.Mt5TermApiConnection.OnJournalRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnJournalMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns log entries from the terminal Experts tab.
     * The Experts tab contains messages from Expert Advisors (EAs), scripts and indicators
     * including Print() output, initialization/deinitialization events and runtime errors.
     * Works regardless of which tab is currently active in the terminal UI.
     * </pre>
     */
    public mt5_term_api.Mt5TermApiConnection.JournalReply experts(mt5_term_api.Mt5TermApiConnection.JournalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExpertsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Streams log entries from the terminal Experts tab in real-time.
     * Requires 'id' header with the terminal connection GUID returned by Connect.
     * </pre>
     */
    public java.util.Iterator<mt5_term_api.Mt5TermApiConnection.OnJournalReply> onExperts(
        mt5_term_api.Mt5TermApiConnection.OnJournalRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getOnExpertsMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Provides access to terminal log content (Journal and Experts tabs).
   * Reads log entries directly from the MT5 terminal GUI.
   * Requires 'id' header with the terminal connection GUID returned by Connect.
   * </pre>
   */
  public static final class LogsFutureStub extends io.grpc.stub.AbstractFutureStub<LogsFutureStub> {
    private LogsFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogsFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogsFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns log entries from the terminal Journal tab.
     * The Journal tab contains system messages about terminal connection status, 
     * network activity, server synchronization and other internal events.
     * Works regardless of which tab is currently active in the terminal UI.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiConnection.JournalReply> journal(
        mt5_term_api.Mt5TermApiConnection.JournalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getJournalMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns log entries from the terminal Experts tab.
     * The Experts tab contains messages from Expert Advisors (EAs), scripts and indicators
     * including Print() output, initialization/deinitialization events and runtime errors.
     * Works regardless of which tab is currently active in the terminal UI.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiConnection.JournalReply> experts(
        mt5_term_api.Mt5TermApiConnection.JournalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExpertsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_JOURNAL = 0;
  private static final int METHODID_ON_JOURNAL = 1;
  private static final int METHODID_EXPERTS = 2;
  private static final int METHODID_ON_EXPERTS = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LogsImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LogsImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_JOURNAL:
          serviceImpl.journal((mt5_term_api.Mt5TermApiConnection.JournalRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.JournalReply>) responseObserver);
          break;
        case METHODID_ON_JOURNAL:
          serviceImpl.onJournal((mt5_term_api.Mt5TermApiConnection.OnJournalRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.OnJournalReply>) responseObserver);
          break;
        case METHODID_EXPERTS:
          serviceImpl.experts((mt5_term_api.Mt5TermApiConnection.JournalRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.JournalReply>) responseObserver);
          break;
        case METHODID_ON_EXPERTS:
          serviceImpl.onExperts((mt5_term_api.Mt5TermApiConnection.OnJournalRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiConnection.OnJournalReply>) responseObserver);
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

  private static abstract class LogsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LogsBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiConnection.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Logs");
    }
  }

  private static final class LogsFileDescriptorSupplier
      extends LogsBaseDescriptorSupplier {
    LogsFileDescriptorSupplier() {}
  }

  private static final class LogsMethodDescriptorSupplier
      extends LogsBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LogsMethodDescriptorSupplier(String methodName) {
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
      synchronized (LogsGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LogsFileDescriptorSupplier())
              .addMethod(getJournalMethod())
              .addMethod(getOnJournalMethod())
              .addMethod(getExpertsMethod())
              .addMethod(getOnExpertsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
