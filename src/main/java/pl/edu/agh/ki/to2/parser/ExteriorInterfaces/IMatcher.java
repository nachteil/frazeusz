package ExteriorInterfaces;

import java.net.URL;
import java.util.List;

/**
 * Created by Adam on 29.11.2015.
 */
public interface IMatcher {
    public void match(URL url, List<String> sentences);
    public void matchVer2(List<String> sentences);
}
