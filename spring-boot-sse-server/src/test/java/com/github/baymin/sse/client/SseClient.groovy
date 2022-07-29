package com.github.baymin.sse.client

/**
 * 参考以下代码实现
 * https://github.com/nicferrier/java-simple-sse-client/blob/master/src/main/java/uk/me/ferrier/sseclient/SSEClient.java
 */
class SseClient {

    static class SseEvent {
        public def data
        public def type
    }

    static interface EventListener {
        void message(SseEvent event);
    }

    URL target;
    EventListener listener;

    SseClient(URL destination, EventListener listener) {
        target = destination;
        this.listener = listener;
    }

    void readEvent(InputStream inputStream) throws IOException {
        String eventType = "message" // default according to spec
        StringBuilder payLoad = new StringBuilder()
        boolean readEvent = true
        while (readEvent) {
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String line = input.readLine()
            println("原始的响应：${line}")
            if (line == null) {
                continue
            }
            if (line.startsWith(":")) {
                continue
            }
            if (line.startsWith("data:")) {
                payLoad.append(line.substring(5))
                break
            }
            if (line.startsWith("event:")) {
                eventType = line.substring(6)
                break
            }
            if ("\n" == line || "\r\n" == line) {
                break
            }
        }

        def evt = new SseEvent()
        evt.type = eventType
        evt.data = payLoad.toString()
        listener.message(evt)
    }

    void connect() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) target.openConnection()
        InputStream inputStream = connection.getInputStream()
        while (true) {
            readEvent(inputStream)
        }
    }

}
