# Spring kafka demo
Simple request-response demo.

## REST endpoints
* __POST__ ``/services/send-message`` - send message, fire and forget.
* __POST__ ``/services/send-message-and-get-response`` - send message, and wait for reply.

### message
```
{
  "message": "your-message-here"
}
``` 
