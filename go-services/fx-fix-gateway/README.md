# fx-fix-gateway

FIX protocol gateway for institutional client connectivity. Supports FIX 4.2 and FIX 4.4 protocols.

**Port**: 8091

## Features

- FIX 4.2 / 4.4 protocol implementation
- Session management (Logon, Logout, Heartbeat, TestRequest)
- Order routing (NewOrderSingle, OrderCancelRequest, OrderCancelReplaceRequest)
- Execution reports (ExecutionReport messages)
- Market data streaming (MarketDataRequest, MarketDataSnapshotFullRefresh)
- Multiple simultaneous sessions

## FIX Messages Supported

### Trading
| MsgType | Name | Direction |
|---------|------|-----------|
| D | NewOrderSingle | Client → Gateway |
| F | OrderCancelRequest | Client → Gateway |
| G | OrderCancelReplaceRequest | Client → Gateway |
| 8 | ExecutionReport | Gateway → Client |

### Market Data
| MsgType | Name | Direction |
|---------|------|-----------|
| V | MarketDataRequest | Client → Gateway |
| W | MarketDataSnapshotFullRefresh | Gateway → Client |
| X | MarketDataIncrementalRefresh | Gateway → Client |

### Session
| MsgType | Name | Direction |
|---------|------|-----------|
| A | Logon | Bidirectional |
| 0 | Heartbeat | Bidirectional |
| 1 | TestRequest | Bidirectional |
| 5 | Logout | Bidirectional |

## Configuration

```yaml
app:
  name: fx-fix-gateway
  port: 8091
fix:
  sessions:
    - sender-comp-id: GLOBALFX
      target-comp-id: INSTITUTION_A
      host: 0.0.0.0
      port: 9876
      heartbeat-interval: 30
    - sender-comp-id: GLOBALFX
      target-comp-id: INSTITUTION_B
      host: 0.0.0.0
      port: 9877
      heartbeat-interval: 30
```

## Run

```bash
cd go-services/fx-fix-gateway
go mod tidy
go run cmd/server/main.go
```
