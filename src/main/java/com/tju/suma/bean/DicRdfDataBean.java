package com.tju.suma.bean;

import java.util.Objects;

public class DicRdfDataBean {
    private int Rs;
    private int Rp;
    private int Ro;
    private int Nsp;
    private int Np;
    private int Nop;

    public int getRs() {
        return Rs;
    }

    public void setRs(int rs) {
        Rs = rs;
    }

    public int getRp() {
        return Rp;
    }

    public void setRp(int rp) {
        Rp = rp;
    }

    public int getRo() {
        return Ro;
    }

    public void setRo(int ro) {
        Ro = ro;
    }

    public int getNsp() {
        return Nsp;
    }

    public void setNsp(int nsp) {
        Nsp = nsp;
    }

    public void setNsp(int nsp, int index) {
        if(nsp == -1){
            this.setNsp(-1);
        }
        else{
            DicRdfDataBean dataBean = DicRdfDataMap.getDataBean(nsp);
            Objects.requireNonNull(dataBean,"dataBean is null");
            this.setNsp(dataBean.getNsp());
            dataBean.setNsp(index);
        }
    }

    public void setNop(int nop, int index) {
        if(nop == -1){
            this.setNop(-1);
        }
        else{
            DicRdfDataBean dataBean = DicRdfDataMap.getDataBean(nop);
            Objects.requireNonNull(dataBean,"dataBean is null");
            this.setNop(dataBean.getNop());
            dataBean.setNop(index);
        }
    }

    public void setNp(int np) {
        Np = np;
    }

    public int getNop() {
        return Nop;
    }

    public void setNop(int nop) {
        Nop = nop;
    }

    @Override
    public String toString() {
        return "RdfDataBean{" +
                "Rs='" + Rs + '\'' +
                ", Rp='" + Rp + '\'' +
                ", Ro='" + Ro + '\'' +
                ", Nsp=" + Nsp +
                ", Np=" + Np +
                ", Npo=" + Nop +
                '}';
    }


}
