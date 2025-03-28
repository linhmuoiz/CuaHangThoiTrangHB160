package dto;

public class SanPhamBanChayDTO {
    private int MaSP;
    private String TenSP;
    private int SoLuongBanRa;

    public SanPhamBanChayDTO() {
    }

    public SanPhamBanChayDTO(int MaSP, String TenSP, int SoLuongBanRa) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.SoLuongBanRa = SoLuongBanRa;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int MaSP) {
        this.MaSP = MaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public int getSoLuongBanRa() {
        return SoLuongBanRa;
    }

    public void setSoLuongBanRa(int SoLuongBanRa) {
        this.SoLuongBanRa = SoLuongBanRa;
    }
    
    
}