package enity;

public class ChiTietHD {
    private int ID;
    private int MaHD;
    private int MaSP;
    private int SoLuong;
    private int MaKM;

    public ChiTietHD() {
    }

    public ChiTietHD(int MaHD, int MaSP, int SoLuong, int MaKM) {
        this.MaHD = MaHD;
        this.MaSP = MaSP;
        this.SoLuong = SoLuong;
        this.MaKM = MaKM;
    }

    public ChiTietHD(int ID, int MaHD, int MaSP, int SoLuong, int MaKM) {
        this.ID = ID;
        this.MaHD = MaHD;
        this.MaSP = MaSP;
        this.SoLuong = SoLuong;
        this.MaKM = MaKM;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int MaHD) {
        this.MaHD = MaHD;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int MaSP) {
        this.MaSP = MaSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public int getMaKM() {
        return MaKM;
    }

    public void setMaKM(int MaKM) {
        this.MaKM = MaKM;
    }

    
}
