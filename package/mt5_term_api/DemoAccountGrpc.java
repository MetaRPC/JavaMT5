package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * Demo account creation service.
 * Automates the MT5 "Open an Account" wizard via Win32 GUI automation.
 * Does NOT require 'id' header — auto-picks any available terminal.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-demo-account.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DemoAccountGrpc {

  private DemoAccountGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.DemoAccount";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> getFindCompaniesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "FindCompanies",
      requestType = mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> getFindCompaniesMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest, mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> getFindCompaniesMethod;
    if ((getFindCompaniesMethod = DemoAccountGrpc.getFindCompaniesMethod) == null) {
      synchronized (DemoAccountGrpc.class) {
        if ((getFindCompaniesMethod = DemoAccountGrpc.getFindCompaniesMethod) == null) {
          DemoAccountGrpc.getFindCompaniesMethod = getFindCompaniesMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest, mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "FindCompanies"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply.getDefaultInstance()))
              .setSchemaDescriptor(new DemoAccountMethodDescriptorSupplier("FindCompanies"))
              .build();
        }
      }
    }
    return getFindCompaniesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> getServersAndAccountTypesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ServersAndAccountTypes",
      requestType = mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> getServersAndAccountTypesMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest, mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> getServersAndAccountTypesMethod;
    if ((getServersAndAccountTypesMethod = DemoAccountGrpc.getServersAndAccountTypesMethod) == null) {
      synchronized (DemoAccountGrpc.class) {
        if ((getServersAndAccountTypesMethod = DemoAccountGrpc.getServersAndAccountTypesMethod) == null) {
          DemoAccountGrpc.getServersAndAccountTypesMethod = getServersAndAccountTypesMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest, mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ServersAndAccountTypes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply.getDefaultInstance()))
              .setSchemaDescriptor(new DemoAccountMethodDescriptorSupplier("ServersAndAccountTypes"))
              .build();
        }
      }
    }
    return getServersAndAccountTypesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> getEnumControlsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "EnumControls",
      requestType = mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> getEnumControlsMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest, mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> getEnumControlsMethod;
    if ((getEnumControlsMethod = DemoAccountGrpc.getEnumControlsMethod) == null) {
      synchronized (DemoAccountGrpc.class) {
        if ((getEnumControlsMethod = DemoAccountGrpc.getEnumControlsMethod) == null) {
          DemoAccountGrpc.getEnumControlsMethod = getEnumControlsMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest, mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "EnumControls"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply.getDefaultInstance()))
              .setSchemaDescriptor(new DemoAccountMethodDescriptorSupplier("EnumControls"))
              .build();
        }
      }
    }
    return getEnumControlsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> getOpenDemoAccountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "OpenDemoAccount",
      requestType = mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest.class,
      responseType = mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest,
      mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> getOpenDemoAccountMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest, mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> getOpenDemoAccountMethod;
    if ((getOpenDemoAccountMethod = DemoAccountGrpc.getOpenDemoAccountMethod) == null) {
      synchronized (DemoAccountGrpc.class) {
        if ((getOpenDemoAccountMethod = DemoAccountGrpc.getOpenDemoAccountMethod) == null) {
          DemoAccountGrpc.getOpenDemoAccountMethod = getOpenDemoAccountMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest, mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "OpenDemoAccount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply.getDefaultInstance()))
              .setSchemaDescriptor(new DemoAccountMethodDescriptorSupplier("OpenDemoAccount"))
              .build();
        }
      }
    }
    return getOpenDemoAccountMethod;
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
    if ((getDemoOpenAccountInteractiveMethod = DemoAccountGrpc.getDemoOpenAccountInteractiveMethod) == null) {
      synchronized (DemoAccountGrpc.class) {
        if ((getDemoOpenAccountInteractiveMethod = DemoAccountGrpc.getDemoOpenAccountInteractiveMethod) == null) {
          DemoAccountGrpc.getDemoOpenAccountInteractiveMethod = getDemoOpenAccountInteractiveMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage, mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DemoOpenAccountInteractive"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage.getDefaultInstance()))
              .setSchemaDescriptor(new DemoAccountMethodDescriptorSupplier("DemoOpenAccountInteractive"))
              .build();
        }
      }
    }
    return getDemoOpenAccountInteractiveMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DemoAccountStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DemoAccountStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DemoAccountStub>() {
        @java.lang.Override
        public DemoAccountStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DemoAccountStub(channel, callOptions);
        }
      };
    return DemoAccountStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DemoAccountBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DemoAccountBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DemoAccountBlockingStub>() {
        @java.lang.Override
        public DemoAccountBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DemoAccountBlockingStub(channel, callOptions);
        }
      };
    return DemoAccountBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DemoAccountFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DemoAccountFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DemoAccountFutureStub>() {
        @java.lang.Override
        public DemoAccountFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DemoAccountFutureStub(channel, callOptions);
        }
      };
    return DemoAccountFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Demo account creation service.
   * Automates the MT5 "Open an Account" wizard via Win32 GUI automation.
   * Does NOT require 'id' header — auto-picks any available terminal.
   * </pre>
   */
  public static abstract class DemoAccountImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Search for broker companies by name.
     * Returns a list of matching companies from the wizard's ListView.
     * [DefaultValues]
     * {
     *   "searchText": "MetaQuotes"
     * }
     * </pre>
     */
    public void findCompanies(mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindCompaniesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Get available servers and account types for a company.
     * Navigates: company selection → demo account → reads dropdown options.
     * [DefaultValues]
     * {
     *   "companyName": "MetaQuotes Ltd."
     * }
     * </pre>
     */
    public void serversAndAccountTypes(mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getServersAndAccountTypesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Debug: enumerate the controls of the wizard dialog currently open in the terminal.
     * controlCount is -1 and the dump empty when no wizard dialog is open.
     * </pre>
     */
    public void enumControls(mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getEnumControlsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Open a demo account. The wire protocol is tried first (a direct request to the trade
     * server, about a second); when the trade server does not accept it (TeleTrade answers
     * 1001 INVALID_ACCOUNT, for example) the terminal's "Open an Account" wizard is driven
     * instead, bounded by timeoutSeconds (default 90). debugLog says which path produced the
     * account ("Wire protocol" or "Wire protocol failed: ...; GUI wizard used").
     * Returns login, password, and investor password for the new demo account.
     * [DefaultValues]
     * {
     *   "company": "MetaQuotes Ltd.",
     *   "firstName": "Test",
     *   "lastName": "User",
     *   "email": "test&#64;test.com",
     *   "phone": "+1234567890",
     *   "timeoutSeconds": "60"
     * }
     * </pre>
     */
    public void openDemoAccount(mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getOpenDemoAccountMethod(), responseObserver);
    }

    /**
     * <pre>
     * Interactive step-by-step demo account opening wizard.
     * Bidirectional streaming session: search company -&gt; select -&gt; form schema -&gt; submit -&gt; 2FA (if any) -&gt; completed.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage> demoOpenAccountInteractive(
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getDemoOpenAccountInteractiveMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFindCompaniesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest,
                mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply>(
                  this, METHODID_FIND_COMPANIES)))
          .addMethod(
            getServersAndAccountTypesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest,
                mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply>(
                  this, METHODID_SERVERS_AND_ACCOUNT_TYPES)))
          .addMethod(
            getEnumControlsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest,
                mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply>(
                  this, METHODID_ENUM_CONTROLS)))
          .addMethod(
            getOpenDemoAccountMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest,
                mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply>(
                  this, METHODID_OPEN_DEMO_ACCOUNT)))
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
   * <pre>
   * Demo account creation service.
   * Automates the MT5 "Open an Account" wizard via Win32 GUI automation.
   * Does NOT require 'id' header — auto-picks any available terminal.
   * </pre>
   */
  public static final class DemoAccountStub extends io.grpc.stub.AbstractAsyncStub<DemoAccountStub> {
    private DemoAccountStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DemoAccountStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DemoAccountStub(channel, callOptions);
    }

    /**
     * <pre>
     * Search for broker companies by name.
     * Returns a list of matching companies from the wizard's ListView.
     * [DefaultValues]
     * {
     *   "searchText": "MetaQuotes"
     * }
     * </pre>
     */
    public void findCompanies(mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindCompaniesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get available servers and account types for a company.
     * Navigates: company selection → demo account → reads dropdown options.
     * [DefaultValues]
     * {
     *   "companyName": "MetaQuotes Ltd."
     * }
     * </pre>
     */
    public void serversAndAccountTypes(mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getServersAndAccountTypesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Debug: enumerate the controls of the wizard dialog currently open in the terminal.
     * controlCount is -1 and the dump empty when no wizard dialog is open.
     * </pre>
     */
    public void enumControls(mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getEnumControlsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Open a demo account. The wire protocol is tried first (a direct request to the trade
     * server, about a second); when the trade server does not accept it (TeleTrade answers
     * 1001 INVALID_ACCOUNT, for example) the terminal's "Open an Account" wizard is driven
     * instead, bounded by timeoutSeconds (default 90). debugLog says which path produced the
     * account ("Wire protocol" or "Wire protocol failed: ...; GUI wizard used").
     * Returns login, password, and investor password for the new demo account.
     * [DefaultValues]
     * {
     *   "company": "MetaQuotes Ltd.",
     *   "firstName": "Test",
     *   "lastName": "User",
     *   "email": "test&#64;test.com",
     *   "phone": "+1234567890",
     *   "timeoutSeconds": "60"
     * }
     * </pre>
     */
    public void openDemoAccount(mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getOpenDemoAccountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Interactive step-by-step demo account opening wizard.
     * Bidirectional streaming session: search company -&gt; select -&gt; form schema -&gt; submit -&gt; 2FA (if any) -&gt; completed.
     * </pre>
     */
    public io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveClientMessage> demoOpenAccountInteractive(
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoInteractiveServerMessage> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getDemoOpenAccountInteractiveMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * Demo account creation service.
   * Automates the MT5 "Open an Account" wizard via Win32 GUI automation.
   * Does NOT require 'id' header — auto-picks any available terminal.
   * </pre>
   */
  public static final class DemoAccountBlockingStub extends io.grpc.stub.AbstractBlockingStub<DemoAccountBlockingStub> {
    private DemoAccountBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DemoAccountBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DemoAccountBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Search for broker companies by name.
     * Returns a list of matching companies from the wizard's ListView.
     * [DefaultValues]
     * {
     *   "searchText": "MetaQuotes"
     * }
     * </pre>
     */
    public mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply findCompanies(mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindCompaniesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Get available servers and account types for a company.
     * Navigates: company selection → demo account → reads dropdown options.
     * [DefaultValues]
     * {
     *   "companyName": "MetaQuotes Ltd."
     * }
     * </pre>
     */
    public mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply serversAndAccountTypes(mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getServersAndAccountTypesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Debug: enumerate the controls of the wizard dialog currently open in the terminal.
     * controlCount is -1 and the dump empty when no wizard dialog is open.
     * </pre>
     */
    public mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply enumControls(mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getEnumControlsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Open a demo account. The wire protocol is tried first (a direct request to the trade
     * server, about a second); when the trade server does not accept it (TeleTrade answers
     * 1001 INVALID_ACCOUNT, for example) the terminal's "Open an Account" wizard is driven
     * instead, bounded by timeoutSeconds (default 90). debugLog says which path produced the
     * account ("Wire protocol" or "Wire protocol failed: ...; GUI wizard used").
     * Returns login, password, and investor password for the new demo account.
     * [DefaultValues]
     * {
     *   "company": "MetaQuotes Ltd.",
     *   "firstName": "Test",
     *   "lastName": "User",
     *   "email": "test&#64;test.com",
     *   "phone": "+1234567890",
     *   "timeoutSeconds": "60"
     * }
     * </pre>
     */
    public mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply openDemoAccount(mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getOpenDemoAccountMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Demo account creation service.
   * Automates the MT5 "Open an Account" wizard via Win32 GUI automation.
   * Does NOT require 'id' header — auto-picks any available terminal.
   * </pre>
   */
  public static final class DemoAccountFutureStub extends io.grpc.stub.AbstractFutureStub<DemoAccountFutureStub> {
    private DemoAccountFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DemoAccountFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DemoAccountFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Search for broker companies by name.
     * Returns a list of matching companies from the wizard's ListView.
     * [DefaultValues]
     * {
     *   "searchText": "MetaQuotes"
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply> findCompanies(
        mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindCompaniesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Get available servers and account types for a company.
     * Navigates: company selection → demo account → reads dropdown options.
     * [DefaultValues]
     * {
     *   "companyName": "MetaQuotes Ltd."
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply> serversAndAccountTypes(
        mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getServersAndAccountTypesMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Debug: enumerate the controls of the wizard dialog currently open in the terminal.
     * controlCount is -1 and the dump empty when no wizard dialog is open.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply> enumControls(
        mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getEnumControlsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Open a demo account. The wire protocol is tried first (a direct request to the trade
     * server, about a second); when the trade server does not accept it (TeleTrade answers
     * 1001 INVALID_ACCOUNT, for example) the terminal's "Open an Account" wizard is driven
     * instead, bounded by timeoutSeconds (default 90). debugLog says which path produced the
     * account ("Wire protocol" or "Wire protocol failed: ...; GUI wizard used").
     * Returns login, password, and investor password for the new demo account.
     * [DefaultValues]
     * {
     *   "company": "MetaQuotes Ltd.",
     *   "firstName": "Test",
     *   "lastName": "User",
     *   "email": "test&#64;test.com",
     *   "phone": "+1234567890",
     *   "timeoutSeconds": "60"
     * }
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountReply> openDemoAccount(
        mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getOpenDemoAccountMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FIND_COMPANIES = 0;
  private static final int METHODID_SERVERS_AND_ACCOUNT_TYPES = 1;
  private static final int METHODID_ENUM_CONTROLS = 2;
  private static final int METHODID_OPEN_DEMO_ACCOUNT = 3;
  private static final int METHODID_DEMO_OPEN_ACCOUNT_INTERACTIVE = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DemoAccountImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DemoAccountImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FIND_COMPANIES:
          serviceImpl.findCompanies((mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoFindCompaniesReply>) responseObserver);
          break;
        case METHODID_SERVERS_AND_ACCOUNT_TYPES:
          serviceImpl.serversAndAccountTypes((mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoServersAndTypesReply>) responseObserver);
          break;
        case METHODID_ENUM_CONTROLS:
          serviceImpl.enumControls((mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiGui.GuiDemoEnumControlsReply>) responseObserver);
          break;
        case METHODID_OPEN_DEMO_ACCOUNT:
          serviceImpl.openDemoAccount((mt5_term_api.Mt5TermApiGui.GuiDemoOpenAccountRequest) request,
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

  private static abstract class DemoAccountBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DemoAccountBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiDemoAccount.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DemoAccount");
    }
  }

  private static final class DemoAccountFileDescriptorSupplier
      extends DemoAccountBaseDescriptorSupplier {
    DemoAccountFileDescriptorSupplier() {}
  }

  private static final class DemoAccountMethodDescriptorSupplier
      extends DemoAccountBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DemoAccountMethodDescriptorSupplier(String methodName) {
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
      synchronized (DemoAccountGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DemoAccountFileDescriptorSupplier())
              .addMethod(getFindCompaniesMethod())
              .addMethod(getServersAndAccountTypesMethod())
              .addMethod(getEnumControlsMethod())
              .addMethod(getOpenDemoAccountMethod())
              .addMethod(getDemoOpenAccountInteractiveMethod())
              .build();
        }
      }
    }
    return result;
  }
}
