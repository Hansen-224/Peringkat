class Tim {
    private String nama;
    private int poin;

    public Tim(String nama) {
        this.nama = nama;
        this.poin = 0;
    }

    public String getNama() {
        return nama;
    }

    public int getPoin() {
        return poin;
    }

    public void tambahPoin(int tambah) {
        poin += tambah;
    }
}
