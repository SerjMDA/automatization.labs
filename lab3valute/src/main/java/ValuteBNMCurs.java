import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ValCurs")
public class ValuteBNMCurs {

    @XStreamImplicit(itemFieldName = "Valute")
    private  List<ValuteBNM> cur = new ArrayList<>();

    public  List<ValuteBNM> getCur() {
        return cur;
    }

    public void setCur(List<ValuteBNM> bans) {
        this.cur = bans;
    }


}