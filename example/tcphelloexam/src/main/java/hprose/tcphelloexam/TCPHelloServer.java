package hprose.tcphelloexam;

import hprose.common.HproseException;
import hprose.server.HproseTcpServer;
import hprose.server.ServiceContext;
import hprose.util.concurrent.Promise;
import java.io.IOException;
import java.net.URISyntaxException;

public class TCPHelloServer {
    public static Promise hello(String name, ServiceContext context) throws HproseException {
        context.clients.push("news", "this is a pushed message: " + name);
        return Promise.value("Hello " + name + "!");
    }
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        HproseTcpServer server = new HproseTcpServer("tcp://localhost:4321");
        server.setReactorThreads(2);

//        server.addFilter(new HproseFilter() {
//            public String getString(ByteBuffer buffer) {
//                Charset charset;
//                CharsetDecoder decoder;
//                CharBuffer charBuffer;
//                try
//                {
//                    charset = Charset.forName("UTF-8");
//                    decoder = charset.newDecoder();
//                    charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
//                    return charBuffer.toString();
//                }
//                catch (Exception ex)
//                {
//                    ex.printStackTrace();
//                    return "";
//                }
//            }
//            @Override
//            public ByteBuffer inputFilter(ByteBuffer istream, HproseContext context) {
//                System.out.println(getString(istream));
//                return istream;
//            }
//            @Override
//            public ByteBuffer outputFilter(ByteBuffer ostream, HproseContext context) {
//                System.out.println(getString(ostream));
//                return ostream;
//            }
//        });

        server.add("hello", TCPHelloServer.class);
        server.setDebugEnabled(true);
        server.publish("news");
//        server.setEnabledThreadPool(true);
//        ExecutorService pool = Executors.newFixedThreadPool(2);
//        server.setThreadPool(pool);
        server.start();
        System.out.println("START");
        System.in.read();
        server.stop();
        System.out.println("STOP");
        //pool.shutdown();
    }
}
