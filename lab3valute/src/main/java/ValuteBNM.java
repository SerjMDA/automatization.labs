import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("Valute")
public class ValuteBNM {

    private String NumCode;
    private String CharCode;
    private int Nominal;
    private String Name;
    private double Value;


    public String getNumCode() {
        return NumCode;
    }
    public void setNumCode(String NumCode) {
        this.NumCode = NumCode;
    }
    public String getCharCode() {
        return CharCode;
    }
    public void setCharCode(String charCode) {
        CharCode = charCode;
    }
    public int getNominal() {
        return Nominal;
    }
    public void setNominal(int nominal) {
        Nominal = nominal;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public double getValue() {
        return Value;
    }
    public void setValue(double value) {
        Value = value;
    }
    public ValuteBNM(String NumCode, String charCode, int nominal, String name,
                  double value) {
        super();
        this.NumCode = NumCode;
        CharCode = charCode;
        Nominal = nominal;
        Name = name;
        Value = value;
    }

    public ValuteBNM(){}

    @Override
    public String toString(){
        return NumCode + " " + CharCode + " " + Nominal + " " + Name + " " + Value;
    }
}