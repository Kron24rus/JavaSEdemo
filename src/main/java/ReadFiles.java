import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kron on 10.02.16.
 */
public class ReadFiles implements Runnable {



    private String[] paths;

    ReadFiles(String paths) {
        this.paths = paths.split(";");
    }

    @Override
    public void run() {
        int i = 0;
        while (i < paths.length && !Thread.interrupted()) {
            readFile(paths[i]);
            i++;
        }
        System.exit(0);
    }

    public void readFile(String path) {
        InputStream stream;
        try {
            if (path.startsWith("http")) {
                URL url = new URL(path);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.addRequestProperty("User-Agent", "Mozilla/5");
                con.connect();
                stream = con.getInputStream();
            } else {
                stream = new FileInputStream(path);
            }

            boolean isHTML = false;
            int simple = 0, complex = 0, tmp = 0;
            int i;
            while ((i = stream.read()) != -1) {
                char s = (char) i;
                if (s == '&' && !isHTML) {
                    isHTML = true; tmp = 1;
                } else if (isHTML) {
                    if (s == ';') {
                        complex++;
                        isHTML = false;
                        tmp = 0;
                    } else if (i == 32) {
                        isHTML = false;
                        simple += tmp;
                        tmp = 0;
                    } else tmp++;
                } else {
                    simple++;
                }
            }
            Main.result.add(String.format("%d : %.2f%%", complex, (double) complex / (double) simple * 100));
            stream.close();
        } catch (MalformedURLException e) {
            System.out.println("Protocol problems");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        } catch (IOException e) {
            System.out.println("Bad url path: " + path);
            e.printStackTrace();
        }
    }
}
