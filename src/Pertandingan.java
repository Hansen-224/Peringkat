class Pertandingan {
    private Tim timA;
    private Tim timB;
    private Tim pemenang;

    public Pertandingan(Tim timA, Tim timB) {
        this.timA = timA;
        this.timB = timB;
        this.pemenang = null;
    }

    public Tim getTimA() {
        return timA;
    }

    public Tim getTimB() {
        return timB;
    }

    public Tim getPemenang() {
        return pemenang;
    }

    public void hasil(int golA, int golB) {
        if (golA > golB) {
            pemenang = timA;
        } else if (golA < golB) {
            pemenang = timB;
        } else {
            // Handle draw (for simplicity, consider no extra time or penalty shootout)
            pemenang = null;
        }
    }
}
