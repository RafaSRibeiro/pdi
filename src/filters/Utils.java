package filters;

import java.util.Arrays;

public class Utils {

    public static final int VIZINHOS_3x3 = 1;
    public static final int VIZINHOS_CROSS = 2;
    public static final int VIZINHOS_X = 3;
    public static final int CHANNEL_R = 1;
    public static final int CHANNEL_G = 2;
    public static final int CHANNEL_B = 3;

    public static double median(Pixel[] pixels, int channel) {
        double v[] = new double[pixels.length];
        for(int i=0; i<pixels.length; i++) {
            if(channel == Utils.CHANNEL_R) {
                v[i] = pixels[i].r;
            }
            if(channel == Utils.CHANNEL_G) {
                v[i] = pixels[i].g;
            }
            if(channel == Utils.CHANNEL_B) {
                v[i] = pixels[i].b;
            }
        }
        Arrays.sort(v);
        return v[v.length/2];
    }
}
