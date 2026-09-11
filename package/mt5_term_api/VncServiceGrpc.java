package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-vnc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class VncServiceGrpc {

  private VncServiceGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.VncService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiVnc.VncData,
      mt5_term_api.Mt5TermApiVnc.VncData> getVncStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "VncStream",
      requestType = mt5_term_api.Mt5TermApiVnc.VncData.class,
      responseType = mt5_term_api.Mt5TermApiVnc.VncData.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiVnc.VncData,
      mt5_term_api.Mt5TermApiVnc.VncData> getVncStreamMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiVnc.VncData, mt5_term_api.Mt5TermApiVnc.VncData> getVncStreamMethod;
    if ((getVncStreamMethod = VncServiceGrpc.getVncStreamMethod) == null) {
      synchronized (VncServiceGrpc.class) {
        if ((getVncStreamMethod = VncServiceGrpc.getVncStreamMethod) == null) {
          VncServiceGrpc.getVncStreamMethod = getVncStreamMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiVnc.VncData, mt5_term_api.Mt5TermApiVnc.VncData>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "VncStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiVnc.VncData.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiVnc.VncData.getDefaultInstance()))
              .setSchemaDescriptor(new VncServiceMethodDescriptorSupplier("VncStream"))
              .build();
        }
      }
    }
    return getVncStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static VncServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VncServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VncServiceStub>() {
        @java.lang.Override
        public VncServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VncServiceStub(channel, callOptions);
        }
      };
    return VncServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VncServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VncServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VncServiceBlockingStub>() {
        @java.lang.Override
        public VncServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VncServiceBlockingStub(channel, callOptions);
        }
      };
    return VncServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static VncServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VncServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VncServiceFutureStub>() {
        @java.lang.Override
        public VncServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VncServiceFutureStub(channel, callOptions);
        }
      };
    return VncServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class VncServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Bidirectional stream: raw VNC (RFB) binary data flows in both directions
     * </pre>
     */
    public io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiVnc.VncData> vncStream(
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiVnc.VncData> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getVncStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getVncStreamMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiVnc.VncData,
                mt5_term_api.Mt5TermApiVnc.VncData>(
                  this, METHODID_VNC_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class VncServiceStub extends io.grpc.stub.AbstractAsyncStub<VncServiceStub> {
    private VncServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VncServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VncServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Bidirectional stream: raw VNC (RFB) binary data flows in both directions
     * </pre>
     */
    public io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiVnc.VncData> vncStream(
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiVnc.VncData> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getVncStreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class VncServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<VncServiceBlockingStub> {
    private VncServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VncServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VncServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   */
  public static final class VncServiceFutureStub extends io.grpc.stub.AbstractFutureStub<VncServiceFutureStub> {
    private VncServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VncServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VncServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_VNC_STREAM = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final VncServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(VncServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_VNC_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.vncStream(
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiVnc.VncData>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class VncServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    VncServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiVnc.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("VncService");
    }
  }

  private static final class VncServiceFileDescriptorSupplier
      extends VncServiceBaseDescriptorSupplier {
    VncServiceFileDescriptorSupplier() {}
  }

  private static final class VncServiceMethodDescriptorSupplier
      extends VncServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    VncServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (VncServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new VncServiceFileDescriptorSupplier())
              .addMethod(getVncStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
