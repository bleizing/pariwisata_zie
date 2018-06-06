package bleizing.pariwisata;

public class Wisata {
    private int id;
    private String isi;
    private String foto;
    private String info;
    private String kontak;
    private Double lat;
    private Double lng;

    public Wisata(int id, String isi, String foto, String info, String kontak, Double lat, Double lng) {
        this.id = id;
        this.isi = isi;
        this.foto = foto;
        this.info = info;
        this.kontak = kontak;
        this.lat = lat;
        this.lng = lng;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getIsi() {
        return isi;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getLat() {
        return lat;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public Double getLng() {
        return lng;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getInfo() {
        return info;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getKontak() {
        return kontak;
    }
}
