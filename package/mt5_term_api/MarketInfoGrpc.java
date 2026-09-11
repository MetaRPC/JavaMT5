package mt5_term_api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The MarketInfo service provides information about the current state of the market
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler",
    comments = "Source: mt5-term-api-market-info.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MarketInfoGrpc {

  private MarketInfoGrpc() {}

  public static final String SERVICE_NAME = "mt5_term_api.MarketInfo";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply> getSymbolsTotalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolsTotal",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply> getSymbolsTotalMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply> getSymbolsTotalMethod;
    if ((getSymbolsTotalMethod = MarketInfoGrpc.getSymbolsTotalMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolsTotalMethod = MarketInfoGrpc.getSymbolsTotalMethod) == null) {
          MarketInfoGrpc.getSymbolsTotalMethod = getSymbolsTotalMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolsTotal"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolsTotal"))
              .build();
        }
      }
    }
    return getSymbolsTotalMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply> getSymbolExistMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolExist",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply> getSymbolExistMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply> getSymbolExistMethod;
    if ((getSymbolExistMethod = MarketInfoGrpc.getSymbolExistMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolExistMethod = MarketInfoGrpc.getSymbolExistMethod) == null) {
          MarketInfoGrpc.getSymbolExistMethod = getSymbolExistMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolExist"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolExist"))
              .build();
        }
      }
    }
    return getSymbolExistMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply> getSymbolNameMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolName",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply> getSymbolNameMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply> getSymbolNameMethod;
    if ((getSymbolNameMethod = MarketInfoGrpc.getSymbolNameMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolNameMethod = MarketInfoGrpc.getSymbolNameMethod) == null) {
          MarketInfoGrpc.getSymbolNameMethod = getSymbolNameMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolName"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolName"))
              .build();
        }
      }
    }
    return getSymbolNameMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply> getSymbolSelectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolSelect",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply> getSymbolSelectMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply> getSymbolSelectMethod;
    if ((getSymbolSelectMethod = MarketInfoGrpc.getSymbolSelectMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolSelectMethod = MarketInfoGrpc.getSymbolSelectMethod) == null) {
          MarketInfoGrpc.getSymbolSelectMethod = getSymbolSelectMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolSelect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolSelect"))
              .build();
        }
      }
    }
    return getSymbolSelectMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply> getSymbolIsSynchronizedMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolIsSynchronized",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply> getSymbolIsSynchronizedMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply> getSymbolIsSynchronizedMethod;
    if ((getSymbolIsSynchronizedMethod = MarketInfoGrpc.getSymbolIsSynchronizedMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolIsSynchronizedMethod = MarketInfoGrpc.getSymbolIsSynchronizedMethod) == null) {
          MarketInfoGrpc.getSymbolIsSynchronizedMethod = getSymbolIsSynchronizedMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolIsSynchronized"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolIsSynchronized"))
              .build();
        }
      }
    }
    return getSymbolIsSynchronizedMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply> getSymbolInfoDoubleMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolInfoDouble",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply> getSymbolInfoDoubleMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply> getSymbolInfoDoubleMethod;
    if ((getSymbolInfoDoubleMethod = MarketInfoGrpc.getSymbolInfoDoubleMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolInfoDoubleMethod = MarketInfoGrpc.getSymbolInfoDoubleMethod) == null) {
          MarketInfoGrpc.getSymbolInfoDoubleMethod = getSymbolInfoDoubleMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolInfoDouble"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolInfoDouble"))
              .build();
        }
      }
    }
    return getSymbolInfoDoubleMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply> getSymbolInfoIntegerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolInfoInteger",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply> getSymbolInfoIntegerMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply> getSymbolInfoIntegerMethod;
    if ((getSymbolInfoIntegerMethod = MarketInfoGrpc.getSymbolInfoIntegerMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolInfoIntegerMethod = MarketInfoGrpc.getSymbolInfoIntegerMethod) == null) {
          MarketInfoGrpc.getSymbolInfoIntegerMethod = getSymbolInfoIntegerMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolInfoInteger"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolInfoInteger"))
              .build();
        }
      }
    }
    return getSymbolInfoIntegerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply> getSymbolInfoStringMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolInfoString",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply> getSymbolInfoStringMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply> getSymbolInfoStringMethod;
    if ((getSymbolInfoStringMethod = MarketInfoGrpc.getSymbolInfoStringMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolInfoStringMethod = MarketInfoGrpc.getSymbolInfoStringMethod) == null) {
          MarketInfoGrpc.getSymbolInfoStringMethod = getSymbolInfoStringMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolInfoString"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolInfoString"))
              .build();
        }
      }
    }
    return getSymbolInfoStringMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply> getSymbolInfoMarginRateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolInfoMarginRate",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply> getSymbolInfoMarginRateMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply> getSymbolInfoMarginRateMethod;
    if ((getSymbolInfoMarginRateMethod = MarketInfoGrpc.getSymbolInfoMarginRateMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolInfoMarginRateMethod = MarketInfoGrpc.getSymbolInfoMarginRateMethod) == null) {
          MarketInfoGrpc.getSymbolInfoMarginRateMethod = getSymbolInfoMarginRateMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolInfoMarginRate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolInfoMarginRate"))
              .build();
        }
      }
    }
    return getSymbolInfoMarginRateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply> getSymbolInfoTickMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolInfoTick",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply> getSymbolInfoTickMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply> getSymbolInfoTickMethod;
    if ((getSymbolInfoTickMethod = MarketInfoGrpc.getSymbolInfoTickMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolInfoTickMethod = MarketInfoGrpc.getSymbolInfoTickMethod) == null) {
          MarketInfoGrpc.getSymbolInfoTickMethod = getSymbolInfoTickMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolInfoTick"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolInfoTick"))
              .build();
        }
      }
    }
    return getSymbolInfoTickMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply> getSymbolInfoSessionQuoteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolInfoSessionQuote",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply> getSymbolInfoSessionQuoteMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply> getSymbolInfoSessionQuoteMethod;
    if ((getSymbolInfoSessionQuoteMethod = MarketInfoGrpc.getSymbolInfoSessionQuoteMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolInfoSessionQuoteMethod = MarketInfoGrpc.getSymbolInfoSessionQuoteMethod) == null) {
          MarketInfoGrpc.getSymbolInfoSessionQuoteMethod = getSymbolInfoSessionQuoteMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolInfoSessionQuote"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolInfoSessionQuote"))
              .build();
        }
      }
    }
    return getSymbolInfoSessionQuoteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply> getSymbolInfoSessionTradeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolInfoSessionTrade",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply> getSymbolInfoSessionTradeMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply> getSymbolInfoSessionTradeMethod;
    if ((getSymbolInfoSessionTradeMethod = MarketInfoGrpc.getSymbolInfoSessionTradeMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolInfoSessionTradeMethod = MarketInfoGrpc.getSymbolInfoSessionTradeMethod) == null) {
          MarketInfoGrpc.getSymbolInfoSessionTradeMethod = getSymbolInfoSessionTradeMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolInfoSessionTrade"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolInfoSessionTrade"))
              .build();
        }
      }
    }
    return getSymbolInfoSessionTradeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest,
      mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply> getMarketBookAddMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MarketBookAdd",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest,
      mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply> getMarketBookAddMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest, mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply> getMarketBookAddMethod;
    if ((getMarketBookAddMethod = MarketInfoGrpc.getMarketBookAddMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getMarketBookAddMethod = MarketInfoGrpc.getMarketBookAddMethod) == null) {
          MarketInfoGrpc.getMarketBookAddMethod = getMarketBookAddMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest, mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MarketBookAdd"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("MarketBookAdd"))
              .build();
        }
      }
    }
    return getMarketBookAddMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest,
      mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply> getMarketBookReleaseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MarketBookRelease",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest,
      mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply> getMarketBookReleaseMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest, mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply> getMarketBookReleaseMethod;
    if ((getMarketBookReleaseMethod = MarketInfoGrpc.getMarketBookReleaseMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getMarketBookReleaseMethod = MarketInfoGrpc.getMarketBookReleaseMethod) == null) {
          MarketInfoGrpc.getMarketBookReleaseMethod = getMarketBookReleaseMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest, mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MarketBookRelease"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("MarketBookRelease"))
              .build();
        }
      }
    }
    return getMarketBookReleaseMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest,
      mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply> getMarketBookGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MarketBookGet",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest,
      mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply> getMarketBookGetMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest, mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply> getMarketBookGetMethod;
    if ((getMarketBookGetMethod = MarketInfoGrpc.getMarketBookGetMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getMarketBookGetMethod = MarketInfoGrpc.getMarketBookGetMethod) == null) {
          MarketInfoGrpc.getMarketBookGetMethod = getMarketBookGetMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest, mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MarketBookGet"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("MarketBookGet"))
              .build();
        }
      }
    }
    return getMarketBookGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply> getSymbolListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SymbolList",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest,
      mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply> getSymbolListMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply> getSymbolListMethod;
    if ((getSymbolListMethod = MarketInfoGrpc.getSymbolListMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getSymbolListMethod = MarketInfoGrpc.getSymbolListMethod) == null) {
          MarketInfoGrpc.getSymbolListMethod = getSymbolListMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest, mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SymbolList"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("SymbolList"))
              .build();
        }
      }
    }
    return getSymbolListMethod;
  }

  private static volatile io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest,
      mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply> getPriceHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PriceHistory",
      requestType = mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest.class,
      responseType = mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest,
      mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply> getPriceHistoryMethod() {
    io.grpc.MethodDescriptor<mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest, mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply> getPriceHistoryMethod;
    if ((getPriceHistoryMethod = MarketInfoGrpc.getPriceHistoryMethod) == null) {
      synchronized (MarketInfoGrpc.class) {
        if ((getPriceHistoryMethod = MarketInfoGrpc.getPriceHistoryMethod) == null) {
          MarketInfoGrpc.getPriceHistoryMethod = getPriceHistoryMethod =
              io.grpc.MethodDescriptor.<mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest, mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PriceHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply.getDefaultInstance()))
              .setSchemaDescriptor(new MarketInfoMethodDescriptorSupplier("PriceHistory"))
              .build();
        }
      }
    }
    return getPriceHistoryMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MarketInfoStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MarketInfoStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MarketInfoStub>() {
        @java.lang.Override
        public MarketInfoStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MarketInfoStub(channel, callOptions);
        }
      };
    return MarketInfoStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MarketInfoBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MarketInfoBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MarketInfoBlockingStub>() {
        @java.lang.Override
        public MarketInfoBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MarketInfoBlockingStub(channel, callOptions);
        }
      };
    return MarketInfoBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MarketInfoFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MarketInfoFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MarketInfoFutureStub>() {
        @java.lang.Override
        public MarketInfoFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MarketInfoFutureStub(channel, callOptions);
        }
      };
    return MarketInfoFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The MarketInfo service provides information about the current state of the market
   * </pre>
   */
  public static abstract class MarketInfoImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Returns the number of available (selected in Market Watch or all) symbols
     * https://www.mql5.com/en/docs/marketinformation/symbolstotal
     * </pre>
     */
    public void symbolsTotal(mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolsTotalMethod(), responseObserver);
    }

    /**
     * <pre>
     * Checks if a symbol with a specified name exists
     * https://www.mql5.com/en/docs/marketinformation/symbolexist
     * </pre>
     */
    public void symbolExist(mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolExistMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns the name of a symbol
     * https://www.mql5.com/en/docs/marketinformation/symbolname 
     * </pre>
     */
    public void symbolName(mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolNameMethod(), responseObserver);
    }

    /**
     * <pre>
     * Selects a symbol in the Market Watch window or removes a symbol from the window
     * https://www.mql5.com/en/docs/marketinformation/symbolselect
     * </pre>
     */
    public void symbolSelect(mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolSelectMethod(), responseObserver);
    }

    /**
     * <pre>
     * The function checks whether data of a selected symbol in the terminal are synchronized with data on the trade server.
     * https://www.mql5.com/en/docs/marketinformation/symbolissynchronized
     * </pre>
     */
    public void symbolIsSynchronized(mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolIsSynchronizedMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. Immediately returns the property value.
     * https://www.mql5.com/en/docs/marketinformation/symbolinfodouble
     * </pre>
     */
    public void symbolInfoDouble(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolInfoDoubleMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. 
     * https://www.mql5.com/en/docs/marketinformation/symbolinfointeger
     * </pre>
     */
    public void symbolInfoInteger(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolInfoIntegerMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. Immediately returns the property value
     * https://www.mql5.com/en/docs/marketinformation/symbolinfostring  
     * </pre>
     */
    public void symbolInfoString(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolInfoStringMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns the margin rates depending on the order type and direction
     * https://www.mql5.com/en/docs/marketinformation/symbolinfomarginrate
     * </pre>
     */
    public void symbolInfoMarginRate(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolInfoMarginRateMethod(), responseObserver);
    }

    /**
     * <pre>
     * The function returns current prices of a specified symbol in a variable of the MqlTick type
     * https://www.mql5.com/en/docs/marketinformation/symbolinfotick
     * </pre>
     */
    public void symbolInfoTick(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolInfoTickMethod(), responseObserver);
    }

    /**
     * <pre>
     * Allows receiving time of beginning and end of the specified quoting sessions for a specified symbol and day of week
     * https://www.mql5.com/en/docs/marketinformation/symbolinfosessionquote
     * </pre>
     */
    public void symbolInfoSessionQuote(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolInfoSessionQuoteMethod(), responseObserver);
    }

    /**
     * <pre>
     * Allows receiving time of beginning and end of the specified trading sessions for a specified symbol and day of week
     * https://www.mql5.com/en/docs/marketinformation/symbolinfosessiontrade
     * </pre>
     */
    public void symbolInfoSessionTrade(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolInfoSessionTradeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Provides opening of Depth of Market for a selected symbol, and subscribes for receiving notifications of the DOM changes
     * https://www.mql5.com/en/docs/marketinformation/marketbookadd
     * </pre>
     */
    public void marketBookAdd(mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMarketBookAddMethod(), responseObserver);
    }

    /**
     * <pre>
     * Provides closing of Depth of Market for a selected symbol, and cancels the subscription for receiving notifications of the DOM changes
     * https://www.mql5.com/en/docs/marketinformation/marketbookrelease
     * </pre>
     */
    public void marketBookRelease(mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMarketBookReleaseMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns a structure array MqlBookInfo containing records of the Depth of Market of a specified symbol
     * https://www.mql5.com/en/docs/marketinformation/marketbookget
     * </pre>
     */
    public void marketBookGet(mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getMarketBookGetMethod(), responseObserver);
    }

    /**
     * <pre>
     * Returns a collection of a broker symbols
     * </pre>
     */
    public void symbolList(mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSymbolListMethod(), responseObserver);
    }

    /**
     * <pre>
     * Historical bars for a symbol - what a chart is drawn from.
     * This API had no such call, so a client holding a terminal id could list symbols, read a live
     * price and trade, and still not draw a chart. Ticks are not candles.
     * </pre>
     */
    public void priceHistory(mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPriceHistoryMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSymbolsTotalMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply>(
                  this, METHODID_SYMBOLS_TOTAL)))
          .addMethod(
            getSymbolExistMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply>(
                  this, METHODID_SYMBOL_EXIST)))
          .addMethod(
            getSymbolNameMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply>(
                  this, METHODID_SYMBOL_NAME)))
          .addMethod(
            getSymbolSelectMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply>(
                  this, METHODID_SYMBOL_SELECT)))
          .addMethod(
            getSymbolIsSynchronizedMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply>(
                  this, METHODID_SYMBOL_IS_SYNCHRONIZED)))
          .addMethod(
            getSymbolInfoDoubleMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply>(
                  this, METHODID_SYMBOL_INFO_DOUBLE)))
          .addMethod(
            getSymbolInfoIntegerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply>(
                  this, METHODID_SYMBOL_INFO_INTEGER)))
          .addMethod(
            getSymbolInfoStringMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply>(
                  this, METHODID_SYMBOL_INFO_STRING)))
          .addMethod(
            getSymbolInfoMarginRateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply>(
                  this, METHODID_SYMBOL_INFO_MARGIN_RATE)))
          .addMethod(
            getSymbolInfoTickMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply>(
                  this, METHODID_SYMBOL_INFO_TICK)))
          .addMethod(
            getSymbolInfoSessionQuoteMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply>(
                  this, METHODID_SYMBOL_INFO_SESSION_QUOTE)))
          .addMethod(
            getSymbolInfoSessionTradeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply>(
                  this, METHODID_SYMBOL_INFO_SESSION_TRADE)))
          .addMethod(
            getMarketBookAddMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest,
                mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply>(
                  this, METHODID_MARKET_BOOK_ADD)))
          .addMethod(
            getMarketBookReleaseMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest,
                mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply>(
                  this, METHODID_MARKET_BOOK_RELEASE)))
          .addMethod(
            getMarketBookGetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest,
                mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply>(
                  this, METHODID_MARKET_BOOK_GET)))
          .addMethod(
            getSymbolListMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest,
                mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply>(
                  this, METHODID_SYMBOL_LIST)))
          .addMethod(
            getPriceHistoryMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest,
                mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply>(
                  this, METHODID_PRICE_HISTORY)))
          .build();
    }
  }

  /**
   * <pre>
   * The MarketInfo service provides information about the current state of the market
   * </pre>
   */
  public static final class MarketInfoStub extends io.grpc.stub.AbstractAsyncStub<MarketInfoStub> {
    private MarketInfoStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MarketInfoStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MarketInfoStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns the number of available (selected in Market Watch or all) symbols
     * https://www.mql5.com/en/docs/marketinformation/symbolstotal
     * </pre>
     */
    public void symbolsTotal(mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolsTotalMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Checks if a symbol with a specified name exists
     * https://www.mql5.com/en/docs/marketinformation/symbolexist
     * </pre>
     */
    public void symbolExist(mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolExistMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns the name of a symbol
     * https://www.mql5.com/en/docs/marketinformation/symbolname 
     * </pre>
     */
    public void symbolName(mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolNameMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Selects a symbol in the Market Watch window or removes a symbol from the window
     * https://www.mql5.com/en/docs/marketinformation/symbolselect
     * </pre>
     */
    public void symbolSelect(mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolSelectMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * The function checks whether data of a selected symbol in the terminal are synchronized with data on the trade server.
     * https://www.mql5.com/en/docs/marketinformation/symbolissynchronized
     * </pre>
     */
    public void symbolIsSynchronized(mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolIsSynchronizedMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. Immediately returns the property value.
     * https://www.mql5.com/en/docs/marketinformation/symbolinfodouble
     * </pre>
     */
    public void symbolInfoDouble(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolInfoDoubleMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. 
     * https://www.mql5.com/en/docs/marketinformation/symbolinfointeger
     * </pre>
     */
    public void symbolInfoInteger(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolInfoIntegerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. Immediately returns the property value
     * https://www.mql5.com/en/docs/marketinformation/symbolinfostring  
     * </pre>
     */
    public void symbolInfoString(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolInfoStringMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns the margin rates depending on the order type and direction
     * https://www.mql5.com/en/docs/marketinformation/symbolinfomarginrate
     * </pre>
     */
    public void symbolInfoMarginRate(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolInfoMarginRateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * The function returns current prices of a specified symbol in a variable of the MqlTick type
     * https://www.mql5.com/en/docs/marketinformation/symbolinfotick
     * </pre>
     */
    public void symbolInfoTick(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolInfoTickMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Allows receiving time of beginning and end of the specified quoting sessions for a specified symbol and day of week
     * https://www.mql5.com/en/docs/marketinformation/symbolinfosessionquote
     * </pre>
     */
    public void symbolInfoSessionQuote(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolInfoSessionQuoteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Allows receiving time of beginning and end of the specified trading sessions for a specified symbol and day of week
     * https://www.mql5.com/en/docs/marketinformation/symbolinfosessiontrade
     * </pre>
     */
    public void symbolInfoSessionTrade(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolInfoSessionTradeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Provides opening of Depth of Market for a selected symbol, and subscribes for receiving notifications of the DOM changes
     * https://www.mql5.com/en/docs/marketinformation/marketbookadd
     * </pre>
     */
    public void marketBookAdd(mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getMarketBookAddMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Provides closing of Depth of Market for a selected symbol, and cancels the subscription for receiving notifications of the DOM changes
     * https://www.mql5.com/en/docs/marketinformation/marketbookrelease
     * </pre>
     */
    public void marketBookRelease(mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getMarketBookReleaseMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns a structure array MqlBookInfo containing records of the Depth of Market of a specified symbol
     * https://www.mql5.com/en/docs/marketinformation/marketbookget
     * </pre>
     */
    public void marketBookGet(mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getMarketBookGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns a collection of a broker symbols
     * </pre>
     */
    public void symbolList(mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSymbolListMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Historical bars for a symbol - what a chart is drawn from.
     * This API had no such call, so a client holding a terminal id could list symbols, read a live
     * price and trade, and still not draw a chart. Ticks are not candles.
     * </pre>
     */
    public void priceHistory(mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest request,
        io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPriceHistoryMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The MarketInfo service provides information about the current state of the market
   * </pre>
   */
  public static final class MarketInfoBlockingStub extends io.grpc.stub.AbstractBlockingStub<MarketInfoBlockingStub> {
    private MarketInfoBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MarketInfoBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MarketInfoBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns the number of available (selected in Market Watch or all) symbols
     * https://www.mql5.com/en/docs/marketinformation/symbolstotal
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply symbolsTotal(mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolsTotalMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Checks if a symbol with a specified name exists
     * https://www.mql5.com/en/docs/marketinformation/symbolexist
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply symbolExist(mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolExistMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns the name of a symbol
     * https://www.mql5.com/en/docs/marketinformation/symbolname 
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply symbolName(mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolNameMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Selects a symbol in the Market Watch window or removes a symbol from the window
     * https://www.mql5.com/en/docs/marketinformation/symbolselect
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply symbolSelect(mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolSelectMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * The function checks whether data of a selected symbol in the terminal are synchronized with data on the trade server.
     * https://www.mql5.com/en/docs/marketinformation/symbolissynchronized
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply symbolIsSynchronized(mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolIsSynchronizedMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. Immediately returns the property value.
     * https://www.mql5.com/en/docs/marketinformation/symbolinfodouble
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply symbolInfoDouble(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolInfoDoubleMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. 
     * https://www.mql5.com/en/docs/marketinformation/symbolinfointeger
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply symbolInfoInteger(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolInfoIntegerMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. Immediately returns the property value
     * https://www.mql5.com/en/docs/marketinformation/symbolinfostring  
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply symbolInfoString(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolInfoStringMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns the margin rates depending on the order type and direction
     * https://www.mql5.com/en/docs/marketinformation/symbolinfomarginrate
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply symbolInfoMarginRate(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolInfoMarginRateMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * The function returns current prices of a specified symbol in a variable of the MqlTick type
     * https://www.mql5.com/en/docs/marketinformation/symbolinfotick
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply symbolInfoTick(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolInfoTickMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Allows receiving time of beginning and end of the specified quoting sessions for a specified symbol and day of week
     * https://www.mql5.com/en/docs/marketinformation/symbolinfosessionquote
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply symbolInfoSessionQuote(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolInfoSessionQuoteMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Allows receiving time of beginning and end of the specified trading sessions for a specified symbol and day of week
     * https://www.mql5.com/en/docs/marketinformation/symbolinfosessiontrade
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply symbolInfoSessionTrade(mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolInfoSessionTradeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Provides opening of Depth of Market for a selected symbol, and subscribes for receiving notifications of the DOM changes
     * https://www.mql5.com/en/docs/marketinformation/marketbookadd
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply marketBookAdd(mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getMarketBookAddMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Provides closing of Depth of Market for a selected symbol, and cancels the subscription for receiving notifications of the DOM changes
     * https://www.mql5.com/en/docs/marketinformation/marketbookrelease
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply marketBookRelease(mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getMarketBookReleaseMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns a structure array MqlBookInfo containing records of the Depth of Market of a specified symbol
     * https://www.mql5.com/en/docs/marketinformation/marketbookget
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply marketBookGet(mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getMarketBookGetMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns a collection of a broker symbols
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply symbolList(mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSymbolListMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Historical bars for a symbol - what a chart is drawn from.
     * This API had no such call, so a client holding a terminal id could list symbols, read a live
     * price and trade, and still not draw a chart. Ticks are not candles.
     * </pre>
     */
    public mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply priceHistory(mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPriceHistoryMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The MarketInfo service provides information about the current state of the market
   * </pre>
   */
  public static final class MarketInfoFutureStub extends io.grpc.stub.AbstractFutureStub<MarketInfoFutureStub> {
    private MarketInfoFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MarketInfoFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MarketInfoFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Returns the number of available (selected in Market Watch or all) symbols
     * https://www.mql5.com/en/docs/marketinformation/symbolstotal
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply> symbolsTotal(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolsTotalMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Checks if a symbol with a specified name exists
     * https://www.mql5.com/en/docs/marketinformation/symbolexist
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply> symbolExist(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolExistMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns the name of a symbol
     * https://www.mql5.com/en/docs/marketinformation/symbolname 
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply> symbolName(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolNameMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Selects a symbol in the Market Watch window or removes a symbol from the window
     * https://www.mql5.com/en/docs/marketinformation/symbolselect
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply> symbolSelect(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolSelectMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * The function checks whether data of a selected symbol in the terminal are synchronized with data on the trade server.
     * https://www.mql5.com/en/docs/marketinformation/symbolissynchronized
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply> symbolIsSynchronized(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolIsSynchronizedMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. Immediately returns the property value.
     * https://www.mql5.com/en/docs/marketinformation/symbolinfodouble
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply> symbolInfoDouble(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolInfoDoubleMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. 
     * https://www.mql5.com/en/docs/marketinformation/symbolinfointeger
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply> symbolInfoInteger(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolInfoIntegerMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns the corresponding property of a specified symbol. Immediately returns the property value
     * https://www.mql5.com/en/docs/marketinformation/symbolinfostring  
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply> symbolInfoString(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolInfoStringMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns the margin rates depending on the order type and direction
     * https://www.mql5.com/en/docs/marketinformation/symbolinfomarginrate
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply> symbolInfoMarginRate(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolInfoMarginRateMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * The function returns current prices of a specified symbol in a variable of the MqlTick type
     * https://www.mql5.com/en/docs/marketinformation/symbolinfotick
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply> symbolInfoTick(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolInfoTickMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Allows receiving time of beginning and end of the specified quoting sessions for a specified symbol and day of week
     * https://www.mql5.com/en/docs/marketinformation/symbolinfosessionquote
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply> symbolInfoSessionQuote(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolInfoSessionQuoteMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Allows receiving time of beginning and end of the specified trading sessions for a specified symbol and day of week
     * https://www.mql5.com/en/docs/marketinformation/symbolinfosessiontrade
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply> symbolInfoSessionTrade(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolInfoSessionTradeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Provides opening of Depth of Market for a selected symbol, and subscribes for receiving notifications of the DOM changes
     * https://www.mql5.com/en/docs/marketinformation/marketbookadd
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply> marketBookAdd(
        mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getMarketBookAddMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Provides closing of Depth of Market for a selected symbol, and cancels the subscription for receiving notifications of the DOM changes
     * https://www.mql5.com/en/docs/marketinformation/marketbookrelease
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply> marketBookRelease(
        mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getMarketBookReleaseMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns a structure array MqlBookInfo containing records of the Depth of Market of a specified symbol
     * https://www.mql5.com/en/docs/marketinformation/marketbookget
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply> marketBookGet(
        mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getMarketBookGetMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns a collection of a broker symbols
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply> symbolList(
        mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSymbolListMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Historical bars for a symbol - what a chart is drawn from.
     * This API had no such call, so a client holding a terminal id could list symbols, read a live
     * price and trade, and still not draw a chart. Ticks are not candles.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply> priceHistory(
        mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPriceHistoryMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SYMBOLS_TOTAL = 0;
  private static final int METHODID_SYMBOL_EXIST = 1;
  private static final int METHODID_SYMBOL_NAME = 2;
  private static final int METHODID_SYMBOL_SELECT = 3;
  private static final int METHODID_SYMBOL_IS_SYNCHRONIZED = 4;
  private static final int METHODID_SYMBOL_INFO_DOUBLE = 5;
  private static final int METHODID_SYMBOL_INFO_INTEGER = 6;
  private static final int METHODID_SYMBOL_INFO_STRING = 7;
  private static final int METHODID_SYMBOL_INFO_MARGIN_RATE = 8;
  private static final int METHODID_SYMBOL_INFO_TICK = 9;
  private static final int METHODID_SYMBOL_INFO_SESSION_QUOTE = 10;
  private static final int METHODID_SYMBOL_INFO_SESSION_TRADE = 11;
  private static final int METHODID_MARKET_BOOK_ADD = 12;
  private static final int METHODID_MARKET_BOOK_RELEASE = 13;
  private static final int METHODID_MARKET_BOOK_GET = 14;
  private static final int METHODID_SYMBOL_LIST = 15;
  private static final int METHODID_PRICE_HISTORY = 16;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MarketInfoImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MarketInfoImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SYMBOLS_TOTAL:
          serviceImpl.symbolsTotal((mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolsTotalReply>) responseObserver);
          break;
        case METHODID_SYMBOL_EXIST:
          serviceImpl.symbolExist((mt5_term_api.Mt5TermApiMarketInfo.SymbolExistRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolExistReply>) responseObserver);
          break;
        case METHODID_SYMBOL_NAME:
          serviceImpl.symbolName((mt5_term_api.Mt5TermApiMarketInfo.SymbolNameRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolNameReply>) responseObserver);
          break;
        case METHODID_SYMBOL_SELECT:
          serviceImpl.symbolSelect((mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolSelectReply>) responseObserver);
          break;
        case METHODID_SYMBOL_IS_SYNCHRONIZED:
          serviceImpl.symbolIsSynchronized((mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolIsSynchronizedReply>) responseObserver);
          break;
        case METHODID_SYMBOL_INFO_DOUBLE:
          serviceImpl.symbolInfoDouble((mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoDoubleReply>) responseObserver);
          break;
        case METHODID_SYMBOL_INFO_INTEGER:
          serviceImpl.symbolInfoInteger((mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoIntegerReply>) responseObserver);
          break;
        case METHODID_SYMBOL_INFO_STRING:
          serviceImpl.symbolInfoString((mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoStringReply>) responseObserver);
          break;
        case METHODID_SYMBOL_INFO_MARGIN_RATE:
          serviceImpl.symbolInfoMarginRate((mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoMarginRateReply>) responseObserver);
          break;
        case METHODID_SYMBOL_INFO_TICK:
          serviceImpl.symbolInfoTick((mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoTickRequestReply>) responseObserver);
          break;
        case METHODID_SYMBOL_INFO_SESSION_QUOTE:
          serviceImpl.symbolInfoSessionQuote((mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionQuoteReply>) responseObserver);
          break;
        case METHODID_SYMBOL_INFO_SESSION_TRADE:
          serviceImpl.symbolInfoSessionTrade((mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolInfoSessionTradeReply>) responseObserver);
          break;
        case METHODID_MARKET_BOOK_ADD:
          serviceImpl.marketBookAdd((mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.MarketBookAddReply>) responseObserver);
          break;
        case METHODID_MARKET_BOOK_RELEASE:
          serviceImpl.marketBookRelease((mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.MarketBookReleaseReply>) responseObserver);
          break;
        case METHODID_MARKET_BOOK_GET:
          serviceImpl.marketBookGet((mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.MarketBookGetReply>) responseObserver);
          break;
        case METHODID_SYMBOL_LIST:
          serviceImpl.symbolList((mt5_term_api.Mt5TermApiMarketInfo.SymbolListRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.SymbolListReply>) responseObserver);
          break;
        case METHODID_PRICE_HISTORY:
          serviceImpl.priceHistory((mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryRequest) request,
              (io.grpc.stub.StreamObserver<mt5_term_api.Mt5TermApiMarketInfo.PriceHistoryReply>) responseObserver);
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

  private static abstract class MarketInfoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MarketInfoBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return mt5_term_api.Mt5TermApiMarketInfo.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MarketInfo");
    }
  }

  private static final class MarketInfoFileDescriptorSupplier
      extends MarketInfoBaseDescriptorSupplier {
    MarketInfoFileDescriptorSupplier() {}
  }

  private static final class MarketInfoMethodDescriptorSupplier
      extends MarketInfoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MarketInfoMethodDescriptorSupplier(String methodName) {
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
      synchronized (MarketInfoGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MarketInfoFileDescriptorSupplier())
              .addMethod(getSymbolsTotalMethod())
              .addMethod(getSymbolExistMethod())
              .addMethod(getSymbolNameMethod())
              .addMethod(getSymbolSelectMethod())
              .addMethod(getSymbolIsSynchronizedMethod())
              .addMethod(getSymbolInfoDoubleMethod())
              .addMethod(getSymbolInfoIntegerMethod())
              .addMethod(getSymbolInfoStringMethod())
              .addMethod(getSymbolInfoMarginRateMethod())
              .addMethod(getSymbolInfoTickMethod())
              .addMethod(getSymbolInfoSessionQuoteMethod())
              .addMethod(getSymbolInfoSessionTradeMethod())
              .addMethod(getMarketBookAddMethod())
              .addMethod(getMarketBookReleaseMethod())
              .addMethod(getMarketBookGetMethod())
              .addMethod(getSymbolListMethod())
              .addMethod(getPriceHistoryMethod())
              .build();
        }
      }
    }
    return result;
  }
}
