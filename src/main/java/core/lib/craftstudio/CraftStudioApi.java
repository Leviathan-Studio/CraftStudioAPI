package lib.craftstudio;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lib.craftstudio.client.CSModelMesher;
import lib.craftstudio.client.json.CSJsonReader;
import lib.craftstudio.utils.Version;
import net.minecraft.util.ResourceLocation;

public class CraftStudioApi
{
    private static final Logger LOGGER         = LogManager.getLogger("CraftStudio");

    private static Version      versionChecker = new Version();

    public static void preInit() throws Exception {

        /* Start of Fix */
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {}

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {}

        } };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        /* End of the fix */

        URL url = new URL("https://leviathan-studio.com");
        URLConnection con = url.openConnection();
        Reader reader = new InputStreamReader(con.getInputStream());
        while (true) {
            int ch = reader.read();
            if (ch == -1)
                break;
        }

        CraftStudioApi.LOGGER.info("Validated Leviathan Studio's Website certificate");

        CraftStudioApi.getVersion().preInit();
    }

    public static void registerModel(ResourceLocation resourceIn, String modelNameIn) {
        CSJsonReader jsonReader = new CSJsonReader(resourceIn);
        CSModelMesher.models.put(modelNameIn, jsonReader.readModel());

    }

    public static Version getVersion() {
        return CraftStudioApi.versionChecker;
    }

    public static Logger getLogger() {
        return CraftStudioApi.LOGGER;
    }
}