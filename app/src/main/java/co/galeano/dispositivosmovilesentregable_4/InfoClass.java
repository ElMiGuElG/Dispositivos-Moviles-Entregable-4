package co.galeano.dispositivosmovilesentregable_4;

public class InfoClass {
    private String dataname;
    private String datadesc;
    private String datacate;
    private String dataimage;

    public String getDataname() {
        return dataname;
    }

    public String getDatadesc() {
        return datadesc;
    }

    public String getDatacate() {
        return datacate;
    }

    public String getDataimage() {
        return dataimage;
    }

    public InfoClass(String dataname, String datadesc, String datacate, String dataimage) {
        this.dataname = dataname;
        this.datadesc = datadesc;
        this.datacate = datacate;
        this.dataimage = dataimage;
    }
}
