package hprose.example.filter.compress;

import hprose.client.HproseClient;
import java.io.IOException;
import java.net.URISyntaxException;

interface IEcho {
    int[] echo(int[] obj);
}
public class Client {
    public static void main(String[] args) throws URISyntaxException, IOException {
        HproseClient client = HproseClient.create("tcp://127.0.0.1:8083");
        client.addFilter(new SizeFilter("Non compressed"));
        client.addFilter(new CompressFilter());
        client.addFilter(new SizeFilter("Compressed"));
        IEcho h = client.useService(IEcho.class);
        int n = 100000;
        int[] value = new int[n];
        for (int i = 0; i < n; ++i) {
            value[i] = i;
        }
        System.out.println(h.echo(value).length);
    }
}
